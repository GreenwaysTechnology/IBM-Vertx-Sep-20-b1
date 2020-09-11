package com.ibm.vertx.rx;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

public class PostApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(PostApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    WebClient webClient = WebClient.create(vertx);
    vertx.createHttpServer()
      .requestHandler(request -> {
        webClient
          .getAbs("https://jsonplaceholder.typicode.com/posts")
          .rxSend()
          .map(HttpResponse::bodyAsJsonArray)
          .doOnSuccess(res -> request.response().end(res.encodePrettily()))
          .subscribe();
      })
      .rxListen(8081)
      .subscribe(httpServer -> System.out.println("Twitter Server is Running!!!"));

  }
}
