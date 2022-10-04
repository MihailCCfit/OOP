package ru.nsu.fit.tsukanov;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Tree is collection. User can add new Nodes to root, or to other Nodes.
 * It's easy to create branches, this structure is fragile.
 *
 * @param <T> for objects in Tree.
 */
public class Tree<T> implements Collection<T> {
    private final Node<T> root;

    /**
     * Node class for Tree. Node has many nodes and
     * has one father node. There are object values in
     * each node.
     *
     * @param <T> that is given from tree
     */
    public static class Node<T> {
        private int cur = -1;
        private final ArrayList<Node<T>> children;
        private Node<T> father;
        private T object;

        /**
         * Create new Node with saving object in this.
         *
         * @param obj object that will be place into node
         */
        Node(T obj) {
            setObject(obj);
            children = new ArrayList<>();
        }

        /**
         * Add in this node child_list specify node.
         *
         * @param node that exist.
         * @return new node.
         */
        Node<T> add(Node<T> node) {
            getChildren().add(node);
            node.father = this;
            return node;
        }

        /**
         * Create node with object and add that to this node.
         *
         * @param obj that will be saved in tree node.
         * @return new node with object.
         */
        Node<T> add(T obj) {
            Node<T> node = new Node<>(obj);
            return this.add(node);
        }

        /**
         * return String representation of {@code Node}.
         *
         * @return String representation.
         */
        @Override
        public String toString() {
            return "Node{"
                    + "cur="
                    + cur
                    + "[" + getObject()
                    + "], children="
                    + getChildren()
                    + '}';
        }

        /**
         * Remove this node from parent node.
         */
        void remove() {
            var father = this.getFather();
            father.getChildren().remove(this);
        }

        /**
         * Return object that placed in this node.
         * @return object of this node.
         */
        public T getObject() {
            return object;
        }

        /**
         * Set object in this node.
         * @param object that's placed in the node.
         */

        public void setObject(T object) {
            this.object = object;
        }

        /**
         * Return children of this node.
         * @return child nodes list
         */
        public ArrayList<Node<T>> getChildren() {
            return children;
        }

        /**
         * Get the upper (father) node.
         * @return parent node.
         */
        public Node<T> getFather() {
            return father;
        }
    }

    /**
     * Create new Tree with root node.
     */
    public Tree() {
        root = new Node<>(null);
        root.father = null;
    }

    /**
     * Create Tree with root node. Add to root new node with specify object.
     *
     * @param obj that will be added to tree.
     */
    public Tree(T obj) {
        root = new Node<>(null);
        root.add(obj);
        root.father = null;
    }

    /**
     * Count nodes in tree by BFS iterations.
     *
     * @return number of nodes in tree.
     */
    @Override
    public int size() {
        int count = 0;
        for (Object ignored : this) {
            count++;
        }
        return count;
    }

    /**
     * return true if there is no element in tree, else - false.
     *
     * @return true if there is no element in tree, else - false.
     */
    @Override
    public boolean isEmpty() {
        return root.getChildren().isEmpty();
    }

