/* *****************************************************************************
 *  Name: Aditya Kumar
 *  Date: 29th August, 2025
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public String outcast(String[] nouns) {
        String outcast = nouns[0];
        int d = dSum(nouns[0], nouns);

        for (int i = 1; i < nouns.length; i++) {
            int tempdist = dSum(nouns[i], nouns);

            if (d < tempdist) {
                d = tempdist;
                outcast = nouns[i];
            }
        }

        return outcast;
    }

    // helper methods
    private int dSum(String noun, String[] nouns) {
        int d = 0;

        for (String n : nouns) {
            d += wordnet.distance(noun, n);
        }

        return d;
    }

    // unit test
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);

        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
