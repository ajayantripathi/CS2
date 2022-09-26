package edu.caltech.cs2.project01;

import javax.crypto.Cipher;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SubstitutionCipher {
    private String ciphertext;
    private Map<Character, Character> key;

    // Use this Random object to generate random numbers in your code,
    // but do not modify this line.
    private static final Random RANDOM = new Random();

    /**
     * Construct a SubstitutionCipher with the given cipher text and key
     * @param ciphertext the cipher text for this substitution cipher
     * @param key the map from cipher text characters to plaintext characters
     */
    public SubstitutionCipher(String ciphertext, Map<Character, Character> key) {
       this.ciphertext = ciphertext;
       this.key = key;
    }

    /**
     * Construct a SubstitutionCipher with the given cipher text and a randomly
     * initialized key.
     * @param ciphertext the cipher text for this substitution cipher
     */
    public SubstitutionCipher(String ciphertext) {
        this.ciphertext = ciphertext;
        Map<Character, Character> identity = new HashMap<>();
        for (int i = 0; i < 26; i++){
            identity.put(CaesarCipher.ALPHABET[i], CaesarCipher.ALPHABET[i]);
        }
        SubstitutionCipher s = new SubstitutionCipher(ciphertext, identity);
        for (int i = 0; i < 10000; i++){
            s = s.randomSwap();
        }
        this.key = s.key;
    }

    /**
     * Returns the unedited cipher text that was provided by the user.
     * @return the cipher text for this substitution cipher
     */
    public String getCipherText() {
        return this.ciphertext;
    }

    /**
     * Applies this cipher's key onto this cipher's text.
     * That is, each letter should be replaced with whichever
     * letter it maps to in this cipher's key.
     * @return the resulting plain text after the transformation using the key
     */
    public String getPlainText() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < this.getCipherText().length(); i++){
            result.append(this.key.get(this.getCipherText().charAt(i)));
        }
        return result.toString();
    }

    /**
     * Returns a new SubstitutionCipher with the same cipher text as this one
     * and a modified key with exactly one random pair of characters exchanged.
     *
     * @return the new SubstitutionCipher
     */
    public SubstitutionCipher randomSwap() {
        Map<Character, Character> newKey = new HashMap<>(this.key);
        char swap1 = CaesarCipher.ALPHABET[RANDOM.nextInt(26)];
        char swap2 = CaesarCipher.ALPHABET[RANDOM.nextInt(26)];
        while (swap2 == swap1){
            swap2 = CaesarCipher.ALPHABET[RANDOM.nextInt(26)];
        }
        char k = newKey.get(swap1);
        newKey.put(swap1, newKey.get(swap2));
        newKey.put(swap2, k);
        return new SubstitutionCipher(this.ciphertext, newKey);
    }

    /**
     * Returns the "score" for the "plain text" for this cipher.
     * The score for each individual quadgram is calculated by
     * the provided likelihoods object. The total score for the text is just
     * the sum of these scores.
     * @param likelihoods the object used to find a score for a quadgram
     * @return the score of the plain text as calculated by likelihoods
     */
    public double getScore(QuadGramLikelihoods likelihoods) {
        double total = 0;
        String text = this.getPlainText();
        for (int i = 0; i < text.length()-3; i++){
            total += likelihoods.get(text.substring(i, i+4));
        }
        return total;
    }

    /**
     * Attempt to solve this substitution cipher through the hill
     * climbing algorithm. The SubstitutionCipher this is called from
     * should not be modified.
     * @param likelihoods the object used to find a score for a quadgram
     * @return a SubstitutionCipher with the same ciphertext and the optimal
     *  found through hill climbing
     */
    public SubstitutionCipher getSolution(QuadGramLikelihoods likelihoods) {
        SubstitutionCipher s = new SubstitutionCipher(this.getPlainText());
        double score = s.getScore(likelihoods);
        int count = 0;
        while (count<1000){
            SubstitutionCipher s2 = s.randomSwap();
            double newScore = s2.getScore(likelihoods);
            if (newScore > score){
                score = newScore;
                s = s2;
                count = 0;
            }
            count++;
        }
        return s;
    }
}
