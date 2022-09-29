import java.util.*;

public class Tree<T> implements Collection<T> {
    private final Node<T> root;

    public static class Node<T> {
        int cur = -1;
        ArrayList<Node<T>> children;
        Node<T> father;
        T object;

        Node(T obj) {
            object = obj;
            children = new ArrayList<>();
        }

        Node<T> add(Node<T> node) {
            children.add(node);
            node.father = this;
            return node;
        }

        Node<T> add(T obj) {
            Node<T> node = new Node<>(obj);
            return this.add(node);
        }

        @Override
        public String toString() {
            return "Node{" + "cur=" + cur +
                    "[" + object +
                    "], children=" + children +
                    '}';
        }

        void remove() {
            father.children.remove(this);
        }

    }


    public Tree() {
        root = new Node<>(null);
    }

    public Tree(T obj) {
        root = new Node<>(null);
        root.add(obj);
    }


    @Override
    public int size() {
        int count = 0;
        for (Object ignored : this) {
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return root.children.isEmpty();
    }

    @Override
    public boolean contains(Object o) {

        for (T t : this) {
            if (t.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new TreeIterBFS();
    }

    @Override
    public Object[] toArray() {
        Iterator<T> iterator = iterator();
        ArrayList<T> arr = new ArrayList<>();
        while (iterator.hasNext()) {
            arr.add(iterator.next());
        }
        return arr.toArray();
    }

    @Override
    public boolean add(T o) {
        if (o == null) {
            return false;
        }
        root.add(o);
        return true;
    }

    public Node<T> addN(T o) {
        return root.add(o);
    }

    public Node<T> add(Node<T> node,T o) {
        if (o == null) {
            return null;
        }
        return node.add(o);
    }

    @Override
    public boolean remove(Object o) {
        TreeIterBFS iterator = new TreeIterBFS();
        boolean rem = false;
        while (iterator.hasNext()) {
            Node<T> node = iterator.nextN();
            if (node.object.equals(o)) {
                node.remove();
                rem = true;
            }
        }

        return rem;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {
        TreeIterBFS iterator = new TreeIterBFS();
        while (iterator.hasNext()) {
            iterator.remove();//
        }

        System.gc();

    }

    @Override
    public boolean retainAll(Collection c) {
        HashSet<Object> hashSet = new HashSet<Object>(c);
        boolean changed = false;
        TreeIterBFS iterator = new TreeIterBFS();
        while (iterator.hasNext()) {
            Node<T> node = iterator.nextN();
            if (!hashSet.contains(node.object)) {
                node.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection c) {
        HashSet<Object> hashSet = new HashSet<Object>(c);
        boolean changed = false;
        TreeIterBFS iterator = new TreeIterBFS();
        while (iterator.hasNext()) {
            Node<T> node = iterator.nextN();
            if (hashSet.contains(node.object)) {
                node.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean containsAll(Collection c) {
        HashSet<Object> set = new HashSet<Object>(c);
        for (T obj : this) {
            set.remove(obj);
        }
        return set.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray(Object[] a) {
        return (T[]) this.toArray();
    }

    @Override
    public String toString() {
        //return Arrays.toString( this.toArray());
        return "Tree: size="+size()+"root:"+root;
    }
    public TreeIterBFS iteratorBFS(){
        return new TreeIterBFS();
    }
    public TreeIterDFS iteratorDFS(){
        return new TreeIterDFS();
    }


    public class TreeIterBFS implements Iterator<T> {

        private final ArrayList<Node<T>> nodeList;

        public TreeIterBFS() {

            nodeList = new ArrayList<>();
            nodeList.add(root);
        }

        @Override
        public boolean hasNext() {
            return (nodeList.size()>1) || (!nodeList.get(0).children.isEmpty());
        }

        @Override
        public T next() throws IllegalArgumentException {

            Node<T> node = nodeList.remove(0);
            nodeList.addAll(node.children);
            if (nodeList.isEmpty()) {
                throw new IllegalStateException("No elements");
            }

            return nodeList.get(0).object;
        }

        public Node<T> nextN() throws IllegalArgumentException {
            Node<T> node = nodeList.remove(0);
            nodeList.addAll(node.children);
            if (nodeList.isEmpty()) {
                throw new IllegalStateException("No elements");
            }

            return nodeList.get(0);
        }

        @Override
        public void remove() {
            nodeList.remove(0).remove();//?
        }

        @Override
        public String toString() {
            return nodeList.toString();
        }
    }

    public class TreeIterDFS implements Iterator<T> {

        Node<T> currentNode;

        TreeIterDFS() {
            Tree.this.root.cur = 0;
            var it = Tree.this.iteratorBFS();
            while (it.hasNext()){
                var tmp = it.nextN();
                tmp.cur=-1;
            }
            currentNode = Tree.this.root;

        }

        @Override
        public boolean hasNext() {
            return peek(currentNode)!=null;//?
        }
        private Node<T> peek(Node<T> node){
            if (node.cur<0){
                return node;
            }
            if (node.cur<node.children.size()){
                return node.children.get(node.cur);
            }
            else {
                if(node==root) return null;
                return peek(node.father);
            }
        }

        @Override
        public T next() throws IllegalArgumentException {
            /*
            if (!hasNext()) {
                throw new IllegalStateException("No elements");
            }

            currentNode.cur +=1;
            if (currentNode.cur >= currentNode.children.size()){
                currentNode = currentNode.father;
            }
            else {
                currentNode=currentNode.children.get(currentNode.cur);
            }*/
            return nextN().object;

        }

        public Node<T> nextN() {
            if (!hasNext()) {
                throw new IllegalStateException("No elements");
            }
            if (currentNode.cur<0){
                currentNode.cur=0;
                return currentNode;
            }
            if (currentNode.cur<currentNode.children.size()){
                currentNode = currentNode.children.get(currentNode.cur++);
                currentNode.cur=0;
                return currentNode;
            }
            else {
                if(currentNode==root) return null;
                currentNode.cur=-1;
                currentNode = currentNode.father;
                return nextN();
            }
        }

        @Override
        public void remove() throws IllegalArgumentException {
            currentNode.remove();
            currentNode = currentNode.father;
        }
    }
}
