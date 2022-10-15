package ru.nsu.fit.tsukanov.graph.implementations;

import java.util.*;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;
import ru.nsu.fit.tsukanov.graph.core.Graph;


/**
 * Oriented weighted graph. There is class for edge.
 * It uses incident matrix for method implementation.
 * Big-O notations:
 * Get:
 * V-V O(E)
 * V-* O(E)
 * *-* O(E)
 * Contains:
 * V O(log V) - because there is treemap
 * E O(1) - because there is hashmap
 * Add/Remove:
 * V O(E)
 * E O(N)
 * Memory:
 * O(N*E)
 * ev - vertex's edges
 * Not bad.
 *
 * @param <V> object, that will be vertex.
 * @param <E> object, that contained in Edge.
 * @see EdgeDefault
 */
public class GraphIncMatrix<V extends Comparable<V>, E> implements Graph<V, E> {
    @Override
    public String toString() {
        return "GraphIncMatrix{"
                + "columns=" + columns
                + "\nmatrix=" + matrix
                + '}';
    }

    private enum Direction {
        NO, FROM, TO
    }

    private final Stack<Integer> indexesStackV;
    private final Stack<Integer> indexesStackE;
    private final TreeMap<V, Integer> vertexMap;
    private final HashMap<EdgeDefault<V, E>, Integer> edgeMap;
    private final ArrayList<EdgeDefault<V, E>> columns;
    private final ArrayList<ArrayList<Direction>> matrix;

    /**
     * Creates maps, stack for indexing, and matrix
     */
    public GraphIncMatrix() {
        indexesStackV = new Stack<>();
        indexesStackE = new Stack<>();
        vertexMap = new TreeMap<>();
        edgeMap = new HashMap<>();
        matrix = new ArrayList<>();
        columns = new ArrayList<>();
    }

