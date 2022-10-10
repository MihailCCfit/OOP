import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tsukanov.FindPath.Dijkstra;
import ru.nsu.fit.tsukanov.basicGraph.EdgeDefault;
import ru.nsu.fit.tsukanov.basicGraph.Graph;
import ru.nsu.fit.tsukanov.graphImplementations.GraphAdjMatrix;
import GraphTest.ExampleTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TestGraph {

    public static void txtGraph(Graph<String, String> graph) throws IOException {
        FileReader fileReader = new FileReader("matrix.txt");
        //BufferedReader reader = new BufferedReader(fileReader);

        //Scanner scanner = new Scanner(reader);
        //System.out.println(scanner.next());
        /*for (int i = 0; i < size; i++) {
            graph.addVertex(scanner.next());
        }
        System.out.println(graph.vertexSet());*/
    }
//    @Test
    void snd(){
        Graph<String, String> graph = new GraphAdjMatrix<>();
        try {
            txtGraph(graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void fst(){
        Graph<String, String> graph = new GraphAdjMatrix<>();
        graph.addVertex("A");
        graph.addVertex("B");
        var ed1 = graph.addEdge("A","B", 5.0);
        graph.addVertex("C");
        var ed2 =graph.addEdge("A","C", 10.0);
        var ed3 = graph.addEdge("B","C", 4.0);
        System.out.println(graph.getEdge("B","A"));
        Dijkstra<String, String> dijkstra = new Dijkstra<>(graph, "A");
        System.out.println(dijkstra.getPathV("C"));
        System.out.println(dijkstra.getPathE("C"));
        System.out.println(dijkstra.getDistant("C"));
        Assertions.assertTrue(graph.containsEdge("A","B"));
        Assertions.assertTrue(graph.containsEdge(ed1));

        graph.removeEdge("A","B");
        dijkstra.reuse();
        System.out.println(dijkstra.getPathV("C"));
        System.out.println(dijkstra.getPathE("C"));
        System.out.println(dijkstra.getDistant("C"));
        System.out.println(dijkstra.getPathV("B"));
        System.out.println(dijkstra.getDistant("B"));
        Assertions.assertFalse(graph.containsEdge("A","B"));
        Assertions.assertFalse(graph.containsEdge(ed1));
        Assertions.assertNull(graph.getEdge("A", "B"));
        Assertions.assertTrue(graph.edgeSet().containsAll(List.of(ed2,ed3)));
        Assertions.assertTrue(graph.vertexSet().containsAll(List.of("A","B","C")));
        Assertions.assertFalse(graph.addVertex("A"));
        Assertions.assertTrue(graph.removeVertex("B"));
        Assertions.assertTrue(graph.addVertex("B"));
        EdgeDefault<String,String> edge = new EdgeDefault<>("C", "B","Hello", 3);
        Assertions.assertTrue(graph.addEdge(edge));
        Assertions.assertTrue(graph.removeAllVertices(List.of("A","C")));/**/
    }
}
