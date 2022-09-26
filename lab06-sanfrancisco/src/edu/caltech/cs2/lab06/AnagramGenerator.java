package edu.caltech.cs2.lab06;

import java.util.ArrayList;
import java.util.List;

public class AnagramGenerator {

    private static void helper (List<String> accum, List<String> dictionary, LetterBag letterBag) {
        for (String word : dictionary) {
            LetterBag subtract = letterBag.subtract(new LetterBag(word));
            if (subtract == null) {
                continue;
            }
            if (subtract.isEmpty()) {
                accum.add(word);
                System.out.println(accum);
                accum.remove(word);
            }
            else {
                accum.add(word);
                helper(accum, dictionary, subtract);
                accum.remove(word);
            }
        }
    }

    public static void printPhrases(String phrase, List<String> dictionary) {
        if (phrase.length() == 0 || phrase.equals(" ")) {
            System.out.println("[]");
            return;
        }
        List<String> accum = new ArrayList<>();
        helper(accum, dictionary, new LetterBag(phrase));
    }

    public static void printWords(String word, List<String> dictionary) {
        LetterBag letterBag = new LetterBag(word);
        for (String words : dictionary) {
            LetterBag subtract = letterBag.subtract(new LetterBag(words));
            if (subtract == null) {
                continue;
            }
            if (subtract.isEmpty()) {
                System.out.println(words);
            }
        }
    }
}
