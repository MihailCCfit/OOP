package nsu.fit.tsukanov.parallel;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.linear.LinearNonPrimeFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.multithread.ParallelThreadWithAtomic;
import nsu.fit.tsukanov.parallel.prime.implementations.parallelstream.ParallelStreamNonPrimeNumberFinder;
import nsu.fit.tsukanov.parallel.prime.implementations.tools.EratosthenesSieve;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

public class DifferentTypesBenchmarking {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"2", "10", "25", "50", "100", "250", "500", "1000", "5000", "10000", "50000"})
        public int numberOfPrimeNumbers;
        public int maxInt = 10000000;
        public EratosthenesSieve eratosthenesSieve = new EratosthenesSieve(maxInt);
        public List<Integer> primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);
        public List<Integer> bigPrimeNumbers;

        @Setup(Level.Trial)
        public void setUp() {
            bigPrimeNumbers = new ArrayList<>();
            for (int i = 0; i < numberOfPrimeNumbers; i++) {
                bigPrimeNumbers.add(1000000007);
            }
            eratosthenesSieve.setSeed();
            primeNumbers = eratosthenesSieve.getNumbers(numberOfPrimeNumbers);
        }
    }

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.Throughput)
    public static class DifferentTypesBigNumbers {
        /**
         * large numbers
         */
        @Benchmark
        public void ParallelThread(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
            var res = finder.hasNoPrime(state.bigPrimeNumbers);
            assert (!res);
        }

        @Benchmark

        public void ParallelThreadWithAtomic(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelThreadWithAtomic();
            var res = finder.hasNoPrime(state.bigPrimeNumbers);
            assert (!res);
        }

        @Benchmark
        public void Linear(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new LinearNonPrimeFinder();
            var res = finder.hasNoPrime(state.bigPrimeNumbers);
            assert (!res);
        }

        //
        @Benchmark
        public void ParallelStream(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelStreamNonPrimeNumberFinder();
            var res = finder.hasNoPrime(state.bigPrimeNumbers);
            assert (!res);
        }
    }

    @State(Scope.Benchmark)
    @BenchmarkMode(Mode.Throughput)
    public static class DifferentTypes {

        /**
         * random numbers
         */
        @Benchmark
        public void ParallelThread(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelThreadNonPrimeNumberFinder();
            var res = finder.hasNoPrime(state.primeNumbers);
            assert (!res);
        }

        @Benchmark
        public void ParallelThreadWithAtomic(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelThreadWithAtomic();
            var res = finder.hasNoPrime(state.primeNumbers);
            assert (!res);
        }


        @Benchmark
        public void Linear(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new LinearNonPrimeFinder();
            var res = finder.hasNoPrime(state.primeNumbers);
            assert (!res);
        }


        @Benchmark
        public void ParallelStream(Blackhole blackhole, BenchmarkState state) {
            NonPrimesFinder finder = new ParallelStreamNonPrimeNumberFinder();
            var res = finder.hasNoPrime(state.primeNumbers);
            assert (!res);
        }

    }

}