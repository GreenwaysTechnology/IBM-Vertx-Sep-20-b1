package com.ibm.vertx.microservices.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

class MessageController {
  public static void hello(RoutingContext routingContext) {
    //two objects you can get from RoutingContext - reqest, response
    HttpServerResponse response = routingContext.response();
    response.end("Hello Message");
  }
}

public class SimpleWebVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(SimpleWebVerticle.class);
  }

  public void buildRouter() {

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

  private void homePage(RoutingContext routingCtx) {
    //two objects you can get from RoutingContext - reqest, response
    HttpServerResponse response = routingCtx.response();
    response.end("Home Page");
  }

  public void buildDomainRouters() {
    HttpServer httpServer = vertx.createHttpServer();

    Router homeRouter = Router.router(vertx);
    //HTTP GET request
    homeRouter.get("/").handler(this::homePage);
    ///
    Router messageRouter = Router.router(vertx);
    //message domain
    messageRouter.get("/hello").handler(MessageController::hello);
    messageRouter.get("/hai").handler(routingCtx -> {
      //two objects you can get from RoutingContext - reqest, response
      HttpServerResponse response = routingCtx.response();
      response.end("Hai Message");
    });
    //news domain
    Router newsRouter = Router.router(vertx);
    newsRouter.get("/weather").handler(routingCtx -> {
      //two objects you can get from RoutingContext - reqest, response
      HttpServerResponse response = routingCtx.response();
      response.end("Weather News");
    });
    newsRouter.get("/sports").handler(routingCtx -> {
      //two objects you can get from RoutingContext - reqest, response
      HttpServerResponse response = routingCtx.response();
      response.end("Sports news");
    });

    //proxy router, receives requests, according to url mapping , req will redirected
    Router proxyRouter = Router.router(vertx);

    proxyRouter.mountSubRouter("/", homeRouter);
    proxyRouter.mountSubRouter("/api/message", messageRouter);
    proxyRouter.mountSubRouter("/api/news", newsRouter);

    //bind router with request
    httpServer.requestHandler(proxyRouter);

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
    // buildRouter();
    buildDomainRouters();
  }
}
