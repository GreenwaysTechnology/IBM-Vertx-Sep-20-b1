package com.ibm.vertx.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class ExternalConfigVerticle extends AbstractVerticle {

  public Future<JsonObject> getConfig() {
    Promise<JsonObject> promise = Promise.promise();
    //store options
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "conf/config.json"));
    //config reteriver
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));
    //read configuration
    retriever.getConfig(config -> {
      if (config.succeeded()) {
        promise.complete(config.result());
      } else {
        promise.fail("Config Error : " + config.cause());
      }
    });
    return promise.future();

  }

  @Override
  public void start() throws Exception {
    super.start();


  }
}


class MyVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    JsonObject config = config();
    System.out.println(config.getString("name"));
    System.out.println(config.getString("message"));
    System.out.println(config.getInteger("port", 8080));
    System.out.println(config.getString("appname"));
    vertx.createHttpServer().requestHandler(request -> {
      request.response().end(config.getString("name") + config.getString("message"));
    }).listen(config.getInteger("port", 8080));

    //how to read config from operating system
    System.out.println(System.getProperty("java.home"));
    System.out.println(System.getProperty("path.separator"));
    System.out.println(System.getenv("path"));


  }

}


public class VerticleConfiguration extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(VerticleConfiguration.class);
  }
  public Future<JsonObject> getConfig() {
    Promise<JsonObject> promise = Promise.promise();
    //store options
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "conf/config.json"));
    //config reteriver
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));
    //read configuration
    retriever.getConfig(config -> {
      if (config.succeeded()) {
        promise.complete(config.result());
      } else {
        promise.fail("Config Error : " + config.cause());
      }
    });
    return promise.future();

  }

  @Override
  public void start() throws Exception {
    super.start();

    getConfig().onSuccess(myconfig -> {
    //  System.out.println(myconfig.encodePrettily());
      JsonObject config = new JsonObject()
        .put("name", "Subramnaian")
        .put("message", "Hello!!")
        .put("port", 8082)
        .mergeIn(myconfig);
      DeploymentOptions options = new DeploymentOptions().setConfig(config);
      vertx.deployVerticle(new MyVerticle(), options);
    });

   // vertx.deployVerticle(new ExternalConfigVerticle());
  }
}
