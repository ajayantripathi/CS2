package edu.caltech.cs2.project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CaesarCipherSolver {
    /**
     * Rotates every character of every string in the List sentence by amount many characters.
     * Note that this method does not return a new List. It should modify the original copy of
     * the List instead. You may find it useful to call your rot String method (from CaesarCipher)
     * when writing this one. This method assumes that every string in sentence is only made up of
     * upper-case alphabetic characters.
     *
     * For example:
     *  rot(["A", "HELLO"], 1) modifies the list to hold ["B", "IFMMP"]
     *
     * @param sentence the List whose strings should be rotated by amount many characters
     * @param amount the amount to rotate each character by
     */
    public static void rot(List<String> sentence, int amount) {
        for (int i = 0; i < sentence.size(); i++){
            sentence.set(i, CaesarCipher.rot(sentence.get(i), amount));
        }
    }

    /**
     * Creates an List out of the input string, s, where each entry in the output
     * is a single word from s. This method assumes s is a bunch of words separated by single
     * spaces. This method does not need to worry about double spaces,
     * spaces at the beginning or end, or other punctuation.
     *
     * For example:
     *  splitBySpaces("THE CAT IN THE HAT") returns ["THE", "CAT", "IN", "THE", "HAT"]
     *
     * @param s the string to split
     * @return the List of words created
     */
    public static List<String> splitBySpaces(String s) {
        List<String> l = new ArrayList<>();
        String word = "";
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ' '){
                l.add(word);
                word = "";
            } else {
                word += Character.toString(s.charAt(i));
            }
        }
        l.add(word);
        return l;
    }

    /**
     * Returns a string that has all the words in the List concatenated with a single space between each one.
     *
     * For example:
     *  putTogetherWithSpaces(["THE", "CAT", "IN", "THE", "HAT"]) returns "THE CAT IN THE HAT"
     *
     * @param words the List of words to concatenate
     * @return a string with all the words from the input List separated by spaces
     */
    public static String putTogetherWithSpaces(List<String> words) {
        String line = "";
        for (String word : words){
            line += word + " ";
        }
        return line.substring(0, line.length()-1);
    }

    /**
     * Returns the number of strings in sentence that are also in dictionary.
     *
     * For example:
     *  howManyWordsIn(["THE", "CAT", "IN", "THE", "ZZZ", "PRQ"], ["CAT", "COOKIE", "IN", "THE"]) returns 4
     *
     * @param sentence the List of words to check in the dictionary
     * @param dictionary the dictionary of words to check
     * @return the number of words in sentence that are also in dictionary
     */
    public static int howManyWordsIn(List<String> sentence, List<String> dictionary) {
        int count = 0;
        for (String word : sentence){
            for (String check : dictionary) {
                if (word.equals(check)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * main() should allow the user to decrypt a previously encrypted Caesar Cipher.
     * It should use the console to get the sentence to decrypt, loop through all the
     * possible rotations, and print out all likely translations. You may find it
     * useful to call your rot, splitBySpaces, putTogetherWithSpaces, and
     * howManyWordsIn methods when writing this one.
     */
    public static void main(String[] args) {
        List<String> dictionary = getDictionary();
        Scanner s = new Scanner(System.in);
        System.out.print("Type a sentence to decrypt: ");
        String line = s.nextLine();
        List<String> words = CaesarCipherSolver.splitBySpaces(line);
        for (int i = 0;  i < 26; i++){
            if (CaesarCipherSolver.howManyWordsIn(words, dictionary) > words.size()/2){
                System.out.println(CaesarCipherSolver.putTogetherWithSpaces(words));
            }
            CaesarCipherSolver.rot(words, 1);
        }
    }






    /**
     *
     *
     * You do not need to edit below this point.
     * These methods are provided to deal with the dictionary file!
     *
     *
     **/
    public static List<String> getDictionary() {
        List<String> dictionary = new ArrayList<>();
        try (Scanner s = getDictionaryScanner()) {
            while (s.hasNextLine()) {
                dictionary.add(s.nextLine());
            }
        }
        return dictionary;
    }

    public static Scanner getDictionaryScanner() {
        try {
            return new Scanner(new File("dictionary.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to open dictionary");
        }
    }
}