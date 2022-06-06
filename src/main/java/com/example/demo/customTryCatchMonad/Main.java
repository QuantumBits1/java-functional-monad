package com.example.demo.customTryCatchMonad;

public class Main {

    public static void main(String[] args) {

        //perform a computation that will fail
        //return a Try-wrapped Integer type
        Try<Integer> tryValue = Try.ofThrowable(() -> Integer.valueOf("1"));

        //parse a string and get the value
        try {
            int value = Try.ofThrowable(() -> Integer.valueOf("a")).get();
            System.out.println(value);
        } catch (Throwable e) {
            System.out.println("error: " + e.getMessage());
        }

        try {
            String value2 = Try.ofThrowable(() -> String.valueOf(1))
                    .flatMap(s -> Try.ofThrowable(s::toUpperCase))
                    .get();
            System.out.println(value2);
        } catch (Throwable e) {
            System.out.println("error: " + e.getMessage());
        }

    }
}
