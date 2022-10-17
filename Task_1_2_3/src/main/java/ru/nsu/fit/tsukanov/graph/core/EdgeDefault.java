package ru.nsu.fit.tsukanov.graph.core;

import java.util.Objects;

/**
 * Edge class, which is used for graph.
 * It has object, weight, source and target vertex
 *
 * @param <V> object in source and target vertex
 * @param <E> object in the edge
 */
public class EdgeDefault<V, E> {
    public V getSourceVertex() {
        return sourceVertex;
    }

    private final V sourceVertex;

    public V getTargetVertex() {
        return targetVertex;
    }

    /**
     * Return string representation of edge.
     * sourceVertex + targetVertex + weight + object.
     * @return string representation of edge
     */
    @Override
    public String toString() {
        return "EdgeDefault{"
                + "sourceVertex=" + sourceVertex
                + ", targetVertex=" + targetVertex
                + ", weight=" + weight
                + ", object=" + object
                + '}';
    }

    private final V targetVertex;
    private double weight;
    /**
     * Default weight for edges.
     */
    public static final double DEFAULT_WEIGHT = 1.0;

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

    /**
     * Creates edge with specify vertices, object and weight.
     *
     * @param start is source vertex
     * @param end is target vertex
     * @param object is object that is placed into edge
     * @param weight is double-value weight of edge
     */
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

    /**
     * Compares for equality of edges.
     * Check for
     * @param obj edge that will be compared with this edge.
     * @return true, if objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof EdgeDefault<?,?>) {
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
