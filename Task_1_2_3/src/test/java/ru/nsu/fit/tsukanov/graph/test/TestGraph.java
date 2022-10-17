package ru.nsu.fit.tsukanov.graph.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.tsukanov.graph.alg.pathfinder.Dijkstra;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;
import ru.nsu.fit.tsukanov.graph.implementations.GraphAdjMatrix;
import ru.nsu.fit.tsukanov.graph.implementations.GraphIncList;
import ru.nsu.fit.tsukanov.graph.implementations.GraphIncMatrix;



/**
 * Testing different graph for interface methods.
 */
public class TestGraph {
    /*
    public static void txtGraph(Graph<String, String> graph) throws IOException {
    FileReader fileReader = new FileReader("ru/nsu/fit/tsukanov/GraphTest/matrix.txt");
    BufferedReader reader = new BufferedReader(fileReader);

    Scanner scanner = new Scanner(reader);
    System.out.println(scanner.next());
        /*for (int i = 0; i < size; i++) {
            graph.addVertex(scanner.next());
        }
        System.out.println(graph.vertexSet());
    }
    */
    private static Stream<Graph> graphStream() {
        return Stream.of(new GraphIncMatrix(),
                new GraphAdjMatrix<>(),
                new GraphIncList()
        );
    }

    @ParameterizedTest
    @MethodSource("graphStream")
    void snd(Graph<String, String> graph) {
        String txt = "7\n"
                + "A B C D E F G\n"
                + "C\n"
                + "- 5 - 12 - - 25\n"
                + "5 - - 8 - - -\n"
                + "- - - 2 4 5 10\n"
                + "12 8 2 - - - -\n"
                + "- - 4 - - - 5\n"
                + "- - 5 - - - 5\n"
                + "25 - 10 - 5 5 -\n";
        Scanner scanner = new Scanner(txt);
        int n = scanner.nextInt();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arrayList.add(scanner.next());
            graph.addVertex(arrayList.get(i));
        }
        String startVertex = scanner.next();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String dist = scanner.next();
                if (!dist.equals("-")) {
                    graph.addEdge(arrayList.get(i), arrayList.get(j),
                            "" + i + j, Integer.parseInt(dist));
                }
            }
        }
        Dijkstra<String, String> alg = new Dijkstra<>(graph, startVertex);
        ArrayList<String> arrCopy = alg.getOrdering();
        String string = "";
        for (String s : arrCopy) {
            string += String.format("%s(%s) ", s, alg.getDistant(s));
        }
        Assertions.assertEquals("C(0.0) D(2.0) E(4.0) F(5.0) G(9.0) B(10.0) A(14.0) ",
                string);
        Assertions.assertEquals(arrCopy.stream().collect(Collectors.toList()),
                List.of("C", "D", "E", "F", "G", "B", "A"));

    }


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
        Assertions.assertTrue(dijkstra.getPathV("C").containsAll(
                List.of("A", "B", "C"))
        );
        Assertions.assertTrue(dijkstra.getPathE("C").containsAll(
                List.of(ed1, ed3)
        ));
        Assertions.assertEquals(9, dijkstra.getDistant("C"));
        Assertions.assertTrue(graph.containsEdge("A", "B"));
        Assertions.assertTrue(graph.containsEdge(ed1));
        graph.removeEdge("A", "B");
        dijkstra.reuse();
        Assertions.assertTrue(dijkstra.hasPath("C"));
        Assertions.assertEquals(Double.POSITIVE_INFINITY, dijkstra.getDistant("B"));
        Assertions.assertFalse(graph.containsEdge("A", "B"));
        Assertions.assertFalse(graph.containsEdge(ed1));
        Assertions.assertNull(graph.getEdge("A", "B"));
        Assertions.assertTrue(graph.edgeSet().containsAll(List.of(ed2, ed3)));
        Assertions.assertTrue(graph.vertexSet().containsAll(List.of("A", "B", "C")));
        Assertions.assertFalse(graph.addVertex("A"));
        Assertions.assertTrue(graph.removeVertex("B"));
        Assertions.assertFalse(graph.removeVertex("B"));
        Assertions.assertTrue(graph.addVertex("B"));
        EdgeDefault<String, String> edge = new EdgeDefault<>("C", "B", "Hello", 3);
        Assertions.assertTrue(graph.addEdge(edge));
        Assertions.assertFalse(graph.addEdge(edge));
        graph.setEdgeWeight("C", "B", 5);
        Assertions.assertEquals(edge.getWeight(), 5);
        Assertions.assertNull(graph.removeEdge("A", "D"));
        Assertions.assertTrue(graph.removeAllVertices(List.of("A", "C")));

    }

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
        Assertions.assertNull(graph.getAllEdges(0, 1));
        Assertions.assertNull(graph.getAllEdges(null, 1));
        Assertions.assertNull(graph.getAllEdges(0, null));
        Assertions.assertNull(graph.getAllEdges(null, null));
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
        Assertions.assertFalse(graph.addVertex(null));
        Assertions.assertNotEquals(2, edge);
        //Для существующего ребра сделать
    }

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

    @ParameterizedTest
    @MethodSource("graphStream")
    void testDijkstraAndIncEdges(Graph<Integer, String> graph) {
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
        Assertions.assertEquals(graph.getAllEdges(0, 5),
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
        Assertions.assertNull(dijkstra.getPathE(4));
        Assertions.assertNull(dijkstra.getPathV(4));
        Assertions.assertThrows(NullPointerException.class, () -> dijkstra.getPathE(null));
        Assertions.assertThrows(NullPointerException.class, () -> dijkstra.getPathV(null));
        Assertions.assertThrows(NullPointerException.class, () -> dijkstra.hasPath(null));
        Assertions.assertNull(graph.removeEdge(0, 0));
    }

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
        Assertions.assertNull(graph.getAllEdges(0, 1));
        Assertions.assertTrue(graph.removeVertex(0));
        Assertions.assertFalse(graph.containsEdge(0, 1));

    }
}
