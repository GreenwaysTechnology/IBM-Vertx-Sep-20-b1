package com.ibm.vertx.rx;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.Message;

class HelloEventBusConsumerMicroservice extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer()
      .requestHandler(req -> {
        Single<JsonObject> obs1 = vertx.eventBus()
          .<JsonObject>rxRequest("hello", "Luke")
          .map(Message::body);
        Single<JsonObject> obs2 = vertx.eventBus()
          .<JsonObject>rxRequest("hello", "Leia")
          .map(Message::body);

        Single.zip(obs1, obs2, (luke, leia) ->
          new JsonObject()
            .put("Luke", luke.getString("message")
              + " from " + luke.getString("served-by"))
            .put("Leia", leia.getString("message")
              + " from " + leia.getString("served-by"))
        )
          .subscribe(
            x -> req.response().end(x.encodePrettily()),
            t -> req.response().setStatusCode(500).end(t.getMessage())
          );
      })
      .listen(8082);
  }

}


class HelloEventBusMicroservice extends AbstractVerticle {

  @Override
  public void start() {
    // Receive message from the address 'hello'
    vertx.eventBus().<String>consumer("hello", message -> {
      JsonObject json = new JsonObject()
        .put("served-by", this.toString());
      // Check whether we have received a payload in the
      // incoming message
      if (message.body().isEmpty()) {
        message.reply(json.put("message", "hello"));
      } else {
        message.reply(json.put("message", "hello " + message.body()));
      }
    });
  }

}


public class WebReactiveEventBusVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(WebReactiveEventBusVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.rxDeployVerticle(new HelloEventBusMicroservice()).subscribe(r->{
      System.out.println("HelloEventBusMicroservice ");
    });
    vertx.rxDeployVerticle(new HelloEventBusConsumerMicroservice()).subscribe(r->{
      System.out.println("HelloEventBusConsumerMicroservice ");
    });
  }
}
