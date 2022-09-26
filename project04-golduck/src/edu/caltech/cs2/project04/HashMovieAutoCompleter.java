package edu.caltech.cs2.project04;

import edu.caltech.cs2.datastructures.ArrayDeque;
import edu.caltech.cs2.interfaces.IDeque;

import java.util.HashMap;
import java.util.Map;

public class HashMovieAutoCompleter extends AbstractMovieAutoCompleter {
    private static Map<String, IDeque<String>> titles = new HashMap<>();

    public static void populateTitles() {
        Map<String, String> ID = getIDMap();
        for (String s : ID.keySet()) {
            IDeque<String> toAdd = new ArrayDeque<>();
            toAdd.add(s);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ' ') {
                    toAdd.add(s.substring(i + 1));
                }
            }
            titles.put(s, toAdd);
        }
    }

    public static IDeque<String> complete(String term) {
        IDeque<String> matches = new ArrayDeque<>();
        for (String title : titles.keySet()) {
            for (String suffix : titles.get(title)) {
                if (suffix.length() >= term.length()) {
                    String word = suffix.substring(0, term.length()).toLowerCase();
                    if (word.equals(term.toLowerCase())) {
                        if (suffix.length() == term.length() || suffix.charAt(term.length()) == ' ') {
                            matches.add(title);
                            break;
                        }
                    }
                }
            }
        }
        return matches;
    }
}
