package ru.nsu.fit.tsukanov.basicGraph;

import java.util.Objects;

public class EdgeDefault<V, E> {
    public V getSourceVertex() {
        return sourceVertex;
    }

    private final V sourceVertex;

    public V getTargetVertex() {
        return targetVertex;
    }

    @Override
    public String toString() {
        return "EdgeDefault{" +
                "sourceVertex=" + sourceVertex +
                ", targetVertex=" + targetVertex +
                ", weight=" + weight +
                ", object=" + object +
                '}';
    }

    private final V targetVertex;
    private double weight;
    public static final double  DEFAULT_WEIGHT = 1.0;

    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }

    private E object;

    public EdgeDefault(V start, V end) {
        this(start, end, null, DEFAULT_WEIGHT);
    }

    public EdgeDefault(V start, V end, E object) {
        this(start, end, object, DEFAULT_WEIGHT);
    }

    public EdgeDefault(V start, V end, E object, double weight) {
        this.sourceVertex = start;
        this.targetVertex = end;
        this.weight = weight;
        this.object = object;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public V getEdgeSource() {
        return sourceVertex;
    }

    public V getEdgeTarget() {
        return targetVertex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() == getClass()) {
            EdgeDefault<?, ?> temp = (EdgeDefault<?, ?>) obj;
            return temp.sourceVertex.equals(sourceVertex)
                    && temp.targetVertex.equals(this.targetVertex)
                    && ((temp.object == null && this.object == null)
                    || Objects.equals(temp.object, this.object));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceVertex, targetVertex, object);
    }
}
