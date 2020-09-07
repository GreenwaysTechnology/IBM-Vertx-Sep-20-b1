package com.ibm.vertx.fp.lambdarules;

@FunctionalInterface
interface Welcome {
    void sayWelcome();
}

/**
 * Arg type  can be any thing : primitives, int ,char or can be objects or even another function
 * Return type can be anything.
 */

//args and parameters
@FunctionalInterface
interface Adder {
    void add(int a, int b);
}

//return type :
@FunctionalInterface
interface Multiplication {
    int multiply(int a, int b);
}

//single input and single output
@FunctionalInterface
interface Single {
    int accept(int a);
}

public class LambdaAdvanced {
    public static void main(String[] args) {
        Welcome welcome = null;
        welcome = () -> {
            System.out.println("Hello");
            System.out.println("Hello");
            System.out.println("Hello");
            System.out.println("Hello");

        };
        welcome.sayWelcome();
        //if function body, has only one line of code : we can remove {}
        welcome = () -> System.out.println("single line of body");
        welcome.sayWelcome();
        ////////////////////////////////////////////////////////////////////////////
        Adder adder = null;
        //a, and b are args
        adder = (int a, int b) -> {
            int c = a + b;
            System.out.println("Adder result " + c);
        };
        //10 and 20 are parameters
        adder.add(10, 20);
        //Type inference: implicit type understanding., Type of a and b is understood by default
        adder = (a, b) -> {
            int c = a + b;
            System.out.println("Adder result " + c);
        };
        //10 and 20 are parameters
        adder.add(10, 20);
        //////////////////////////////////////////////////////////////////////////////
        Multiplication multiplication = null;
        multiplication = (a, b) -> {
            int c = a * b;
            return c;
        };
        System.out.println(multiplication.multiply(10, 10));
        //return only result
        multiplication = (a, b) -> {
            return a * b;
        };
        System.out.println(multiplication.multiply(10, 10));
        //if function has only return statement, no more body : remove {} and return statement
        multiplication = (a, b) -> a * b;
        System.out.println(multiplication.multiply(10, 10));
        //Single input and single output
        Single single = null;
        single = (a) -> a * 2;
        System.out.println(single.accept(10));
        //single input, we can remove () as well
        single = a -> a * 2;
        System.out.println(single.accept(10));


    }
}
