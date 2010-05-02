package rs;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.data.Index;
import rs.io.ArffReader;
import weka.core.Instance;
import weka.core.Instances;
import java.util.Enumeration;
import rs.data.Distance;
import weka.core.Attribute;

/**
 *
 * @author Christian
 */
public class Main {

    private static CmdLineParser clp;
    private static String sIndices;
    private static String sNames;
    private static Integer iKnum;
    private static LinkedList<Index> lindices;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        clp = new CmdLineParser();

        CmdLineParser.Option indices = clp.addStringOption('I', "indices");
        CmdLineParser.Option names = clp.addStringOption('n', "names");
        CmdLineParser.Option knum = clp.addIntegerOption('k', "number");
        try {
            clp.parse(args);

            sIndices = (String) clp.getOptionValue(indices, "");
            sNames = (String) clp.getOptionValue(names, "");
            iKnum = (Integer) clp.getOptionValue(knum, 1);

            /*process indices*/
            ArffReader arff = new ArffReader("");

            StringTokenizer st = new StringTokenizer(sIndices, ",");
            String[] aIndices = new String[st.countTokens()];
            for (int i = 0; i < aIndices.length; i++) {
                aIndices[i] = st.nextToken();
                arff.setFilename(aIndices[i]);
                lindices.add(arff.readArff());
            }


            st = new StringTokenizer(sNames, ",");
            String[] aNames = new String[st.countTokens()];
            for (int i = 0; i < aNames.length; i++) {
                aNames[i] = st.nextToken();
            }
            Iterator<Index> it = null;

            for (String name : aNames) {
                it = lindices.iterator();
                Index tmp;
                while (it.hasNext()) {
                    tmp = it.next();
                    processQuery(tmp, name);
                }
            }



        } catch (IllegalOptionValueException ex) {
            printUsage();
        } catch (UnknownOptionException ex) {
            printUsage();
        }


        // TODO code application logic here
    }

    private static void processQuery(Index index, String docname) {
        Enumeration<Instance> einstances;
        String[] splitName;
        String category;
        String name;
        
    
        Instance tmp = null;

        splitName = docname.split("/");

        if (splitName.length >= 2) {

            category = splitName[0];
            name = splitName[1];

            Instances instances = index.getInstances();
            einstances = instances.enumerateInstances();



            while (einstances.hasMoreElements()) {
                tmp = einstances.nextElement();
                if ((tmp.attribute(0).equals(name)) && (tmp.attribute(1).equals(category))) {
                    kNearestDocs(tmp,index,iKnum);
                }
            }

        }

    }

    /*finds k nearest documents and prints them out together with some statistics */
    private static LinkedList<Distance> kNearestDocs(Instance from, Index index, Integer k) {
        Enumeration<Instance> einstances;
        Instance tmp;
        Instances instances = index.getInstances();
        einstances = instances.enumerateInstances();
        LinkedList<Distance> distances = new LinkedList<Distance>();
        LinkedList<Distance> knearest = new LinkedList<Distance>();

        

        einstances = instances.enumerateInstances();
        while (einstances.hasMoreElements()) {
            tmp = einstances.nextElement();
            if(!(from.equals(tmp))){
                Distance dist = new Distance(from, tmp, calculateDistance(from,tmp));
                distances.add(dist);
            }
        }

        Collections.sort(distances);

        for(int i = 0;i < k; i++){
            knearest.add(distances.get(i));
        }

        return knearest;

    }

    private static void output(){

    }

    
    private static void printUsage() {
    }

    private static double calculateDistance(Instance from, Instance to) {
         double distancePart = 0;

        // check which datatape
        Enumeration<Attribute> attlist = from.enumerateAttributes();
        int index = 0;
        int foundindex = 0;
        while (attlist.hasMoreElements()) {
            Attribute current = attlist.nextElement();
            if (current.isNumeric()) {
                // got a numeric attribute so its ok

                distancePart += Math.pow(from.value(index) - to.value(index), 2);
                foundindex++;
            }
            if (current.isNominal()) {
                double num = 0;
                Enumeration<Object> enumlist = current.enumerateValues();
                while (enumlist.hasMoreElements()) {
                    enumlist.nextElement();
                    num += 1;
                }
                
                distancePart += Math.pow(from.value(index) / num - to.value(index) / num, 2);

                foundindex++;
            }

            index++;
        }
        distancePart = Math.sqrt(distancePart);
       
        return distancePart;
    }
     
}
