package ru.nsu.fit.tsukanov.GraphTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.tsukanov.alg.pathfinder.Dijkstra;
import ru.nsu.fit.tsukanov.core.EdgeDefault;
import ru.nsu.fit.tsukanov.core.Graph;
import ru.nsu.fit.tsukanov.graphImplementations.GraphAdjMatrix;
import ru.nsu.fit.tsukanov.graphImplementations.*;


import java.util.List;
import java.util.stream.Stream;

public class TestGraph {

    //public static void txtGraph(Graph<String, String> graph) throws IOException {
        //FileReader fileReader = new FileReader("ru/nsu/fit/tsukanov/GraphTest/matrix.txt");
        //BufferedReader reader = new BufferedReader(fileReader);

        //Scanner scanner = new Scanner(reader);
        //System.out.println(scanner.next());
        /*for (int i = 0; i < size; i++) {
            graph.addVertex(scanner.next());
        }
        System.out.println(graph.vertexSet());*/
    //}

    /*    @Test
    void snd() {
        Graph<String, String> graph = new GraphAdjMatrix<>();
        try {
            txtGraph(graph);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    private static Stream<Graph> graphStream() {
        return Stream.of(new GraphIncMatrix(), new GraphAdjMatrix<>());
    }

    @ParameterizedTest
    @MethodSource("graphStream")
    void fst(Graph<String, String> graph) {

        //Graph<String, String> graph = new GraphIncMatrix<String, String>();
        graph.addVertex("A");
        graph.addVertex("B");
        var ed1 = graph.addEdge("A", "B", 5.0);
        graph.addVertex("C");
        System.out.println(graph);

        var ed2 = graph.addEdge("A", "C", 10.0);
        var ed3 = graph.addEdge("B", "C", 4.0);
        System.out.println(graph);

        System.out.println(graph.getEdge("B", "A"));
        Dijkstra<String, String> dijkstra = new Dijkstra<>(graph, "A");
        System.out.println(dijkstra.getPathV("C"));
        System.out.println(dijkstra.getPathE("C"));
        System.out.println(dijkstra.getDistant("C"));
        Assertions.assertTrue(graph.containsEdge("A", "B"));
        Assertions.assertTrue(graph.containsEdge(ed1));

        graph.removeEdge("A", "B");
        dijkstra.reuse();
        Assertions.assertTrue(dijkstra.hasPath("C"));
        System.out.println(dijkstra.getPathV("C"));
        System.out.println(dijkstra.getPathE("C"));
        System.out.println(dijkstra.getDistant("C"));
        System.out.println(dijkstra.getPathV("B"));
        System.out.println(dijkstra.getDistant("B"));
        Assertions.assertFalse(graph.containsEdge("A", "B"));
        Assertions.assertFalse(graph.containsEdge(ed1));
        Assertions.assertNull(graph.getEdge("A", "B"));
        Assertions.assertTrue(graph.edgeSet().containsAll(List.of(ed2, ed3)));
        Assertions.assertTrue(graph.vertexSet().containsAll(List.of("A", "B", "C")));
        Assertions.assertFalse(graph.addVertex("A"));
        Assertions.assertTrue(graph.removeVertex("B"));
        Assertions.assertTrue(graph.addVertex("B"));
        EdgeDefault<String, String> edge = new EdgeDefault<>("C", "B", "Hello", 3);
        Assertions.assertTrue(graph.addEdge(edge));
        Assertions.assertTrue(graph.removeAllVertices(List.of("A", "C")));/**/
    }
    @ParameterizedTest
    @MethodSource("graphStream")
    void testNulls(Graph<Integer, String> graph){
        EdgeDefault<Integer, String> edge = new EdgeDefault<>(0,1,"01",5);
        EdgeDefault<Integer,String> edgeNull = new EdgeDefault<>(null,null,null,0);
        Assertions.assertNull(graph.getEdge(0,1));
        Assertions.assertNull(graph.getEdge(null,1));
        Assertions.assertNull(graph.addEdge(0,1));
        Assertions.assertNull(graph.addEdge(null,1));
        Assertions.assertNull(graph.addEdge(null,null));
        Assertions.assertFalse(graph.addEdge(edgeNull));
        Assertions.assertFalse(graph.addEdge(null));
        Assertions.assertFalse(graph.containsVertex(0));
        Assertions.assertFalse(graph.containsVertex(null));
        Assertions.assertFalse(graph.containsEdge(null));
        Assertions.assertFalse(graph.containsEdge(edge));
        Assertions.assertNull(graph.getAllEdges(0,1));
        Assertions.assertNull(graph.getAllEdges(null,1));
        Assertions.assertNull(graph.getAllEdges(null,null));
        Assertions.assertNull(graph.outgoingEdgesOf(0));
        Assertions.assertNull(graph.outgoingEdgesOf(null));
        Assertions.assertNull(graph.incomingEdgesOf(null));
        Assertions.assertTrue(graph.edgeSet().isEmpty());
        Assertions.assertEquals(graph.inDegreeOf(0), -1);
        Assertions.assertEquals(graph.outDegreeOf(0), -1);
        Assertions.assertFalse(graph.removeAllEdges(null));
        Assertions.assertFalse(graph.removeAllVertices(null));
        Assertions.assertNull(graph.removeEdge(0, 0));
        Assertions.assertNull(graph.removeEdge(null, null));
        graph.setEdgeWeight(null,null,0);
        graph.setEdgeWeight(-1,-1,0);
        //Dijkstra<Integer, String> dijkstra = new Dijkstra<>(graph, null);
        //Assertions.assertThrows(NullPointerException.class,
        //        (Executable) dijkstra.getPathV(null));
        //Для существующего ребра сделать
    }
}
