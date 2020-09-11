package com.ibm.vertx.rx.resilence;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.client.WebClient;

import java.util.concurrent.TimeUnit;

public class ResilencePatterns extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ResilencePatterns.class);
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
          .timeout(1000, TimeUnit.MILLISECONDS)
          .map(HttpResponse::bodyAsString)
          .doOnSuccess(res -> request.response().end(res))
          .doOnError(err -> System.out.println(err))
          .subscribe();
      })
      .rxListen(8081)
      .subscribe(httpServer -> System.out.println("Twitter Server is Running!!!"));

  }
}
