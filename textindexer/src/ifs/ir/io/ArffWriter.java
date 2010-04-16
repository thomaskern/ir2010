package ifs.ir.io;

import ifs.ir.NGramResult;
import ifs.ir.ParsedFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: thomaskern
 * Date: Mar 23, 2010
 * Time: 8:18:39 PM
 */
public class ArffWriter {
    private BufferedWriter bw;

    public void write(NGramResult cng, String filename) {
        System.out.println("TEST: " + cng.getCorpus().size());

        try {
            bw = new BufferedWriter(new FileWriter(filename));
            wl("@relation 'NGram Index'\n");

            add_attributes(cng.getCorpus());
            add_ngrams(cng);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void add_attributes(HashMap<String, Integer> cng) throws IOException {
        for (String name : cng.keySet()) {
            wl("@attribute ngram_" + name + " NUMERICAL");
        }

        wl("@attribute class String");
    }

    private void wl(Object s) throws IOException {
        bw.write(s + "\n");
        bw.flush();
    }

    private int sum_ngrams_up(HashMap<String, Integer> al) {
        int ret = 0;

        for (Integer ng : al.values()) {
            ret += ng;
        }
        return ret;
    }

    private void add_ngrams(NGramResult cng) throws IOException {
        int size = sum_ngrams_up(cng.getCorpus());

        wl("\n\n@Data\n\n");
//        int i = 0;
        for (ParsedFile file : cng.getFiles()) {


            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : cng.getCorpus().entrySet()) {
                sb.append(file.getData().get(entry.getKey()) == null ? "0" : file.getData().get(entry.getKey()) * 100.00 / size).append(",");
            }


            sb.append(file.getKlass());
            wl(sb.toString());
//            System.out.println("FILE: "+i);
//            i++;
        }
    }

//    private void w(Object s) throws IOException {
//        bw.write(s.toString());
//        bw.flush();
//    }
}
