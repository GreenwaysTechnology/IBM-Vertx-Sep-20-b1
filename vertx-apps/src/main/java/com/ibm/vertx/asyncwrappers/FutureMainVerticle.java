package com.ibm.vertx.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;


class FutureVerticle extends AbstractVerticle {

  //future object creation and how to use
  public Future<Void> getEmptyFuture() {
    //how to send empty success response
    Future<Void> future = Future.future();
    //encapsulate response : success/ failure
    future.complete();
    //return future instance reference
    return future;
  }

  //future object which sends success result ; String
  public Future<String> getSuccessFuture() {
    //create Future
    Future<String> future = Future.future();

    //encapsulate success result
    future.complete("Hello , This is Vertx future");

    return future;
  }

  //future object which sends failure result ; String
  public Future<String> getFailureFuture() {
    //create Future
    Future<String> future = Future.future();

    //encapsulate success result
    future.fail("Hello ,This failed Response");

    return future;
  }

  //biz logic
  public Future<String> loginFuture() {
    //create Future
    Future<String> future = Future.future();
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      future.complete("Login Success " + userName);
    } else {
      future.fail("Login failed");
    }

    return future;
  }

  public Future<String> loginFutureUsingFactory() {
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      return Future.succeededFuture("Login Success " + userName);
    }
    return Future.failedFuture("Login failed");

  }

  @Override
  public void start() throws Exception {
    super.start();
    //handle async result.
    if (getEmptyFuture().succeeded()) {
      System.out.println("Result is success");
    }
    //handle success response
    getSuccessFuture().setHandler(new Handler<AsyncResult<String>>() {
      @Override
      public void handle(AsyncResult<String> event) {
        //read result
        if (event.succeeded()) {
          System.out.println(event.result());
        } else {
          System.out.println(event.cause());
        }
      }
    });
    getSuccessFuture().setHandler(event -> {
      //read result
      if (event.succeeded()) {
        System.out.println(event.result());
      } else {
        System.out.println(event.cause());
      }
    });
    getSuccessFuture().onComplete(event -> {
      //read result
      if (event.succeeded()) {
        System.out.println(event.result());
      } else {
        System.out.println(event.cause());
      }
    });
    getSuccessFuture().onSuccess(response -> System.out.println(response));
    getSuccessFuture().onSuccess(System.out::println);
    //
    getFailureFuture().onComplete(event -> {
      //read result
      if (event.succeeded()) {
        System.out.println(event.result());
      } else {
        System.out.println(event.cause());
      }
    });
    getFailureFuture().onFailure(System.out::println);
    ////////////////////////////////////////////////////////////
    loginFuture().onComplete(event -> {
      if (event.succeeded()) {
        System.out.println(event.result());
      } else {
        System.out.println(event.cause());
      }
    });
    loginFuture()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    loginFutureUsingFactory()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);

  }
}


public class FutureMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FutureMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FutureVerticle());
  }
}
