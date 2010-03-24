import ifs.ir.ngrams.CountedNGram;
import ifs.ir.ngrams.io.ArffWriter;
import ifs.ir.ngrams.io.Reader;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: thomaskern
 * Date: Mar 24, 2010
 * Time: 12:23:29 PM
 */
public class Runner {


    public ArrayList<CountedNGram> run_from_dir(String input_dir, String output, int n, boolean stemming, int lower, int upper) throws IOException {
        Reader r = new Reader(lower, upper);
        r.setStemming(stemming);

        ArrayList<CountedNGram> cng = r.readFromDirectory(input_dir, n);

        ArffWriter arff = new ArffWriter();
        arff.write(cng, output);

        return cng;
    }
}