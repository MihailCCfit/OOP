package ru.nsu.fit.tsukanov.basicGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Oriented weighted graph. There is class for edge
 * @see EdgeDefault
 *
 * @param <V> object, that will be vertex.
 * @param <E> object, that contained in Edge.
 */
public interface Graph<V,E> {
    /** Return all edges, that connects two vertices.
     * Edges is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex start vertex
     * @param targetVertex end vertex
     * @return set of all edges, that connects vertices
     */
    default Set<EdgeDefault<V,E>> getAllEdges(V sourceVertex, V targetVertex){
        Set<EdgeDefault<V,E>> edgeDefaults = new HashSet<>();
        edgeDefaults.add(getEdge(sourceVertex, targetVertex));
        return edgeDefaults;
    }
    /** Return edge, that connects two vertices.
     * Edge is directed from sourceVertex to targetVertex.
     *
     * @param sourceVertex start vertex
     * @param targetVertex end vertex
     * @return edge connects vertices
     */
    EdgeDefault<V,E> getEdge(V sourceVertex, V targetVertex);

    /** Retuturn Edge according to element
     *
     * @param e element, that maybe corresponded to some edge
     * @return Edge according to e
     */
    default EdgeDefault<V,E> getEdge(E e){
        throw new UnsupportedOperationException("Not implemented");
    }

    /** Add edge, that connects vertices in direction from sourceVertex to targetVertex.
     * Edge has null as edge object.
     *
     * @param sourceVertex is start vertex for edge
     * @param targetVertex is end vertex for edge
     * @return edge, that connects vertices in direction from sourceVertex to targetVertex
     */
    default EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex){
        return addEdge(sourceVertex,targetVertex, (E) null);
    }

    /**Add edge, that connects vertices in direction from sourceVertex to targetVertex.
     * Edge has an object.
     *
     * @param sourceVertex is start vertex for edge
     * @param targetVertex is end vertex for edge
     * @param object is object that will be placed into edge
     * @return new Edge
     */
    EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex, E object);

    /** Add specify edge, if there is no such edge.
     *
     * @param e is edge that will be added to graph
     * @return true, if there are no equal Edge.
     */
    boolean addEdge(EdgeDefault<V,E> e);

    /** Create Edge with specify vertices, weight and object.
     *
     * @param sourceVertex is start vertex for edge
     * @param targetVertex is end vertex for edge
     * @param e is object that will be placed into edge
     * @param weight is weight that will be placed into edge
     * @return edge
     */
    EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex, E e, double weight);

    /**
     * Create Edge with specify vertices, weight and null object.
     *
     * @param sourceVertex is start vertex for edge
     * @param targetVertex is end vertex for edge
     * @param weight is weight that will be placed into edge
     * @return edge
     */
    default EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex, double weight){
        return addEdge(sourceVertex, targetVertex, null, weight);
    }

    /** Add vertex to graph, if there is no such vertex.
     * If it isn't, then return false.
     *
     * @param v is vertex value, or vertex object.
     * @return true, if vertex has been added.
     */
    boolean addVertex(V v);

    /** Check for existing edge between two vertices.
     *
     * @param sourceVertex is start vertex for some edge
     * @param targetVertex is end vertex for some edge
     * @return true, if there is edge from source to target.
     */

    boolean containsEdge(V sourceVertex, V targetVertex);

    /** Check for exising edge in the graph.
     *
     * @param e is edge for checking
     * @return true, if the graph contains specify edge
     */
    boolean containsEdge(EdgeDefault<V,E> e);

    /** Check for exising vertex in the graph.
     *
     * @param v is vertex object
     * @return true, if the graph contains specify vertex.
     */
    boolean containsVertex(V v);

    /** Return set of all edges from graph.
     *
     * @return set of all edges from this graph.
     */
    Set<EdgeDefault<V,E>> edgeSet();

    /** inDegree of specify vertex. If graph doesn't contain vertex, then return -1;
     *
     * @param vertex is vertex object.
     * @return inDegree of specify vertex. If graph doesn't contain vertex return -1;
     */
    default int inDegreeOf(V vertex){
        if (!containsVertex(vertex)) return -1;
        return incomingEdgesOf(vertex).size();
    }

    /** Return set of all incoming edges to specify vertex.
     *
     * @param vertex is vertex
     * @return set of all incoming edges to specify vertex.
     */
    Set<EdgeDefault<V,E>> incomingEdgesOf(V vertex);

    /** outDegreeOf of specify vertex. If graph doesn't contain vertex, then return -1;
     *
     * @param vertex is vertex object.
     * @return outDegreeOf of specify vertex. If graph doesn't contain vertex return -1;
     */
    default int outDegreeOf(V vertex){
        return  outgoingEdgesOf(vertex).size();
    }

    /** Return set of all incoming edges to specify vertex.
     *
     * @param vertex is vertex
     * @return set of all incoming edges to specify vertex.
     */
    Set<EdgeDefault<V,E>> outgoingEdgesOf(V vertex);

    /** Removes all of this graph's edges that are also contained in the
     * specified collection.
     *
     * @param edges is collections with edges.
     * @return true, if there is some changes in graph.
     */

    default boolean removeAllEdges(Collection<? extends EdgeDefault<V,E>> edges){
        boolean flag = false;
        for (EdgeDefault<V,E> edge : edges) {
            flag |= removeEdge(edge);
        }
        return flag;
    }

    /** Removes all of this graph's vertices that are also contained in the
     * specified collection.
     *
     * @param vertices is collections with vertices.
     * @return true, if there is some changes in graph.
     */
    default boolean removeAllVertices(Collection<? extends V> vertices){
        boolean flag = false;
        for (V vertex : vertices) {
            flag |= removeVertex(vertex);
        }
        return flag;
    }

    /** Remove edge with null object from graph between two specify vertices.
     *
     * @param sourceVertex is source vertex maybe for some edge.
     * @param targetVertex is target vertex maybe for some edge.
     * @return removed edge.
     */
     EdgeDefault<V,E> removeEdge(V sourceVertex, V targetVertex);

    /** Remove specify edge from graph. And check for graph changes.
     *
     * @param e is edge
     * @return true, if there was removing edge from graph.
     */

    boolean removeEdge(EdgeDefault<V,E> e);

    /** Remove specify vertex from graph. And check for graph changes.
     *
     * @param v is vertex object
     * @return true, if there was removing vertex from graph.
     */
    boolean removeVertex(V v);

    /** Return set of all vertices from graph.
     *
     * @return set of all vertices from this graph
     */
    Set<V> vertexSet();

    /** Set edge weight to specify weight.
     *
     * @param e is edge
     * @param weight is new weight for specify edge
     */
    default void setEdgeWeight(EdgeDefault<V,E> e, double weight){
        e.setWeight(weight);
    }

    /** Find edge between two specify vertices.
     * Set the edge weight to specify weight.
     *
     * @param sourceVertex is start vertex
     * @param targetVertex is start vertex
     * @param weight is new weight
     */
    default void setEdgeWeight(V sourceVertex, V targetVertex, double weight) {
        getEdge(sourceVertex, targetVertex).setWeight(weight);
    }

}
