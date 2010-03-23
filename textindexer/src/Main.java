import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.io.ArffWriter;
import ifs.ir.ngrams.io.Reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] files) {
        Reader r = new Reader();
        try {
            File f = new File("");

//            ArrayList<CountedNGram> cng = r.readFromFile(f.getAbsolutePath() + "/angabe/20news-18828/alt.atheism/51119", 5, null);
            ArrayList<CountedNGram> cng = r.readFromDirectory(f.getAbsolutePath() + "/angabe/20news-18828/alt.atheism/", 3);

            ArffWriter arff = new ArffWriter();
            arff.write(cng, f.getAbsolutePath() + "/data/all.arff");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
