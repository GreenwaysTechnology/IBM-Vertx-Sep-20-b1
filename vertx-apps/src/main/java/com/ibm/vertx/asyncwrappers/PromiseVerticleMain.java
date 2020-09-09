package com.ibm.vertx.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class PromiseVerticle extends AbstractVerticle {

  public Future<String> getSuccess() {
    Promise<String> promise = Promise.promise();
    promise.complete("Hello Promise");
    return promise.future();
  }

  public Future<String> login() {
    //create Future
    Promise<String> promise = Promise.promise();
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      promise.complete("Login Success " + userName);
    } else {
      promise.fail("Login failed");
    }

    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getSuccess().onSuccess(System.out::println);
    login()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
  }
}

public class PromiseVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(PromiseVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PromiseVerticle());
  }
}
