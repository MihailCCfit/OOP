package nsu.fit.tsukanov.parallel.prime.core;

import java.util.Arrays;
import java.util.Collection;

public class CheckerProvider {

    public static PrimeNumberChecker create(Collection<Integer> numbers) {
        return create(numbers, 1);
    }

    public static PrimeNumberChecker create(Collection<Integer> numbers, int threads) {
        double instant = 0;
        double preProc = 0;
        int max = Integer.MIN_VALUE;
        for (Integer number : numbers) {
            if (number > max) {
                max = number;
            }
            instant += Math.sqrt(number) + 2;
            preProc += Math.sqrt(number) / (Math.log(number + 1) + 1) + 2;
        }
        instant /= threads;
        preProc /= threads;
        preProc += Math.sqrt(max) * Math.log(Math.log(max + 2)) * 3;
        if (preProc < instant) {
            return new PrimeNumberCheckerWithPreprocessing(max);
        }
        return new PrimeNumberCheckerInstant();
    }

}
