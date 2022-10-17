package ru.nsu.fit.tsukanov.graph.implementations;

import java.util.ArrayList;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;

class Vertex<V, E> {

    ArrayList<EdgeDefault<V, E>> inEdge;

    ArrayList<EdgeDefault<V, E>> outEdge;


    Vertex() {
        inEdge = new ArrayList<>();
        outEdge = new ArrayList<>();
    }

}
