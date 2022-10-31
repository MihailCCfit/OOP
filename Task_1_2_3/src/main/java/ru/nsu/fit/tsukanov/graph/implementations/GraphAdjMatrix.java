package ru.nsu.fit.tsukanov.graph.implementations;

import java.util.*;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;



/**
 * Oriented weighted graph. There is class for edge.
 * It uses adjacency matrix for method implementation.
 * Big-O notations:
 * Get:
 * V-V O(1+ev)
 * V-* O(V+ev)
 * *-* O(V^2 + E)
 * Contains:
 * V O(1) - because there is vertex hashmap
 * E O(1) - because edge contains source and target vertex
 * Add/Remove:
 * V O(N)
 * E O(ev)
 * Memory:
 * O(N^2+E)
 * ev - vertex's edges
 * So it's good for static, but bad for dynamic.
 *
 * @param <V> object, that will be vertex.
 * @param <E> object, that contained in Edge.
 * @see EdgeDefault
 * @see Graph
 */
public class GraphAdjMatrix<V, E> implements Graph<V, E> {
    private final Deque<Integer> indexesStack;
    private final Map<V, Integer> vertexMap;
    private final List<List<List<EdgeDefault<V, E>>>> matrix;

    /**
     * Creates stack for indexing,
     * map for mapping V object to integer
     * and adjacency matrix.
     */
    public GraphAdjMatrix() {
        this.indexesStack = new ArrayDeque<>();
        this.vertexMap = new HashMap<>();
        this.matrix = new ArrayList<>();
    }

    private List<EdgeDefault<V, E>> getListEdges(V sourceVertex, V targetVertex) {
        if (!containsVertex(sourceVertex)
                || !containsVertex(targetVertex)) {
            return null;
        }
        int i = vertexMap.get(sourceVertex);
        int k = vertexMap.get(targetVertex);
        return matrix.get(i).get(k);
    }

    /**
     * Return all edges, that connects two vertices.
     * Edges the directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex start vertex
     * @param targetVertex end vertex
     * @return set of all edges, that connects vertices
     */
    @Override
    public Set<EdgeDefault<V, E>> getEdges(V sourceVertex, V targetVertex) {
        var list = getListEdges(sourceVertex, targetVertex);
        if (list == null) {
            return null;
        }
        return new HashSet<>(list);
    }

    /**
     * Return edge, that connects two vertices and has specified object.
     * Edge the directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex start vertex
     * @param targetVertex end vertex
     * @param obj          object in edge
     * @return edge connects vertices
     */
    @Override
    public EdgeDefault<V, E> getEdge(V sourceVertex, V targetVertex, E obj) {
        if (sourceVertex == null || targetVertex == null) {
            return null;
        }
        List<EdgeDefault<V, E>> l = getListEdges(sourceVertex, targetVertex);
        if (l == null) {
            return null;
        }
        if (l.isEmpty()) {
            return null;
        }
        var pat = new EdgeDefault<>(sourceVertex, targetVertex, obj);
        for (EdgeDefault<V, E> edge : l) {
            if (edge.equals(pat)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Add edge, that connects vertices in direction from sourceVertex to targetVertex.
     * Edge has an object.
     *
     * @param sourceVertex the start vertex for edge
     * @param targetVertex the end vertex for edge
     * @param object       the object that will be placed into edge
     * @return new Edge
     */
    @Override
    public EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E object) {
        return addEdge(sourceVertex, targetVertex, object, EdgeDefault.DEFAULT_WEIGHT);
    }

    /**
     * Add specified edge, if there is no such edge.
     *
     * @param e the edge that will be added to graph
     * @return true, if there are no equal Edge.
     */
    @Override
    public boolean addEdge(EdgeDefault<V, E> e) {
        if (e == null) {
            return false;
        }
        var list = getListEdges(e.getSourceVertex(), e.getTargetVertex());
        if (list == null) {
            return false;
        }

        for (EdgeDefault<V, E> edge : list) {
            if (edge.equals(e)) {
                boolean answer = edge.getWeight() != e.getWeight();
                edge.setWeight(e.getWeight());
                return answer;
            }
        }

        return list.add(e);
    }


    /**
     * Create Edge with specified vertices, weight and object.
     *
     * @param sourceVertex the start vertex for edge
     * @param targetVertex the end vertex for edge
     * @param e            the object that will be placed into edge
     * @param weight       the weight that will be placed into edge
     * @return edge
     */
    @Override
    public EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E e, double weight) {
        EdgeDefault<V, E> edge = new EdgeDefault<>(sourceVertex, targetVertex, e, weight);

        return addEdge(edge) ? edge : null;
    }

    /**
     * Add vertex to graph, if there is no such vertex.
     * If it isn't, then return false.
     *
     * @param v the vertex value, or vertex object.
     * @return true, if vertex has been added.
     */
    @Override
    public boolean addVertex(V v) {
        if (v == null) {
            return false;
        }
        if (indexesStack.isEmpty()) {
            indexesStack.push(vertexMap.size());
        }
        if (containsVertex(v)) {
            return false;
        }
        Integer index = indexesStack.pop();
        vertexMap.put(v, index);
        if (index >= matrix.size()) {
            int newSize = matrix.size() + 1;
            List<List<EdgeDefault<V, E>>> newCol = new ArrayList<>();
            for (int i = 0; i < newSize; i++) {
                newCol.add(new ArrayList<>());
            }
            matrix.add(newCol);
            for (int i = 0; i < index; i++) {
                matrix.get(i).add(new ArrayList<>());
            }
        }
        return true;
    }

    /**
     * Check for existing edge between two vertices.
     *
     * @param sourceVertex the start vertex for some edge
     * @param targetVertex the end vertex for some edge
     * @return true, if there is edge from source to target.
     */
    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        var list = getListEdges(sourceVertex, targetVertex);
        if (list == null) {
            return false;
        }
        return !list.isEmpty();
    }

