package com.ibm.vertx.fp.lambdarules;

@FunctionalInterface
interface Greeter {
    void greet();
}

public class FunctionalInterfaceDemo {
    public static void main(String[] args) {
        Greeter greeter = null;
        greeter = () -> {
            System.out.println("Functional interface demo");
        };
        greeter.greet();

    }
}
