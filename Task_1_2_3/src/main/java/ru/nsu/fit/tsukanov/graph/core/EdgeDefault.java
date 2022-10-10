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

    /**
     * Default weight for edges.
     */
    public static final double DEFAULT_WEIGHT = 1.0;
    private final V sourceVertex;
    private final V targetVertex;
    private double weight;
    private E object;

    /**
     * Returns object that placed in the edge.
     *
     * @return object that placed in the edge.
     */
    public E getObject() {
        return object;
    }

    /**
     * Sets the object in edge.
     *
     * @param object that will be placed into this edge.
     */
    public void setObject(E object) {
        this.object = object;
    }


    /**
     * Return source vertex of this edge.
     *
     * @return source vertex of this edge
     */
    public V getSourceVertex() {
        return sourceVertex;
    }

    /**
     * Return target vertex of this edge.
     *
     * @return target vertex of this edge
     */
    public V getTargetVertex() {
        return targetVertex;
    }

    /**
     * Return the edge weight.
     *
     * @return the edge weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the edge weight.
     *
     * @param weight that will be saved in the edge.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Creates edge with specified vertices, null object and default weight.
     *
     * @param start is source vertex
     * @param end   is target vertex
     */
    public EdgeDefault(V start, V end) {
        this(start, end, null, DEFAULT_WEIGHT);
    }

    /**
     * Creates edge with specified vertices, object and default weight.
     *
     * @param start  is source vertex
     * @param end    is target vertex
     * @param object is object that is placed into edge
     */
    public EdgeDefault(V start, V end, E object) {
        this(start, end, object, DEFAULT_WEIGHT);
    }

    /**
     * Creates edge with specified vertices, object and weight.
     *
     * @param start  is source vertex
     * @param end    is target vertex
     * @param object is object that is placed into edge
     * @param weight is double-value weight of edge
     */
    public EdgeDefault(V start, V end, E object, double weight) {
        this.sourceVertex = start;
        this.targetVertex = end;
        this.weight = weight;
        this.object = object;
    }


    /**
     * Compares for equality of edges.
     * Check source and target vertices, object.
     *
     * @param o edge that will be compared with this edge.
     * @return true, if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EdgeDefault<?, ?> that = (EdgeDefault<?, ?>) o;
        return Objects.equals(sourceVertex, that.sourceVertex)
                && Objects.equals(targetVertex, that.targetVertex)
                && Objects.equals(object, that.object);
    }

    /**
     * Return hashCode depends on source, target, object.
     *
     * @return hashCode depends on source, target, object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sourceVertex, targetVertex, object);
    }

    /**
     * Return string representation of edge.
     * sourceVertex + targetVertex + weight + object.
     *
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
}
