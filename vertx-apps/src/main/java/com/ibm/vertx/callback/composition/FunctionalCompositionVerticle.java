package com.ibm.vertx.callback.composition;

import io.vertx.core.*;
import io.vertx.example.util.Runner;

class UserVerticle extends AbstractVerticle {

  public Future<String> getUser() {
    Promise<String> promise = Promise.promise();
    //biz
    String userName = "admin";
    if (userName != null) {
      promise.complete(userName);
    } else {
      promise.fail("User not found");
    }

    return promise.future();
  }

  public Future<String> login(String userName) {
    Promise<String> promise = Promise.promise();
    //biz
    if (userName.equals("admin")) {
      promise.complete("login success");
    } else {
      promise.fail("login failed");
    }
    return promise.future();
  }

  public Future<String> page(String status) {
    Promise<String> promise = Promise.promise();
    //biz
    if (status.equals("login success")) {
      promise.complete("Admin Page");
    } else {
      promise.fail("Guest Page");
    }
    return promise.future();
  }

  public void callbackHellCode() {
    getUser().onComplete(event -> {
      if (event.succeeded()) {
        System.out.println("getUser is called ");
        login(event.result()).onComplete(loginevent -> {
          if (loginevent.succeeded()) {
            System.out.println("login is called");
            page(loginevent.result()).onComplete(pageevent -> {
              System.out.println("Page is called");
              if (pageevent.succeeded()) {
                System.out.println(pageevent.result());
              } else {
                System.out.println(pageevent.cause());
              }
            });
          } else {
            System.out.println(loginevent.cause());
          }
        });
      } else {
        System.out.println(event.cause());
      }
    });
  }

  //we dont want to return future/promise but we need to transfer data to caller

  public void prepareDatabase(Handler<AsyncResult<String>> aHandler) {
    String connectionString = "localhost;3302;mongodb";
    if (connectionString != null) {
      //wrap result into future
      aHandler.handle(Future.succeededFuture(connectionString));
    } else {
      aHandler.handle(Future.failedFuture("Connection is not available"));
    }
  }

  //how to avoid callback hell code

  public void compose() {

    getUser().compose(userName -> {
      System.out.println("getUser is called ");
      return login(userName);
    }).compose(status -> {
      System.out.println("login is called");
      return page(status);
    }).onSuccess(System.out::println)
      .onFailure(System.out::println);

    getUser()
      .compose(userName -> login(userName))
      .compose(status -> page(status))
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //compose();
    //function as parameter pattern
    prepareDatabase(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
  }
}


public class FunctionalCompositionVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FunctionalCompositionVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new UserVerticle());
  }

  @Override
  public void stop() throws Exception {
    super.stop();
  }
}
