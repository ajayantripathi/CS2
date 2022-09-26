package edu.caltech.cs2.project04;

import edu.caltech.cs2.datastructures.ArrayDeque;
import edu.caltech.cs2.datastructures.LinkedDeque;
import edu.caltech.cs2.datastructures.TrieMap;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.ITrieMap;


public class TrieMovieAutoCompleter extends AbstractMovieAutoCompleter {
    private static ITrieMap<String, IDeque<String>, IDeque<String>> titles = new TrieMap<>((IDeque<String> s) -> s);

    public static void populateTitles() {
        for (String s : ID_MAP.keySet()) {
            IDeque<String> suffixes = new LinkedDeque<>();
            String[] s2 = s.split(" ");
            for (int i = 0; i < s2.length; i++) {
                suffixes.addBack(s2[i].toLowerCase());
            }
            for (int i = 0; i < s2.length; i++) {
                if (titles.containsKey(suffixes)){
                    IDeque<String> val = titles.get(suffixes);
                    val.add(s);
                    titles.put(suffixes, val);
                } else {
                    IDeque<String> val = new LinkedDeque<>();
                    val.add(s);
                    titles.put(suffixes, val);
                }
                suffixes.removeFront();
            }
        }
    }

    public static IDeque<String> complete(String term) {
        IDeque<String> termDeque = new LinkedDeque<>();
        String[] toAdd = term.split(" ");
        for (String word : toAdd) {
            termDeque.addBack(word.toLowerCase());
        }
        IDeque<String> completions = new LinkedDeque<>();
        IDeque<IDeque<String>> map = titles.getCompletions(termDeque);
        for (IDeque<String> values : map) {
            for (String value : values) {
                if (!completions.contains(value)) {
                    completions.add(value);
                }
            }
        }
        return completions;
    }
}

