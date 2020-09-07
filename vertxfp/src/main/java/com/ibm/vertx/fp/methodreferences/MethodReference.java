package com.ibm.vertx.fp.methodreferences;


@FunctionalInterface
interface Printer {
    void print(String message);
}

@FunctionalInterface
interface UpperCase {
    String convertToUpper(String message);
}

class ThreadImpl implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

//
class MicroTask {
    public static void startstaticMicrotask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startMicrotask() {
        System.out.println(Thread.currentThread().getName());
    }
}

class Task {

    //thread logic
    private void startMicrotask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void start() {
        Thread thread = null;
        thread = new Thread(new ThreadImpl());
        thread.start();
        //
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();
        //lambda
        Runnable runner = () -> System.out.println(Thread.currentThread().getName());
        thread = new Thread(runner);
        thread.start();
        //inline lambda
        thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();
        //isloate thread logic into another method.
        thread = new Thread(() -> this.startMicrotask());
        thread.start();
        //using method reference we can isloate the code
        //instance method - method reference
        thread = new Thread(this::startMicrotask);
        thread.start();
        MicroTask microTask = new MicroTask();
        thread = new Thread(microTask::startMicrotask);
        thread.start();
        thread = new Thread(new MicroTask()::startMicrotask);
        thread.start();
        thread = new Thread(MicroTask::startstaticMicrotask);
        thread.start();
    }
}


public class MethodReference {
    public static void main(String[] args) {
        Task task = new Task();
        task.start();

        Printer printer = null;
        printer = message -> System.out.println(message);
        printer.print("Hello Printer");
        //method Reference
        printer = System.out::println;
        printer.print("Hello Printer");

        UpperCase upperCase = null;
        upperCase = message -> {
            return message.toUpperCase();
        };
        System.out.println(upperCase.convertToUpper("hello"));
        upperCase = message -> message.toUpperCase();
        System.out.println(upperCase.convertToUpper("hello"));
        //method reference
        upperCase = String::toUpperCase;
        System.out.println(upperCase.convertToUpper("hello"));


    }
}
