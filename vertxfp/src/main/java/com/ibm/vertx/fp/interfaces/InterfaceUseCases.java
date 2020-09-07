package com.ibm.vertx.fp.interfaces;

interface Greeter {
    void greet();
}

//implement with class
class GreeterImpl implements Greeter {
    @Override
    public void greet() {
        System.out.println("Hello Greeter");
    }
}


public class InterfaceUseCases {
    public static void main(String[] args) {
        //Declare Greeter type
        Greeter greeter = null;
        //create GreeterImpl instance
        greeter = new GreeterImpl();
        greeter.greet();
        //inner classes way of implementing interfaces. :Annonmous inner classes
        greeter = new Greeter() {
            @Override
            public void greet() {
                System.out.println("Greeter with annonmous inner class");
            }
        };
        greeter.greet();

        //functional way of implementing interface : just simplify annonmous inner class syntax only
        greeter = () -> {
            System.out.println("Greeter with Lambda");
        };
        greeter.greet();


    }
}
