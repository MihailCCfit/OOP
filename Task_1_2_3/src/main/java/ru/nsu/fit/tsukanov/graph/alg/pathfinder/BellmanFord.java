package ru.nsu.fit.tsukanov.graph.alg.pathfinder;

import java.util.*;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;



/**
 * Bellman-Ford algorithm.
 * Works with:
 * O (E*V)
 *
 * @param <V> vertex object
 * @param <E> edge object
 * @see Graph
 */
public class BellmanFord<V, E> {
    private V startVert;
    private final HashMap<V, Double> marksMap;
    private final HashMap<V, EdgeDefault<V, E>> pathMap;
    private final Graph<V, E> graph;

    /**
     * Initialize maps and start alg.
     *
     * @param graph     the graph where will be finding paths and distances
     * @param startVert the start vertex, from which will calculates distance
     */
    public BellmanFord(Graph<V, E> graph, V startVert) {
        this.startVert = startVert;
        this.marksMap = new HashMap<>();
        this.graph = graph;
        pathMap = new HashMap<>();
        reuse();
    }

    /**
     * Use Bellman alg for finding path.
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
        marksMap.clear();
        pathMap.clear();
        marksMap.put(startVert, 0.0);
        alg();
    }

    /**
     * Reuse Bellman algorithm.
     * It's useful, if there are some modifications in the graph.
     */
    public void reuse() {
        reuse(startVert);
    }

    private void relax(EdgeDefault<V, E> edge) {
        V start = edge.getSourceVertex();
        V end = edge.getTargetVertex();
        if (marksMap.containsKey(start)) {
            double res = marksMap.get(start) + edge.getWeight();
            if (!marksMap.containsKey(end) || res < marksMap.get(end)) {
                marksMap.put(end, res);
                pathMap.put(end, edge);
            }
        }
    }

    private void alg() {
        Set<EdgeDefault<V, E>> edgeSet = graph.edgeSet();
        for (int i = 0; i < graph.vertexSet().size() - 1; i++) {
            for (EdgeDefault<V, E> edge : edgeSet) {
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
        return marksMap.getOrDefault(v, Double.POSITIVE_INFINITY);
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
            return null;
        }
        LinkedList<V> list = new LinkedList<>();
        V curr = v;
        list.add(v);
        while (curr != startVert) {
            curr = pathMap.get(curr).getSourceVertex();
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
    public ArrayList<V> getOrdering() {
        ArrayList<V> arrayList = new ArrayList<>(marksMap.keySet());
        arrayList.sort((x, y) -> Double.compare(getDistant(x), getDistant(y)));
        return arrayList;
    }
}
