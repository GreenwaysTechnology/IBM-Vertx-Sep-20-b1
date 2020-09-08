package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReactiveThreading {
    public static void main(String[] args) {
        Flowable.just("Hello Scheduler")
                .map(message -> { //up stream
                    System.out.println("First Map is running on " + Thread.currentThread().getName());
                    return message;
                })
                .observeOn(Schedulers.computation()) // attach thread , so that down streams will run
                .map(message -> { //down and up stream
                    System.out.println("Second Map is running on " + Thread.currentThread().getName());
                    return message;
                })
                .observeOn(Schedulers.io()) // attach thread , so that down streams will run
                .map(message -> { //down and up stream
                    System.out.println("Third Map is running on " + Thread.currentThread().getName());
                    return message;
                })
                .subscribeOn(Schedulers.newThread())
                .blockingLast();


    }
}
