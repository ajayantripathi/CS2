package edu.caltech.cs2.project02.choosers;

import edu.caltech.cs2.project02.interfaces.IHangmanChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RandomHangmanChooser implements IHangmanChooser {

  private static final Random random = new Random();
  private int wordLength;
  private int maxGuesses;
  private final String secretWord;
  private SortedSet<Character> guesses = new TreeSet<>();

  public RandomHangmanChooser(int wordLength, int maxGuesses) throws FileNotFoundException {
    if (wordLength < 1 || maxGuesses < 1){
      throw new IllegalArgumentException();
    }
    File s = new File("data/scrabble.txt");
    Scanner r = new Scanner(s);
    boolean e = true;
    SortedSet<String> words = new TreeSet<>();
    while (r.hasNextLine()){
      String word = r.nextLine();
      if (word.length() == wordLength){
        e = false;
        words.add(word);
      }
    }
    if (e){
      throw new IllegalStateException();
    }
    int j = random.nextInt(words.size());
    int i = 0;
    String secret = "";
    for (String word : words){
      if (i == j){
        secret = word;
      }
      i++;
    }
    this.secretWord = secret;
    this.wordLength = wordLength;
    this.maxGuesses = maxGuesses;
  }

  @Override
  public int makeGuess(char letter) {
    if (this.maxGuesses < 1){
      throw new IllegalStateException();
    }
    if (!Character.isLowerCase(letter) || this.guesses.contains(letter)){
      throw new IllegalArgumentException();
    }
    int count = 0;
    for (int i = 0; i < this.wordLength; i++){
      if (this.secretWord.charAt(i) == letter){
        count++;
      }
    }
    this.guesses.add(letter);
    if (count == 0){
      this.maxGuesses--;
    }
    return count;
  }

  @Override
  public boolean isGameOver() {
    if (this.maxGuesses <= 0){
      return true;
    }
    for (int i = 0; i < this.wordLength; i++){
      if (!this.guesses.contains(this.secretWord.charAt(i))){
        return false;
      }
    }
    return true;
  }

  @Override
  public String getPattern() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < this.wordLength; i++){
      if (this.guesses.contains(this.secretWord.charAt(i))){
        result.append(this.secretWord.charAt(i));
      } else {
        result.append("-");
      }
    }
    return result.toString();
  }

  @Override
  public SortedSet<Character> getGuesses() {
    return this.guesses;
  }

  @Override
  public int getGuessesRemaining() {
    return this.maxGuesses;
  }

  @Override
  public String getWord() {
    this.maxGuesses = 0;
    return this.secretWord;
  }
}