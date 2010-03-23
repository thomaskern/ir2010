package ifs.ir.ngrams.io;

import ifs.ir.ngrams.CountedNGram;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * User: thomaskern
 * Date: Mar 23, 2010
 * Time: 8:18:39 PM
 */
public class ArffWriter {
    public void write(ArrayList<CountedNGram> cng, String filename) {
        ArffSaver saver = new ArffSaver();

        FastVector atts;
        Instances data;
        double[] vals;

        atts = new FastVector();


        atts.addElement(new Attribute("ngram", (FastVector) null));
        atts.addElement(new Attribute("count"));

        data = new Instances("NGram Index", atts, 0);


        for (CountedNGram c : cng) {
            vals = new double[data.numAttributes()];
            vals[0] = data.attribute(0).addStringValue(c.getString());
            vals[1] = c.getCount();
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
