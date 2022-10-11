package ru.nsu.fit.tsukanov.graphImplementations;

import ru.nsu.fit.tsukanov.basicGraph.EdgeDefault;
import ru.nsu.fit.tsukanov.basicGraph.Graph;

import java.util.*;

public class GraphIncMatrix<V extends Comparable<V>, E> implements Graph<V, E> {
    @Override
    public String toString() {
        return "GraphIncMatrix{" +
                "columns=" + columns +
                "\nmatrix=" + matrix +
                '}';
    }

    private enum Direction {
        NO, FROM, TO
    }

    private final Stack<Integer> indexesStackV;
    private final Stack<Integer> indexesStackE;
    private final TreeMap<V, Integer> vIntegerTreeMap;
    private final HashMap<EdgeDefault<V, E>, Integer> eIntegerHashMap;
    private final ArrayList<EdgeDefault<V, E>> columns;
    private final ArrayList<ArrayList<Direction>> matrix;

    public GraphIncMatrix() {
        indexesStackV = new Stack<>();
        indexesStackE = new Stack<>();
        vIntegerTreeMap = new TreeMap<>();
        eIntegerHashMap = new HashMap<>();
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
        if (!vIntegerTreeMap.containsKey(sourceVertex)
                || !vIntegerTreeMap.containsKey(targetVertex)) {
            return null;
        }
        Set<EdgeDefault<V, E>> set = new HashSet<>();
        int ind1 = vIntegerTreeMap.get(sourceVertex);
        int ind2 = vIntegerTreeMap.get(targetVertex);
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
        if (!vIntegerTreeMap.containsKey(sourceVertex)
                || !vIntegerTreeMap.containsKey(targetVertex)) {
            return null;
        }
        int ind1 = vIntegerTreeMap.get(sourceVertex);
        int ind2 = vIntegerTreeMap.get(targetVertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(ind1) == Direction.FROM
                    && matrix.get(i).get(ind2) == Direction.TO) {
                return columns.get(i);
            }
        }
        return null;
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
     * Add specify edge, if there is no such edge.
     *
     * @param e is edge that will be added to graph
     * @return true, if there are no equal Edge.
     */
    @Override
    public boolean addEdge(EdgeDefault<V, E> e) {
        EdgeDefault<V, E> edge = addEdge(e.getEdgeSource(), e.getEdgeTarget(), e.getObject(), e.getWeight());
        return edge != null;
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
        if (eIntegerHashMap.containsKey(newEdge)) {
            return null;
        }
        if (!vIntegerTreeMap.containsKey(sourceVertex)
                || !vIntegerTreeMap.containsKey(targetVertex)) {
            return null;
        }
        if (indexesStackE.isEmpty()) {
            indexesStackE.push(eIntegerHashMap.size());
            columns.add(newEdge);
        }


        int eIndex = indexesStackE.pop();
        columns.set(eIndex,newEdge);
        eIntegerHashMap.put(newEdge, eIndex);
        int vIndex1 = vIntegerTreeMap.get(sourceVertex);
        int vIndex2 = vIntegerTreeMap.get(targetVertex);

        if (eIndex >= matrix.size()) {
            ArrayList<Direction> newColumn = new ArrayList<>();
            for (int i = 0; i < vIntegerTreeMap.size(); i++) {
                newColumn.add(Direction.NO);
            }
            matrix.add(newColumn);
        }
        matrix.get(eIndex).set(vIndex1, Direction.FROM);
        matrix.get(eIndex).set(vIndex2, Direction.TO);

        return newEdge;
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
        if (vIntegerTreeMap.containsKey(v)) return false;
        boolean flag = false;
        if (indexesStackV.isEmpty()) {
            indexesStackV.push(vIntegerTreeMap.size());
            flag = true;
        }
        int vIndex = indexesStackV.pop();
        vIntegerTreeMap.put(v, vIndex);
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
        return eIntegerHashMap.containsKey(e);
    }

    /**
     * Check for exising vertex in the graph.
     *
     * @param v is vertex object
     * @return true, if the graph contains specify vertex.
     */
    @Override
    public boolean containsVertex(V v) {
        return vIntegerTreeMap.containsKey(v);
    }

    /**
     * Return set of all edges from graph.
     *
     * @return set of all edges from this graph.
     */
    @Override
    public Set<EdgeDefault<V, E>> edgeSet() {
        return eIntegerHashMap.keySet();
    }

    /**
     * Return set of all incoming edges to specify vertex.
     *
     * @param vertex is vertex
     * @return set of all incoming edges to specify vertex.
     */
    @Override
    public Set<EdgeDefault<V, E>> incomingEdgesOf(V vertex) {
        if (!vIntegerTreeMap.containsKey(vertex)) return null;
        Set<EdgeDefault<V, E>> set = new HashSet<>();
        int vIndex = vIntegerTreeMap.get(vertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(vIndex) == Direction.TO) {
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
        if (!vIntegerTreeMap.containsKey(vertex)) return null;
        Set<EdgeDefault<V, E>> set = new HashSet<>();
        int vIndex = vIntegerTreeMap.get(vertex);
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(vIndex) == Direction.FROM) {
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
        EdgeDefault<V, E> edge = getEdge(sourceVertex, targetVertex);
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
        if (!eIntegerHashMap.containsKey(e)) return false;
        int eIndex = eIntegerHashMap.get(e);
        Collections.fill(matrix.get(eIndex), Direction.NO);
        indexesStackE.push(eIndex);
        columns.set(eIndex, null);
        eIntegerHashMap.remove(e);
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
        if (!vIntegerTreeMap.containsKey(v)) return false;
        int vIndex = vIntegerTreeMap.get(v);
        removeAllEdges(outgoingEdgesOf(v));
        removeAllEdges(incomingEdgesOf(v));
        indexesStackV.push(vIndex);
        vIntegerTreeMap.remove(v);
        return true;
    }

    /**
     * Return set of all vertices from graph.
     *
     * @return set of all vertices from this graph
     */
    @Override
    public Set<V> vertexSet() {
        return vIntegerTreeMap.keySet();
    }
}
