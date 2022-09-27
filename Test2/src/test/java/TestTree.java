import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestTree {
    @Test
    public void testTree() {
        TreeR<Integer> treeR = new TreeR<>(5);
        treeR.add(7);
        Integer[] ar = new Integer[treeR.size()];
        int i = 0;
        for (Integer integer : treeR) {
            ar[i++] = integer;
        }
        System.out.println(Arrays.stream(ar).toArray().toString());
    }
}
