package nsu.fit.tsukanov.parallel.prime.implementations.multithread;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerProvider;

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

    private static class atomicBoolean {
        private boolean flag;

        public atomicBoolean() {
            this(false);
        }

        public atomicBoolean(boolean flag) {
            this.flag = flag;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

    /**
     * @param integers
     * @return
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        WorkingThread workingThread = new WorkingThread(integers);
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
        return workingThread.result.isFlag();
    }

    private static class WorkingThread implements Runnable {
        private final Iterator<Integer> iterator;
        private final Collection<Integer> integers;
        private final atomicBoolean result = new atomicBoolean();

        private WorkingThread(Collection<Integer> integers) {
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
            PrimeNumberChecker primeNumberChecker = PrimeNumberCheckerProvider.create(integers);
            while ((number = getNext()) != null) {
                if (primeNumberChecker.notPrime(number)) {
                    result.setFlag(true);
                }
            }
        }

        private Integer getNext() {
            if (result.isFlag()) {
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
