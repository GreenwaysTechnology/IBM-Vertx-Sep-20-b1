package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;


public class StreamTransformation {
    //transformation ; transform stream data from one fromate to another
    public static void transform() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) //source / up stream
                .map(i -> {
                    System.out.println("first Map " + i); // down / up stream
                    return i * 10; //new stream
                })
                .map(i -> {
                    System.out.println("Second Map " + i);
                    return i * 2; //new stream
                })
                .map(i -> {
                    System.out.println("Third Map " + i);
                    return i * 5; // new stream
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));
    }

    public static void filter() {
        Observable
                .range(1, 100)
                .filter(even -> {  //up stream
                    System.out.println("Filter is called for " + even);
                    return even % 2 == 0;
                })
                .map(i -> { //down stream ; it wont get all data from the source.
                    System.out.println("map is called for " + i);
                    return i;
                })
                .subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));

    }

    //zip operator ; coimbines two stream result into one.
    public static void zip() {
        Observable<Integer> intStream = Observable.just(1, 2, 3, 4);
        Observable<String> stringStream = Observable.just("a", "b", "c", "d", "e");

        Observable.zip(intStream, stringStream, (i, str) -> {
            System.out.println("I " + i + " String " + str);
            return i + str;
        }).subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));

    }

    //flatMap ;
    public static void flatMap() {
        Observable.just(1, 2, 3, 4, 5).map(i -> {
                    System.out.println("map return vaule");
                    return i;// return only value
                }
        ).subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));
        Observable.just(1, 2, 3, 4, 5).flatMap(i -> {
                    System.out.println("source value " + i);
                    return Observable.just(2, 3, 8, 9);
                }
        ).map(j -> {
            System.out.println("Previous flat map value : " + j);
            return j * 2;
        }).subscribe(System.out::println, System.out::println, () -> System.out.println("Done"));
    }

    //other operators distinct,sorted
    public static void utilties() {
        List<String> words = Arrays.asList("the", "quick", "quick", "brown", "fox", "apple", "fox", "jumped", "over", "the", "lazy", "dog");
        Observable<String> manyLetters = Observable.fromIterable(words)
                .flatMap(word -> Observable.just(word))
                .distinct() // boolean  operator will eleminate duplicates
                .sorted();  // operator which sorting.
        manyLetters.subscribe(System.out::println);
    }
  //mathmetical operators
//    public static void sum(){
//        Observable.range(1,10).max
//    }

    public static void main(String[] args) {
        // transform();
        //filter();
        //zip();
        //flatMap();
        utilties();
    }
}
