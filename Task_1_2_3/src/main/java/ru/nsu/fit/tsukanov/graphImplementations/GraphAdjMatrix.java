package ru.nsu.fit.tsukanov.graphImplementations;

import ru.nsu.fit.tsukanov.basicGraph.EdgeDefault;
import ru.nsu.fit.tsukanov.basicGraph.Graph;

import java.util.*;

public class GraphAdjMatrix<V extends Comparable<V>, E> implements Graph<V, E> {
    Stack<Integer> indexesStack;
    TreeMap<V, Integer> vIntegerTreeMap;
    ArrayList<ArrayList<ArrayList<EdgeDefault<V, E>>>> matrix;

    public GraphAdjMatrix() {
        this.indexesStack = new Stack<>();
        this.vIntegerTreeMap = new TreeMap<>();
        this.matrix = new ArrayList<>();
    }

    private ArrayList<EdgeDefault<V, E>> getListEdges(V sourceVertex, V targetVertex) {
        int i = vIntegerTreeMap.get(sourceVertex);
        int k = vIntegerTreeMap.get(targetVertex);
        return matrix.get(i).get(k);
    }

    @Override
    public Set<EdgeDefault<V, E>> getAllEdges(V sourceVertex, V targetVertex) {
        return new HashSet<>(getListEdges(sourceVertex, targetVertex));
    }

    @Override
    public EdgeDefault<V, E> getEdge(V sourceVertex, V targetVertex) {
        ArrayList<EdgeDefault<V, E>> l = getListEdges(sourceVertex, targetVertex);
        if (l.isEmpty()) return null;
        return getListEdges(sourceVertex, targetVertex).get(0);
    }

    @Override
    public EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E object) {
        EdgeDefault<V, E> edge = new EdgeDefault<>(sourceVertex, targetVertex, object);
        getListEdges(sourceVertex, targetVertex).add(edge);
        return edge;
    }

    @Override
    public boolean addEdge(EdgeDefault<V, E> e) {
        return getListEdges(e.getSourceVertex(), e.getTargetVertex()).add(e);
    }

    @Override
    public EdgeDefault<V, E> addEdge(V sourceVertex, V targetVertex, E e, double weight) {
        EdgeDefault<V, E> edgeDefault = new EdgeDefault<>(sourceVertex, targetVertex, e, weight);
        getListEdges(sourceVertex, targetVertex).add(edgeDefault);
        return edgeDefault;
    }

    @Override
    public boolean addVertex(V v) {
        if (indexesStack.isEmpty()) {
            indexesStack.push(vIntegerTreeMap.size());
        }
        if (vIntegerTreeMap.containsKey(v)) return false;
        Integer index = indexesStack.pop();
        vIntegerTreeMap.put(v, index);

        if (index >= matrix.size()) {
            int newSize = matrix.size() + 1;
            ArrayList<ArrayList<EdgeDefault<V, E>>> newCol = new ArrayList<>();
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

    @Override
    public boolean containsEdge(V sourceVertex, V targetVertex) {
        return !getListEdges(sourceVertex, targetVertex).isEmpty();
    }

    @Override
    public boolean containsEdge(EdgeDefault<V, E> e) {
        return containsEdge(e.getSourceVertex(), e.getTargetVertex());
    }

    @Override
    public boolean containsVertex(V v) {
        return vIntegerTreeMap.containsKey(v);
    }

    @Override
    public Set<EdgeDefault<V, E>> edgeSet() {
        Set<EdgeDefault<V, E>> edgeSet = new HashSet<>();
        for (V v : vIntegerTreeMap.keySet()) {
            edgeSet.addAll(outgoingEdgesOf(v));
        }
        return edgeSet;
    }

    @Override
    public Set<EdgeDefault<V, E>> incomingEdgesOf(V vertex) {
        int index = vIntegerTreeMap.get(vertex);
        Set<EdgeDefault<V, E>> edgeDefaultSet = new HashSet<>();
        for (ArrayList<ArrayList<EdgeDefault<V, E>>> list : matrix) {
            edgeDefaultSet.addAll(list.get(index));
        }
        return edgeDefaultSet;
    }

    @Override
    public Set<EdgeDefault<V, E>> outgoingEdgesOf(V vertex) {
        int index = vIntegerTreeMap.get(vertex);
        ArrayList<ArrayList<EdgeDefault<V, E>>> arrayLists = matrix.get(index);
        Set<EdgeDefault<V, E>> edgeDefaultSet = new HashSet<>();
        for (ArrayList<EdgeDefault<V, E>> arrayList : arrayLists) {
            edgeDefaultSet.addAll(arrayList);
        }

        return edgeDefaultSet;
    }

    @Override
    public boolean removeEdge(EdgeDefault<V, E> e) {
        int i = vIntegerTreeMap.get(e.getEdgeSource());
        int k = vIntegerTreeMap.get(e.getTargetVertex());
        return matrix.get(i).get(k).remove(e);
    }

    @Override
    public boolean removeVertex(V v) {
        if (!vIntegerTreeMap.containsKey(v)) return false;
        int index = vIntegerTreeMap.get(v);
        ArrayList<ArrayList<EdgeDefault<V, E>>> arrayLists = matrix.get(index);
        for (ArrayList<EdgeDefault<V, E>> list : arrayLists) {
            list.clear();
        }
        for (ArrayList<ArrayList<EdgeDefault<V, E>>> lists : matrix) {
            lists.get(index).clear();
        }
        vIntegerTreeMap.remove(v);
        return true;
    }

    @Override
    public Set<V> vertexSet() {
        return vIntegerTreeMap.keySet();
    }
}
