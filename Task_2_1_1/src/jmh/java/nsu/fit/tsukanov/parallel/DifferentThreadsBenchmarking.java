package nsu.fit.tsukanov.parallel;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.tools.EratosthenesSieve;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;

public class DifferentThreadsBenchmarking {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"10", "25", "50", "100", "250", "500", "1000", "2500", "5000", "10000", "50000"})
        public int numberOfPrimeNumbers;
        @Param({"1", "2", "4", "6", "8"})
        public int threadsAmount;
        public int maxInt = 40000000;
        EratosthenesSieve eratosthenesSieve = new EratosthenesSieve(maxInt);
        public List<Integer> primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);

        @Setup(Level.Trial)
        public void setUp() {
            eratosthenesSieve.setSeed();
            primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);
        }
    }

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.Throughput)
    public static class DifferentThreads {
        @Benchmark
        public void TestParallelThreadNonPrime(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder(state.threadsAmount);
            var res = finder.hasNoPrime(state.primeNumbers);
            assert (!res);
        }
    }

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.Throughput)
    public static class DifferentThreadsWithAtomic {
        @Benchmark
        public void TestParallelThreadNonPrime(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder(state.threadsAmount);
            var res = finder.hasNoPrime(state.primeNumbers);
            assert (!res);
        }
    }

}
