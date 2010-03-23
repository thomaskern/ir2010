package ifs.ir.ngrams;

public class CountedNGram
        // extends NGramImpl
        implements Comparable, NGram {
    protected int count = 1;
    protected NGram gram;

    public CountedNGram(NGram ng) {
        gram = ng;
    }

    public CountedNGram(byte[] ba, int start, int len) {
        gram = NGramImpl.newNGram(ba, start, len);
    }

    public int hashCode() {
        return gram.hashCode();
    }

    public int getCount() {
        return count;
    }

    public void inc() {
        count++;
    }

    public NGram getNGram() {
        return gram;
    }

    public int compareTo(Object e1) {
        return ((CountedNGram) e1).getCount() - getCount();
    }

    public boolean equals(Object e1) {
        if (e1 instanceof CountedNGram)
            return getNGram().equals(((CountedNGram) e1).getNGram());
        else if (e1 instanceof NGram)
            return e1.equals(getNGram());
        return false;
    }

    public String toString() {
        return getNGram().toString() + "[" + getCount() + "]";
    }

    public int getSize() {
        return getNGram().getSize();
    }

    /**
     * Return a single byte of the NGram.
     *
     * @throws ArrayIndexOutOfBoundException (implicitly)
     */
    public int getByte(int pos) {
        return getNGram().getByte(pos);
    }

    /**
     * Compare a ngram to a bunch of bytes
     */
    public boolean equals(byte[] bytes, int start, int length) {
        return getNGram().equals(bytes, start, length);
    }

    /**
     * Hand out a special representation of yourself
     */
    public NGramImpl getNGramImpl() {
        return getNGram().getNGramImpl();
    }
}
