import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;




/**
 * TreeTest testing.
 * Testing ordering, sorting, adding, removing and throwing exceptions.
 */
public class TreeTest {
    @Test
    public void exampleTest(){
        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> node = tree.addN("B");
        tree.add(node,"AB");
        node.add("BB");
        var s = tree.contain((nod)->nod.children.isEmpty()).
                stream().map((nod)->nod.object).filter((str)->str.contains("B"));
        String[] strings = {"AB", "BB"};
        var z = Arrays.stream(strings).toList();
        Assertions.assertTrue(CheckContains(s.toList(),z));
    }
    public ArrayList<Integer>[] getRandomArrayLists(){
        ArrayList<Integer>[] arrayLists = new ArrayList[10];
        Random random = new Random();
        for (int i = 0; i < arrayLists.length; i++) {
            int bound = random.nextInt(25)+1;
            arrayLists[i] = new ArrayList<>();
            for (int i1 = 0; i1 < bound; i1++) {
                arrayLists[i].add(random.nextInt(bound));
            }
        }
        return arrayLists;
    }

    @Test
    public void testsWithArrayList(){
        Random random = new Random(15);
        ArrayList<Integer>[] arr = getRandomArrayLists();
        ArrayList<Integer> arrForCheck = new ArrayList<>();
        Tree<Integer> tree = new Tree<>();
        for (ArrayList<Integer> arrayList : arr) {
            int choice = random.nextInt(4);
            switch (choice) {
                case 0 -> {
                    tree.addAll(arrayList);
                    arrForCheck.addAll(arrayList);
                }
                case 1 -> {
                    tree.removeAll(arrayList);
                    arrForCheck.removeAll(arrayList);
                }
                case 2 -> {
                    tree.retainAll(arrayList);
                    arrForCheck.retainAll(arrayList);
                }
                case 3 -> {
                    tree.add(arrayList.get(0));
                    arrForCheck.add(arrayList.get(0));
                }
            }
            Assertions.assertTrue(CheckContains(arrForCheck, tree));
            Assertions.assertEquals(tree.size(), arrForCheck.size());


        }
    }
    private<T> boolean CheckContains(Collection<T> fst, Collection<T> snd){
        return fst.containsAll(snd) && snd.containsAll(fst);
    }
    @Test
    public void testSomething(){
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

    }

    /*
    private int[] getRandomArr(int length) {
        Random random = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

    @Test
    void testSorting() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = getRandomArr(1000);
            int[] arrForCheck = arr.clone();
            Arrays.sort(arrForCheck);
            Assertions.assertArrayEquals(arrForCheck, Heap.heapSort(arr));
        }

    }

    @Test
    void testCorrectOrderInHeap() {
        for (int i = 0; i < 1000; i++) {
            Heap heap = new Heap(getRandomArr(i));
            for (int j = 1; j < heap.size(); j++) {
                Assertions.assertTrue(heap.get(j) > heap.get((j - 1) / 2));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {100, -10})
    void testThrows(int x) {
        Heap heap = new Heap();
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> heap.get(x));
        Assertions.assertThrows(IndexOutOfBoundsException.class, heap::extractMin);
        Assertions.assertThrows(IllegalStateException.class, heap::getMin);

    }

    @Test
    void testForMin() {
        for (int i = 1; i < 500; i++) {
            int[] arr = getRandomArr(i);
            int x = Arrays.stream(arr).min().getAsInt();
            Heap heap = new Heap(arr);
            Assertions.assertEquals(heap.getMin(), x);
            Assertions.assertEquals(heap.size(), arr.length);
            x--;
            heap.add(x);
            Assertions.assertEquals(heap.getMin(), x);
            x = Arrays.stream(arr).max().getAsInt() + 5;
            heap.add(x);
            Assertions.assertEquals(heap.get(heap.size() - 1), x);
        }
    }

    @Test
    void uselessTest() {
        int[] ar = {4, 2, 3, 1};
        Heap heap = new Heap(ar);
        Assertions.assertEquals(heap.toString(), "[1, 2, 3, 4]");
    }
*/

}