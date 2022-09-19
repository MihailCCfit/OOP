package ru.nsu.fit.tsukanov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

/**
 * ru.nsu.fit.tsukanov.StackTest.
 * Testing throws, pushs and pops. Testing based on comparing with arraylist.
 */

public class StackTest {

    @ParameterizedTest
    @NullSource
    void someTests(Stack<Integer> sta) {
        Stack<Integer> stack = new Stack<>(null);
        Assertions.assertEquals(stack.size(), 0);
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertThrows(IllegalStateException.class, stack::pop);
        Assertions.assertThrows(IllegalStateException.class, () -> stack.popStack(100));
        stack.push(5);
        int sizeBeforePush = stack.size();
        stack.pushStack(sta);
        Assertions.assertEquals(stack.size(), sizeBeforePush);
    }

    static Stream<Integer[]> arrS() {
        Integer[][] arr = {{1, 2, 3, 4, 5}};
        return Stream.of(arr);
    }

    @ParameterizedTest
    @MethodSource("arrS")
    void iterTest() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Stack<Integer> stack = new Stack<>(arr);
        int i = 0;
        for (Object o : stack) {
            Assertions.assertEquals(o, arr[i]);
            i++;
        }
    }

    @Test
    void sillyTest() {
        Stack<Double> stack = new Stack<>();
        stack.push(1.0);
        Assertions.assertFalse(stack.toString().isBlank());
    }

    @Test
    void testStack() {
        Random random = new Random();
        ArrayList<Integer> arrayList = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 20000; i++) {
            int choice = random.nextInt() % 5;
            choice = choice < 0 ? -choice : choice;
            if (choice == 0) {
                int val = random.nextInt();
                arrayList.add(val);
                stack.push(val);
            } else if (choice == 1) {
                if (!stack.isEmpty()) {
                    Integer a1 = arrayList.remove(arrayList.size() - 1);
                    Integer a2 = stack.pop();
                    Assertions.assertEquals(a1.intValue(), a2.intValue());
                }
            } else if (choice == 2) {
                Assertions.assertEquals(stack.count(), arrayList.size());
            } else if (choice == 3) {
                Integer[] someArr = new Integer[random.nextInt(5) + 1];
                for (int i1 = 0; i1 < someArr.length; i1++) {
                    someArr[i1] = random.nextInt();
                }
                Stack<Integer> tmpStack = new Stack<>(someArr);
                arrayList.addAll(Arrays.asList(someArr));
                stack.pushStack(tmpStack);
            } else {
                Integer[] someArr = new Integer[random.nextInt(5) + 1];
                for (int i1 = 0; i1 < someArr.length; i1++) {
                    someArr[i1] = random.nextInt();
                }
                Stack<Integer> tmpStack = new Stack<>(someArr);
                int sizeBeforePush = stack.size();
                stack.pushStack(tmpStack);
                Assertions.assertEquals(sizeBeforePush + tmpStack.size(), stack.size());
                Stack<Integer> afterStack = stack.popStack(tmpStack.size());

                Object[] ar1 = afterStack.getElements();
                Object[] ar2 = tmpStack.getElements();
                for (int j = 0; j < tmpStack.count(); j++) {
                    Assertions.assertEquals(((Integer) (ar1[j])).intValue(),
                            ((Integer) (ar2[j])).intValue());
                }
            }
        }


    }

}

