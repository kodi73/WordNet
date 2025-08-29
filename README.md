# WordNet

This project is an implementation of a WordNet client, designed to compute the semantic distance and a shortest ancestral path between nouns in the Princeton WordNet database. The project models the WordNet hierarchy as a rooted directed acyclic graph (DAG).
## Overview of Core Concepts

**Synsets**: Sets of synonyms that represent a single conceptual meaning. Synsets are the vertices (nodes) in the graph.

**Hypernyms**: A more general or abstract concept. The "is-a" relationship (e.g., "cat" is a "feline") forms the directed edges in the graph, pointing from the more specific synset to the more general one.

**Shortest Ancestral Path (SAP):** The shortest path between two synsets that goes "up" the hierarchy to a common ancestor. The length of this path is the semantic distance.

**Outcast**: A noun in a group that is the least semantically related to the others, determined by summing the distances to all other nouns in the set.

## Project Structure

The project is composed of three main classes:

**WordNet.java**: The main client that reads the synsets.txt and hypernyms.txt files, builds the graph, and provides methods for finding noun distances and ancestral synsets. It also handles the project's validation by ensuring the input data forms a rooted DAG.

**SAP.java**: A helper class that efficiently computes the shortest ancestral path and a common ancestor between two synsets (or sets of synsets) using a simultaneous Breadth-First Search (BFS) algorithm. It also utilizes caching to optimize performance for repeated queries.

**Outcast.java**: A client that uses the WordNet class to identify the "outcast" noun in a given list by computing the total semantic distance from each noun to all others.

## How to Run the Program

**Prerequisites**: Ensure you have the algs4.jar library in your classpath, as this project relies on its Digraph, In, StdIn, and StdOut classes. You'll also need the synsets.txt and hypernyms.txt data files.

**Compilation**: Compile all three Java files.
```
javac-algs4 WordNet.java SAP.java Outcast.java
```
**Execution**: You can run the Outcast client from the command line, providing the data files as arguments.
```
java-algs4 Outcast synsets.txt hypernyms.txt outcast1.txt outcast2.txt outcast3.txt
```