    /**
     * Check for exising edge in the graph.
     *
     * @param e the edge for checking
     * @return true, if the graph contains specified edge
     */
    @Override
    public boolean containsEdge(EdgeDefault<V, E> e) {
        if (e == null) {
            return false;
        }
        if (!containsVertex(e.getSourceVertex())
                || !containsVertex(e.getTargetVertex())) {
            return false;
        }

        return matrix.get(vertexMap.get(e.getSourceVertex()))
                .get(vertexMap.get(e.getTargetVertex()))
                .contains(e);
    }

    /**
     * Check for exising vertex in the graph.
     *
     * @param v the vertex object
     * @return true, if the graph contains specified vertex.
     */
    @Override
    public boolean containsVertex(V v) {
        if (v == null) {
            return false;
        }
        return vertexMap.containsKey(v);
    }

    /**
     * Return set of all edges from graph.
     *
     * @return set of all edges from this graph.
     */
    @Override
    public Set<EdgeDefault<V, E>> edgeSet() {
        Set<EdgeDefault<V, E>> edgeSet = new HashSet<>();
        for (V v : vertexMap.keySet()) {
            edgeSet.addAll(outgoingEdgesOf(v));
        }
        return edgeSet;
    }

    /**
     * Return set of all incoming edges to specified vertex.
     *
     * @param vertex the vertex
     * @return set of all incoming edges to specified vertex.
     */
    @Override
    public Set<EdgeDefault<V, E>> incomingEdgesOf(V vertex) {
        if (!containsVertex(vertex)) {
            return null;
        }
        int index = vertexMap.get(vertex);
        Set<EdgeDefault<V, E>> edgeDefaultSet = new HashSet<>();
        for (List<List<EdgeDefault<V, E>>> list : matrix) {
            edgeDefaultSet.addAll(list.get(index));
        }
        return edgeDefaultSet;
    }

    /**
     * Return set of all incoming edges to specified vertex.
     *
     * @param vertex the vertex
     * @return set of all incoming edges to specified vertex.
     */
    @Override
    public Set<EdgeDefault<V, E>> outgoingEdgesOf(V vertex) {
        if (!containsVertex(vertex)) {
            return null;
        }
        int index = vertexMap.get(vertex);
        List<List<EdgeDefault<V, E>>> arrayLists = matrix.get(index);
        Set<EdgeDefault<V, E>> edgeDefaultSet = new HashSet<>();
        for (List<EdgeDefault<V, E>> arrayList : arrayLists) {
            edgeDefaultSet.addAll(arrayList);
        }

        return edgeDefaultSet;
    }

    /**
     * Remove edge with null object from graph between two specified vertices.
     *
     * @param sourceVertex the source vertex maybe for some edge.
     * @param targetVertex the target vertex maybe for some edge.
     * @return removed edge.
     */
    @Override
    public EdgeDefault<V, E> removeEdge(V sourceVertex, V targetVertex) {
        if (sourceVertex == null || targetVertex == null) {
            return null;
        }
        if (!containsVertex(sourceVertex) || !containsVertex(targetVertex)) {
            return null;
        }
        EdgeDefault<V, E> edge = getEdge(sourceVertex, targetVertex);
        return removeEdge(edge) ? edge : null;
    }

    /**
     * Remove specified edge from graph. And check for graph changes.
     *
     * @param e the edge
     * @return true, if there was removing edge from graph.
     */

    @Override
    public boolean removeEdge(EdgeDefault<V, E> e) {
        if (e == null) {
            return false;
        }
        if (!(containsVertex(e.getSourceVertex())
                && containsVertex(e.getTargetVertex()))) {
            return false;
        }
        int i = vertexMap.get(e.getSourceVertex());
        int k = vertexMap.get(e.getTargetVertex());
        return matrix.get(i).get(k).remove(e);
    }

    /**
     * Remove specified vertex from graph. And check for graph changes.
     *
     * @param v the vertex object
     * @return true, if there was removing vertex from graph.
     */
    @Override
    public boolean removeVertex(V v) {
        if (!containsVertex(v)) {
            return false;
        }
        int index = vertexMap.get(v);
        List<List<EdgeDefault<V, E>>> arrayLists = matrix.get(index);
        for (List<EdgeDefault<V, E>> list : arrayLists) {
            list.clear();
        }
        for (List<List<EdgeDefault<V, E>>> lists : matrix) {
            lists.get(index).clear();
        }
        vertexMap.remove(v);
        indexesStack.push(index);
        return true;
    }

    /**
     * Return set of all vertices from graph.
     *
     * @return set of all vertices from this graph
     */
    @Override
    public Set<V> vertexSet() {
        return new HashSet<>(vertexMap.keySet());
    }

    @Override
    public String toString() {
        return "GraphAdjMatrix{"
                + "indexesStack=" + indexesStack
                + ", vertexMap=" + vertexMap
                + ", matrix=" + matrix
                + '}';
    }
}
