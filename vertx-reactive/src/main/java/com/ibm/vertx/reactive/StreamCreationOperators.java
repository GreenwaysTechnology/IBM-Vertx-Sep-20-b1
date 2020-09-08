package com.ibm.vertx.reactive;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Employee {
    private int id;
    private String name;

    Employee() {
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


public class StreamCreationOperators {
    public static void justOperator() {
        Observable<Integer> stream = Observable.just(1, 2, 3, 4, 5, 6, 7);
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    public static void justEmployee() {
        Observable<Employee> stream = Observable.just(new Employee(1, "Subramanian"));
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));

    }

    //data source is array
    public static void arrayOperator() {
        Integer[] array = {1, 2, 3, 4, 5};
        Observable<Integer> stream = Observable.fromArray(array);
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    public static void listOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Observable<Integer> stream = Observable.fromIterable(list);
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    public static void listEmployeeOperator() {
        List<Employee> list = Arrays.asList(new Employee(1, "a"), new Employee(2, "b"));
        Observable<Employee> stream = Observable.fromIterable(list);
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    //i want to generate a seq of 1000 no;
    public static void rangeOperator() {
        Observable<Integer> stream = Observable.range(1, 100);
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    //range and interval looks like same but, interval start emitting a sequence of numbmers based on cpu
    //timer
    public static void intervalOperator() {
        Observable<Long> stream = Observable.interval(1000, TimeUnit.MILLISECONDS);
        //  stream.subscribe(data -> System.out.println(data), error -> System.out.println(error), () -> System.out.println("complete"));
        stream.subscribe(System.out::println, System.out::println, () -> System.out.println("complete"));
    }

    public static void main(String[] args) throws InterruptedException {
        //justOperator();
        //arrayOperator();
        //listOperator();
        //rangeOperator();
        // intervalOperator();
        justEmployee();
        listEmployeeOperator();
        Thread.sleep(10000);

    }
}
