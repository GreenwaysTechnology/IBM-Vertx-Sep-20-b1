package com.ibm.vertx.dataformat;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class JSONVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(JSONVerticle.class);
  }

  public void simpleJSON() {
    JsonObject message = new JsonObject();
    message.put("name", "Subramanian");
    message.put("when", 1);
    message.put("status", true);
    System.out.println(message.getString("name"));
    System.out.println(message.getInteger("when"));
    System.out.println(message.getBoolean("status"));
    //json.stringify - string representation
    System.out.println(message.encodePrettily());

  }

  //vertx apis are fluent API:A fluent API is where multiple methods calls can be chained together
  public void simpleWithFluentJSON() {
    JsonObject message = new JsonObject().put("name", "Subramanian").put("when", 1).put("status", true);
    System.out.println(message.getString("name"));
    System.out.println(message.getInteger("when"));
    System.out.println(message.getBoolean("status"));
    //json.stringify - string representation
    System.out.println(message.encodePrettily());
  }

  //nested json
  public void nestedJson() {
    JsonObject message = new JsonObject().put("name", "Subramanian")
      .put("when", 1)
      .put("status", true)
      .put("address", new JsonObject().put("city", "coimbatore")
        .put("state", "Tamil Nadue"));
    System.out.println(message.getString("name"));
    System.out.println(message.getInteger("when"));
    System.out.println(message.getBoolean("status"));
    //json.stringify - string representation
    System.out.println(message.encodePrettily());
  }

  //list of jsons [ ]
  public void listofJson() {
    JsonArray list = new JsonArray()
      .add(new JsonObject().put("name", "Subramanian")
        .put("when", 1)
        .put("status", true)
        .put("address", new JsonObject().put("city", "coimbatore")
          .put("state", "Tamil Nadue")))
      .add(new JsonObject().put("name", "Subramanian")
        .put("when", 1)
        .put("status", true)
        .put("address", new JsonObject().put("city", "coimbatore")
          .put("state", "Tamil Nadue")));

    //json.stringify - string representation
    System.out.println(list.encodePrettily());
  }

  //how to reuturn json with Future/Promise
  public Future<JsonObject> getPromise() {
    Promise<JsonObject> promise = Promise.promise();
    JsonObject message = new JsonObject().put("name", "Subramanian")
      .put("when", 1)
      .put("status", true)
      .put("address", new JsonObject().put("city", "coimbatore")
        .put("state", "Tamil Nadue"));
    promise.complete(message);
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    simpleJSON();
    simpleWithFluentJSON();
    nestedJson();
    listofJson();
    getPromise().onSuccess(res -> {
      System.out.println(res.encodePrettily());
    });
  }
}
