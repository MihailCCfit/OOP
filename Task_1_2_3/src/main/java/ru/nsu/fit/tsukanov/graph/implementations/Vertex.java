package ru.nsu.fit.tsukanov.graph.implementations;

import java.util.ArrayList;
import ru.nsu.fit.tsukanov.graph.core.EdgeDefault;

class Vertex<V extends Comparable<V>, E> {
    V object;

    ArrayList<EdgeDefault<V, E>> inEdge;

    ArrayList<EdgeDefault<V, E>> outEdge;


    Vertex(V object) {
        this.object = object;
        inEdge = new ArrayList<>();
        outEdge = new ArrayList<>();
    }


}
