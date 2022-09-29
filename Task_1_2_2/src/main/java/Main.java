import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> nodeB = tree.addN("B");
        tree.add(nodeB,"BB");
        tree.add(nodeB,"AB");
        String str = "B";
        String[] strings = {"B"};
        System.out.println(tree);
        System.out.println(Arrays.toString( tree.stream().filter(s -> s.contains(str)&& !s.equals(str)).toArray()));

        for (String s : tree) {
            System.out.println(s);
        }
        //tree.removeAll(List.of(strings));
        tree.remove("B");
        for (String s : tree) {
            System.out.println(s);
        }
        //tree.clear();
        tree.remove("BB");
        System.out.println(tree);
        System.out.println(tree.size());

    }
}