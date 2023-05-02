package nsu.fit.tsukanov.parallel.prime.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates all possible prime divisor for the range.
 * Complexity:
 * O(N^(1/2)*log(log(N))) - preprocessing
 * O(N*N^(1/2) / (log(N)-B)), where  B=1,08366.
 * for numbers large then 10^8, it will speed up by 10.
 */
public class PrimeNumberCheckerWithPreprocessing implements PrimeNumberChecker {

    private final List<Integer> primeDividers = new ArrayList<>();
    private final int max;

    /**
     * Creates prime number checker, based on factorisation using eratosthenes Sieve.
     *
     * @param maxNumber the max number of possible numbers for checking
     */

    public PrimeNumberCheckerWithPreprocessing(int maxNumber) {
        this.max = maxNumber;
        int[] arr = new int[(int) (Math.sqrt(maxNumber) + 2)];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] == 0) {
                primeDividers.add(i);
                if (i * i > arr.length) continue;
                for (int j = i + i; j < arr.length; j += i) {
                    arr[j] = 1;
                }
            }
        }
    }

    /**
     * Check num if it's prime number.
     *
     * @param num is number which will be checked
     * @return true if num is prime
     */
    @Override
    public boolean notPrime(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Negative argument: " + num);
        }
        if (num > max) {
            throw new IllegalArgumentException("tooLarge argument: " + num);
        }
        if (num < 2) {
            return true;
        }
        for (Integer divider : primeDividers) {
            if (divider >= num) {
                break;
            }
            if (num % divider == 0) {
                return true;
            }
        }
        return false;
    }

}
