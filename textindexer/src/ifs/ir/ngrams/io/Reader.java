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

        ArrayList<CountedNGram> list = new ArrayList<CountedNGram>();
        for (String file : f.list()) {
            System.out.println(file);
            list = readFromFile(f.getAbsolutePath() + "/" + file, n);
            System.out.println("List: " + list.size());
        }

        return list;
    }

    public ArrayList<CountedNGram> readFromFile(String file, int n) throws IOException {
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        return read(stream, n);
    }

    public ArrayList<CountedNGram> read(InputStream stream, int n)
            throws IOException {
        return read(stream, false, n);
    }

    /**
     * Parses InputStream into a weighted list of (byte-)NGrams.
     *
     * @param cache determines, weather created ngrams are cached. false is faster
     * @param n
     */
    public ArrayList<CountedNGram> read(InputStream stream, boolean cache, int n)
            throws IOException {
        // XXX to get the last performance kick a high performance
        // HashMap replacement could be dropped in here (e.g. gnu.trove stuff)
        HashMap<NGram, CountedNGram> count = new HashMap<NGram, CountedNGram>(1000);
        BufferedInputStream bi = new BufferedInputStream(stream);
        int b;
        byte ba[] = new byte[5];
        ba[4] = 42;
        int i = 0;
        while ((b = bi.read()) != -1) {
            // XXX ???
            if (b == 13 || b == 10 || b == 9)
                b = 32;
            i++;
            if (b != 32 || ba[3] != 32) {

                ba[0] = ba[1];
                ba[1] = ba[2];
                ba[2] = ba[3];
                ba[3] = ba[4];
                ba[4] = (byte) b;


                newNGram(count, ba, 0, n, cache);

//                newNGram(count, ba, 4, 1, cache);

//                for (int z = 0; z < n; z++) {
//                    if (i > z + 1)
//                        newNGram(count, ba, 3, 2, cache);
//                }
//                if (i > 1)
//                    newNGram(count, ba, 3, 2, cache);
//                if (i > 2)
//                    newNGram(count, ba, 2, 3, cache);
//                if (i > 3)
//                    newNGram(count, ba, 1, 4, cache);
//                if (i > 4)
//                    newNGram(count, ba, 0, 5, cache);
            }
        }
        ArrayList<CountedNGram> order = new ArrayList<CountedNGram>(count.values());
        Collections.sort(order);
        return order;
    }

    protected void newNGram(HashMap<NGram, CountedNGram> count, byte[] ba, int start, int len, boolean cache) {
        NGram ng = NGramImpl.newNGram(ba, start, len, cache);
        CountedNGram cng = count.get(ng);
        if (cng != null)
            cng.inc();
        else
            count.put(ng, new CountedNGram(ng));
    }

}


