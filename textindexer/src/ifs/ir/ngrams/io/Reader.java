package ifs.ir.ngrams.io;

import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.NGram;
import ifs.ir.ngrams.NGramImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Reader {

    public ArrayList<CountedNGram> readFromDirectory(String dir, int n) throws IOException {
        File f = new File(dir);

        ArrayList<CountedNGram> al = new ArrayList<CountedNGram>();
        for (String file : f.list()) {
            al = readFromFile(f.getAbsolutePath() + "/" + file, n, al);
        }

        return al;
    }

    public ArrayList<CountedNGram> readFromFile(String file, int n, ArrayList<CountedNGram> ng) throws IOException {
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        return read(stream, n, ng);
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
        BufferedInputStream bi = new BufferedInputStream(stream);

        int b;
        byte ba[] = new byte[n];
        ba[n - 1] = 42;
        int i = 0;
        while ((b = bi.read()) != -1) {
            if (b == 13 || b == 10 || b == 9)
                b = 32;
            i++;

            if (i > n && b != 32) {
                System.arraycopy(ba, 1, ba, 0, n - 1);
                ba[n - 1] = (byte) b;
                newNGram(count, ba);
            }
        }

        ArrayList<CountedNGram> order = new ArrayList<CountedNGram>(count.values());
        Collections.sort(order);
        return order;
    }

    protected void newNGram(HashMap<NGram, CountedNGram> count, byte[] ba) {
        NGramImpl ng = new NGramImpl(ba);

        CountedNGram cng = count.get(ng);

        if (cng != null)
            cng.inc();
        else
            count.put(ng, new CountedNGram(ng));
    }

}


