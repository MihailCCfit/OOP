package ru.nsu.fit.tsukanov.graph.core;

import java.util.Collection;
import java.util.Set;

/**
 * Oriented weighted graph. There is class for edge
 *
 * @param <V> object, that will be vertex.
 * @param <E> object, that contained in Edge.
 * @see EdgeDefault
 * Implementations:
 * @see ru.nsu.fit.tsukanov.graph.implementations.GraphAdjMatrix
 * @see ru.nsu.fit.tsukanov.graph.implementations.GraphIncList
 * @see ru.nsu.fit.tsukanov.graph.implementations.GraphIncMatrix
 */
public interface Graph<V, E> {

    /**
     * Return all edges, that connects two vertices.
     * Edges is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex the start vertex
     * @param targetVertex the end vertex
     * @return set of all edges, that connects vertices
     */
    Set<EdgeDefault<V, E>> getEdges(V sourceVertex, V targetVertex);

    /**
     * Return edge, that connects two vertices and has null object.
     * Edge is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex the start vertex
     * @param targetVertex the end vertex
     * @return edge connects vertices
     */
    default EdgeDefault<V, E> getEdge(V sourceVertex, V targetVertex) {
        return getEdge(sourceVertex, targetVertex, null);
    }

    /**
     * Return edge, that connects two vertices and has specified object.
     * Edge is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex the start vertex
     * @param targetVertex the end vertex
     * @param obj          the object in edge
     * @return edge connects vertices
     */
    EdgeDefault<V, E> getEdge(V sourceVertex, V targetVertex, E obj);

