import java.io.IOException;

/**
 * User: thomaskern
 * Date: Apr 13, 2010
 * Time: 11:05:37 AM
 */
public class Test {

    public static void main(String[] files) throws IOException {
        Runner r = new Runner();
        r.run_from_dir("/Users/thomaskern/Documents/uni/current/ifs/ir/ir2010/textindexer/angabe/20news-18828", "test4.arff", 2, false, 0.3, 30);
//        r.run_from_dir("/Users/thomaskern/Documents/uni/current/ifs/ir/ir2010/textindexer/angabe/banksearch-5classes-3000","test2.arff",3,false,0.1,0.40);
    }

}
