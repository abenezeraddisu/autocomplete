//import java.util.*;
//
//public class HashListAutocomplete implements Autocompletor {
//
//
//    private static final int MAX_PREFIX = 10;
//    private Map<String, List<Term>> myMap;
//    public Term[] myTerms;
//    private int mySize;
//
//    public HashListAutocomplete(String[] terms, double[] weights) {
//        if (terms == null || weights == null) {
//            throw new NullPointerException("One or more arguments null");
//        }
//        initialize(terms, weights);
//
//    }
//
//    @Override
//    public List<Term> topMatches(String prefix, int k) {
//        for (int i = 0; i < myTerms.length; i++) {
//            String s = myTerms[i].getWord().substring(0, MAX_PREFIX+1);
//            ArrayList<Term> holder = new ArrayList<>();
//            for (int j = 0; j < myTerms.length; j++) {
//                if (myTerms[j].getWord().substring(0, MAX_PREFIX+1).startsWith(s)) {
//                    holder.add(myTerms[j]);
//                }
//
//            }
//            myMap.put(s, holder);
//        }
//        Set keys = myMap.keySet();
//        for (Iterator i = keys.iterator(); i.hasNext(); ) {
//            String key = (String) i.next();
//            Collections.sort(myMap.get(key), Comparator.comparing(Term::getWeight).reversed());
//        }
//        prefix = prefix.substring((Math.max(prefix.length(), MAX_PREFIX)));
//        if (keys.contains(prefix)) {
//            List<Term> all = myMap.get(prefix);
//            List<Term> list = all.subList(0, Math.min(k, all.size()));
//            return list;
//        }
//        return null;
//    }
//
//    @Override
//    public void initialize(String[] terms, double[] weights) {
//        myTerms = new Term[terms.length];
//
//        for (int i = 0; i < terms.length; i++) {
//            myTerms[i] = new Term(terms[i], weights[i]);
//        }
//
//        Arrays.sort(myTerms);
//    }
//
//    @Override
//    public int sizeInBytes() {
//        if (mySize == 0) {
//
//            for (Term t : myTerms) {
//                mySize += BYTES_PER_DOUBLE +
//                        BYTES_PER_CHAR * t.getWord().length();
//            }
//            for (String s : myMap.keySet()) {
//                mySize += BYTES_PER_CHAR *s.length();
//            }
//            return mySize;
//
//        }
//        return  mySize;
//    }
//}
import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;
    private Term[] myTerms;

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (prefix.length() > MAX_PREFIX){ prefix = prefix.substring(0, MAX_PREFIX);}

        List<Term> all = myMap.get(prefix);

        if(all != null) {
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        return new ArrayList<Term>();
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<String, List<Term>>();

        for (int i = 0; i < terms.length; i++){
            if(terms[i].length() >= MAX_PREFIX) {
                for (int j = 0; j <= MAX_PREFIX; j++) {
                    myMap.putIfAbsent(terms[i].substring(0, j), new ArrayList<Term>());
                    myMap.get(terms[i].substring(0, j)).add(new Term(terms[i], weights[i]));
                }
            }
            if (terms[i].length() < MAX_PREFIX){
                for (int j = 0; j <= terms[i].length(); j++){
                    myMap.putIfAbsent(terms[i].substring(0, j), new ArrayList<Term>());
                    myMap.get(terms[i].substring(0, j)).add(new Term(terms[i], weights[i]));
                }
            }
        }

        for (String s: myMap.keySet()){
            Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
        for(String s: myMap.keySet()){
            mySize += s.length()*BYTES_PER_CHAR;
            List<Term> list = myMap.get(s);

            for(int i=0; i< list.size(); i++){
                Term t = list.get(i);
                mySize += BYTES_PER_DOUBLE + t.getWord().length()*BYTES_PER_CHAR;
            }
        }
        return mySize;
    }
}
