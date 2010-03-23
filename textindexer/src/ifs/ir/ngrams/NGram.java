package ifs.ir.ngrams;

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


    /**
     * Compare a ngram to a bunch of bytes
     */
    public boolean equals(byte[] bytes, int start, int length);

    /**
     * Hand out a special representation of yourself
     */
    public NGramImpl getNGramImpl();
}