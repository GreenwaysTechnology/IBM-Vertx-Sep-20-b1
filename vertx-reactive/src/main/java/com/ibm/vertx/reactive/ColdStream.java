package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class ColdStream {
    public static void coldStream() throws InterruptedException {
        Observable<Long> myObservable = Observable.interval(1, TimeUnit.SECONDS);

        //many subscribers; when ever i call subscribe method, new subscriber object is
        myObservable.subscribe(item -> System.out.println("Observer 1: " + item));
        ;
        //after 3scs new subscriber joins
        Thread.sleep(3000);
        Disposable subscriber2 = myObservable
                .doOnSubscribe((r) -> System.out.println("Observer 2 Joining"))
                .doFinally(() -> System.out.println("Observer 2 left"))
                .subscribe(item -> System.out.println("Observer 2: " + item));
        Thread.sleep(5000);
        subscriber2.dispose();
        Thread.sleep(8000);
    }

    public static void hotStream() throws InterruptedException {
        //cold
        Observable<Long> myObservable = Observable.interval(1, TimeUnit.SECONDS);
        //convert cold into hot
        ConnectableObservable<Long> connectableObservable = myObservable.publish();
        connectableObservable.connect();
        connectableObservable
                .doOnSubscribe((r) -> System.out.println("Observer 1 Joining"))
                .subscribe(item -> System.out.println("Observer 1: " + item));
        Thread.sleep(3000);
        connectableObservable
                .doOnSubscribe((r) -> System.out.println("Observer 2 Joining"))
                .subscribe(item -> System.out.println("Observer 2: " + item));
        Thread.sleep(3500);
        connectableObservable
                .doOnSubscribe((r) -> System.out.println("Observer 3 Joining"))
                .subscribe(item -> System.out.println("Observer 3: " + item));

        Thread.sleep(8000);

    }

    public static void main(String[] args) throws InterruptedException {
        //    coldStream();
        hotStream();

    }
}
