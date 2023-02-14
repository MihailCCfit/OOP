package nsu.fit.tsukanov.parallel.prime.implementations.parallelstream;

import nsu.fit.tsukanov.parallel.prime.core.CheckerProvider;
import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerInstant;

import java.util.Collection;

public class ParallelStreamNonPrimeNumberFinder implements NonPrimesFinder {
    /**
     * Check collection for containing prime numbers.
     *
     * @param integers collection of numbers for checking
     * @return true if collection has a complex number
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        PrimeNumberChecker primeNumberChecker = new PrimeNumberCheckerInstant();
        return integers.parallelStream().anyMatch(primeNumberChecker::notPrime);
    }
}
