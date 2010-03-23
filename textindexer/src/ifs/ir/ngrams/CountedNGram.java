package ifs.ir.ngrams;

public class CountedNGram
        implements Comparable<CountedNGram>, NGram {
    protected int count = 1;
    protected NGram gram;

    public CountedNGram(NGram ng) {
        gram = ng;
    }

    public CountedNGram(byte[] ba) {
        gram = new NGramImpl(ba);
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

    public int compareTo(CountedNGram el) {
        return el.getCount() - getCount();
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

    public String getString() {

        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < ((NGramImpl) getNGram()).getBytes().length; i++) {
            sb.append((char) ((NGramImpl) getNGram()).getBytes()[i]);
        }

        return sb.toString();
    }
}
