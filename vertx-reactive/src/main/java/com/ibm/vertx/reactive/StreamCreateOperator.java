package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Observable;

public class StreamCreateOperator {
    public static void main(String[] args) {
        //observable + push based iterator + event driven
        Observable<Integer> stream = Observable.create(emitter -> {
            //send via event notication ; push
            emitter.onNext(1); //data event -----subscribe
            emitter.onNext(2); //data event -----subscribe
            emitter.onNext(3); //data event -----subscribe
            //throw error
            // emitter.onError(new RuntimeException("something went wrong"));
            emitter.onNext(4); //data event -----subscribe
            emitter.onNext(5); //data event -----subscribe
            emitter.onNext(6); //data event -----subscribe

            emitter.onComplete();
            emitter.onNext(7); //data event -----subscribe ; will not be reachable

        });

        //subscriber
        stream.subscribe(data -> {
            System.out.println(data);
        }, error -> {
            System.out.println(error);
        }, () -> System.out.println("complete"));


    }
}
