package nsu.fit.tsukanov.parallel.prime.implementations.parallelstream;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerProvider;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;

import java.util.Collection;

public class ParallelStreamNonPrimeNumberFinder implements NonPrimesFinder {
    /**
     * @param integers
     * @return
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        PrimeNumberChecker primeNumberChecker = PrimeNumberCheckerProvider.create(integers);
        return integers.parallelStream().anyMatch(primeNumberChecker::notPrime);
    }
}
