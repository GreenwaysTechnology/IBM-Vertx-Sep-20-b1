package com.ibm.vertx.rx;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.Message;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class HelloConsumerFalutMicroservice extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer().requestHandler(req -> {

      Single<JsonObject> obs1 = vertx.eventBus().
        <JsonObject>rxRequest("hello", "Luke")
        .timeout(3, TimeUnit.SECONDS)
        .retry((i, t) -> {
          System.out.println("Retrying... because of " + t.getMessage());
          return true;
        }).map(Message::body);

      Single<JsonObject> obs2 = vertx.eventBus().
        <JsonObject>rxRequest("hello", "Leia")
        .timeout(3, TimeUnit.SECONDS)
        .retry((i, t) -> {
          System.out.println("Retrying... because of " + t.getMessage());
          return true;
        }).map(Message::body);

      Single.zip(obs1, obs2, (luke, leia) ->
        new JsonObject()
          .put("Luke", luke.getString("message")
            + " from " + luke.getString("served-by"))
          .put("Leia", leia.getString("message")
            + " from " + leia.getString("served-by"))
      ).subscribe(
        x -> req.response().end(x.encodePrettily()),
        t -> req.response().setStatusCode(500).end(t.getMessage())
      );
    }).rxListen(8082).subscribe(handler -> {
      System.out.println("Port : " + handler.actualPort());
    });
  }

}

class HelloFaultMicroservice extends AbstractVerticle {

  @Override
  public void start() {
    Random random = new Random();
    vertx.eventBus().<String>consumer("hello", message -> {
      int chaos = random.nextInt(10);
      JsonObject json = new JsonObject()
        .put("served-by", this.toString());

      if (chaos < 6) {
        // Normal behavior
        if (message.body().isEmpty()) {
          message.reply(json.put("message", "hello"));
        } else {
          message.reply(json.put("message", "hello " + message.body()));
        }
      } else if (chaos < 9) {
        System.out.println("Returning a failure");
        // Reply with a failure
        message.fail(500, "message processing failure");
      } else {
        System.out.println("Not replying");
        // Just do not reply, leading to a timeout on the consumer side.
      }
    });
  }

}

public class ReactiveMicroServiceWithFault extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ReactiveMicroServiceWithFault.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.rxDeployVerticle(new HelloFaultMicroservice()).subscribe();
    vertx.rxDeployVerticle(new HelloConsumerFalutMicroservice()).subscribe();
  }
}
