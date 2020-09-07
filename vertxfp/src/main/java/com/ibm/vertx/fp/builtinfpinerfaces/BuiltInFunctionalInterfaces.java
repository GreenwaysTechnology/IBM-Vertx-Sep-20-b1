package com.ibm.vertx.fp.builtinfpinerfaces;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuiltInFunctionalInterfaces {
    public static void main(String[] args) {
        Consumer<String> consumer = null;
        //consumer - annonmous
        consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("Hello How are you?");
        consumer = s -> System.out.println(s);
        consumer.accept("Hello how are you?");
        consumer = System.out::println;
        consumer.accept("Hello how are you?");
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.forEach(i -> System.out.println(i));
        list.forEach(System.out::println);

        //Supplier return value
        Supplier<String> supplier = null;
        supplier = () -> "Hello";
        System.out.println(supplier.get());

        //Predicate : boolean results
        Predicate<Integer> predicate = null;
        predicate = value -> value == 10;
        System.out.println(predicate.test(10));
        //Function
        Function<Integer, Integer> function = null;
        function = value -> value * 2;
        System.out.println(function.apply(10));

    }
}
