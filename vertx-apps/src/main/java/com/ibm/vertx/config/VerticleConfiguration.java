package com.ibm.vertx.config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class MyVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(config().getString("name"));
    System.out.println(System.getProperty("java.home"));
    System.out.println(System.getProperty("path.separator"));
    System.out.println(System.getenv("path"));

  }
}


public class VerticleConfiguration extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(VerticleConfiguration.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    JsonObject config = new JsonObject().put("name", "tim").put("directory", "/blah");
    DeploymentOptions options = new DeploymentOptions().setConfig(config);
    vertx.deployVerticle(new MyVerticle(), options);
    vertx.close();
  }
}
