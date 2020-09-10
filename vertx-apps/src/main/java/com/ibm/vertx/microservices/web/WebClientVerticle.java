package com.ibm.vertx.microservices.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

class ClientVerticle extends AbstractVerticle {
  //create webclient to call server verticle

  public void callServer() {
    HttpServer httpServer = vertx.createHttpServer();

    httpServer.requestHandler(handler -> {
      String url = "/api/message/hello";
      String host = "localhost";
      WebClient webClient = WebClient.create(vertx);
      Integer port = 3000;
      webClient.get(port, host, url).send(ar -> {
        if (ar.succeeded()) {
          //Obtain Response
          HttpResponse<Buffer> response = ar.result();
          System.out.println(response.bodyAsString());
          handler.response().end(response.bodyAsString());
        } else {
          System.out.println(ar.cause());
        }
      });

    });

    httpServer.listen(3001, handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is up " + handler.result().actualPort());
      } else {
        System.out.println("Server is down!!!" + handler.cause());
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    callServer();
  }
}

class ServerVerticle extends AbstractVerticle {

  public void process() {

    HttpServer httpServer = vertx.createHttpServer();
    Router router = Router.router(vertx);
    //HTTP GET request
    router.get("/").handler(routingCtx -> {
      //two objects you can get from RoutingContext - reqest, response
      HttpServerResponse response = routingCtx.response();
      response.end("Home Page");
    });
    router.get("/api/message/hello").handler(MessageController::hello);
    router.get("/api/message/hai").handler(routingCtx -> {
      //two objects you can get from RoutingContext - reqest, response
      HttpServerResponse response = routingCtx.response();
      response.end("Hai Message");
    });
    //bind router with request
    httpServer.requestHandler(router);

    httpServer.listen(3000, handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is up " + handler.result().actualPort());
      } else {
        System.out.println("Server is down!!!" + handler.cause());
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    process();
  }
}

public class WebClientVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(WebClientVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ServerVerticle());
    vertx.deployVerticle(new ClientVerticle());
  }
}
