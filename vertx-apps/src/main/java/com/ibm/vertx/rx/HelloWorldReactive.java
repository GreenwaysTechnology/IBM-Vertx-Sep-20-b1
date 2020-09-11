package com.ibm.vertx.rx;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;

class GreeterServer extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    vertx.createHttpServer()
      .requestHandler(request -> {
        request.response().end("Hello");
      })
      .rxListen(8080).subscribe(httpServer -> {
      System.out.println(httpServer.actualPort());
    });

  }
}


public class HelloWorldReactive extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HelloWorldReactive.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //callback style deployment
//    vertx.deployVerticle("", (h) -> {
//      if (h.succeeded()) {
//        System.out.println(h.result());
//      }
//    });
    //reactive style deployment
    vertx.rxDeployVerticle(new GreeterServer()).subscribe(
      System.out::println
    );
  }
}
