import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A dictionary of all anagram sets. Note: the processing is case-sensitive; so
 * if the dictionary has all lower case words, you will likely want any string
 * you test to have all lower case letters too, and likewise if the dictionary
 * words are all upper case.
 */
public class AnagramDictionary {
   private Map<String, ArrayList<String>> map;
      /* Representation Invariant: map
      * - Maps the words in ArrayList to the MultiSet of letters that comprise them.
      * - Map will contiain only strings and array lists of strings with 2 characters or more. 
      * - Strings will only contain upper and lower case english letters. no numbers or punctuation.
      * - Each key string will have its characters in alphabetical order.
      * - Each string in each array list will be a word from the dictionary loaded from the constructor.
      * - Each key string and value arraylist entry will have the same characters. 
      */

   /**
    * Create an anagram dictionary from the list of words given in the file
    * indicated by fileName.
    * 
    * @param fileName the name of the file to read from
    * @throws FileNotFoundException      if the file is not found
    * @throws IllegalDictionaryException if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException, IllegalDictionaryException {
      Scanner dictionary = new Scanner(new File(fileName));
      map = new HashMap<String, ArrayList<String>>();

      while (dictionary.hasNext()) {
         String word = dictionary.next();
         String letterSet = sort(word);
         if (!map.containsKey(letterSet)) {
            map.put(letterSet, new ArrayList<String>());
         }
         else if (map.get(letterSet).contains(word)) {
            throw new IllegalDictionaryException();
         }
         map.get(letterSet).add(word);
      }
   }

   /**
    * Get all anagrams of the given string. This method is case-sensitive. E.g.
    * "CARE" and "race" would not be recognized as anagrams.
    * 
    * @param s string to process
    * @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String s) {
      String word = sort(s);
      if (map.containsKey(word)) {
         return map.get(word);
      } else {
         return new ArrayList<String>();
      }
   }

   /**
    * Sorts a string's characters according to their inherent integer value.
    * 
    * @param word is any valid string
    * @return a string of all the characters in word sorted
    */
   private String sort(String word) {
      char[] chars = word.toCharArray();
      Arrays.sort(chars);
      return String.copyValueOf(chars);
   }
}
