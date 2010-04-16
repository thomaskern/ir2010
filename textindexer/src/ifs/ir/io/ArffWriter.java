package ifs.ir.io;

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
    public void write(HashMap<String, Integer> cng, String filename) {
        System.out.println("TEST: " + cng.size());
        ArffSaver saver = new ArffSaver();
        FastVector atts = new FastVector();

        atts.addElement(new Attribute("ngram", (FastVector) null));
        atts.addElement(new Attribute("count"));

        Instances data = new Instances("NGram Index", atts, 0);

        List<String> keys = new ArrayList(cng.keySet());

        sort(cng, keys);
        add_ngrams(cng, data, keys);

        write(filename, saver, data);
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

    private void add_ngrams(HashMap<String, Integer> cng, Instances data, List<String> keys) {
        double[] vals;
        for (String key : keys) {
            vals = new double[data.numAttributes()];

            Integer i = cng.get(key);
            vals[0] = data.attribute(0).addStringValue(key);
            vals[1] = i;
            data.add(new Instance(1.0, vals));
        }
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
