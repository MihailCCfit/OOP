package nsu.fit.tsukanov.parallel;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerWithPreprocessing;
import nsu.fit.tsukanov.parallel.prime.implementations.linear.LinearNonPrimeFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.tools.EratosthenesSieve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class TesterDifferentTypes {

    int numberOfPrimeNumbers = 10000;
    int maxInt = 50000000;

    public List<Integer> getNumbers(int max, int number) {
        EratosthenesSieve eratosthenesSieve = new EratosthenesSieve(max);
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            integers.add(eratosthenesSieve.getRandomPrime());
        }
        return integers;
    }

    private List<Integer> listOfBigPrimeNumbers(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(1000000007);
        }
        list.add(1000000005);
        for (int i = 0; i < 5; i++) {
            list.add(1000000007);
        }
        return list;
    }

    /**
     * large numbers
     */
    @Test
    public void TestParallelThreadNonPrimeFinderWithBigNumbers() {
        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
        var list = listOfBigPrimeNumbers(numberOfPrimeNumbers);
        var res = finder.hasNoPrime(list);
        Assertions.assertTrue(res);
    }

    @Test
    public void TestLinearNonPrimeFinderWithBigNumbers() {
        NonPrimesFinder finder = new LinearNonPrimeFinder();
        var res = finder.hasNoPrime(listOfBigPrimeNumbers(numberOfPrimeNumbers));
        Assertions.assertTrue(res);
    }

    @Test
    public void TestParallelStreamWithBigNumbers() {
        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
        var res = finder.hasNoPrime(listOfBigPrimeNumbers(numberOfPrimeNumbers));
        Assertions.assertTrue(res);
    }

    /**
     * random numbers
     */
    @Test
    public void TestParallelThreadNonPrime() {
        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
        var res = finder.hasNoPrime(getNumbers(maxInt, numberOfPrimeNumbers));
        Assertions.assertFalse(res);
    }

    @Test
    public void TestLinearNonPrime() {
        NonPrimesFinder finder = new LinearNonPrimeFinder();

        var res = finder.hasNoPrime(getNumbers(maxInt, numberOfPrimeNumbers));
        Assertions.assertFalse(res);
    }

    @Test
    public void TestParallelStream() {
        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
        var res = finder.hasNoPrime(getNumbers(maxInt, numberOfPrimeNumbers));
        Assertions.assertFalse(res);
    }

    @Test
    public void Test() {
        PrimeNumberCheckerWithPreprocessing checker = new PrimeNumberCheckerWithPreprocessing(3343);
        var res = checker.notPrime(3343);
        Assertions.assertFalse(res);
    }


}
