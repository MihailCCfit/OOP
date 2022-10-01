import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) {

        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> node = tree.addN("B");
        node.add("AB");
        node.add("BB");


        var iterDFS = tree.iteratorDFS();
//        ArrayList<String> arrayList = new ArrayList<>();
//        while (iterDFS.hasNext()){
//            Tree.Node<String> tNode = iterDFS.nextN();
//            if (tNode.children.isEmpty() && tNode.object.contains("B")){
//                arrayList.add(tNode.object);
//            }
//        }
//        for (String s : arrayList) {
//            System.out.println(s);
//        }
//
//        iterDFS = tree.iteratorDFS();
//        arrayList = new ArrayList<>();
//        while (iterDFS.hasNext()){
//            Tree.Node<String> tNode = iterDFS.nextN();
//            if (tNode.children.isEmpty() && tNode.object.contains("B")){
//                arrayList.add(tNode.object);
//            }
//        }
//        iterDFS = tree.iteratorDFS();
//        while (iterDFS.hasNext()){
//            Tree.Node<String> tNode = iterDFS.nextN();
//            if (tNode.object.equals("B")){
//                iterDFS.remove();
//            }
//        }
        for (String s : tree) {
            tree.remove(s);
        }
        System.out.println(tree);
        Integer[] a = {1,5,3,4};

    }
}