/* *****************************************************************************
 *  Name: ADITYA KUMAR
 *  Date: 29th August, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {
    private final Digraph wordNet;
    private final HashMap<String, Bag<Integer>> nounToSynsetId;
    private final HashMap<Integer, String> synsetIdToSynSet;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        nounToSynsetId = new HashMap<>();
        synsetIdToSynSet = new HashMap<>();

        int countOfSynsets = readSynsets(synsets);

        wordNet = new Digraph(countOfSynsets);
        readHypernyms(hypernyms);

        DirectedCycle dc = new DirectedCycle(wordNet);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException("wordnet has cycle");
        }
        sap = new SAP(wordNet);

        int countOfVerticesWith0Outdegree = 0;
        for (int v = 0; v < countOfSynsets; v++) {
            if (wordNet.outdegree(v) == 0) {
                countOfVerticesWith0Outdegree++;
            }
        }

        if (countOfVerticesWith0Outdegree != 1) {
            throw new IllegalArgumentException("wordnet has multiple roots");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToSynsetId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("word to check for noun is null");

        return nounToSynsetId.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(nounA + " or " + nounB + " is not in the wordnet");
        }

        Bag<Integer> nounAIds = nounToSynsetId.get(nounA);
        Bag<Integer> nounBIds = nounToSynsetId.get(nounB);

        return sap.length(nounAIds, nounBIds);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(nounA + " or " + nounB + " is not in the wordnet");
        }

        Bag<Integer> nounAIds = nounToSynsetId.get(nounA);
        Bag<Integer> nounBIds = nounToSynsetId.get(nounB);

        int ancestor = sap.ancestor(nounAIds, nounBIds);

        return synsetIdToSynSet.get(ancestor);
    }

    // populates the maps and returns the number of synsets in the input
    private int readSynsets(String synsets) {
        if (synsets == null) {
            throw new IllegalArgumentException("synsets is null");
        }

        In in = new In(synsets);
        int countOfSynsets = 0;

        while (in.hasNextLine()) {
            countOfSynsets++;
            String line = in.readLine();
            String[] fields = line.split(",");
            int synsetId = Integer.parseInt(fields[0]);
            synsetIdToSynSet.put(synsetId, fields[1]);
            String[] nouns = fields[1].split(" ");

            for (String noun : nouns) {
                nounToSynsetId.computeIfAbsent(noun, k -> new Bag<>()).add(synsetId);
            }
        }

        return countOfSynsets;
    }

    private void readHypernyms(String hypernyms) {
        if (hypernyms == null) {
            throw new IllegalArgumentException("hypernyms is null");
        }

        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);

            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);
                wordNet.addEdge(v, w);
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet1 = new WordNet(args[0], args[1]);

        for (String noun : wordNet1.nouns()) {
            System.out.print(noun + " ");
        }
        System.out.println();
    }
}
