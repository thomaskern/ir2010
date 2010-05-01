/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.data.Index;
import rs.io.ArffReader;
import weka.core.Instance;

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
            Iterator<Index> it= null;

            for(String name : aNames){
                it = lindices.iterator();
                Index tmp;
                while(it.hasNext()){
                    tmp = it.next();
                    processQuery(tmp,name);
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
    }

    private static void processIndex() {
    }

    private static void printUsage() {
    }

    private void calculateDistance(Instance from, Instance to, List<DistanceItem> distanceList) {
    }
}
