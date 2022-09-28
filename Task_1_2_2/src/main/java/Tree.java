import java.util.*;

public class Tree<T> implements Collection<T> {
    private final Node<T> root;


    private enum isChecked {
        CHECKED, UNCHECKED
    }

    public static class Node<T> {
        isChecked checked;
        ArrayList<Node<T>> childs;
        Node<T> father;
        T object;

        Node(T obj) {
            checked = isChecked.CHECKED;
            object = obj;
            childs = new ArrayList<>();
        }

        Node<T> add(Node<T> node) {
            childs.add(node);
            node.father = this;
            return node;
        }

        Node<T> add(T obj) {
            Node<T> node = new Node<>(obj);
            return this.add(node);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "[" + object +
                    "], childs=" + childs +
                    '}';
        }

        void remove() {
            if (!childs.isEmpty()) {
                System.out.println("Son");
                Node<T> son = childs.remove(0);
                for (Node<T> child : childs) {
                    son.add(child);
                }
                father.add(son);
            }
            father.childs.remove(this);


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
        for (Object t : this) {
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return root.childs.isEmpty();
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

    public Node<T> add(T o, Node<T> node) {
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

    @Override
    public T[] toArray(Object[] a) {
        return (T[]) this.toArray();
    }

    @Override
    public String toString() {
        //return Arrays.toString( this.toArray());
        return Arrays.toString(this.toArray());
    }

    private class TreeIterBFS implements Iterator<T> {

        private final ArrayList<Node<T>> nodeList;

        TreeIterBFS() {

            nodeList = new ArrayList<>();
            if (!root.childs.isEmpty()) {
                var arr = root.childs;
                nodeList.addAll(arr);
            }
        }

        @Override
        public boolean hasNext() {
            return !nodeList.isEmpty();
        }

        @Override
        public T next() throws IllegalArgumentException {
            if (nodeList.isEmpty()) {
                throw new IllegalStateException("No elements");
            }
            Node<T> node = nodeList.remove(0);
            nodeList.addAll(node.childs);

            return node.object;
        }

        public Node<T> nextN() throws IllegalArgumentException {
            if (nodeList.isEmpty()) {
                throw new IllegalStateException("No elements");
            }
            Node<T> node = nodeList.remove(0);
            nodeList.addAll(node.childs);

            return node;
        }

        @Override
        public void remove() throws IllegalArgumentException {
            if (hasNext()) {
                Node<T> node = nextN();
                Tree.this.remove(node);
            } else {
                throw new IllegalStateException("No elements");
            }
        }

        @Override
        public String toString() {
            return nodeList.toString();
        }
    }
}
