package ifs.ir;

import sun.security.x509.CRLExtensions;

import java.util.HashMap;

/**
 * User: thomaskern
 * Date: Apr 16, 2010
 * Time: 2:39:48 PM
 */
public class ParsedFile {
    private String klass;
    private HashMap<String, Integer> data;

    public ParsedFile(String klass, HashMap<String, Integer> data) {
        this.klass = klass;
        this.data = data;
    }

    public String getKlass() {
        return klass;
    }

    public HashMap<String, Integer> getData() {
        return data;
    }
}
