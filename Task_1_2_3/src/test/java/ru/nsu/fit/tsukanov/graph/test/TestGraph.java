package ru.nsu.fit.tsukanov.graph.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.tsukanov.graph.alg.pathfinder.BellmanFord;
import ru.nsu.fit.tsukanov.graph.alg.pathfinder.Dijkstra;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;
import ru.nsu.fit.tsukanov.graph.implementations.GraphAdjMatrix;
import ru.nsu.fit.tsukanov.graph.implementations.GraphIncList;
import ru.nsu.fit.tsukanov.graph.implementations.GraphIncMatrix;
import ru.nsu.fit.tsukanov.graph.parser.GraphParser;



/**
 * Testing different graph for interface methods.
 */
public class TestGraph {
    private static Stream<Graph> graphStream() {
        return Stream.of(new GraphIncMatrix(),
                new GraphAdjMatrix<>(),
                new GraphIncList()
        );
    }

    /**
     * Testing example from task.
     *
     * @param graph different graphs
     * @throws FileNotFoundException just for fun
     */
    @ParameterizedTest
    @MethodSource("graphStream")
    void parserTest(Graph<String, String> graph) throws FileNotFoundException {
        File file = new File("src/test/resources/matrix.txt");
        GraphParser.parseAdjacencyMatrix(file, graph);
        Dijkstra<String, String> alg = new Dijkstra<>(graph, "C");
        Assertions.assertEquals(new ArrayList<>(alg.getOrdering()),
                List.of("C", "D", "E", "F", "G", "B", "A"));
        BellmanFord<String, String> alg2 = new BellmanFord<>(graph, "C");
        Assertions.assertEquals(new ArrayList<>(alg2.getOrdering()),
                List.of("C", "D", "E", "F", "G", "B", "A"));
        var sett = graph.vertexSet();
        System.out.println(graph);
        graph.removeAllVertices(sett);
        file = new File("src/test/resources/list.txt");
        GraphParser.parseList(file, graph);
        alg.reuse("A");
        Assertions.assertEquals(alg.getDistant("D"), 6);
        alg2.reuse("A");
        Assertions.assertEquals(alg2.getDistant("D"), 6);
    }

