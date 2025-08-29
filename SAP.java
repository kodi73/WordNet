/* *****************************************************************************
 *  Name: ADITYA KUMAR
 *  Date: 29th August, 2025
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SAP {
    private final Digraph digraph;
    private final HashMap<Set<Integer>, int[]> cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Digraph is null.");

        digraph = G;
        cache = new HashMap<>();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        sap(v, w);
        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);

        return cache.get(key)[0];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        sap(v, w);
        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);

        return cache.get(key)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return sap(v, w)[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return sap(v, w)[1];
    }

    private void sap(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        HashSet<Integer> key = new HashSet<>();
        key.add(v);
        key.add(w);

        if (cache.containsKey(key))
            return;

        BreadthFirstDirectedPaths breadthFirstDirectedPathsV = new BreadthFirstDirectedPaths(
                digraph, v);
        BreadthFirstDirectedPaths breadthFirstDirectedPathsW = new BreadthFirstDirectedPaths(
                digraph, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = -2;

        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (breadthFirstDirectedPathsV.hasPathTo(vertex)
                    && breadthFirstDirectedPathsV.distTo(vertex) < distance &&
                    breadthFirstDirectedPathsW.hasPathTo(vertex)
                    && breadthFirstDirectedPathsW.distTo(vertex) < distance) {
                int totalDistance = breadthFirstDirectedPathsV.distTo(vertex)
                        + breadthFirstDirectedPathsW.distTo(vertex);

                if (totalDistance < distance) {
                    distance = totalDistance;
                    ancestor = vertex;
                }
            }
        }

        if (distance == Integer.MAX_VALUE) {
            distance = -1;
            ancestor = -1;
        }

        cache.put(key, new int[] { distance, ancestor });
    }

    private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertices(v);
        validateVertices(w);

        BreadthFirstDirectedPaths breadthFirstDirectedPathsV = new BreadthFirstDirectedPaths(
                digraph, v);
        BreadthFirstDirectedPaths breadthFirstDirectedPathsW = new BreadthFirstDirectedPaths(
                digraph, w);

        int distance = Integer.MAX_VALUE;
        int ancestor = -2;

        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (breadthFirstDirectedPathsV.hasPathTo(vertex)
                    && breadthFirstDirectedPathsV.distTo(vertex) < distance &&
                    breadthFirstDirectedPathsW.hasPathTo(vertex)
                    && breadthFirstDirectedPathsW.distTo(vertex) < distance) {
                int totalDistance = breadthFirstDirectedPathsV.distTo(vertex)
                        + breadthFirstDirectedPathsW.distTo(vertex);

                if (totalDistance < distance) {
                    distance = totalDistance;
                    ancestor = vertex;
                }
            }
        }

        if (distance == Integer.MAX_VALUE) {
            distance = -1;
            ancestor = -1;
        }

        return new int[] { distance, ancestor };
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= digraph.V()) {
            throw new IllegalArgumentException("Invalid Vertex " + v);
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int n = digraph.V();
        for (int v : vertices) {
            if (v < 0 || v >= n) {
                throw new IllegalArgumentException(
                        "vertex " + v + " is not between 0 and " + (n - 1));
            }
        }
    }
}

