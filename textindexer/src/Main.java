import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.io.Reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] files) {
        Reader r = new Reader();
        try {
            File f = new File("");
            ArrayList<CountedNGram> cng = r.readFromDirectory(f.getAbsolutePath() + "/angabe/20news-18828/alt.atheism/", 5);

            for (CountedNGram countedNGram : cng) {
                System.out.println(countedNGram);
            }

            System.out.println("Out: " + cng.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
