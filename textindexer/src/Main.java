import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.io.ArffWriter;
import ifs.ir.ngrams.io.Reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] files) {

        File f = new File("");
//        Reader r = new Reader(10, 100);
//        r.setStemming(true);
//
//        try {
//            ArrayList<CountedNGram> cng = r.readFromFile(f.getAbsolutePath() + "/angabe/20news-18828/alt.atheism/51119", 3, null);
//            ArffWriter arff = new ArffWriter();
//            arff.write(cng, f.getAbsolutePath() + "/data/stem_test.arff");
//
//            r.setStemming(false);
//            cng = r.readFromFile(f.getAbsolutePath() + "/angabe/20news-18828/alt.atheism/51119", 3, null);
//            arff = new ArffWriter();
//            arff.write(cng, f.getAbsolutePath() + "/data/stem_test2.arff");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        for (int i = 1; i < 6; i++) {
            int[][] arr = new int[][]{{0, 0}, {10, 20}, {10, 150}};

            for (int[] a : arr) {
                Reader r = new Reader(a[0], a[1]);
                try {
// ohne stemming
                    read(f, i, a, r, false);
// mit stemming
                    read(f, i, a, r, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void read(File f, int i, int[] a, Reader r, boolean stemming) throws IOException {
        r.setStemming(stemming);
        ArrayList<CountedNGram> cng = r.readFromDirectory(f.getAbsolutePath() + "/angabe/20news-18828/alt.atheism/", i);

        ArffWriter arff = new ArffWriter();
        arff.write(cng, f.getAbsolutePath() + "/data/" + i + "/lb_" + a[0] + "_up_" + a[1] + "_" + (stemming ? "with" : "without") + "_stemming.arff");
    }
}
