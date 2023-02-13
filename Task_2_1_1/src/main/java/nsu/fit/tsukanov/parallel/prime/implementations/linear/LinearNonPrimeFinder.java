package nsu.fit.tsukanov.parallel.prime.implementations.linear;

import nsu.fit.tsukanov.parallel.prime.core.NonPrimesFinder;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberCheckerProvider;
import nsu.fit.tsukanov.parallel.prime.core.PrimeNumberChecker;

import java.util.Collection;

public class LinearNonPrimeFinder implements NonPrimesFinder {

    /**
     * @param integers
     * @return
     */
    @Override
    public boolean hasNoPrime(Collection<Integer> integers) {
        PrimeNumberChecker primeNumberChecker = PrimeNumberCheckerProvider.create(integers);
        for (Integer integer : integers) {
            if (primeNumberChecker.notPrime(integer)){
                return true;
            }
        }
        return false;
    }
}
