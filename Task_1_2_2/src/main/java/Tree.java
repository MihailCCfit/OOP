import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

/** Tree is collection. User can add new Nodes to root, or to other Nodes.
 * It's easy to create branches, this structure is fragile.
 * @param <T> for objects in Tree.
 */
public class Tree<T> implements Collection<T> {
    private final Node<T> root;

    /**
     *
     * @param <T> that is given from tree
     */
    public static class Node<T> {
        private int cur = -1;
        /**
         * List of child nodes.
         */
        public ArrayList<Node<T>> children;
        /**
         * Upper (father) node.
         */
        public Node<T> father;
        /**
         * Object that saved in this node.
         */
        public T object;

        /**
         * Create new Node with saving object in this.
         * @param obj object that will be place into node
         */
        Node(T obj) {
            object = obj;
            children = new ArrayList<>();
        }

        /**
         * Add in this node child_list specify node.
         * @param node that exist.
         * @return new node.
         */

        Node<T> add(Node<T> node) {
            children.add(node);
            node.father = this;
            return node;
        }

        /**
         * Create node with object and add that to this node.
         * @param obj that will be saved in tree node.
         * @return new node with object.
         */
        Node<T> add(T obj) {
            Node<T> node = new Node<>(obj);
            return this.add(node);
        }

        /**
         * return String representation.
         * @return String representation.
         */
        @Override
        public String toString() {
            return "Node{" + "cur=" + cur +
                    "[" + object +
                    "], children=" + children +
                    '}';
        }

        /**
         * Remove this node from parent node.
         */
        void remove() {
            var father = this.father;
            if (father.children.indexOf(this)<father.cur){//?
                --father.cur;
            }
            father.children.remove(this);
        }

    }

    /**
     * Create new Tree with root node.
     */
    public Tree() {
        root = new Node<>(null);
    }

    /**
     * Create new Tree with root node. Add to root new node with specify object.
     * @param obj that will be added to tree.
     */
    public Tree(T obj) {
        root = new Node<>(null);
        root.add(obj);
    }

    /**
     * Count nodes in tree by BFS iterations.
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
     * @return true if there is no element in tree, else - false.
     */
    @Override
    public boolean isEmpty() {
        return root.children.isEmpty();
    }

    /**
     * Check tree for containing specify element.
     * @param o element whose presence in this collection is to be tested
     * @return true if tree contains this object.
     */
    @Override
    public boolean contains(Object o) {
        TreeIterBFS iterBFS = this.iteratorBFS();
        while (iterBFS.hasNext()){
            var obj = iterBFS.next();
            if (o.equals(obj)) return true;
        }
        return false;
    }
    public ArrayList<Node<T>> contain(Object o){
        ArrayList<Node<T>> nodeArrayList = new ArrayList<>();
        TreeIterBFS iterBFS = this.iteratorBFS();
        while (iterBFS.hasNext()){
            var node = iterBFS.nextN();
            if (node.object.equals(o)){
                nodeArrayList.add(node);
            }
        }
        return nodeArrayList;
    }
    public ArrayList<Node<T>> contain(Predicate<Node<T>> predicate){
        ArrayList<Node<T>> nodeArrayList = new ArrayList<>();
        TreeIterBFS iterBFS = this.iteratorBFS();
        while (iterBFS.hasNext()){
            var node = iterBFS.nextN();
            if (predicate.test(node)){
                nodeArrayList.add(node);
            }
        }
        return nodeArrayList;
    }

    /**
     * Create and return new BFS iterator.
     * @return BFS iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new TreeIterDFS();
    }

    /**
     * return object array from tree nodes using iterator
     * @return object array from tree nodes.
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int i=0;
        for (T t : this) {
            arr[i++] = t;
        }
        return arr;
    }

    /**
     * Add object to tree.
     * @param o element whose presence in this collection is to be ensured
     * @return true if object isn't null
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean add(Object o) {
        root.add((T)o);
        return true;
    }

    /**
     * Create node and put object into node.
     * @param o object
     * @return node with specify object.
     */
    public Node<T> addN(T o) {
        return root.add(o);
    }

    /** Create new node with specify object and put it into specify node.
     * @param node to which will add new node with specify object.
     * @param o object that will be put in node.
     * @return new created node.
     */
    public Node<T> add(Node<T> node,T o) {
        if (node == null) {
            throw new IllegalArgumentException("Null pointer");
        }
        return node.add(o);
    }

    /**
     * remove node and his subtrees from tree according to specify object.
     * @param o element to be removed from this collection, if present
     * @return true if tree has changes
     */
    @Override
    public boolean remove(Object o) {
        TreeIterDFS iterator = iteratorDFS();
        boolean rem = false;
        while (iterator.hasNext()) {
            Node<T> node = iterator.nextN();
            if (node.object.equals(o)) {
                iterator.remove();
                rem = true;
            }
        }
        return rem;
    }

