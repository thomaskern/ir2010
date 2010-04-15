package ifs.ir.ngrams.io;

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
    public void write(HashMap<String,Integer> cng, String filename) {
        ArffSaver saver = new ArffSaver();

        FastVector atts;
        Instances data;
        double[] vals;

        atts = new FastVector();

        atts.addElement(new Attribute("ngram", (FastVector) null));
        atts.addElement(new Attribute("count"));

        data = new Instances("NGram Index", atts, 0);


        List keys = new ArrayList(cng.keySet());

        final Map<String, Integer> langForComp = cng;
		Collections.sort(keys,
			new Comparator(){
				public int compare(Object left, Object right){
					String leftKey = (String)left;
					String rightKey = (String)right;

					Integer leftValue = langForComp.get(leftKey);
					Integer rightValue = langForComp.get(rightKey);
					return rightValue.compareTo(leftValue);
				}
			});


        for (Object key : keys) {
            vals = new double[data.numAttributes()];

            String k = (String)key;
            Integer i = cng.get(k);
            vals[0] = data.attribute(0).addStringValue(k);
            vals[1] = i;
            data.add(new Instance(1.0, vals));


        }

        try {
            saver.setInstances(data);
            saver.setFile(new File(filename));

            saver.setDestination(new File(filename));   // **not** necessary in 3.5.4 and later
            saver.writeBatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
