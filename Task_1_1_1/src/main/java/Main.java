import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Heap heap = new Heap();
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            heap.add(scanner.nextInt());
        }
        System.out.println(heap);

    }
}
