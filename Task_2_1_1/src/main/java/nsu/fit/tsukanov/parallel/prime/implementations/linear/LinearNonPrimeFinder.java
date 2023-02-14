package nsu.fit.tsukanov.parallel.prime.implementations.linear;

import nsu.fit.tsukanov.parallel.prime.core.CheckerProvider;
import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;

import java.util.Collection;

public class LinearNonPrimeFinder implements NonPrimesFinder {

    /**
     * Check collection for containing prime numbers.
     *
     * @param integers collection of numbers for checking
     * @return true if collection has a complex number
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        PrimeNumberChecker primeNumberChecker = CheckerProvider.create(integers);
        for (Integer integer : integers) {
            if (primeNumberChecker.notPrime(integer)) {
                return true;
            }
        }
        return false;
    }
}