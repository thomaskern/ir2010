package ifs.ir.io;

import ifs.ir.NGramResult;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * User: thomaskern
 * Date: Mar 23, 2010
 * Time: 8:18:39 PM
 */
public class ArffWriter {
    public void write(NGramResult cng, String filename) {
        System.out.println("TEST: " + cng.getCorpus().size());
        ArffSaver saver = new ArffSaver();
        FastVector atts = new FastVector();

        add_attributes(cng.getCorpus(), atts);
        Instances data = new Instances("NGram Index", atts, 0);

//        List<String> keys = new ArrayList(cng.keySet());

//        sort(cng, keys);
        add_ngrams(cng, data);

        write(filename, saver, data);
    }

    private void add_attributes(HashMap<String, Integer> cng, FastVector atts) {
        for (String name : cng.keySet()) {
            atts.addElement(new Attribute("ngram_" + name));
        }

        atts.addElement(new Attribute("class"));
    }

    private void write(String filename, ArffSaver saver, Instances data) {
        try {
            saver.setInstances(data);
            saver.setFile(new File(filename));

            saver.setDestination(new File(filename));   // **not** necessary in 3.5.4 and later
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void add_ngrams(NGramResult cng, Instances data) {
        double[] vals;
        int size = cng.getCorpus().size();

        for (HashMap<String, Integer> file : cng.getFiles()) {
            vals = new double[data.numAttributes()];

            int i = 0;
            for (Map.Entry<String, Integer> entry : cng.getCorpus().entrySet()) {
                vals[i] = file.get(entry.getKey()) == null ? 0 : file.get(entry.getKey()) * 100.00 / size;
//                if (vals[i] > 0 && file.get(entry.getKey()) > 5)
//                    System.out.println("Val: " + file.get(entry.getKey()));
//                    System.out.println("OUT: "+vals[i] + " :: "+file.get(entry.getKey()));
                i++;
            }

            vals[vals.length - 1] = 1;
            data.add(new Instance(1.0, vals));


        }
//        for (String key : cng.getCorpus()) {
//            vals = new double[data.numAttributes()];
//
//            Integer i = cng.get(key);
//            vals[0] = data.attribute(0).addStringValue(key);
//            vals[1] = i;
//            data.add(new Instance(1.0, vals));
//        }
    }

    private void sort(HashMap<String, Integer> cng, List keys) {
        final Map<String, Integer> langForComp = cng;
        Collections.sort(keys,
                new Comparator() {
                    public int compare(Object left, Object right) {
                        String leftKey = (String) left;
                        String rightKey = (String) right;

                        Integer leftValue = langForComp.get(leftKey);
                        Integer rightValue = langForComp.get(rightKey);
                        return rightValue.compareTo(leftValue);
                    }
                });
    }
}
