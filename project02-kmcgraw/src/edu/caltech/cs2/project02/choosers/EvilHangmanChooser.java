package edu.caltech.cs2.project02.choosers;

import edu.caltech.cs2.project02.interfaces.IHangmanChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EvilHangmanChooser implements IHangmanChooser {
  private int wordLength;
  private int maxGuesses;
  private SortedSet<Character> guesses = new TreeSet<>();
  private ArrayList<String> secretWord = new ArrayList<>();

  public EvilHangmanChooser(int wordLength, int maxGuesses) throws FileNotFoundException {
    if (wordLength < 1 || maxGuesses < 1){
      throw new IllegalArgumentException();
    }
    File s = new File("data/scrabble.txt");
    Scanner r = new Scanner(s);
    boolean e = true;
    while (r.hasNextLine()){
      String word = r.nextLine();
      if (word.length() == wordLength){
        e = false;
        this.secretWord.add(word);
      }
    }
    if (e){
      throw new IllegalStateException();
    }
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
    this.guesses.add(letter);
    Map<String, ArrayList<String>> fams = new HashMap<>();
    SortedSet<String> keys = new TreeSet<>();
    for (String word : this.secretWord){
      String p = this.getPattern(word);
      if (!fams.containsKey(p)){
        fams.put(p, new ArrayList<>());
      }
      fams.get(p).add(word);
      keys.add(p);
    }
    ArrayList<String> largest = new ArrayList<>();
    String largestPattern = "";
    for (String key : keys){
      if (fams.get(key).size() > largest.size()){
        largest = fams.get(key);
        largestPattern = key;
      }
    }
    this.secretWord = largest;
    int count = 0;
    for (int i = 0; i < this.wordLength; i++){
      if (largestPattern.charAt(i) == letter){
        count++;
      }
    }
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
      if (!this.guesses.contains(this.secretWord.get(0).charAt(i))){
        return false;
      }
    }
    return true;
  }

  @Override
  public String getPattern() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < this.wordLength; i++){
      if (this.guesses.contains(this.secretWord.get(0).charAt(i))){
        result.append(this.secretWord.get(0).charAt(i));
      } else {
        result.append("-");
      }
    }
    return result.toString();
  }

  public String getPattern(String word) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < this.wordLength; i++){
      if (this.guesses.contains(word.charAt(i))){
        result.append(word.charAt(i));
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
    return this.secretWord.get(0);
  }
}