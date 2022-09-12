import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        checkSorting();
        correctOrderInHeap();
    }

    static int[] getRandomArr(int length) {
        Random random = new Random();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt();
        }
        return arr;
    }

    static void checkSorting() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = getRandomArr(1000);
            int[] arrForCheck = arr.clone();
            Arrays.sort(arrForCheck);
            assert Arrays.equals(arrForCheck, Heap.heapSort(arr));
        }

    }

    static void correctOrderInHeap() {
        int size = 1000;
        for (int i = 0; i < 1000; i++) {
            Heap heap = new Heap(getRandomArr(size));
            for (int j = 1; j < heap.size(); j++) {
                assert (heap.get(j) > heap.get((j - 1) / 2));
            }
        }

    }
}
