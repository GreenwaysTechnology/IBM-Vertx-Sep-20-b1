package com.ibm.vertx.fp.lambdarules;

//functional interface: interface must have only one abstract method, if so, the compiler will throw
//error when you declare lambda
interface Hello {
    //static methods
    public static void saySomething() {
        System.out.println("Something static");
    }
    public static void saySomething1() {
        System.out.println("Something1 static");
    }
    //abstract method
    void sayHello();
    //other methods
    public default void doSomething() {
        System.out.println("something default");
    }
    public default void doSomething1() {
        System.out.println("something default");
    }
}


public class LambdaRules {
    public static void main(String[] args) {
        Hello hello = null;
        hello = () -> {
            System.out.println("Hello");
        };
        hello.sayHello();
        hello.doSomething();
        Hello.saySomething();
    }
}

