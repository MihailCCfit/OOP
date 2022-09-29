import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> node = tree.addN("B");
        node.add("AB");
        node.add("BB");

        var iterDFS = tree.iteratorDFS();
        ArrayList<String> arrayList = new ArrayList<>();
        while (iterDFS.hasNext()){
            Tree.Node<String> tNode = iterDFS.nextN();
            if (tNode.children.isEmpty() && tNode.object.contains("B")){
                arrayList.add(tNode.object);
            }
        }
        for (String s : arrayList) {
            System.out.println(s);
        }

        iterDFS = tree.iteratorDFS();
        arrayList = new ArrayList<>();
        while (iterDFS.hasNext()){
            Tree.Node<String> tNode = iterDFS.nextN();
            if (tNode.children.isEmpty() && tNode.object.contains("B")){
                arrayList.add(tNode.object);
            }
        }
        for (String s : arrayList) {
            System.out.println(s);
        }
        System.out.println(tree);

    }
}