package nsu.fit.tsukanov.parallel.prime.core;

import java.util.Collection;

public interface NonPrimesFinder {
    /**
     * Check collection for containing prime numbers.
     *
     * @param integers collection of numbers for checking
     * @return true if collection has a complex number
     */
    boolean hasNoPrime(Collection<Integer> integers);
}
