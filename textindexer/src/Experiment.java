import ifs.ir.NGramResult;
import ifs.ir.io.ArffWriter;
import ifs.ir.io.Reader;

import java.io.File;
import java.io.IOException;

public class Experiment {

    public static void main(String[] files) {
        File f = new File("");

        String[] corpuse = new String[]{"20news-18828/", "banksearch-5classes-3000/"};

        deleteDirectory(f.getAbsoluteFile()+"/data/");

        for (String corpus : corpuse) {
            for (int i = 2; i < 4; i++) {
                double[][] arr = new double[][]{{0, 0}, {0.5, 70}, {1, 40}};

                for (double[] a : arr) {
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

    static public boolean deleteDirectory(String filepath) {
        File path = new File(filepath);
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
        }
        return (path.delete());
    }

    private static void read(File f, int i, double[] a, Reader r, boolean stemming, String corpus) throws IOException {
        r.setStemming(stemming);
        NGramResult cng = r.readFromDirectory(f.getAbsolutePath() + "/angabe/" + corpus, i);

        ArffWriter arff = new ArffWriter();

        new File(f.getAbsolutePath() + "/data/" + corpus + i).mkdirs();
        

        arff.write(cng, f.getAbsolutePath() + "/data/" + corpus + i + "/lb_" + a[0] + "_up_" + a[1] + "_" + (stemming ? "with" : "without") + "_stemming.arff");
    }
}
