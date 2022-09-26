package edu.caltech.cs2.lab04;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullStringTree {

    public StringNode root;
    private ArrayList<String> elements;

    protected static class StringNode {
        public final String data;
        public StringNode left;
        public StringNode right;

        public StringNode(String data) {
            this(data, null, null);
        }

        public StringNode(String data, StringNode left, StringNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
            // Ensures that the StringNode is either a leaf or has two child nodes.
            if ((this.left == null || this.right == null) && !this.isLeaf()) {
                throw new IllegalArgumentException("StringNodes must represent nodes in a full binary tree");
            }
        }

        // Returns true if the StringNode has no child nodes.
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    protected StringNode FullStringTree() {
        if (this.elements.size() == 0){
            return null;
        }
        String line = elements.get(0);
        if (line.charAt(0) == 'I'){
            StringNode n = new StringNode(line.substring(3));
            this.elements.remove(0);
            n.left = FullStringTree();
            n.right = FullStringTree();
            return n;
        }
        this.elements.remove(0);
        return new StringNode(line.substring(3));
    }

    public FullStringTree(Scanner in) {
        this.elements = new ArrayList<>();
        while (in.hasNextLine()){
            String line = in.nextLine();
            elements.add(line);
        }
        this.root = FullStringTree();
    }

    private StringNode deserialize(Scanner in) {
        return null;
    }

    private List<String> explore(List data, StringNode curr){
        if (curr == null){
            return data;
        }
        if (curr.left == null){
            data.add("L: " + curr.data);
            return data;
        }
        data.add("I: " + curr.data);
        data = explore(data, curr.left);
        return explore(data, curr.right);
    }

    public List<String> explore() {
        List<String> data = new ArrayList<>();
        return explore(data, root);
    }

    public void serialize(PrintStream output) {
        List<String> data = explore();
        for (String s : data) {
            output.format(s + "\n");
        }
    }
}