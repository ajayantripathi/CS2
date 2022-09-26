package edu.caltech.cs2.project02.guessers;

import edu.caltech.cs2.project02.interfaces.IHangmanGuesser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AIHangmanGuesser implements IHangmanGuesser {

  private static String DICTIONARY = "data/scrabble.txt";

  @Override
  public char getGuess(String pattern, Set<Character> guesses) throws FileNotFoundException {
    File s = new File(DICTIONARY);
    Scanner r = new Scanner(s);
    ArrayList<String> words = new ArrayList<>();
    while (r.hasNextLine()){
      String word = r.nextLine();
      StringBuilder newWord = new StringBuilder();
      for (int i = 0; i < word.length(); i++){
        if (guesses.contains(word.charAt(i))){
          newWord.append(word.charAt(i));
        } else {
          newWord.append('-');
        }
      }
      String SWord = newWord.toString();
      if (SWord.equals(pattern)){
        words.add(word);
      }
    }
    char best = 'a';
    int bestScore = 0;
    for (char i = 'a'; i <= 'z'; i++){
      if (guesses.contains(i)){
        continue;
      }
      int score = 0;
      for (String word : words){
        for (int j = 0; j < word.length(); j++){
          if (word.charAt(j) == i){
            score++;
        }
      }
      if (score > bestScore){
        bestScore = score;
        best = i;
      }
      }
    }
    return best;
  }
}
