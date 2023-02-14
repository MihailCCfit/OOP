package nsu.fit.tsukanov.parallel.prime.implementations.multithread;

import nsu.fit.tsukanov.parallel.prime.core.CheckerProvider;
import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ParallelThreadNonPrimeNumberFinder implements NonPrimesFinder {
    private int numberOfThreads = 6;

    public ParallelThreadNonPrimeNumberFinder() {


    }

    public ParallelThreadNonPrimeNumberFinder(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    private static class MyBoolean {
        boolean flag;

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
                CheckerProvider.create(integers, numberOfThreads));
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
        private final Iterator<Integer> iterator;
        private final Collection<Integer> integers;
        private final MyBoolean result = new MyBoolean();
        private final PrimeNumberChecker primeNumberChecker;

        private WorkingThread(Collection<Integer> integers, PrimeNumberChecker primeNumberChecker) {
            this.primeNumberChecker = primeNumberChecker;
            iterator = integers.iterator();
            this.integers = integers;
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
            synchronized (iterator) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
            }
            return null;
        }
    }

}
