package ru.nsu.fit.tsukanov.graph.alg.pathfinder;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;

/**
 * Dijkstra algorithm.
 * Works with:
 * O (E*log V)
 *
 * @param <V> vertex object
 * @param <E> edge object
 */
public class Dijkstra<V extends Comparable<V>, E> {
    private V startVert;
    private final TreeMap<V, Double> marksTree;
    private final TreeMap<V, EdgeDefault<V, E>> pathMap;
    private final PriorityQueue<V> heap;

    private final Graph<V, E> graph;

    /**
     * Initialize maps, heap and start alg.
     *
     * @param graph the graph where will be finding paths and distances
     * @param startVert the start vertex, from which will calculates distance
     */
    public Dijkstra(Graph<V, E> graph, V startVert) {
        this.startVert = startVert;
        this.marksTree = new TreeMap<>();
        this.heap = new PriorityQueue<>((v1, v2) ->
                (marksTree.get(v1).compareTo(marksTree.get(v2))));
        this.graph = graph;
        pathMap = new TreeMap<>();
        reuse();
    }

    /**
     * Use Dijkstra alg for finding path.
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
        this.startVert = start;
        heap.clear();
        marksTree.clear();
        pathMap.clear();
        heap.add(startVert);
        marksTree.put(startVert, 0.0);
        alg();
    }

    /**
     * Reuse Dijkstra algorithm.
     * It's useful, if there are some modifications in the graph.
     */
    public void reuse() {
        reuse(startVert);
    }

    private void relax(EdgeDefault<V, E> edge) {
        V start = edge.getEdgeSource();
        V end = edge.getEdgeTarget();
        double res = marksTree.get(start) + edge.getWeight();
        if (!marksTree.containsKey(end) || res < marksTree.get(end)) {
            marksTree.put(end, res);
            pathMap.put(end, edge);
            heap.add(end);
        }
    }

    private void alg() {
        while (!heap.isEmpty()) {
            V curr = heap.poll();
            for (EdgeDefault<V, E> edge : graph.outgoingEdgesOf(curr)) {
                relax(edge);
            }
        }
    }

    /**
     * Return distance from start vertex to specify.
     *
     * @param v is vertex object
     * @return distance from start vertex to specify
     * @throws NullPointerException if argument is null
     */
    public double getDistant(V v) {
        if (v == null) {
            throw new NullPointerException("Null vertex");
        }
        return marksTree.getOrDefault(v, Double.POSITIVE_INFINITY);
    }

    /**
     * Return vertex path from start vertex to specify.
     *
     * @param v is vertex object
     * @return vertex path from start vertex to specify
     * @throws NullPointerException if argument is null
     */
    public List<V> getPathV(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        if (!pathMap.containsKey(v)) {
            System.out.println(v + "|HE");
            return null;
        }
        LinkedList<V> list = new LinkedList<>();
        V curr = v;
        list.add(v);
        while (curr != startVert) {
            curr = pathMap.get(curr).getEdgeSource();
            list.add(0, curr);
        }
        return list;
    }

    /**
     * Return edge path from start vertex to specify.
     *
     * @param v is vertex object
     * @return edge path from start vertex to specify
     * @throws NullPointerException if argument is null
     */
    public List<EdgeDefault<V, E>> getPathE(V v) {
        if (v == null) {
            throw new NullPointerException();
        }
        if (!pathMap.containsKey(v)) {
            return null;
        }
        LinkedList<EdgeDefault<V, E>> list = new LinkedList<>();
        V curr = v;

        while (curr != startVert) {
            list.add(0, pathMap.get(curr));
            curr = pathMap.get(curr).getEdgeSource();
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


}
