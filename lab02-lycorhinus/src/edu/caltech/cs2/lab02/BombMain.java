package edu.caltech.cs2.lab02;

public class BombMain {
    public static void main(String[] args) {
        Bomb b = new Bomb();
        b.phase0("22961293");
        b.phase1("hdc");
        String password = "";
        for (int i = 0; i  < 10000; i++) {
            if (i == 5000) {
                password += "1374866960 ";
            }
            else {
                password += "2 ";
            }
        }
        b.phase2(password);
    }
}