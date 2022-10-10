package ru.nsu.fit.tsukanov.basicGraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
public interface Graph<V,E> {
    default Set<EdgeDefault<V,E>> getAllEdges(V sourceVertex, V targetVertex){
        Set<EdgeDefault<V,E>> edgeDefaults = new HashSet<>();
        edgeDefaults.add(getEdge(sourceVertex, targetVertex));
        return edgeDefaults;
    }
    EdgeDefault<V,E> getEdge(V sourceVertex, V targetVertex);

    default EdgeDefault<V,E> getEdge(E e){
        throw new UnsupportedOperationException("Not implemented");
    }

    default EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex){
        return addEdge(sourceVertex,targetVertex, (E) null);
    }

    EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex, E object);

    boolean addEdge(EdgeDefault<V,E> e);

    EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex, E e, double weight);
    default EdgeDefault<V,E> addEdge(V sourceVertex, V targetVertex, double weight){
        return addEdge(sourceVertex, targetVertex, null, weight);
    }

    boolean addVertex(V v);

    boolean containsEdge(V sourceVertex, V targetVertex);

    boolean containsEdge(EdgeDefault<V,E> e);

    boolean containsVertex(V v);

    Set<EdgeDefault<V,E>> edgeSet();

    default int inDegreeOf(V vertex){
        return incomingEdgesOf(vertex).size();
    }

    Set<EdgeDefault<V,E>> incomingEdgesOf(V vertex);

    default int outDegreeOf(V vertex){
        return  outgoingEdgesOf(vertex).size();
    }

    Set<EdgeDefault<V,E>> outgoingEdgesOf(V vertex);

    default boolean removeAllEdges(Collection<? extends EdgeDefault<V,E>> edges){
        boolean flag = false;
        for (EdgeDefault<V,E> edge : edges) {
            flag |= removeEdge(edge);
        }
        return flag;
    }

    default boolean removeAllVertices(Collection<? extends V> vertices){
        boolean flag = false;
        for (V vertex : vertices) {
            flag |= removeVertex(vertex);
        }
        return flag;
    }

    default EdgeDefault<V,E> removeEdge(V sourceVertex, V targetVertex){
        EdgeDefault<V,E> edge = getEdge(sourceVertex, targetVertex);
        removeEdge(edge);
        return edge;
    }

    boolean removeEdge(EdgeDefault<V,E> e);

    boolean removeVertex(V v);

    Set<V> vertexSet();

    default void setEdgeWeight(EdgeDefault<V,E> e, double weight){
        e.setWeight(weight);
    }

    default void setEdgeWeight(V sourceVertex, V targetVertex, double weight) {
        getEdge(sourceVertex, targetVertex).setWeight(weight);
    }

}
