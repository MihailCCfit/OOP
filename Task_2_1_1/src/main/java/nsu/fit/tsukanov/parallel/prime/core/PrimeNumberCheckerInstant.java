package nsu.fit.tsukanov.parallel.prime.core;

/**
 * Check one number for prime.
 */
public class PrimeNumberCheckerInstant implements PrimeNumberChecker {

    /**
     * Check num if it's prime number.
     *
     * @param num is number which will be checked
     * @return true if num is prime
     */
    @Override
    public boolean notPrime(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Negative argument: " + num);
        }
        if (num < 2) {
            return true;
        }
        for (int i = 2; (long) i * i <= (long) num; i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }
}
