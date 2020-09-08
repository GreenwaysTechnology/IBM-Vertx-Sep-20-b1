package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SubscriptionApis {
    public static void main(String[] args) {
        Observable.range(1, 10)
                .map(i -> {
                    System.out.println("map is called");
                    return i;
                })
                .doOnSubscribe(sub -> {
                    System.out.println("subscription has strated");
                })
                .doOnNext(item -> {
                    System.out.println("called for each item " + item);
                })
                .doOnError(err -> {
                    System.out.println("called for error " + err);
                })
                .doOnComplete(() -> {
                    System.out.println("called for complete");
                }).subscribe();
        /////////////////////////////////////////////////////////////////////
        Observable.interval(1000, TimeUnit.MILLISECONDS).doOnSubscribe(subscription -> {
            System.out.println("subscription has strated");
        }).doOnNext(item -> {
            System.out.println("called for each item " + item);
        }).doOnError(err -> {
            System.out.println("called for error " + err);
        }).doOnComplete(() -> {
            System.out.println("called for complete");
        }).doFinally(() -> {
            System.out.println("finally is called");
        }).blockingLast(); //stop the main thread until you dispose it.
    }
}
