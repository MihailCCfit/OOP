package nsu.fit.tsukanov.parallel;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerInstant;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerWithPreprocessing;
import nsu.fit.tsukanov.parallel.prime.implementations.hybrid.Hybrid;
import nsu.fit.tsukanov.parallel.prime.implementations.linear.LinearNonPrimeFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadWithAtomic;
import nsu.fit.tsukanov.parallel.prime.implementations.parallelstream.ParallelStreamNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.tools.EratosthenesSieve;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


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

    private static List<Integer> listOfBigPrimeNumbers(int size) {
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

    public static Stream<NonPrimesFinder> PrimeCheckerStream() {
        return Stream.of(new ParallelThreadNonPrimeNumberFinder(),
                new ParallelStreamNonPrimeNumberFinder(),
                new LinearNonPrimeFinder(),
                new ParallelThreadWithAtomic(),
                new Hybrid());
    }


    @ParameterizedTest
    @MethodSource("PrimeCheckerStream")
    public void TestParallelThreadNonPrimeFinderWithBigNumbers(NonPrimesFinder finder) {
        List<Integer> list;
        list = listOfBigPrimeNumbers(numberOfPrimeNumbers);
        Assertions.assertTrue(finder.hasNoPrime(list));
        list = getNumbers(100, numberOfPrimeNumbers);
        Assertions.assertFalse(finder.hasNoPrime(list));
        list = getNumbers(maxInt, numberOfPrimeNumbers);
        Assertions.assertFalse(finder.hasNoPrime(list));
    }

    /**
     * random numbers
     */
    @Test
    public void testPrimeNumberChecker() {
        var ref = new Object() {
            PrimeNumberChecker checker = new PrimeNumberCheckerWithPreprocessing(1000);
        };
        Assertions.assertThrows(IllegalArgumentException.class, () -> ref.checker.notPrime(-5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ref.checker.notPrime(5000));
        Assertions.assertTrue(ref.checker.notPrime(1));
        ref.checker = new PrimeNumberCheckerInstant();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ref.checker.notPrime(-5));
    }

    @Test
    public void testSieve() {
        EratosthenesSieve eratosthenesSieve = new EratosthenesSieve(10000);
        eratosthenesSieve.setSeed();
        Assertions.assertTrue(eratosthenesSieve.isPrime(5));
        Assertions.assertFalse(eratosthenesSieve.isPrime(4));
        Assertions.assertEquals(5, eratosthenesSieve.nextPrime(4));
        var list = eratosthenesSieve.getNumbers(3000);
        eratosthenesSieve = new EratosthenesSieve(20000);
        Assertions.assertEquals(3000, list.size());
        Assertions.assertTrue(list.stream().allMatch(eratosthenesSieve::isPrime));
    }


}
