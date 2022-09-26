package edu.caltech.cs2.project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Decrypt {
    public static void main(String[] args) throws IOException {
        File d = new File("cryptogram.txt");
        Scanner r = new Scanner(d);
        String s = "";
        while (r.hasNextLine()){
            s += r.nextLine();
        }
        String encrypted = s.replaceAll("[^ABCDEFGHIJKLMNOPQRSTUVWXYZ]", "");
        SubstitutionCipher decrpyt = new SubstitutionCipher(encrypted);
        QuadGramLikelihoods likelihoods = new QuadGramLikelihoods();
        String decrypted = decrpyt.getSolution(likelihoods).getPlainText();
        FileWriter fileWriter = new FileWriter("plaintext.txt");
        fileWriter.write(decrypted);
        fileWriter.close();
    }
}
