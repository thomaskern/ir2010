package ifs.ir.ngrams;

/**
 * Class to model a concrete and simple NGram.
 * <p/>
 * To make it slightly more interestion (and efficient),
 * those NGrams follow a Flyweight pattern! I.e.
 * of each different NGram there will only be one
 * instance in the System. This is a bit technical,
 * but one can safely ignore this and just
 * deliberately call newNGram().
 * </P>
 */
public class NGramImpl
        implements NGram {
    protected byte[] bytes;
    protected int size;

    public NGramImpl(byte[] bytes) {
        this.size = bytes.length;
        this.bytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    /**
     * Return the size of the ngram.
     */
    public int getSize() {
        return size;
    }

    /**
     * Return a single byte of the NGram.
     *
     * @throws ArrayIndexOutOfBoundException ...
     */
    public int getByte(int pos) {
        return bytes[pos];
    }

    public boolean equals(Object o) {
        if (!(o instanceof NGramImpl))
            return false;

        NGramImpl ng = (NGramImpl) o;

        if (size != ng.getSize())
            return false;
        else {
            for (int i = 0; i < size; i++)
                if (this.bytes[i] != ng.getByte(i))
                    return false;
        }
        return true;
    }

    /**
     * Override the hashCode by s.th. that allows to hash NGrams
     * against tiny byte sequences.
     */
    public int hashCode() {
        return code(bytes, 0, bytes.length);
    }

    /**
     * Encode a byte sequence.
     */
    public static int code(byte[] bytes, int start, int length) {
        int h = 0;
        for (int i = 0; i < length; i++)
            h = appendCode(h, bytes[i + start]);
        return h;
    }

    /**
     * scrambler for hashcodes...
     */
    public static int appendCode(int h, byte b) {
        return 0x50501005 * h + 0x0AAA0AAA + b;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("{");
        for (int i = 0; i < size; i++) {
            sb.append((char) (bytes[i]));
        }
        sb.append("}");
        return sb.toString();
    }
}

