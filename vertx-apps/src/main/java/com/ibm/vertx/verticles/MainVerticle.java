package com.ibm.vertx.verticles;

import com.ibm.vertx.core.HelloWorldVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

class GreeterVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter " + Thread.currentThread().getName());
  }
}

class HelloVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Hello " + Thread.currentThread().getName());
  }
}

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(MainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //access vertx instance via abstract verticle
    System.out.println("Main Verticle starts" + Thread.currentThread().getName());
    vertx.deployVerticle(new GreeterVerticle());
    vertx.deployVerticle(new HelloVerticle());
  }
}
