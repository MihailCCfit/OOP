package ru.nsu.fit.tsukanov.Graph.Main;

import ru.nsu.fit.tsukanov.basicGraph.Graph;
import ru.nsu.fit.tsukanov.graphImplementations.GraphAdjMatrix;

public class Main {
    public static void main() {
        Graph<String, String> graph = new GraphAdjMatrix<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A","B", 5.0);
        System.out.println(graph.getEdge("A","B"));
    }
}
