package com.ibm.vertx.blocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.example.util.Runner;

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
  }
}
