package nsu.fit.tsukanov.parallel.prime.implementations.multithread;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerProvider;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParallelThreadAsync implements NonPrimesFinder {
    private int kernels = 6;

    public ParallelThreadAsync() {

    }

    public ParallelThreadAsync(int numberKernels) {
        this.kernels = numberKernels;
    }

    /**
     * @param integers
     * @return
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        int i = 0;
        Thread[] threads = new Thread[kernels];
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(integers);
        Boolean result = false;
        WorkingThread workingThread = new WorkingThread(blockingQueue, result);
        for (int i1 = 0; i1 < kernels; i1++) {
            threads[i1] = new Thread(workingThread);
            threads[i1].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static class WorkingThread implements Runnable {

        private final BlockingQueue<Integer> integers;
        private Boolean result;

        private WorkingThread(BlockingQueue<Integer> integers, Boolean result) {
            this.integers = integers;
            this.result = result;
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
            PrimeNumberChecker primeNumberChecker = PrimeNumberCheckerProvider.create(integers);
            for (Integer integer : integers) {
                if (result) {
                    break;
                }
                if (primeNumberChecker.notPrime(integer)) {
                    result = true;
                    break;
                }
            }
        }

    }
}
