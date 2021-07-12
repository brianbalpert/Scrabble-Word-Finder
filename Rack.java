import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Rack of Scrabble tiles
 */

public class Rack {
   private ArrayList<String> subsets;
   /* Representation Invariant: subsets
   * - All the subsets of a given rack.
   */
   private String unique;
   /* Representation Invariant: unique
   * - All unique characters of the current rack. Contains no Duplicate Characters.
   */
   private int[] mult;
   /* Representation Invariant: mult
   * - The Multiplicity of characters in the current rack.
   * - mult.length == unique.length()
   * - mult[0] corrisponds with unique.charAt(0) and so on.
   */

   /**
    * returns an ArrayList of every subset of at leats 2 characters that can be
    * made from the parameter string. 
    * 
    * @param word is any valid String.
    * @return ArrayList of Strings at least 2 characters long that can be made from
    *         word's letters. returns an empyt ArrayList if no subsets can be
    *         created.
    * @author Brian Alpert
    */
   public ArrayList<String> getSubsets(String word) {
      unique = getUniqueLetters(word);
      mult = getMultiplicity(word);
      subsets = allSubsets(unique, mult, 0);

      for (int i = 0; i < subsets.size(); i++) {
         if (subsets.get(i).length() < 2) {
            subsets.remove(i);
            i--;
         }
      }
      return subsets;
   }

   /**
    * Finds all unique characters in the parameter String and returns them in
    * alphabetical order. This function is case sensitve and will return capital
    * letters first.
    * 
    * @param word Any string
    * @return A string of all unique characters in word in alphabetical order.
    * @author Brian Alpert
    */
   private String getUniqueLetters(String word) {
      if (word.length() == 0) {
         return "";
      }
      char[] chars = word.toCharArray();
      char[] unique = new char[word.length()];
      int j = 0;
      Arrays.sort(chars);

      for (int i = 0; i < chars.length - 1; i++) {
         if (chars[i] != chars[i + 1]) {
            unique[j] = chars[i];
            j++;
         }
      }
      unique[j] = chars[chars.length - 1];
      return String.copyValueOf(unique).trim();
   }

   /**
    * Finds how many times each unique letter appears in a string. Each letter's
    * multiplicity.
    * 
    * @param word Any string
    * @return The multiplicity of each unique letter, listed in alphabetical order.
    * @author Brian Alpert
    */
   private int[] getMultiplicity(String word) {
      int[] mult = new int[unique.length()];
      for (int i = 0; i < unique.length(); i++) {
         for (int j = 0; j < word.length(); j++) {
            if (unique.charAt(i) == word.charAt(j)) {
               mult[i] += 1;
            }
         }
      }
      return mult;
   }

   /**
    * Finds all subsets of the multiset starting at position k in unique and mult.
    * unique and mult describe a multiset such that mult[i] is the multiplicity of
    * the char unique.charAt(i). PRE: mult.length must be at least as big as
    * unique.length() 0 <= k <= unique.length()
    * 
    * @param unique a string of unique letters
    * @param mult   the multiplicity of each letter from unique.
    * @param k      the smallest index of unique and mult to consider.
    * @return all subsets of the indicated multiset. Unlike the multiset in the
    *         parameters, each subset is represented as a String that can have
    *         repeated characters in it.
    * @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();

      if (k == unique.length()) { // multiset is empty
         allCombos.add("");
         return allCombos;
      }

      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k + 1);

      // prepend all possible numbers of the first char (i.e., the one at position k)
      // to the front of each string in restCombos. Suppose that char is 'a'...

      String firstPart = ""; // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {
         for (int i = 0; i < restCombos.size(); i++) { // for each of the subsets
                                                       // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));
         }
         firstPart += unique.charAt(k); // append another instance of 'a' to the first part
      }

      return allCombos;
   }
}