    /**
     * Tree will always add
     * @param c collection containing elements to be added to this collection
     * @return true
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean addAll(Collection c) {
        for (Object o : c) {
            root.add((T)o);
        }
        return  true;
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
     * removes from this collection all of its elements that are not contained in the specified collection.
     * @param c collection containing elements to be retained in this collection
     * @return true, if there are elements that was removed from tree
     */

    @Override
    public boolean retainAll(Collection c) {
        if (c==null){
            throw new IllegalArgumentException("Null pointer");
        }
        boolean flag = false;
        var iter = this.iteratorDFS();
        while (iter.hasNext()){
            Node<T> node = iter.nextN();
            if (!c.contains(node.object)){
                    iter.remove();
            }
        }
        return flag;
    }

    /**
     * Remove from tree all nodes with object contained in collection.
     * @param c collection containing elements to be removed from this collection
     * @return true, if there is some removing.
     */
    @Override
    public boolean removeAll(Collection c) {
        if (c==null){
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
     * @param c collection containing elements to be removed from this collection
     * @return true, if there is some removing.
     */
    @Override
    public boolean containsAll(Collection c) {
        if (c==null){
            throw new IllegalArgumentException("Null pointer");
        }
        for (Object o : c) {
            if (!contains(o)){
                return false;
            }
        }
        return true;
    }

    /** Return array from tree or place objects into the new array.
     *
     * @param a the array into which the elements of this collection are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return array of objects
     */
    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray(Object[] a) {
        return (T[]) this.toArray();
    }

    /**
     * Create tree representation by nodes' representation.
     * @return tree representation
     */
    @Override
    public String toString() {
        return "Tree: size="+size()+"root:"+root;
    }

    /**
     * Create BFS iterator.
     * @return BFS iterator
     */
    public TreeIterBFS iteratorBFS(){
        return new TreeIterBFS();
    }

    /**
     * Create DFS iterator.
     * @return DFS iterator.
     */
    public TreeIterDFS iteratorDFS(){
        return new TreeIterDFS();
    }

    /**
     * BFS iterator.
     */
    private class TreeIterBFS implements Iterator<T> {

        private final ArrayList<Node<T>> nodeList;

        /**
         * Create Stack for BFS iterations.
         */
        public TreeIterBFS() {
            nodeList = new ArrayList<>();
            nodeList.add(root);
        }

        /**
         * Check for next element.
         * @return true if it has next element.
         */
        @Override
        public boolean hasNext() {
            return (nodeList.size()>0) && ((nodeList.size()>1) || (!nodeList.get(0).children.isEmpty()));
        }

        /**
         * Change current element, remove previous from stack and add his children.
         * @return next element
         * @throws IllegalArgumentException if there is no object
         */
        @Override
        public T next() throws IllegalArgumentException {
            return nextN().object;
        }
        /**
         * Change current Node, remove previous from stack and add his children.
         * @return next Node
         * @throws IllegalArgumentException if there is no element
         */
        public Node<T> nextN() throws IllegalArgumentException {
            if (!hasNext()) {
                throw new IllegalArgumentException("There is no element");
            }
            Node<T> node = nodeList.remove(0);
            nodeList.addAll(node.children);
            if (nodeList.isEmpty()) {
                throw new IllegalStateException("No elements");
            }

            return nodeList.get(0);
        }

        /**
         * String representation
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
            while (it.hasNext()){
                var tmp = it.nextN();
                tmp.cur=0;
            }
            currentNode = Tree.this.root;

        }

        /**
         * Check for next element.
         * @return true if it has next element
         */
        @Override
        public boolean hasNext() {
            return peek(currentNode);//?
        }
        private boolean peek(Node<T> node){
            while (node.cur>= node.children.size()){
                if (node==Tree.this.root) return false;
                node = node.father;
            }
            return true;

        }

        /**
         * Return next object.
         * @return Next object
         * @throws IllegalArgumentException if there is no element
         */
        @Override
        public T next() throws IllegalArgumentException {
            return nextN().object;

        }

        /**
         * Return next node according to DFS algorithm, change some data in nodes.
         * @return next Node.
         */
        public Node<T> nextN() {
            if(!hasNext()){
                throw new IllegalStateException("There is no element");
            }
            while (currentNode.cur>=currentNode.children.size()){
                if (currentNode==Tree.this.root){
                    throw new IllegalStateException("No element");
                }
                currentNode = currentNode.father;
            }
            currentNode = currentNode.children.get(currentNode.cur++);
            return currentNode;
        }

        /**
         * Remove current node from tree.
         * @throws IllegalStateException if current node is root
         */
        @Override
        public void remove() throws IllegalStateException {
            var father = currentNode.father;
            if (father == null) {
                throw new IllegalStateException("Current node is root");
            }
            if (father.children.indexOf(currentNode)<father.cur){//?
                --father.cur;
            }
            father.children.remove(currentNode);
            currentNode = father;
        }
    }
}