    /**
     * Testing many methods with normal vertices and edges.
     *
     * @param graph - is different graph implementation.
     */
    @ParameterizedTest
    @MethodSource("graphStream")
    void graphTest1(Graph<String, String> graph) {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        var ed1 = graph.addEdge("A", "B", 5.0);
        var ed2 = graph.addEdge("A", "C", 10.0);
        var ed3 = graph.addEdge("B", "C", 4.0);
        Assertions.assertTrue(graph.containsEdge(ed1));
        Assertions.assertTrue(graph.containsEdge(ed2));
        Assertions.assertTrue(graph.containsEdge(ed3));
        Assertions.assertNull(graph.getEdge("B", "A"));
        Dijkstra<String, String> dijkstra = new Dijkstra<>(graph, "A");

        BellmanFord<String, String> bellmanFord = new BellmanFord<>(graph, "A");
        Assertions.assertTrue(dijkstra.getVerticesPath("C").containsAll(
                List.of("A", "B", "C"))
        );
        Assertions.assertTrue(dijkstra.getEdgesPath("C").containsAll(
                List.of(ed1, ed3)
        ));
        Assertions.assertTrue(bellmanFord.getVerticesPath("C").containsAll(
                List.of("A", "B", "C"))
        );
        Assertions.assertTrue(bellmanFord.getEdgesPath("C").containsAll(
                List.of(ed1, ed3)
        ));
        Assertions.assertEquals(9, dijkstra.getDistant("C"));
        Assertions.assertEquals(9, bellmanFord.getDistant("C"));
        Assertions.assertTrue(graph.containsEdge("A", "B"));
        Assertions.assertTrue(graph.containsEdge(ed1));
        graph.removeEdge("A", "B");
        dijkstra.reuse();
        bellmanFord.reuse();
        Assertions.assertTrue(dijkstra.hasPath("C"));
        Assertions.assertTrue(bellmanFord.hasPath("C"));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, dijkstra.getDistant("B"));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, bellmanFord.getDistant("B"));
        Assertions.assertFalse(graph.containsEdge("A", "B"));
        Assertions.assertFalse(graph.containsEdge(ed1));
        Assertions.assertNull(graph.getEdge("A", "B"));
        Assertions.assertTrue(graph.edgeSet().containsAll(List.of(ed2, ed3)));
        Assertions.assertTrue(graph.vertexSet().containsAll(List.of("A", "B", "C")));
        Assertions.assertFalse(graph.addVertex("A"));
        Assertions.assertTrue(graph.removeVertex("B"));
        Assertions.assertFalse(graph.removeVertex("B"));
        Assertions.assertTrue(graph.addVertex("B"));
        EdgeDefault<String, String> edge = new EdgeDefault<>("C", "B", null, 3);
        Assertions.assertTrue(graph.addEdge(edge));
        edge = new EdgeDefault<>("C", "B", null, 5);
        Assertions.assertTrue(graph.addEdge(edge));
        Assertions.assertFalse(graph.addEdge(edge));
        Assertions.assertEquals(edge.getWeight(), 5);
        graph.setEdgeWeight("C", "B", 5);
        edge.setObject("123");
        Assertions.assertNull(graph.getEdge(edge.getSourceVertex(), edge.getTargetVertex(), "edge.getObject()"));
        Assertions.assertEquals(edge.getWeight(), 5);
        Assertions.assertNull(graph.removeEdge("A", "D"));
        Assertions.assertTrue(graph.removeAllVertices(List.of("A", "C")));

    }

    /**
     * Testing null vertices, null edges, null value of object, and other situations.
     *
     * @param graph - is different graph implementation.
     */
    @ParameterizedTest
    @MethodSource("graphStream")
    void testNulls(Graph<Integer, String> graph) {
        EdgeDefault<Integer, String> edge = new EdgeDefault<>(0, 1, "01", 5);
        EdgeDefault<Integer, String> edgeNull = new EdgeDefault<>(null, null, null, 0);
        Assertions.assertNull(graph.getEdge(0, 1));
        Assertions.assertNull(graph.getEdge(null, 1));
        Assertions.assertNull(graph.addEdge(0, 1));
        Assertions.assertNull(graph.addEdge(null, 1));
        Assertions.assertNull(graph.addEdge(null, null));
        Assertions.assertFalse(graph.addEdge(edgeNull));
        Assertions.assertFalse(graph.addEdge(null));
        Assertions.assertFalse(graph.containsVertex(0));
        Assertions.assertFalse(graph.containsVertex(null));
        Assertions.assertFalse(graph.containsEdge(null));
        Assertions.assertFalse(graph.containsEdge(edge));
        Assertions.assertNull(graph.getEdges(0, 1));
        Assertions.assertNull(graph.getEdges(null, 1));
        Assertions.assertNull(graph.getEdges(0, null));
        Assertions.assertNull(graph.getEdges(null, null));
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
        graph.setEdgeWeight(null, null, 0);
        graph.setEdgeWeight(-1, -1, 0);
        Assertions.assertFalse(graph.removeEdge(null));
        Assertions.assertFalse(graph.removeEdge(edgeNull));
        Assertions.assertFalse(graph.removeVertex(null));
        Assertions.assertThrows(NullPointerException.class,
                () -> new Dijkstra<>(graph, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Dijkstra<>(graph, 0));
        Assertions.assertThrows(NullPointerException.class,
                () -> new BellmanFord<>(graph, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BellmanFord<>(graph, 0));
        Assertions.assertFalse(graph.addVertex(null));
        Assertions.assertNotEquals(2, edge);
        //Для существующего ребра сделать
    }

    /**
     * Testing edge class.
     */
    @Test
    void edgeTest() {
        EdgeDefault<Integer, String> edgeDefault = new EdgeDefault<>(0, 0);
        EdgeDefault<Integer, String> edgeDefault2 = new EdgeDefault<>(0, 1, "Hello");
        EdgeDefault<Integer, String> edgeDefault3 = new EdgeDefault<>(0, 1, "Hello", 666);
        EdgeDefault<Integer, String> edgeDefault4 = new EdgeDefault<>(0, 0, "Hello", 666);
        EdgeDefault<Integer, String> edgeDefault5 = new EdgeDefault<>(1, 0, "Hello", 666);
        Assertions.assertFalse(edgeDefault.equals("HI"));
        Assertions.assertFalse(edgeDefault.equals(null));
        Assertions.assertEquals(edgeDefault2, edgeDefault3);
        Assertions.assertNotEquals(edgeDefault, edgeDefault2);
        Assertions.assertNotEquals(edgeDefault, edgeDefault3);
        Assertions.assertNotEquals(edgeDefault, edgeDefault4);
        Assertions.assertNotEquals(edgeDefault, edgeDefault5);
        double l = edgeDefault2.getWeight();
        edgeDefault3.setWeight(l);
        Assertions.assertEquals(edgeDefault2.getWeight(),
                edgeDefault3.getWeight());
        edgeDefault.setObject(edgeDefault2.getObject());
        Assertions.assertNotEquals(edgeDefault2, edgeDefault);
        Assertions.assertEquals(edgeDefault4, edgeDefault);
    }

    /**
     * Testing Dijkstra and Bellman, and some methods, which are used in algorithm.
     *
     * @param graph - is different graph implementation.
     */
    @ParameterizedTest
    @MethodSource("graphStream")
    void testPathFindersAndIncEdges(Graph<Integer, String> graph) {
        graph.addVertex(0);
        graph.addVertex(5);
        graph.addVertex(10);
        graph.addVertex(-1);
        var edge05 = graph.addEdge(0, 5, "05", 6);
        var edge010 = graph.addEdge(0, 10, "010", 8);
        Assertions.assertEquals(graph.outgoingEdgesOf(0), Set.of(edge05, edge010));
        Assertions.assertEquals(graph.incomingEdgesOf(5), Set.of(edge05));
        Assertions.assertEquals(graph.incomingEdgesOf(10), Set.of(edge010));
        Assertions.assertEquals(graph.outDegreeOf(0), 2);
        Assertions.assertEquals(graph.inDegreeOf(0), 0);
        Assertions.assertEquals(graph.inDegreeOf(5), 1);
        Assertions.assertEquals(graph.inDegreeOf(10), 1);
        Assertions.assertFalse(edge05.toString().isBlank());
        var edge052 = graph.addEdge(0, 5, "052", 5);
        Assertions.assertEquals(graph.getEdges(0, 5),
                Set.of(edge05, edge052));
        graph.addEdge(0, -1, "0-1", 10);
        graph.addEdge(5, -1, "5-1", 5);
        graph.addEdge(10, -1, "10-1", 1);
        graph.addVertex(666);
        Dijkstra<Integer, String> dijkstra = new Dijkstra<>(graph, 0);
        Assertions.assertFalse(dijkstra.hasPath(666));
        Assertions.assertEquals(dijkstra.getDistant(-1), 9);
        Assertions.assertEquals(dijkstra.getDistant(666),
                Double.POSITIVE_INFINITY);
        Assertions.assertThrows(NullPointerException.class,
                () -> dijkstra.getDistant(null));
        Assertions.assertNull(dijkstra.getEdgesPath(4));
        Assertions.assertNull(dijkstra.getVerticesPath(4));
        Assertions.assertThrows(NullPointerException.class, () -> dijkstra.getEdgesPath(null));
        Assertions.assertThrows(NullPointerException.class, () -> dijkstra.getVerticesPath(null));
        Assertions.assertThrows(NullPointerException.class, () -> dijkstra.hasPath(null));
        Assertions.assertNull(graph.removeEdge(0, 0));

        BellmanFord<Integer, String> bellman = new BellmanFord<>(graph, 0);
        Assertions.assertFalse(bellman.hasPath(666));
        Assertions.assertEquals(bellman.getDistant(-1), 9);
        Assertions.assertEquals(bellman.getDistant(666),
                Double.POSITIVE_INFINITY);
        Assertions.assertThrows(NullPointerException.class,
                () -> bellman.getDistant(null));
        Assertions.assertNull(bellman.getEdgesPath(4));
        Assertions.assertNull(bellman.getVerticesPath(4));
        Assertions.assertThrows(NullPointerException.class, () -> bellman.getEdgesPath(null));
        Assertions.assertThrows(NullPointerException.class, () -> bellman.getVerticesPath(null));
        Assertions.assertThrows(NullPointerException.class, () -> bellman.hasPath(null));
        Assertions.assertNull(graph.removeEdge(0, 0));
    }

    /**
     * Common tests for adds.
     *
     * @param graph - is different graph implementation.
     */
    @ParameterizedTest
    @MethodSource("graphStream")
    void testAdds(Graph<Integer, String> graph) {
        EdgeDefault<Integer, String> edge1 = new EdgeDefault<>(0, 1, "01");
        Assertions.assertTrue(graph.addVertex(0));
        Assertions.assertTrue(graph.containsVertex(0));
        Assertions.assertFalse(graph.addEdge(edge1));
        Assertions.assertFalse(graph.addVertex(0));
        Assertions.assertTrue(graph.addVertex(1));
        Assertions.assertTrue(graph.addEdge(edge1));
        Assertions.assertTrue(graph.containsEdge(edge1));
    }

    /**
     * Test removing vertices and edges from the graph.
     *
     * @param graph - is different graph implementation.
     */
    @ParameterizedTest
    @MethodSource("graphStream")
    void testRemoves(Graph<Integer, String> graph) {
        graph.addVertex(0);
        graph.addVertex(1);
        EdgeDefault<Integer, String> edge0 = graph.addEdge(0, 1, "Cool Edge");
        EdgeDefault<Integer, String> edgeNot = new EdgeDefault<>(0, 1);
        Assertions.assertFalse(graph.containsEdge(edgeNot));
        Assertions.assertTrue(graph.containsEdge(edge0));
        Assertions.assertFalse(graph.removeEdge(edgeNot));
        Assertions.assertTrue(graph.removeEdge(edge0));
        graph.addEdge(edge0);
        Assertions.assertFalse(graph.removeVertex(2));
        Assertions.assertTrue(graph.removeVertex(1));
        Assertions.assertFalse(graph.removeEdge(edge0));
        Assertions.assertNull(graph.getEdge(0, 1));
        Assertions.assertNull(graph.getEdges(0, 1));
        Assertions.assertTrue(graph.removeVertex(0));
        Assertions.assertFalse(graph.containsEdge(0, 1));
        graph.addVertex(5);
        graph.addVertex(6);
        Assertions.assertFalse(graph.removeEdges(5, 6));
        graph.addEdge(5, 6, "Why");
        graph.addEdge(5, 6, "Because");
        Assertions.assertTrue(graph.removeEdges(5, 6));
        Assertions.assertTrue(graph.getEdges(5, 6)::isEmpty);
        Assertions.assertFalse(graph.removeEdges(5, 10));
    }

    @ParameterizedTest
    @MethodSource("graphStream")
    void negativeWeightTest(Graph<Integer, String> graph) {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1, -5.0);
        graph.addEdge(1, 0, -5.0);
        Assertions.assertThrows(IllegalStateException.class, () -> new Dijkstra<>(graph, 0));
        Assertions.assertThrows(IllegalStateException.class, () -> new BellmanFord<>(graph, 0));
    }

}
