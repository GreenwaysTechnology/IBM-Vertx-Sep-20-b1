package com.ibm.vertx.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class ExecuteBlockingVerticle extends AbstractVerticle {
  private void sayHello(Promise<String> promise) {
    System.out.println("Say Hello : " + Thread.currentThread().getName());
    try {
      Thread.sleep(4000);
      System.out.println("Wake Up read to send data to Non blocking Service");
      //this result will be accessed inside non blocking code
      promise.complete("Hey this is blocking Result");

    } catch (InterruptedException es) {
      promise.fail("Something went wrong in blocking service");
    }
  }

  //read result from blocking service
  private void resultHandler(AsyncResult<String> ar) {
    System.out.println("Result Handler" + Thread.currentThread().getName());
    if (ar.succeeded()) {
      System.out.println("Blocking api Result goes Ready Here");
      System.out.println(ar.result());
    } else {
      System.out.println(ar.cause().getMessage());
    }
  }

  public void exchange() {
    //run blocking ,blocking code result i need inside nonblocking
    vertx.executeBlocking(this::sayHello, this::resultHandler);
  }

  public void httpblocking() {
    vertx.createHttpServer().requestHandler(request -> {
      vertx.<String>executeBlocking(promise -> {
        // Do the blocking operation in here
        // Imagine this was a call to a blocking API to get the result
        try {
          Thread.sleep(5000);
        } catch (Exception ignore) {
        }
        String result = "hello , i am blocked";

        promise.complete(result);

      }, res -> {

        if (res.succeeded()) {
          request.response().putHeader("content-type", "text/plain").end(res.result());

        } else {
          res.cause().printStackTrace();
        }
      });

    }).listen(8080);
  }

  @Override
  public void start() throws Exception {
    super.start();
    exchange();
    httpblocking();

  }
}


class MyVerticle extends AbstractVerticle {
  public void blockMe() {
    try {
      System.out.println("zzzzzz");
      Thread.sleep(5000);
    } catch (InterruptedException exception) {
      System.out.println(exception);
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    //write blocking code
    blockMe();
    System.out.println(Thread.currentThread().getName());
  }
}


public class BlockingVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BlockingVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //  DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
    //vertx.deployVerticle(new MyVerticle(),deploymentOptions);
    vertx.deployVerticle(new MyVerticle(), new DeploymentOptions().setWorker(true));

    vertx.deployVerticle(new ExecuteBlockingVerticle());
  }
}
