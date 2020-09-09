package com.ibm.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class HelloWorldVerticle extends AbstractVerticle {
  public static void main(String[] args) {
//    Vertx vertx = Vertx.vertx();
//    //vertx.deployVerticle(new HelloWorldVerticle());
//    //pass class name in string format
//    //vertx.deployVerticle("com.ibm.vertx.core.HelloWorldVerticle");
//    vertx.deployVerticle(HelloWorldVerticle.class.getName());
    Runner.runExample(HelloWorldVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("HelloWorld Verticle is deployed");
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("HelloWorld Verticle is un deployed");
  }
}
