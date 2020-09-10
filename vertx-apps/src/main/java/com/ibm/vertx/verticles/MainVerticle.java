package com.ibm.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

class GreeterVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter " + Thread.currentThread().getName());
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    System.out.println("verticle stopped");
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
  String deploymentid = "";

  public static void main(String[] args) {
    Runner.runExample(MainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //access vertx instance via abstract verticle
    System.out.println("Main Verticle starts" + Thread.currentThread().getName());
    vertx.deployVerticle(new GreeterVerticle(), ar -> {
      if (ar.succeeded()) {
        System.out.println("Deployment Id : " + ar.result());
        deploymentid = ar.result();
      }
    });
    vertx.setTimer(5000, h -> {
      vertx.undeploy(deploymentid, res -> {
        if (res.succeeded()) {
          System.out.println("Undeployed ok");
        } else {
          System.out.println("Undeploy failed!");
        }
      });
    });
    vertx.deployVerticle(new HelloVerticle());
  }
}
