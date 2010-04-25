package ifs.ir;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: thomaskern
 * Date: Apr 16, 2010
 * Time: 1:53:31 PM
 */
public class NGramResult {
    private ArrayList<ParsedFile> file_al;
    private HashMap<String, Integer> hm;

    public NGramResult(HashMap<String, Integer> hm, ArrayList<ParsedFile> file_al) {
        this.hm = hm;
        this.file_al = file_al;
    }

    public HashMap<String, Integer> getCorpus() {
        return hm;
    }

    public ArrayList<ParsedFile> getFiles() {
        return file_al;
    }
}
