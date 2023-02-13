package nsu.fit.tsukanov.parallel.prime.implementations.tools;

import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerInstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EratosthenesSieve {

    private final int[] isPrime;
    private final int[] nextPrime;
    private final long SEED = 123456;
    Random random = new Random(SEED);

    public EratosthenesSieve(int maxSize) {
        isPrime = new int[maxSize];
        nextPrime = new int[maxSize];
        fill();
    }


    private void fill() {
        isPrime[0] = 1;
        isPrime[1] = 1;
        int prev = -1;
        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i] == 0) {
                for (int j = i + i; j < isPrime.length; j += i) {
                    isPrime[j] = 1;
                }
                for (int j = prev + 1; j <= i; j++) {
                    nextPrime[j] = i;
                }
                prev = i;
            }
        }
        int n = findNext(prev + 1);
        for (int i = prev + 1; i < nextPrime.length; i++) {
            nextPrime[i] = n;
        }
    }

    private int findNext(int n) {
        PrimeNumberChecker numberChecker = new PrimeNumberCheckerInstant();
        int answer = n;
        while (numberChecker.notPrime(answer)) {
            answer++;
        }
        return answer;
    }

    public boolean isPrime(int n) {
        if (n >= isPrime.length || n < 0) {
            throw new IllegalArgumentException();
        }
        return isPrime[n] == 0;
    }

    public int nextPrime(int n) {
        if (n >= isPrime.length || n < 0) {
            throw new IllegalArgumentException();
        }
        return nextPrime[n];
    }

    public int getRandomPrime() {
        return nextPrime[random.nextInt(nextPrime.length)];
    }

    public List<Integer> getNumbers(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomPrime());
        }
        return list;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    public void setSeed() {
        random.setSeed(SEED);
    }

}