    /**
     * Add edge, that connects vertices in direction from sourceVertex to targetVertex.
     * Edge has null as edge object.
     * If the edge already exists and the weight does not change, then return null.
     * Otherwise, returns true.
     *
     * @param sourceVertex the start vertex for edge
     * @param targetVertex the end vertex for edge
     * @return edge, that connects vertices in direction from sourceVertex to targetVertex
     */
    default EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex) {
        return addEdge(sourceVertex, targetVertex, null);
    }

    /**
     * Add edge, that connects vertices in direction from sourceVertex to targetVertex.
     * Edge has an object.
     * If the edge already exists and the weight does not change, then return null.
     * Otherwise, returns true.
     *
     * @param sourceVertex the start vertex for edge
     * @param targetVertex the end vertex for edge
     * @param object       the object that will be placed into edge
     * @return new Edge
     */
    EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E object);

    /**
     * Add specified edge, if there is no such edge.
     * If the edge already exists and the weight does not change, then return false.
     * Otherwise, returns true.
     *
     * @param e the edge that will be added to graph
     * @return true, if there are no equal Edge with equal weights.
     */
    boolean addEdge(EdgeDefault<V, E> e);

    /**
     * Create Edge with specified vertices, weight and object.
     * If the edge already exists and the weight does not change, then return null.
     * Otherwise, returns true.
     *
     * @param sourceVertex the start vertex for edge
     * @param targetVertex the end vertex for edge
     * @param e            the object that will be placed into edge
     * @param weight       the weight that will be placed into edge
     * @return edge
     */
    EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E e, double weight);

    /**
     * Create Edge with specified vertices, weight and null object.
     *
     * @param sourceVertex the start vertex for edge
     * @param targetVertex the end vertex for edge
     * @param weight       the weight that will be placed into edge
     * @return edge
     */
    default EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, double weight) {
        return addEdge(sourceVertex, targetVertex, null, weight);
    }

    /**
     * Add vertex to graph, if there is no such vertex.
     * If it isn't, then return false.
     *
     * @param v the vertex value, or vertex object.
     * @return true, if vertex has been added.
     */
    boolean addVertex(V v);

    /**
     * Check for existing edge between two vertices.
     *
     * @param sourceVertex the start vertex for some edge
     * @param targetVertex the end vertex for some edge
     * @return true, if there is edge from source to target.
     */

    boolean containsEdge(V sourceVertex, V targetVertex);

    /**
     * Check for exising edge in the graph.
     *
     * @param e the edge for checking
     * @return true, if the graph contains specified edge
     */
    boolean containsEdge(EdgeDefault<V, E> e);

    /**
     * Check for exising vertex in the graph.
     *
     * @param v the vertex object
     * @return true, if the graph contains specified vertex.
     */
    boolean containsVertex(V v);

    /**
     * Return set of all edges from graph.
     *
     * @return set of all edges from this graph.
     */
    Set<EdgeDefault<V, E>> edgeSet();

    /**
     * inDegree of specified vertex. If graph doesn't contain vertex, then return -1;
     *
     * @param vertex the vertex object.
     * @return inDegree of specified vertex. If graph doesn't contain vertex return -1;
     */
    default int inDegreeOf(V vertex) {
        if (!containsVertex(vertex)) {
            return -1;
        }
        return incomingEdgesOf(vertex).size();
    }

    /**
     * Return set of all incoming edges to specified vertex.
     *
     * @param vertex the vertex
     * @return set of all incoming edges to specified vertex.
     */
    Set<EdgeDefault<V, E>> incomingEdgesOf(V vertex);

    /**
     * outDegreeOf of specified vertex. If graph doesn't contain vertex, then return -1;
     *
     * @param vertex the vertex object.
     * @return outDegreeOf of specified vertex. If graph doesn't contain vertex return -1;
     */
    default int outDegreeOf(V vertex) {
        var outSet = outgoingEdgesOf(vertex);
        if (outSet == null) {
            return -1;
        }
        return outSet.size();
    }

    /**
     * Return set of all incoming edges to specified vertex.
     *
     * @param vertex the vertex
     * @return set of all incoming edges to specified vertex.
     */
    Set<EdgeDefault<V, E>> outgoingEdgesOf(V vertex);

    /**
     * Removes all of this graph's edges that are also contained in the
     * specified collection.
     *
     * @param edges the collections with edges.
     * @return true, if there is some changes in graph.
     */

    default boolean removeAllEdges(Collection<? extends EdgeDefault<V, E>> edges) {
        if (edges == null) {
            return false;
        }
        boolean flag = false;
        for (EdgeDefault<V, E> edge : edges) {
            flag |= removeEdge(edge);
        }
        return flag;
    }

    /**
     * Removes all of this graph's vertices that are also contained in the
     * specified collection.
     *
     * @param vertices the collections with vertices.
     * @return true, if there is some changes in graph.
     */
    default boolean removeAllVertices(Collection<? extends V> vertices) {
        if (vertices == null) {
            return false;
        }
        boolean flag = false;
        for (V vertex : vertices) {
            flag |= removeVertex(vertex);
        }
        return flag;
    }

    /**
     * Remove edge with null object from graph between two specified vertices.
     *
     * @param sourceVertex the source vertex maybe for some edge.
     * @param targetVertex the target vertex maybe for some edge.
     * @return removed edge.
     */
    EdgeDefault<V, E> removeEdge(V sourceVertex, V targetVertex);

    /**
     * Remove all edge from graph between two specified vertices.
     *
     * @param sourceVertex the source vertex maybe for some edge.
     * @param targetVertex the target vertex maybe for some edge.
     * @return true, if there is some changes in graph.
     */
    default boolean removeEdges(V sourceVertex, V targetVertex) {
        Set<EdgeDefault<V, E>> edges = getEdges(sourceVertex, targetVertex);
        if (edges == null) {
            return false;
        }
        boolean flag = false;
        for (EdgeDefault<V, E> edge : edges) {
            flag = removeEdge(edge) | flag;
        }
        return flag;
    }

    /**
     * Remove specified edge from graph. And check for graph changes.
     *
     * @param e the edge
     * @return true, if there was removing edge from graph.
     */

    boolean removeEdge(EdgeDefault<V, E> e);

    /**
     * Remove specified vertex from graph. And check for graph changes.
     *
     * @param v the vertex object
     * @return true, if there was removing vertex from graph.
     */
    boolean removeVertex(V v);

    /**
     * Return set of all vertices from graph.
     *
     * @return set of all vertices from this graph
     */
    Set<V> vertexSet();

    /**
     * Find edge between two specified vertices.
     * Set the edge weight to specified weight.
     *
     * @param sourceVertex the start vertex
     * @param targetVertex the start vertex
     * @param weight       the new weight
     */
    default void setEdgeWeight(V sourceVertex, V targetVertex, double weight) {
        var edge = getEdge(sourceVertex, targetVertex);
        if (edge != null) {
            edge.setWeight(weight);
        }
    }

}
