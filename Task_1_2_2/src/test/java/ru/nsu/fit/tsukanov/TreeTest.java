package ru.nsu.fit.tsukanov;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * ru.nsu.fit.tsukanov.TreeTest testing.
 * Testing ordering, sorting, adding, removing and throwing exceptions.
 */
public class TreeTest {
    @Test
    public void exampleTest() {
        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> node = tree.addN("B");
        tree.add(node, "AB");
        node.add("BB");
        var s = tree.contain((nod) -> nod.getChildren().isEmpty()).
                stream().map(Tree.Node::getObject).filter((str) -> str.contains("B")).collect(Collectors.toList());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AB");
        arrayList.add("BB");
        Assertions.assertTrue(CheckContains(s, arrayList));
    }

    @SuppressWarnings({"unchecked"})
    public ArrayList<Integer>[] getRandomArrayLists() {
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
            }
            Assertions.assertEquals(tree.containsAll(arrayList), arrForCheck.containsAll(arrayList));
            Assertions.assertTrue(CheckContains(arrForCheck, tree));
            Assertions.assertEquals(tree.size(), arrForCheck.size());
            Assertions.assertFalse(tree.iteratorBFS().toString().isBlank());
        }
    }

    private <T> boolean CheckContains(Collection<T> fst, Collection<T> snd) {
        return fst.containsAll(snd) && snd.containsAll(fst);
    }

    @Test
    public void testSomething() {
        Tree<Integer> tree = new Tree<>(1);
        Assertions.assertFalse(tree.contain(1).isEmpty());
        Assertions.assertTrue(tree.contain(2).isEmpty());
        tree.clear();
        Assertions.assertTrue(tree.contain(1).isEmpty());
        Tree.Node<Integer> node = tree.addN(5);
        node.add(6);
        node.remove();
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
        while (itDfs.hasNext()) {
            if (itDfs.next().equals("C")) {
                stringTree.remove("A");
            }
        }

    }

    @Test
    void testAsserts() {
        Tree<Double> tree = new Tree<>(5.0);
//        Assertions.assertThrows(IllegalStateException.class, stack::pop);
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.retainAll(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.removeAll(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.containsAll(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.add(null, 5.0));
        var iter = tree.iteratorDFS();
        iter.next();
        iter.remove();
        Assertions.assertThrows(IllegalStateException.class, iter::next);
        Assertions.assertThrows(IllegalStateException.class, iter::remove);
        var iterB = tree.iteratorBFS();
        Assertions.assertThrows(IllegalStateException.class, iterB::next);
    }

}