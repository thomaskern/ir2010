import ifs.ir.NGramResult;
import ifs.ir.io.ArffWriter;
import ifs.ir.io.Reader;

import java.io.IOException;
import java.util.HashMap;

/**
 * User: thomaskern
 * Date: Mar 24, 2010
 * Time: 12:23:29 PM
 */
public class Runner {


    public NGramResult run_from_dir(String input_dir, String output, int n, boolean stemming, double lower, double upper) throws IOException {
        Reader r = new Reader(lower, upper);
        r.setStemming(stemming);

        long time = System.currentTimeMillis();
        NGramResult cng = r.readFromDirectory(input_dir, n);
//        System.out.println("Time: "+(System.currentTimeMillis() - time));

        ArffWriter arff = new ArffWriter();
        arff.write(cng, output);

        return cng;
    }
}
