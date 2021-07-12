import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Prompts user for a scrabble rack and returns a list of words that rack can
 * make, sorted by score. Uses sowpods dictionary by default, but user can
 * specify alternate dictionary to use in command line argument.
 */
public class WordFinder {
/**
 * Main Method runs the user 
 */
    public static void main(String[] args) {
        String filepath;
        if (args.length == 0) {
            filepath = "sowpods.txt";
        } else {
            filepath = args[0];
        }

        AnagramDictionary dic;
        try {
            dic = new AnagramDictionary(filepath);
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary Not Found. Exiting Program.");
            return;
        } catch (IllegalDictionaryException e) {
            System.out.println("Dictionary is not valid. Exiting Program.");
            return;
        }

        Rack rack = new Rack();
        ScoreTable scoreTable = new ScoreTable();
        Scanner in = new Scanner(System.in);
        ArrayList<String> anagrams = new ArrayList<String>();
        ArrayList<String> subsets;
        ArrayList<String> subsetAnagrams;

        System.out.print("Type . to quit\nRack? ");
        while (in.hasNext()) {
            String word = in.next();
            if (word.compareTo(".") == 0) {
                return;
            }

            subsets = rack.getSubsets(word);

            for (String subset : subsets) {
                subsetAnagrams = dic.getAnagramsOf(subset);
                for (String anagram : subsetAnagrams) {
                    if (!anagrams.contains(anagram)) {
                        anagrams.add(anagram);
                    }
                }
            }

            System.out.println("We can make " + anagrams.size() + " words from \"" + word + "\"");
            if (anagrams.size() > 0) {
                System.out.println("All of the words with their scores (sorted by score):");
                scoreTable.printScores(anagrams);
            }
            System.out.print("Rack? ");
            anagrams.clear();
        }
    }
}
