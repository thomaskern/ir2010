package ifs.ir.ngrams.io;

import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.NGram;
import ifs.ir.ngrams.NGramImpl;
import weka.core.stemmers.LovinsStemmer;

import java.io.*;
import java.util.*;

public class Reader {
    private int upper_bound;
    private int lower_bound;
    private boolean stemming;

    public Reader() {
        create(0, -1);
    }

    public Reader(int lower_bound, int upper_bound) {
        create(lower_bound, upper_bound);
    }

    private void create(int lower_bound, int upper_bound) {
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
    }

    public ArrayList<CountedNGram> readFromDirectory(String dir, int n) throws IOException {
        File f = new File(dir);

        ArrayList<CountedNGram> al = new ArrayList<CountedNGram>();
        for (String file : f.list()) {
            al = readFromFile(f.getAbsolutePath() + "/" + file, n, al);
        }
        return filter(al);
    }

    private ArrayList<CountedNGram> filter(ArrayList<CountedNGram> al) {
        if (upper_bound > 0) {
            for (Iterator<CountedNGram> it = al.iterator(); it.hasNext();) {
                CountedNGram ng = it.next();
                if (ng.getCount() < lower_bound || ng.getCount() > upper_bound)
                    it.remove();
            }
        }
        return al;
    }

    public ArrayList<CountedNGram> readFromFile(String file, int n, ArrayList<CountedNGram> ng) throws IOException {
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        ArrayList<CountedNGram> list = read(stream, n, ng);
        return filter(list);
    }

    /**
     * Parses InputStream into a weighted list of (byte-)NGrams.
     *
     * @param n
     */
    private ArrayList<CountedNGram> read(InputStream stream, int n, ArrayList<CountedNGram> ng)
            throws IOException {

        HashMap<NGram, CountedNGram> count = new HashMap<NGram, CountedNGram>();
        if (ng != null) {
            for (CountedNGram cng : ng)
                count.put(cng.getNGram(), cng);
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
        
        ArrayList<CountedNGram> order = new ArrayList<CountedNGram>(count.values());
        Collections.sort(order);
        return order;
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

    private int process(int n, HashMap<NGram, CountedNGram> count, int b, byte[] ba, int i) {
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

    protected void newNGram(HashMap<NGram, CountedNGram> count, byte[] ba) {
        NGramImpl ng = new NGramImpl(ba);

        CountedNGram cng = count.get(ng);

        if (cng != null)
            cng.inc();
        else
            count.put(ng, new CountedNGram(ng));
    }

    public void setStemming(boolean stemming) {
        this.stemming = stemming;
    }
}