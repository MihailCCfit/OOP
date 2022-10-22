package ru.nsu.fit.tsukanov;

import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



/**
 * TreeTest testing.
 * Testing ordering, sorting, adding, removing and throwing exceptions.
 */
public class TreeTest {
    @Test
    public void exampleTest() {
        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> node = tree.addNode("B");
        tree.addNode(node, "AB");
        tree.addNode(node, "BB");
        var s = tree.findNodes((nod) -> nod.getChildren()
                        .isEmpty())
                .stream()
                .map(Tree.Node::getObject)
                .filter((str) -> str.contains("B"))
                .collect(Collectors.toList());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AB");
        arrayList.add("BB");
        Assertions.assertTrue(checkContains(s, arrayList));
    }

    @SuppressWarnings({"unchecked"})
    private ArrayList<Integer>[] getRandomArrayLists() {
        ArrayList<Integer>[] arrayLists = new ArrayList[25];
        Random random = new Random();
        for (int i = 0; i < arrayLists.length; i++) {
            int bound = random.nextInt(25) + 1;
            arrayLists[i] = new ArrayList<>();
            for (int i1 = 0; i1 < bound; i1++) {
                arrayLists[i].add(random.nextInt(bound));
            }
        }
        return arrayLists;
    }

    @Test
    public void testsWithArrayList() {
        Random random = new Random(15);
        ArrayList<Integer>[] arr = getRandomArrayLists();
        ArrayList<Integer> arrForCheck = new ArrayList<>();
        Tree<Integer> tree = new Tree<>();
        for (ArrayList<Integer> arrayList : arr) {
            int choice = random.nextInt(4);
            switch (choice) {
                case 0:
                    tree.addAll(arrayList);
                    arrForCheck.addAll(arrayList);
                    break;
                case 1:
                    tree.removeAll(arrayList);
                    arrForCheck.removeAll(arrayList);
                    break;
                case 2:
                    tree.retainAll(arrayList);
                    arrForCheck.retainAll(arrayList);
                    break;
                case 3:
                    tree.add(arrayList.get(0));
                    arrForCheck.add(arrayList.get(0));
                    break;
                default:

            }
            Assertions.assertEquals(tree.containsAll(arrayList),
                    arrForCheck.containsAll(arrayList));
            Assertions.assertTrue(checkContains(arrForCheck, tree));
            Assertions.assertEquals(tree.size(), arrForCheck.size());
            Assertions.assertFalse(tree.iteratorBFS().toString().isBlank());
        }
    }

    private <T> boolean checkContains(Collection<T> fst, Collection<T> snd) {
        return fst.containsAll(snd) && snd.containsAll(fst);
    }

    private void concurentHelper(Tree tree) {
        for (Object t : tree) {
            tree.remove(t);
        }
    }

    @Test
    private void twiceRemoveIterator() {
        Tree<Integer> tree = new Tree<>();
        tree.addAll(List.of(0, 1, 2, 3, 4, 5));
        Tree.TreeIterDFS treeIterDFS = tree.iteratorDFS();
        while (treeIterDFS.hasNext()) {
            treeIterDFS.next();
            treeIterDFS.remove();
            Assertions.assertThrows(IllegalStateException.class, () -> treeIterDFS.remove());
        }
    }

    @Test
    public void testSomething() {
        Tree<Integer> tree = new Tree<>(1);
        Assertions.assertFalse(tree.findNodes(1).isEmpty());
        Assertions.assertTrue(tree.findNodes(2).isEmpty());
        tree.clear();
        Assertions.assertTrue(tree.findNodes(1).isEmpty());
        Tree.Node<Integer> node = tree.addNode(5);
        tree.addNode(node, 6);
        System.out.println(tree);
        Assertions.assertThrows(ConcurrentModificationException.class,
                () -> concurentHelper(tree));
        tree.remove(node.getObject());
        Assertions.assertTrue(tree.isEmpty());
        Assertions.assertFalse(tree.toString().isBlank());
        tree.add(4);
        Integer[] integers = {4};
        Assertions.assertEquals(integers[0], tree.toArray()[0]);
        tree.add(5);
        var iterDFS = tree.iteratorDFS();
        while (iterDFS.hasNext()) {
            if (iterDFS.next().equals(4)) {
                iterDFS.remove();
            }
        }
        Assertions.assertEquals(tree.size(), 1);
        Tree<String> stringTree = new Tree<>();
        stringTree.add("A");
        stringTree.add("B");
        stringTree.add("B");
        Tree<String>.TreeIterDFS itDfs = stringTree.iteratorDFS();
        tree.toArray(new Object[1]);
        while (itDfs.hasNext()) {
            if (itDfs.next().equals("C")) {
                stringTree.remove("A");
            }
        }

    }

    @Test
    void testAsserts() {
        Tree<Double> tree = new Tree<>(5.0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.retainAll(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.removeAll(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.containsAll(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.addNode(null, 5.0));
        Tree.TreeIterDFS iter = tree.iteratorDFS();
        iter.next();
        iter.remove();
        Assertions.assertThrows(IllegalStateException.class, iter::next);
        Assertions.assertThrows(IllegalStateException.class, iter::remove);
        Tree<Integer> tree1 = new Tree<>();
        tree1.addAll(List.of(1, 2, 3, 4));
        iter = tree1.iteratorDFS();
        tree1.addNode(iter.nextNode(), 1);
        Assertions.assertThrows(ConcurrentModificationException.class, iter::remove);
        var itBFS = tree1.iteratorBFS();
        itBFS.next();
        tree1.add(1);
        Assertions.assertThrows(ConcurrentModificationException.class, itBFS::hasNext);
        Assertions.assertThrows(ConcurrentModificationException.class, itBFS::next);
        tree1.clear();
        itBFS = tree1.iteratorBFS();
        Assertions.assertThrows(IllegalStateException.class, itBFS::next);

    }

}