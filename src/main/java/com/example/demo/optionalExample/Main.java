package com.example.demo.optionalExample;

import java.util.Optional;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        Optional<Integer> val1 = Optional.of(1);
        Optional<Integer> val2 = Optional.of(2);

        Integer result = nestingOptionalAddOperation(val1, val2).get();
        System.out.println(result);

        Optional<Integer> a = Optional.of(13);
        Optional<Integer> b = Optional.of(42);
        BiFunction<Integer, Integer, Integer> plus = (x, y) -> x + y;
        BiFunction<Integer, Integer, Integer> times = (x, y) -> x * y;

        Optional<Integer> addResult = compute(plus, a ,b);
        Optional<Integer> multiplyResult = compute(times, a ,b);

        System.out.println("addResult: " + addResult.get());
        System.out.println("multiplyResult: " + multiplyResult.get());

        //TODO: stream of optionals & flattening

        Counter counter1 = new Counter(1);
        Counter counter2 = new Counter(2);
        Optional<Integer> optCounter1 = Optional.of(counter1.getCounter());
        Optional<Integer> optCounter2 = Optional.of(counter2.getCounter());


    }

    public static Optional<Integer> nestingOptionalAddOperation(Optional<Integer> val1, Optional<Integer> val2) {
        return
                val1.flatMap( first ->
                        val2.flatMap( second ->
                                Optional.of(first + second)
                        ));
    }

    public static <A, B, R> Optional<R> compute(BiFunction<A, B, R> operation, Optional<A> oa, Optional<B> ob) {
        return oa.flatMap(a -> ob.map(b -> operation.apply(a, b)));
    }

    //TODO: complete this function
    //refer to https://medium.com/@afcastano/monads-for-java-developers-part-1-the-optional-monad-aa6e797b8a6e
    public static Optional<Integer> chainingOptionalAddOperation(Optional<Integer> val1, Optional<Integer> val2) {
        return Optional.empty();
    }

}