    /**
     * Check tree for containing specify element.
     *
     * @param o element whose presence in this collection is to be tested
     * @return true if tree contains this object.
     */
    @Override
    public boolean contains(Object o) {
        TreeIterBFS iterBFS = this.iteratorBFS();
        while (iterBFS.hasNext()) {
            var obj = iterBFS.next();
            if (o.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Similar to filter(object.equals),
     * but return nodes rather than objects.
     *
     * @param o object for filter
     * @return ArrayList of nodes.
     */
    public ArrayList<Node<T>> contain(Object o) {
        ArrayList<Node<T>> nodeArrayList = new ArrayList<>();
        TreeIterBFS iterBFS = this.iteratorBFS();
        while (iterBFS.hasNext()) {
            var node = iterBFS.nextN();
            if (node.getObject().equals(o)) {
                nodeArrayList.add(node);
            }
        }
        return nodeArrayList;
    }

    /**
     * Return all nodes, that have true test by predicate.
     *
     * @param predicate for choosing nodes
     * @return ArrayList with objects
     */
    public ArrayList<Node<T>> contain(Predicate<Node<T>> predicate) {
        ArrayList<Node<T>> nodeArrayList = new ArrayList<>();
        TreeIterBFS iterBFS = this.iteratorBFS();
        while (iterBFS.hasNext()) {
            var node = iterBFS.nextN();
            if (predicate.test(node)) {
                nodeArrayList.add(node);
            }
        }
        return nodeArrayList;
    }

    /**
     * Create and return new BFS iterator.
     *
     * @return BFS iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new TreeIterDFS();
    }

    /**
     * Add object to tree.
     *
     * @param o element whose presence in this collection is to be ensured
     * @return true if object isn't null
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean add(Object o) {
        root.add((T) o);
        return true;
    }

    /**
     * Create new node with specify object and put it into specify node.
     *
     * @param node to which will add new node with specify object.
     * @param o    object that will be put in node.
     * @return new created node.
     * @throws IllegalArgumentException if node is null pointr
     */
    public Node<T> add(Node<T> node, T o) throws IllegalArgumentException {
        if (node == null) {
            throw new IllegalArgumentException("Null pointer");
        }
        return node.add(o);
    }

    /**
     * Create node and put object into node.
     *
     * @param o object
     * @return node with specify object.
     */
    public Node<T> addN(T o) {
        return root.add(o);
    }

    /**
     * remove node and his subtrees from tree according to specify object.
     *
     * @param o element to be removed from this collection, if present
     * @return true if tree has changes
     */
    @Override
    public boolean remove(Object o) {
        TreeIterDFS iterator = iteratorDFS();
        boolean rem = false;
        while (iterator.hasNext()) {
            Node<T> node = iterator.nextN();
            if (node.getObject().equals(o)) {
                iterator.remove();
                rem = true;
            }
        }
        return rem;
    }

    /**
     * ru.nsu.fit.tsukanov.Tree will always add
     *
     * @param c collection containing elements to be added to this collection
     * @return true
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean addAll(Collection c) {
        for (Object o : c) {
            root.add((T) o);
        }
        return true;
    }

    /**
     * remove all nodes from tree.
     */
    @Override
    public void clear() {
        for (T t : this) {
            remove(t);
        }
    }

    /**
     * removes from this collection all of its elements
     * that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this collection
     * @return true, if there are elements that was removed from tree
     */

    @Override
    public boolean retainAll(Collection c) {
        if (c == null) {
            throw new IllegalArgumentException("Null pointer");
        }
        boolean flag = false;
        var iter = this.iteratorDFS();
        while (iter.hasNext()) {
            Node<T> node = iter.nextN();
            if (!c.contains(node.getObject())) {
                iter.remove();
            }
        }
        return flag;
    }

    /**
     * Remove from tree all nodes with object contained in collection.
     *
     * @param c collection containing elements to be removed from this collection
     * @return true, if there is some removing.
     */
    @Override
    public boolean removeAll(Collection c) {
        if (c == null) {
            throw new IllegalArgumentException("Null pointer");
        }
        boolean flag = false;
        for (Object o : c) {
            flag = this.remove(o) || flag;
        }
        return flag;
    }

    /**
     * Remove from tree all nodes with object that's not contained in collection.
     *
     * @param c collection containing elements to be removed from this collection
     * @return true, if there is some removing.
     */
    @Override
    public boolean containsAll(Collection c) {
        if (c == null) {
            throw new IllegalArgumentException("Null pointer");
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * return object array from tree nodes using iterator.
     *
     * @return object array from tree nodes.
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int i = 0;
        for (T t : this) {
            arr[i++] = t;
        }
        return arr;
    }

    /**
     * Return array from tree or place objects into the new array.
     *
     * @param a the array into which the elements of this collection are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return array of objects
     */
    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray(Object[] a) {
        return (T[]) this.toArray();
    }

    /**
     * Create tree representation by nodes' representation.
     *
     * @return tree representation
     */
    @Override
    public String toString() {
        return "ru.nsu.fit.tsukanov.Tree: size=" + size() + "root:" + root;
    }

    /**
     * Create BFS iterator.
     *
     * @return BFS iterator
     */
    public TreeIterBFS iteratorBFS() {
        return new TreeIterBFS();
    }

    /**
     * Create DFS iterator.
     *
     * @return DFS iterator.
     */
    public TreeIterDFS iteratorDFS() {
        return new TreeIterDFS();
    }

    /**
     * BFS iterator.
     */
    public class TreeIterBFS implements Iterator<T> {

        private final ArrayList<Node<T>> nodeList;

        /**
         * Create Stack for BFS iterations.
         */
        public TreeIterBFS() {
            nodeList = new ArrayList<>();
            nodeList.add(root);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return (nodeList.size() > 0) && ((nodeList.size() > 1)
                    || (!nodeList.get(0).getChildren().isEmpty()));
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() throws IllegalStateException {
            return nextN().getObject();
        }

        /**
         * Returns the next Node in the iteration.
         *
         * @return the next Node in the iteration
         * @throws NoSuchElementException if the iteration has no more Nodes
         */
        public Node<T> nextN() throws IllegalStateException {
            if (!hasNext()) {
                throw new IllegalStateException("There is no element");
            }
            Node<T> node = nodeList.remove(0);
            nodeList.addAll(node.getChildren());
            return nodeList.get(0);
        }

        /**
         * String representation.
         *
         * @return String representation.
         */
        @Override
        public String toString() {
            return nodeList.toString();
        }
    }

    /**
     * Tree iterator that uses DFS.
     */
    public class TreeIterDFS implements Iterator<T> {

        Node<T> currentNode;

        /**
         * Firstly cleans nodes to unchecked state.
         */
        TreeIterDFS() {

            Tree.this.root.cur = 0;
            var it = Tree.this.iteratorBFS();
            while (it.hasNext()) {
                var tmp = it.nextN();
                tmp.cur = 0;
            }
            currentNode = Tree.this.root;

        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return peek(currentNode);
        }

        private boolean peek(Node<T> node) {
            while (node.cur >= node.getChildren().size()) {
                if (node == Tree.this.root) {
                    return false;
                }
                node = node.getFather();
            }
            return true;

        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() throws IllegalStateException {
            return nextN().getObject();

        }

        /**
         * Returns the next node in the iteration according to DFS algorithm.
         *
         * @return the next node in the iteration
         * @throws NoSuchElementException if the iteration has no more nodes
         */
        public Node<T> nextN() throws IllegalStateException {
            if (!hasNext()) {
                throw new IllegalStateException("There is no element");
            }
            while (currentNode.cur >= currentNode.getChildren().size()) {
                currentNode = currentNode.getFather();
            }
            currentNode = currentNode.getChildren().get(currentNode.cur++);
            return currentNode;
        }

        /**
         * Remove current node from tree.
         * @throws IllegalStateException if current node is root
         */
        @Override
        public void remove() throws IllegalStateException {
            var father = currentNode.getFather();
            if (father == null) {
                throw new IllegalStateException("Current node is root");
            }
            if (father.getChildren().indexOf(currentNode) < father.cur) {
                --father.cur;
            }
            father.getChildren().remove(currentNode);
            currentNode = father;
        }
    }
}
