import java.util.*;


public class Main {
    public static void main(String[] args) {
        Tree<String> tree = new Tree<>();
        tree.add("A");
        Tree.Node<String> node = tree.addN("B");
        node.add("AB");
        node.add("BB");
        var s = tree.contain((nod)->nod.children.isEmpty()).
                stream().map((nod)->nod.object).filter((str)->str.contains("B"));
        System.out.println(Arrays.toString(s.toArray()));



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

//        for (String s : tree) {
//            tree.remove(s);
//        }
//        System.out.println(tree);
//        Integer[] a = {1,5,3,4};
//        Tree<Integer> tree1 = new Tree<>();
//        tree1.addAll(List.of(a));
//        System.out.println(tree1);
//        Integer[] x = {1,5,3};
//        System.out.println(tree1.containsAll(List.of(x)));
//        tree1.removeAll(List.of(x));
//        System.out.println(tree1);
//        System.out.println( Arrays.toString(tree1.toArray()));

        testsWithArrayList();
    }
    static public ArrayList<Integer>[] getRandomArrayLists(){
        ArrayList<Integer>[] arrayLists = new ArrayList[10];
        Random random = new Random(10);
        for (int i = 0; i < arrayLists.length; i++) {
            int bound = random.nextInt(5)+1;
            arrayLists[i] = new ArrayList<>();
            for (int i1 = 0; i1 < bound; i1++) {
                arrayLists[i].add(random.nextInt(bound));
            }
        }
        return arrayLists;
    }


    static public void testsWithArrayList(){
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
            if(!CheckContains(arrForCheck, tree)) {System.out.println("\n\n!!!!!!!!!!!!Bad\n");
                System.out.println(choice);
                System.out.println(tree);
                System.out.println(arrForCheck);
            }
            //tree.size()


        }
    }
    static private<T> boolean CheckContains(Collection<T> fst, Collection<T> snd){
        return fst.containsAll(snd) && snd.containsAll(fst);
    }
}