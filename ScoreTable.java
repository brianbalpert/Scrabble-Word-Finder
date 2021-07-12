import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Keeps track of how many points letters are worth and assigns
 * scores to words based on those point. Can print scores for 
 * lists of words.
 */
public class ScoreTable {
    private Map<String,Integer> wordScores;
    /* Representation Invariant: wordScores
    * - Maps a word to its given score.
    * - String is a dictionary word at least 2 characters in length.
    * - Integer is a score >=2 
    * - Must be initiated as treeMap to preserve alphabetical ordering.
    */
    private mapCompare mapComparator;
    private ArrayList<Map.Entry<String,Integer>> mapEntries;
    /* Representation Invariant: mapEntries
    * - ArrayList of map entries of map wordScores. Used for sorting wordScores by score.
    */
    private static final Map<Character,Integer> SCORES;
    static {
        Map<Character, Integer> aMap = new HashMap<Character,Integer>();
        aMap.put('a',1);  aMap.put('d',2);  aMap.put('k',5);
        aMap.put('a',1);  aMap.put('g',2);  aMap.put('j',8);
        aMap.put('e',1);  aMap.put('b',3);  aMap.put('x',8);
        aMap.put('i',1);  aMap.put('c',3);  aMap.put('q',10);
        aMap.put('o',1);  aMap.put('m',3);  aMap.put('z',10);
        aMap.put('u',1);  aMap.put('p',3);
        aMap.put('l',1);  aMap.put('f',4);
        aMap.put('n',1);  aMap.put('h',4);
        aMap.put('s',1);  aMap.put('v',4);
        aMap.put('t',1);  aMap.put('w',4);
        aMap.put('r',1);  aMap.put('y',4);
        SCORES = Collections.unmodifiableMap(aMap);
    }

/**
 * Creates a ScoreTable object with empty initialized instance variables.
 */
    public ScoreTable(){
        wordScores = new TreeMap<String,Integer>();
        mapComparator = new mapCompare();
        mapEntries = new ArrayList<>();
    }

/**
 * Prints a list of words and their scores in order of score. example: 
 * > printScores("rex")
 * 10: rex
 * 9: ex
 * 2: re
 * 2: er
 * @param wordList a list of words
 */
    public void printScores(ArrayList<String> wordList) {
        scoreAllWords(wordList);
        mapEntries.clear();
        for(Map.Entry<String,Integer> entry : wordScores.entrySet()){
            mapEntries.add(entry);
        } 

        mapEntries.sort(mapComparator);

        for (Map.Entry<String, Integer> curr : mapEntries) {
            System.out.println(curr.getValue() + ": " + curr.getKey());
         }
    }

/**
 * Scores a list of words.
 * @param words an ArrayList of dictionary words.
 * @return A Map pairing a word to its score.
 */
    private Map<String, Integer> scoreAllWords(ArrayList<String> words){
        wordScores.clear();
        for(String w : words){
            wordScores.put(w,scoreWord(w));
        }
        return wordScores;
    } 

/**
 * Returns a word's score.
 * @param word a dictionary word
 * @return integer word score.
 */
    private static int scoreWord(String word){
        String lowerCase = word.toLowerCase();
        char[] chars = lowerCase.toCharArray();
        int score = 0;
        for(char c : chars){
            if(SCORES.containsKey(c)){
                score += SCORES.get(c);
            }
        }
        return score;
    }

/**
 * Private Class used to implement comparable for a Map Entries so entries can be sorted by value.
 */
    class mapCompare implements Comparator<Map.Entry<String, Integer>>{
        public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
            return e2.getValue() - e1.getValue();
        }
    }
}
