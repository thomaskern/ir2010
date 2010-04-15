package ifs.ir.ngrams.io;

import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.NGramImpl;
import weka.core.stemmers.LovinsStemmer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Reader {
    private double upper_bound;
    private double lower_bound;
    private boolean stemming;
    private ArrayList<String> files;

    public Reader() {
        create(0, -1);
    }

    public Reader(double lower_bound, double upper_bound) {
        create(lower_bound, upper_bound);
    }

    private void create(double lower_bound, double upper_bound) {
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
    }

    public HashMap<String, Integer> readFromDirectory(String dir, int n) throws IOException {
        HashMap<String, Integer> al = new HashMap<String, Integer>();
        files = new ArrayList<String>();
        search_for_files(dir);


        int i = 0;
        for (String file : files) {
            i++;
            al = readFromFile(file, n, al);
            if(i > 300)
                break;
        }
        return al;
    }

    private void search_for_files(String path) {
        File f = new File(path);

        for (File file : f.listFiles()) {
            if (file.isDirectory())
                search_for_files(file.getPath());
            else
                files.add(file.getPath());
        }
    }

    private ArrayList<CountedNGram> filter(ArrayList<CountedNGram> al) {
        int total = sum_ngrams_up(al);

        for (Iterator<CountedNGram> it = al.iterator(); it.hasNext();) {
            CountedNGram ng = it.next();

            if (ng.getCount() * 100.0 / total < lower_bound || (upper_bound > 0 && ng.getCount() * 100.0 / total >= upper_bound))
                it.remove();
        }
        return al;
    }

    private int sum_ngrams_up(ArrayList<CountedNGram> al) {
        int ret = 0;

        for (CountedNGram ng : al) {
            ret += ng.getCount();
        }
        return ret;
    }

    private HashMap<String, Integer> readFromFile(String file, int n, HashMap<String, Integer> ng) throws IOException {
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        return read(stream, n, ng);
    }

    private HashMap<String, Integer> read(InputStream stream, int n, HashMap<String, Integer> ng)
            throws IOException {

//        HashMap<NGram, CountedNGram> count = new HashMap<NGram, CountedNGram>();
        HashMap<String, Integer> count = new HashMap<String, Integer>();

        if (ng != null) {
            count = ng;
//            for (CountedNGram cng : ng)
//                count.put(cng.getNGram(), cng);
        }

        byte ba[] = new byte[n];
        ba[n - 1] = 42;
        int i = 0;

        if (stemming) {
            StringBuilder sb = read_file(stream);
            StringTokenizer st = new StringTokenizer(sb.toString());

            for (byte b : stem_and_convert_to_bytes(sb, st))
                i = process(n, count, b, ba, i);
        } else {
            BufferedInputStream bi = new BufferedInputStream(stream);

            int b;
            while ((b = bi.read()) != -1) {
                i = process(n, count, b, ba, i);
            }
        }

        return count;
    }

    private byte[] stem_and_convert_to_bytes(StringBuilder sb, StringTokenizer st) {
        byte[] bytes = new byte[sb.toString().length()];
        int counter = 0;

        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();
            LovinsStemmer stemmer = new LovinsStemmer();

            String tmp2 = stemmer.stem(tmp);
            for (int z = 0; z < tmp2.length(); z++) {
                bytes[counter] = (byte) tmp2.charAt(z);
                counter++;
            }
        }
        return bytes;
    }

    private StringBuilder read_file(InputStream stream) throws IOException {
        InputStreamReader converter = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(converter);

        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        return sb;
    }

    private int process(int n, HashMap<String, Integer> count, int b, byte[] ba, int i) {
        if (b == 13 || b == 10 || b == 9)
            b = 32;
        i++;

        if (i > n && b != 32) {
            System.arraycopy(ba, 1, ba, 0, n - 1);
            ba[n - 1] = (byte) b;
            newNGram(count, ba);
        }
        return i;
    }

    protected void newNGram(HashMap<String, Integer> count, byte[] ba) {
        NGramImpl ng = new NGramImpl(ba);
        Integer cng = count.get(ng.toString());

        if (cng != null){
            count.put(ng.toString(), cng + 1);
        }
        else
            count.put(ng.toString(), 1);
    }

    public void setStemming(boolean stemming) {
        this.stemming = stemming;
    }
}