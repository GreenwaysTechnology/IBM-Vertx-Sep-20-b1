package com.ibm.vertx.async.timers;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class TimerVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(TimerVerticleMain.class);
  }

  //timeout
  public void timeout(long delay) {
    //create timer , fire callback function/handler function
    vertx.setTimer(delay, timerId -> {
      System.out.println("Timer ID :" + timerId);
      System.out.println("Called after " + delay);

    });
  }

  public Future<JsonObject> getDataIndelay(long delay) {
    //create timer , fire callback function/handler function
    Promise<JsonObject> promise = Promise.promise();
    vertx.setTimer(delay, timerId -> {
      //encaspulate data , will be sent to caller called after some delay
      promise.complete(new JsonObject().put("message", "Hello"));
    });
    return promise.future();
  }

  public void getDataCallback(long delay, Handler<AsyncResult<JsonObject>> aHandler) {
    vertx.setTimer(delay, timerId -> {
      aHandler.handle(Future.succeededFuture(new JsonObject().put("message", "Welcome")));
    });
  }

  //i want to fire every delay ms
  public void heartBeat(long delay, Handler<AsyncResult<Double>> aHandler) {
    long timerId = vertx.setPeriodic(delay, id -> {
      aHandler.handle(Future.succeededFuture(Math.random()));
    });
    //cancle the polling after some time-
    vertx.setTimer(10000, arid -> {
      System.out.println("stopping producing random numbers");
      vertx.cancelTimer(timerId);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start");
    timeout(1000);
    timeout(5000);
    getDataIndelay(2500).onComplete(event -> {
      if (event.succeeded()) {
        System.out.println(event.result().encodePrettily());
      }
    });
    getDataCallback(3000, timerHandler -> {
      if (timerHandler.succeeded()) {
        System.out.println(timerHandler.result().encodePrettily());
      }
    });
    //get data
    heartBeat(1000, ar -> {
      System.out.println(ar.result().doubleValue());
    });

    System.out.println("end");
  }
}
