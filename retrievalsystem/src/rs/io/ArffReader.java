/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rs.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.data.Index;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 *
 * @author Christian
 */
public class ArffReader {

    private String filename;
    private File file;


    public ArffReader(String filename){
        this.filename = filename;
    }

    private void loadFile(){
        if(filename != null){
            file = new File(filename);
        }
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getFilename(){
        return this.filename;
    }

    public Index readArff(){
        ArffLoader loader = new ArffLoader();
        Instances instances = null;


        loadFile();
        try {
            loader.setFile(file);
            instances = loader.getDataSet();
            Index retindex = new Index(instances);
            return retindex;
        } catch (IOException ex) {
            Logger.getLogger(ArffReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
