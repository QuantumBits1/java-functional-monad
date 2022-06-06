package com.example.demo.optionalExample;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<Integer> val1 = Optional.of(1);
        Optional<Integer> val2 = Optional.of(2);

        Integer result = nestingOptionalAddOperation(val1, val2).get();
        System.out.println(result);

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

    //TODO: complete this function
    //refer to https://medium.com/@afcastano/monads-for-java-developers-part-1-the-optional-monad-aa6e797b8a6e
    public static Optional<Integer> chainingOptionalAddOperation(Optional<Integer> val1, Optional<Integer> val2) {
        return Optional.empty();
    }

}
