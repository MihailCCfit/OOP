package ru.nsu.fit.tsukanov;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;




/**
 * ru.nsu.fit.tsukanov.Heap testing.
 * Testing ordering, sorting, adding, removing and throwing exceptions.
 */
public class HeapTest {
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


}
