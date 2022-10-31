package ru.nsu.fit.tsukanov.graph.alg.pathfinder;

import java.util.*;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;



/**
 * Dijkstra algorithm for graph without negative weights.
 * If there is negative weight, then it throw exception.
 * Works with:
 * O (E*log V)
 *
 * @param <V> vertex object
 * @param <E> edge object
 * @see Graph
 */
public class Dijkstra<V, E> {
    private V startVertex;
    private final Map<V, Double> marksMap;
    private final Map<V, EdgeDefault<V, E>> pathMap;
    private final PriorityQueue<V> minDistanceHeap;

    private final Graph<V, E> graph;

    /**
     * Initialize maps, heap and start alg.
     *
     * @param graph       the graph where will be finding paths and distances
     * @param startVertex the start vertex, from which will calculates distance
     */
    public Dijkstra(Graph<V, E> graph, V startVertex) {
        this.startVertex = startVertex;
        this.marksMap = new HashMap<>();
        this.minDistanceHeap = new PriorityQueue<>((v1, v2) ->
                (marksMap.get(v1).compareTo(marksMap.get(v2))));
        this.graph = graph;
        pathMap = new HashMap<>();
        reuse();
    }

    /**
     * Use Dijkstra alg for finding path.
     * Throw exceptions if graph doesn't contain vertex
     *
     * @param start the starting vertex
     */
    public void reuse(V start) {
        if (start == null) {
            throw new NullPointerException();
        }
        if (!graph.containsVertex(start)) {
            throw new IllegalArgumentException("No such vertex in graph");
        }

        this.startVertex = start;
        minDistanceHeap.clear();
        marksMap.clear();
        pathMap.clear();
        minDistanceHeap.add(startVertex);
        marksMap.put(startVertex, 0.0);
        alg();
    }

    /**
     * Reuse Dijkstra algorithm.
     * It's useful, if there are some modifications in the graph.
     */
    public void reuse() {
        reuse(startVertex);
    }

    private void relax(EdgeDefault<V, E> edge) {
        V start = edge.getSourceVertex();
        V end = edge.getTargetVertex();
        double res = marksMap.get(start) + edge.getWeight();
        if (!marksMap.containsKey(end)) {
            marksMap.put(end, res);
            pathMap.put(end, edge);
            minDistanceHeap.add(end);
        }
        if (res < marksMap.get(end)) {
            marksMap.put(end, res);
            pathMap.put(end, edge);
        }
    }

    private void alg() {
        while (!minDistanceHeap.isEmpty()) {
            V curr = minDistanceHeap.poll();
            for (EdgeDefault<V, E> edge : graph.outgoingEdgesOf(curr)) {
                if (edge.getWeight() < 0) {
                    throw new IllegalStateException("There is negative edge weight");
                }
                relax(edge);
            }
        }
    }

    /**
     * Return distance from start vertex to specify.
     *
     * @param v the vertex object
     * @return distance from start vertex to specify
     * @throws NullPointerException if argument is null
     */
    public double getDistant(V v) {
        if (v == null) {
            throw new NullPointerException("Null vertex");
        }
        return marksMap.getOrDefault(v, Double.POSITIVE_INFINITY);
    }

    /**
     * Return vertex path from start vertex to specify.
     *
     * @param v the vertex object
     * @return vertex path from start vertex to specify
     * @throws NullPointerException if argument is null
     */
    public List<V> getVerticesPath(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        if (!pathMap.containsKey(v)) {
            return null;
        }
        LinkedList<V> list = new LinkedList<>();
        V curr = v;
        list.add(v);
        while (curr != startVertex) {
            curr = pathMap.get(curr).getSourceVertex();
            list.add(0, curr);
        }
        return list;
    }

    /**
     * Return edge path from start vertex to specify.
     *
     * @param v the vertex object
     * @return edge path from start vertex to specify
     * @throws NullPointerException if argument is null
     */
    public List<EdgeDefault<V, E>> getEdgesPath(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        if (!pathMap.containsKey(v)) {
            return null;
        }
        LinkedList<EdgeDefault<V, E>> list = new LinkedList<>();
        V curr = v;

        while (curr != startVertex) {
            list.add(0, pathMap.get(curr));
            curr = pathMap.get(curr).getSourceVertex();
        }
        return list;
    }

    /**
     * Check existing path from start vertex to specify.
     *
     * @param v the vertex object
     * @return true, if path exist to specify vertex.
     */
    public boolean hasPath(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        return pathMap.containsKey(v);
    }

    /**
     * Ordering vertices according to their distance from start vertex.
     * And return ArrayList of ordered vertices.
     *
     * @return ordered ArrayList according to distance
     */
    public List<V> getOrdering() {
        List<V> arrayList = new ArrayList<>(marksMap.keySet());
        arrayList.sort((x, y) -> Double.compare(getDistant(x), getDistant(y)));
        return arrayList;
    }

}
