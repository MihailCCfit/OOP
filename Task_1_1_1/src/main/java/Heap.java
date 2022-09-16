import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
/*
 *
 *
 *
 *
 *
 * @author Tsukanov
 */

public class Heap {

    private List<Integer> data;
    private int sizeOfHeap;

    private void swap(int ind1, int ind2) {
        int tmp = data.get(ind1);
        data.set(ind1, data.get(ind2));
        data.set(ind2, tmp);
    }

    public Integer[] getData() {
        return (Integer[]) data.toArray();
    }

    public Heap() {
        this.sizeOfHeap = 0;
        this.data = new ArrayList<>();
    }

    public Heap(int[] arr) {
        this.sizeOfHeap = arr.length;
        this.data = new ArrayList<>();
        for (int i : arr) {
            this.data.add(i);
        }
        for (int i = sizeOfHeap / 2; i >= 0; i--) {
            siftDown(i);
        }

    }

    private void siftUp(int index) {

        int prev = (index - 1) / 2;
        if (prev < 0) return;
        while (data.get(prev) > data.get(index)) {

            int tmp = data.get(prev);
            data.set(prev, data.get(index));
            data.set(index, tmp);
            index = prev;
            prev = (index - 1) / 2;
            if (index == 0) break;
        }
    }

    private void siftDown(int index) {
        int next1 = index * 2 + 1;
        int next2 = index * 2 + 2;

        if (next2 < sizeOfHeap) {
            if (data.get(next2) < data.get(index)) {
                if (data.get(next1) < data.get(next2)) {
                    swap(next1, index);
                    siftDown(next1);
                } else {
                    swap(next2, index);
                    siftDown(next2);
                }
                return;
            }

        }
        if (next1 < sizeOfHeap) {
            if (data.get(next1) < data.get(index)) {
                swap(next1, index);
                siftDown(next1);
            }
        }

    }

    public void correctionKey(int index) {
        siftDown(index);
        siftUp(index);
    }

    public void add(int val) {
        data.add(val);
        siftUp(sizeOfHeap);
        sizeOfHeap++;
    }

    public int getMin() {
        return data.get(0);
    }

    public int extractMin() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        int res = data.get(0);
        sizeOfHeap--;
        swap(0, sizeOfHeap);
        data.remove(sizeOfHeap);
        siftDown(0);

        return res;
    }

    public int size() {
        return sizeOfHeap;
    }

    public boolean isEmpty() {
        return sizeOfHeap == 0;
    }


    public int[] toArray() {
        int[] arr = new int[sizeOfHeap];
        int index = 0;
        while (!isEmpty()) {
            arr[index++] = extractMin();
        }
        return arr;
    }

    public static int[] heapSort(int[] arr) {
        Heap heap = new Heap(arr);
        return heap.toArray();
    }

    public int get(int index) {
        if (index < 0 || index > sizeOfHeap) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return data.get(index);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