    /**
     * Return all edges, that connects two vertices.
     * Edges is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex start vertex
     * @param targetVertex end vertex
     * @return set of all edges, that connects vertices
     */
    @Override
    public Set<EdgeDefault<V, E>> getAllEdges(V sourceVertex, V targetVertex) {
        if (!containsVertex(sourceVertex)
                || !containsVertex(targetVertex)) {
            return null;
        }
        Set<EdgeDefault<V, E>> set = new HashSet<>();
        int ind1 = vertexMap.get(sourceVertex);
        int ind2 = vertexMap.get(targetVertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(ind1) == Direction.FROM
                    && matrix.get(i).get(ind2) == Direction.TO) {
                set.add(columns.get(i));
            }
        }
        return set;
    }

    /**
     * Return edge, that connects two vertices.
     * Edge is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex start vertex
     * @param targetVertex end vertex
     * @return edge connects vertices
     */
    @Override
    public EdgeDefault<V, E> getEdge(V sourceVertex, V targetVertex) {
        if (!containsVertex(sourceVertex)
                || !containsVertex(targetVertex)) {
            return null;
        }
        int ind1 = vertexMap.get(sourceVertex);
        int ind2 = vertexMap.get(targetVertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(ind1) == Direction.FROM
                    && matrix.get(i).get(ind2) == Direction.TO) {
                return columns.get(i);
            }
        }
        return null;
    }

    /**
     * Add specify edge, if there is no such edge.
     *
     * @param e is edge that will be added to graph
     * @return true, if there are no equal Edge.
     */
    @Override
    public boolean addEdge(EdgeDefault<V, E> e) {
        if (e == null) {
            return false;
        }
        if (containsEdge(e)) {
            return false;
        }
        V sourceVertex = e.getSourceVertex();
        V targetVertex = e.getTargetVertex();
        if (!containsVertex(sourceVertex)
                || !containsVertex(targetVertex)) {
            return false;
        }
        if (indexesStackE.isEmpty()) {
            indexesStackE.push(edgeMap.size());
            columns.add(e);
        }
        int edgeIndex = indexesStackE.pop();
        columns.set(edgeIndex, e);
        edgeMap.put(e, edgeIndex);
        int vertIndex1 = vertexMap.get(sourceVertex);
        int vertIndex2 = vertexMap.get(targetVertex);

        if (edgeIndex >= matrix.size()) {
            ArrayList<Direction> newColumn = new ArrayList<>();
            for (int i = 0; i < vertexMap.size(); i++) {
                newColumn.add(Direction.NO);
            }
            matrix.add(newColumn);
        }
        matrix.get(edgeIndex).set(vertIndex1, Direction.FROM);
        matrix.get(edgeIndex).set(vertIndex2, Direction.TO);

        return true;
    }

    /**
     * Add edge, that connects vertices in direction from sourceVertex to targetVertex.
     * Edge has an object.
     *
     * @param sourceVertex is start vertex for edge
     * @param targetVertex is end vertex for edge
     * @param object       is object that will be placed into edge
     * @return new Edge
     */
    @Override
    public EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E object) {
        return addEdge(sourceVertex, targetVertex, object, EdgeDefault.DEFAULT_WEIGHT);
    }


    /**
     * Create Edge with specify vertices, weight and object.
     *
     * @param sourceVertex is start vertex for edge
     * @param targetVertex is end vertex for edge
     * @param e            is object that will be placed into edge
     * @param weight       is weight that will be placed into edge
     * @return edge
     */
    @Override
    public EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E e, double weight) {
        EdgeDefault<V, E> newEdge = new EdgeDefault<>(sourceVertex, targetVertex, e, weight);
        return addEdge(newEdge) ? newEdge : null;
    }

    /**
     * Add vertex to graph, if there is no such vertex.
     * If it isn't, then return false.
     *
     * @param v is vertex value, or vertex object.
     * @return true, if vertex has been added.
     */
    @Override
    public boolean addVertex(V v) {
        if (v == null) {
            return false;
        }
        if (containsVertex(v)) {
            return false;
        }
        boolean flag = false;
        if (indexesStackV.isEmpty()) {
            indexesStackV.push(vertexMap.size());
            flag = true;
        }
        int vIndex = indexesStackV.pop();
        vertexMap.put(v, vIndex);
        if (flag) {
            for (ArrayList<GraphIncMatrix.Direction> directions : matrix) {
                directions.add(Direction.NO);
            }
        }
        return true;
    }

    /**
     * Check for existing edge between two vertices.
     *
     * @param sourceVertex is start vertex for some edge
     * @param targetVertex is end vertex for some edge
     * @return true, if there is edge from source to target.
     */
    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {

        return getEdge(sourceVertex, targetVertex) != null;
    }

    /**
     * Check for exising edge in the graph.
     *
     * @param e is edge for checking
     * @return true, if the graph contains specify edge
     */
    @Override
    public boolean containsEdge(EdgeDefault<V, E> e) {
        if (e == null) {
            return false;
        }
        if (e.getSourceVertex() == null
                || e.getTargetVertex() == null) {
            return false;
        }
        return edgeMap.containsKey(e);
    }

    /**
     * Check for exising vertex in the graph.
     *
     * @param v is vertex object
     * @return true, if the graph contains specify vertex.
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
        return edgeMap.keySet();
    }

    /**
     * Return set of all incoming edges to specify vertex.
     *
     * @param vertex is vertex
     * @return set of all incoming edges to specify vertex.
     */
    @Override
    public Set<EdgeDefault<V, E>> incomingEdgesOf(V vertex) {
        if (!containsVertex(vertex)) {
            return null;
        }
        Set<EdgeDefault<V, E>> set = new HashSet<>();
        int vertIndex = vertexMap.get(vertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(vertIndex) == Direction.TO) {
                set.add(columns.get(i));
            }
        }
        return set;
    }

    /**
     * Return set of all incoming edges to specify vertex.
     *
     * @param vertex is vertex
     * @return set of all incoming edges to specify vertex.
     */
    @Override
    public Set<EdgeDefault<V, E>> outgoingEdgesOf(V vertex) {
        if (!containsVertex(vertex)) {
            return null;
        }
        Set<EdgeDefault<V, E>> set = new HashSet<>();
        int vertIndex = vertexMap.get(vertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(vertIndex) == Direction.FROM) {
                set.add(columns.get(i));
            }
        }
        return set;
    }

    /**
     * Remove edge with null object from graph between two specify vertices.
     *
     * @param sourceVertex is source vertex maybe for some edge.
     * @param targetVertex is target vertex maybe for some edge.
     * @return removed edge.
     */
    @Override
    public EdgeDefault<V, E> removeEdge(V sourceVertex, V targetVertex) {
        if (sourceVertex == null || targetVertex == null) {
            return null;
        }
        EdgeDefault<V, E> edge = getEdge(sourceVertex, targetVertex);
        if (edge == null) {
            return null;
        }
        removeEdge(edge);
        return edge;
    }

    /**
     * Remove specify edge from graph. And check for graph changes.
     *
     * @param e is edge
     * @return true, if there was removing edge from graph.
     */
    @Override
    public boolean removeEdge(EdgeDefault<V, E> e) {
        if (e == null) {
            return false;
        }
        if (!containsEdge(e)) {
            return false;
        }

        int edgeIndex = edgeMap.get(e);
        Collections.fill(matrix.get(edgeIndex), Direction.NO);
        indexesStackE.push(edgeIndex);
        columns.set(edgeIndex, null);
        edgeMap.remove(e);
        return true;
    }

    /**
     * Remove specify vertex from graph. And check for graph changes.
     *
     * @param v is vertex object
     * @return true, if there was removing vertex from graph.
     */
    @Override
    public boolean removeVertex(V v) {
        if (!containsVertex(v)) {
            return false;
        }
        int vertIndex = vertexMap.get(v);
        removeAllEdges(outgoingEdgesOf(v));
        removeAllEdges(incomingEdgesOf(v));
        indexesStackV.push(vertIndex);
        vertexMap.remove(v);
        return true;
    }

    /**
     * Return set of all vertices from graph.
     *
     * @return set of all vertices from this graph
     */
    @Override
    public Set<V> vertexSet() {
        return vertexMap.keySet();
    }
}
