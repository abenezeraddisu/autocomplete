import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 * <p>
 * Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     *
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     *
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
        return new PrefixComparator(prefix);
    }

    @Override
    public int compare(Term v, Term w) {
        int yes_sir = Math.min(myPrefixSize, Math.min(v.getWord().length(), w.getWord().length()));
        boolean pp =  yes_sir == myPrefixSize;
        boolean vv = yes_sir == v.getWord().length();
        boolean ww = yes_sir == w.getWord().length();
        boolean tr = v.getWord().length() == w.getWord().length();

        if(pp){
            for (int i = 0; i < yes_sir; i++) {
                if (v.getWord().charAt(i) > w.getWord().charAt(i)) {
                    return 1;
                } else if (v.getWord().charAt(i) < w.getWord().charAt(i)) {
                    return -1;
                }
            }
            return 0;
        }
        if(vv){
            for (int i = 0; i < yes_sir; i++) {
                if (v.getWord().charAt(i) > w.getWord().charAt(i)) {
                    return 1;
                } else if (v.getWord().charAt(i) < w.getWord().charAt(i)) {
                    return -1;
                }
            }
            if(tr){return 0;}
            else{return -1;}
        }
        if(ww){
            for (int i = 0; i < yes_sir; i++) {
                if (v.getWord().charAt(i) > w.getWord().charAt(i)) {
                    return 1;
                } else if (v.getWord().charAt(i) < w.getWord().charAt(i)) {
                    return -1;
                }
            }
            if(tr){return 0;}
            else{return 1;}
        }
        return 0;
    }
}



