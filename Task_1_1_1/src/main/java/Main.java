import java.util.Scanner;

/**
 * Interact with heap.
 */
public class Main {
    /**
     * Doc for review dog, Hello world!
     */
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        Heap heap = new Heap();
        System.out.print("Введите 0, чтобы ввести массив и получить отсортированный\n"
                + "Введите 1, если хотите поработать с кучей: ");

        int flag = scanner.nextInt();
        if (flag == 0) {
            System.out.print("Введите количество элементов в массиве: ");
            int n = scanner.nextInt();
            System.out.println("Введите элементы через пробел:");
            for (int i = 0; i < n; i++) {
                heap.add(scanner.nextInt());
            }
            System.out.println(heap);
        }
        if (flag == 1) {
            System.out.println("Введите 0 чтобы показать кучу\n"
                    + "Введите 1 + число, чтобы добавить это число\n"
                    + "Введите 2 чтобы удалить минимум\n"
                    + "Введите 3 чтобы показали минимум\n"
                    + "Введите 4 чтобы показать размер кучи\n"
                    + "Введите -1, чтобы закончить\n");
            for (; ; ) {
                int x = scanner.nextInt();
                if (x == 0) {
                    System.out.println(heap);
                } else if (x == 1) {
                    x = scanner.nextInt();
                    heap.add(x);
                } else if (x == 2) {
                    System.out.println(heap.extractMin());
                } else if (x == 3) {
                    System.out.println(heap.getMin());
                } else if (x == 4) {
                    System.out.println(heap.size());
                } else if (x == -1) {
                    break;
                } else {
                    System.out.println("?");
                    break;
                }
            }

        }


    }
}
