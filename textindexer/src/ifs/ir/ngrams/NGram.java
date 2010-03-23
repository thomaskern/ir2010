package ifs.ir.ngrams;

import com.sun.tools.javac.util.List;

/**
 * User: thomaskern
 * Date: Mar 22, 2010
 * Time: 8:02:25 PM
 */
public interface NGram {
    /**
     * Returns the size of an ngram in bytes.
     */
    public int getSize();

    /**
     * Return a single byte of the ifs.ir.ngrams.NGram.
     *
     * @throws ArrayIndexOutOfBoundException (implicitly)
     */
    public int getByte(int pos);


}
