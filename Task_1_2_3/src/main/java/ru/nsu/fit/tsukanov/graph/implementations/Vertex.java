package ru.nsu.fit.tsukanov.graph.implementations;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;

class Vertex<V, E> {

    List<EdgeDefault<V, E>> inEdge;

    List<EdgeDefault<V, E>> outEdge;


    Vertex() {
        inEdge = new ArrayList<>();
        outEdge = new ArrayList<>();
    }

}
