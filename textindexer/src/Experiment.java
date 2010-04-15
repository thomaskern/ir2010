import ifs.ir.ngrams.io.ArffWriter;
import ifs.ir.ngrams.io.Reader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Experiment {

    public static void main(String[] files) {
        File f = new File("");

        String[] corpuse = new String[]{"20news-18828/", "banksearch-5classes-3000/"};

        for (String corpus : corpuse) {
            for (int i = 2; i < 5; i++) {
                int[][] arr = new int[][]{{0, 0}, {10, 20}, {10, 150}};

                for (int[] a : arr) {
                    Reader r = new Reader(a[0], a[1]);
                    try {
// ohne stemming
                        read(f, i, a, r, false, corpus);
// mit stemming
                        read(f, i, a, r, true, corpus);
                        System.out.println(i + " done");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    private static void read(File f, int i, int[] a, Reader r, boolean stemming, String corpus) throws IOException {
        r.setStemming(stemming);
        HashMap<String, Integer> cng = r.readFromDirectory(f.getAbsolutePath() + "/angabe/" + corpus, i);

        ArffWriter arff = new ArffWriter();
        arff.write(cng, f.getAbsolutePath() + "/data/" + corpus + "/" + i + "/lb_" + a[0] + "_up_" + a[1] + "_" + (stemming ? "with" : "without") + "_stemming.arff");
    }
}
