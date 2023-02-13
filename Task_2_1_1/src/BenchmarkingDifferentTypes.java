package nsu.fit.tsukanov.parallel;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.linear.LinearNonPrimeFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.tools.EratosthenesSieve;
import org.junit.jupiter.api.Assertions;
import org.openjdk.jmh.annotations.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

//@State(Scope.Benchmark)
//@BenchmarkMode(Mode.Throughput)
//@Warmup(iterations = 3) // число итераций для прогрева нашей функции
//@Measurement(iterations = 5, batchSize = 5)
//public class BenchmarkingDifferentTypes {
//    @Param({"10", "50", "100", "500", "1000", "10000", "100000", "1000000"})
//    int numberOfPrimeNumbers;
//    int maxInt = 40000000;
//    EratosthenesSieve eratosthenesSieve = new EratosthenesSieve(maxInt);
//    List<Integer> primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);
//    List<Integer> bigPrimeNumbers = listOfBigPrimeNumbers(numberOfPrimeNumbers);
//
//
//
//    private List<Integer> listOfBigPrimeNumbers(int size) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < size; i++) {
//            list.add(1000000007);
//        }
//        list.add(1000000005);
//        for (int i = 0; i < 5; i++) {
//            list.add(1000000007);
//        }
//        return list;
//    }
//
//    /**
//     * large numbers
//     */
//    @Benchmark
//    public void TestParallelThreadNonPrimeFinderWithBigNumbers() {
//        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
//        var res = finder.hasNoPrime(bigPrimeNumbers);
//        assert(res);
//    }
//
//    @Benchmark
//    public void TestLinearNonPrimeFinderWithBigNumbers() {
//        NonPrimesFinder finder = new LinearNonPrimeFinder();
//        var res = finder.hasNoPrime(bigPrimeNumbers);
//        assert(res);
//    }
////
//    @Benchmark
//    public void TestParallelStreamWithBigNumbers() {
//        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
//        var res = finder.hasNoPrime(bigPrimeNumbers);
//        assert(res);
//    }
//
//    /**
//     * random numbers
//     */
//    @Benchmark
//    public void TestParallelThreadNonPrime() {
//        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
//        var res = finder.hasNoPrime(primeNumbers);
//        assert(!res);
//    }
////
//    @Benchmark
//    public void TestLinearNonPrime() {
//        NonPrimesFinder finder = new LinearNonPrimeFinder();
//        System.out.println(primeNumbers.size());
//        var res = finder.hasNoPrime(primeNumbers);
//        assert(!res);
//    }
////
//    @Benchmark
//    public void TestParallelStream() {
//        NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
//        var res = finder.hasNoPrime(primeNumbers);
//        assert(!res);
//    }
//
//
//}
