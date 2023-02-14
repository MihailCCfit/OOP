package nsu.fit.tsukanov.parallel.prime.implementations.multithread;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerInstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelThreadWithAtomic implements NonPrimesFinder {
    private int numberOfThreads = 8;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public ParallelThreadWithAtomic() {


    }

    public ParallelThreadWithAtomic(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    private static class MyBoolean {
        public boolean flag;

        public MyBoolean() {
            this(false);
        }

        public MyBoolean(boolean flag) {
            this.flag = flag;
        }
    }

    /**
     * Check collection for containing prime numbers.
     *
     * @param integers collection of numbers for checking
     * @return true if collection has a complex number
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        WorkingThread workingThread = new WorkingThread(integers,
                new PrimeNumberCheckerInstant(), atomicInteger);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(workingThread));
            threads.get(i).start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return workingThread.result.flag;
    }

    private static class WorkingThread implements Runnable {
        private final Integer[] numbers;
        private final MyBoolean result = new MyBoolean();
        private final PrimeNumberChecker primeNumberChecker;
        private final AtomicInteger atomicInteger;

        private WorkingThread(Collection<Integer> integers, PrimeNumberChecker primeNumberChecker,
                              AtomicInteger atomicInteger) {
            this.atomicInteger = atomicInteger;
            this.primeNumberChecker = primeNumberChecker;
            numbers = integers.toArray(new Integer[0]);
        }

        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            Integer number;
            while ((number = getNext()) != null) {
                if (primeNumberChecker.notPrime(number)) {
                    result.flag = true;
                }
            }
        }

        private Integer getNext() {
            if (result.flag) {
                return null;
            }
            int i = atomicInteger.getAndIncrement();
            if (i < numbers.length) {
                return numbers[i];
            }
            return null;
        }
    }


}
