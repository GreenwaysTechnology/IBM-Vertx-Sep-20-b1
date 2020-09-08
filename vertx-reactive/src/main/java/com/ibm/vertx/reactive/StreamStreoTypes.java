package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class StreamStreoTypes {
    //observable 0 to many - o-n
    public static void observableType() {
        Observable.just(1, 2, 3, 4).subscribe(System.out::println, System.out::println, () -> System.out.println("done"));
    }

    public static void singleType() {
        Single.create(emitter -> {
            emitter.onSuccess("Hello");
        }).subscribe(System.out::println);
        //   Single.create(emitter -> emitter.onError(new RuntimeException("error"))).subscribe(System.out::println);
        Single.just(1).subscribe(System.out::println);
    }

    public static void maybeType() {
        //only item
        Maybe.just(1).subscribe(System.out::println);
        // Maybe.error(new RuntimeException("error")).subscribe(System.out::println);
        Maybe.empty()
                .subscribe(System.out::println, System.out::println, () -> System.out.println("onComplete"));

    }

    public static void completeTest() {
        Completable.complete().subscribe(() -> System.out.println("Completeable"));
    }


    public static void main(String[] args) {
        // observableType();
        // singleType();
        //maybeType();
        completeTest();
    }
}
