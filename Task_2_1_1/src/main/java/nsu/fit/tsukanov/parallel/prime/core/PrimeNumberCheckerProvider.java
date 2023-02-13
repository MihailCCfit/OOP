package nsu.fit.tsukanov.parallel.prime.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class PrimeNumberCheckerProvider {

    public static PrimeNumberChecker create(int maxNumber){
        //TODO: Сделать анализатор и более хорошую штуку
        return new PrimeNumberCheckerInstant();
    }
    public static PrimeNumberChecker create(int maxNumber, int size){
        //TODO: Сделать анализатор и более хорошую штуку
        return new PrimeNumberCheckerInstant();
    }
    public static PrimeNumberChecker create(Collection<Integer> numbers){
        //TODO: Сделать анализатор и более хорошую штуку
        Stream<Integer> stream = numbers.stream();
//        return create(stream.max(Integer::compareTo).get(), stream.reduce((int)0,(x,y) -> x+1));
        return new PrimeNumberCheckerInstant();
    }
    public static PrimeNumberChecker create(int[] numbers){
        //TODO: Сделать анализатор и более хорошую штуку
//        return create(Arrays.stream(numbers).max().getAsInt(), numbers.length);
        return new PrimeNumberCheckerInstant();
    }
}
