package ifs.ir;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: thomaskern
 * Date: Apr 16, 2010
 * Time: 1:53:31 PM
 */
public class NGramResult {
    private ArrayList<HashMap<String, Integer>> file_al;
    private HashMap<String, Integer> hm;

    public NGramResult(HashMap<String, Integer> hm, ArrayList<HashMap<String, Integer>> file_al) {
        this.hm = hm;
        this.file_al = file_al;
    }

    public HashMap<String, Integer> getCorpus() {
        return hm;
    }

    public ArrayList<HashMap<String, Integer>> getFiles() {
        return file_al;
    }
}
