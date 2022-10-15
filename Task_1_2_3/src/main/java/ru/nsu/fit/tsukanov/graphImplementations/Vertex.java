package ru.nsu.fit.tsukanov.graphImplementations;

import ru.nsu.fit.tsukanov.core.EdgeDefault;

import java.util.ArrayList;

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
