import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class main {
    public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchAlgorithmException {
        where();
    }

    public static void where() throws ScriptException {
        ArrayList<Integer> start = new ArrayList<>();
        for (int i = 9; i > 1; i--){
            start.add(i);
        }
//        start.add(383);
//        start.add(191);
//        start.add(95);
//        start.add(47);
//        start.add(23);
//        start.add(11);
//        start.add(5);
//        start.add(2);
//        start.add(23);
//        start.add(19);
//        start.add(17);
//        start.add(13);
//        start.add(11);
//        start.add(7);
//        start.add(5);
//        start.add(3);
//        start.add(2);
        whereHelp("(1", start);
        whereHelp("(-1", start);
        whereHelp("-(1", start);
    }

    private static void whereHelp(String i, ArrayList<Integer> left) throws ScriptException {
        if (left.size() == 0){
            i += ")";
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
//            System.out.println(i + " : " + engine.eval(i));
//            int[] solutions = {14522, 14516, 14523, 14512, 14518, 14511, 72585, -14517, 87102};
            try {
//                for (int solution : solutions) {
                    if ((int) engine.eval(i) == 14517 || (int) engine.eval(i) == 4256) {
                        System.out.println(i + " : " + engine.eval(i));
                        System.exit(1);
                    }
//                }
            } catch (java.lang.ClassCastException e) {
//                System.out.println(i + " : " + engine.eval(i));
//                System.exit(1);
            }
//            if ((int) engine.eval(i) == 41570004  || (int) engine.eval(i) == -9300) {
//                System.out.println(i + engine.eval(i));
//                System.exit(0);
//            }
            return;
        }
        ArrayList<Integer> right = (ArrayList<Integer>) left.clone();
        int j = right.remove(right.size()-1);
        if (j == 7){
            whereHelp(i+"+("+j, right);
            whereHelp(i+"*("+j, right);
            whereHelp(i+"-("+j, right);
            whereHelp(i+"+(-"+j, right);
            whereHelp(i+"*(-"+j, right);
            whereHelp(i+"-(-"+j, right);
        } else if (j == 3){
            whereHelp(i+")+"+j, right);
            whereHelp(i+")*"+j, right);
            whereHelp(i+")-"+j, right);
        } else {
            whereHelp(i + "+" + j, right);
            whereHelp(i + "*" + j, right);
            whereHelp(i + "-" + j, right);
        }
    }

    public static Map<String, String> d = new HashMap<>();

    public static String fruit(){
//        for (int j = 0; j < 4; j++) {
//            int largest = 0;
//            String s = "";
//            ArrayList<String> S = new ArrayList<>();
//            S = fruitHelp(S, "", j);
//            for (String a : S) {
//                String i = fruit(a);
//                if (i.length() > largest) {
//                    largest = i.length();
//                    s = a;
//                }
//            }
//            System.out.println(s + largest);
//        }
        for (int k = 1; k < 21; k++) {
            String s = "";
            for (int j = 0; j < k; j++) {
                s += "a";
            }
            s += "ba";
            String i = fruit(s);
            System.out.println(s.length() + " : " + i.length());
        }
        BigInteger i = BigInteger.valueOf(6);
        for (BigInteger j = BigInteger.valueOf(4); j.compareTo(BigInteger.valueOf(78))<0; j = j.add(BigInteger.valueOf(1))){
            i = i.add(i.subtract((j.multiply(BigInteger.valueOf(2))))).add(BigInteger.valueOf(10));
            System.out.println(j.multiply(BigInteger.valueOf(2)).subtract(BigInteger.valueOf(3)) + " : " + i);
        }
        return null;
    }

    private static ArrayList<String> fruitHelp(ArrayList<String> s, String s1, int l) {
        if (s1.length()<l){
            s = fruitHelp(s, s1 + "a", l);
            s = fruitHelp(s, s1 + "b", l);
        } else {
            s.add(s1);
        }
        return s;
    }

    private static String fruit(String X){
        if (d.containsKey(X)){
//            System.out.println(X);
            return d.get(X);
        }
        if (X.length() == 1){
//            System.out.println("1");
            d.put(X, X);
            return X;
        }
        if (X.length() > 2 && X.substring(X.length()-2).equals("ba")){
            d.put(X, fruit("ab"+fruit(D(X))+"b"));
            return d.get(X);
        }
        if (X.length() > 1 && X.substring(0, 1).equals("b")){
//            System.out.println("3");
            d.put(X, fruit(X.substring(1)) + "b");
            return d.get(X);
        }
        if (X.length() > 2 && X.substring(0, 1).equals("a") && X.substring(X.length()-1).equals("a")){
//            System.out.println("4");
            d.put(X, "a" + fruit("a" + D(X.substring(0, X.length()-1))));
            return d.get(X);
        }
        if (X.length() > 1 && X.substring(0, 1).equals("a")){
//            System.out.println("5");
            d.put(X, "a" + fruit(X.substring(1)) + "a");
            return d.get(X);
        }
        return "";
    }

    private static String D(String s) {
        if (s.length()>2){
            return s.substring(2);
        }
        return "";
    }

    public static void shahaha() throws FileNotFoundException, NoSuchAlgorithmException {
        File myObj = new File("words.txt");
        Scanner myReader = new Scanner(myObj);
        List<String> dictionary = new ArrayList<>();
        while (myReader.hasNextLine()) {
            dictionary.add(myReader.nextLine());
        }
        myObj = new File("users.csv");
        myReader = new Scanner(myObj);
        Map<String, String> passwords = new HashMap<>();
        while (myReader.hasNextLine()) {
            String[] pieces = myReader.nextLine().split(",");
            passwords.put(pieces[1], pieces[0]);
        }
        for (String word : dictionary){
            String hash = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(word.toLowerCase().getBytes()));
            if (passwords.containsKey(hash)){
                System.out.println(word + " : " + passwords.get(hash));
            }
        }
    }

    private static class Tree {
        private Node root;

        public void add(int key, String value) {
            this.root = this.add(this.root, key, value);
        }

        private Node add(Node curr, int key, String value) {
            if (curr == null){
                return new Node(key, value);
            }
            if (key > curr.key) {
                curr.right = add(curr.right, key, value);
            } else if (key < curr.key) {
                curr.left = add(curr.left, key, value);
            } else {
                curr.value = value;
            }
            return curr;
        }

        public void get(int key) {
            Node curr = this.root;
            while (curr != null){
                System.out.print(curr.value);
                if (key > curr.key) {
                    curr = curr.right;
                } else if (key < curr.key) {
                    curr = curr.left;
                } else {
                    System.out.print(curr.value);
                    break;
                }
            }
        }

        private class Node {
            public int key;
            public String value;
            public Node left;
            public Node right;

            public Node(int key, String value){
                this.key = key;
                this.value = value;
            }
        }
    }

    public static void telephone() throws FileNotFoundException {
        File myObj = new File("telephone");
        Scanner myReader = new Scanner(myObj);
        Tree data = new Tree();
        while (myReader.hasNextLine()){
            String[] pieces = myReader.nextLine().split(" ");
            data.add(Integer.parseInt(pieces[0]), pieces[1]);
        }
        data.get(2898799);
    }

    public static void intro() throws FileNotFoundException {
        File myObj = new File("helpme.txt");
        Scanner myReader = new Scanner(myObj);
        StringBuilder data = new StringBuilder();
        while (myReader.hasNextLine()) {
            data.append(myReader.nextLine());
        }
        int[] message = new int[300000];
        int[] back = new int[data.length()];
        int ptr = 0;
        int i = 0;
        ArrayList<Integer> jumps = new ArrayList<>();
        for (int j = 0; j < data.length(); j++){
            if (data.charAt(j) == '[') {
                jumps.add(j);
            } else if (data.charAt(j) == ']'){
                int k = jumps.remove(jumps.size()-1);
                back[k] = j;
                back[j] = k;
            }
        }
        while (i < data.length()) {
            if (data.charAt(i) == '>'){
                ptr++;
                i++;
                continue;
            }
            if (data.charAt(i) == '<' && ptr > 0){
                ptr--;
                i++;
                continue;
            }
            if (data.charAt(i) == '+'){
                message[ptr]++;
                i++;
                continue;
            }
            if (data.charAt(i) == '-'){
                message[ptr]--;
                if (message[ptr] < 0){
                    message[ptr] = 0;
                }
                i++;
                continue;
            }
            if (data.charAt(i) == '['){
                if (message[ptr] == 0) {
                    i = back[i];
                }
                i++;
                continue;
            }
            if (data.charAt(i) == ']'){
                if (message[ptr]!= 0) {
                    i = back[i];
                }
                i++;
                continue;
            }
            if (data.charAt(i) == '.'){
                System.out.print((char) message[ptr]);
                i++;
                continue;
            }
            if (data.charAt(i) == ','){
                Scanner in = new Scanner(System.in);
                int a = in.next().charAt(0);
                message[ptr] = a;
                i++;
            }
        }
    }
}
