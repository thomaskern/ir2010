import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: thomaskern
 * Date: Mar 24, 2010
 * Time: 12:23:39 PM
 */
public class Main {

    public static void main(String[] files) throws IOException {

        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);

        System.out.println("Enter input directory (absolute path): ");
        String input_directory = in.readLine();

        System.out.println("Enter arff file location: ");
        String arff = in.readLine();

        System.out.println("Should stemming be activated (true|false)?: ");
        boolean stemming = Boolean.parseBoolean(in.readLine());

        System.out.println("Enter n: ");
        int n = Integer.parseInt(in.readLine());

        System.out.println("Enter upper threshold bound (0 means deactivated): ");
        double upper = Double.parseDouble(in.readLine());

        System.out.println("Enter lower threshold bound (-1 means deactivated): ");

        double lower = Double.parseDouble(in.readLine());

        Runner r = new Runner();
        r.run_from_dir(input_directory, arff, n, stemming, lower, upper);
    }
}
