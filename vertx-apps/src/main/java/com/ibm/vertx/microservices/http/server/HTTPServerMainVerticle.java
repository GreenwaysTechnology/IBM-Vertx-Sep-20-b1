package com.ibm.vertx.microservices.http.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class SomeServiceVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer("ibm.today.message");
    System.out.println("message");
    messageConsumer.handler(news -> {
      System.out.println("Message From HTTP " + news.body());
    });
  }
}

public class HTTPServerMainVerticle extends AbstractVerticle {
  String data = "";

  public static void main(String[] args) {
    Runner.runExample(HTTPServerMainVerticle.class);
  }

  //send simple message
  public void createBasicHttpServer() {
    //create server
    HttpServer httpServer = vertx.createHttpServer();

    //start handling request-response in non blocking
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      //start sending response
      // response.write("Hello");
      //close the stream
      response.end("Hello");
    });
    //start the server
    httpServer.listen(3000, handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is up " + handler.result().actualPort());
      } else {
        System.out.println("Server is down!!!" + handler.cause());
      }
    });
  }

  //sending some json data
  public void sendJson() {
    //create server
    HttpServer httpServer = vertx.createHttpServer();
    //start handling request-response in non blocking
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.putHeader("content-type", "application/json");
      JsonObject message = new JsonObject()
        .put("name", "Subramanian")
        .put("message", "welcome");
      response.end(message.encodePrettily());
    });
    //start the server
    httpServer.listen(3000, handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is up " + handler.result().actualPort());
      } else {
        System.out.println("Server is down!!!" + handler.cause());
      }
    });
  }

  public void readClientData() {
    //create server
    HttpServer httpServer = vertx.createHttpServer();

    //start handling request-response in non blocking
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      //read client data;
      request.bodyHandler(buffer -> {
        System.out.println(buffer.toString());

        response.end(buffer.toString());
      });
      response.endHandler(event -> {
        System.out.println("response committed");
      });

      //  response.end("Hello");
    });
    //start the server
    httpServer.listen(3000, handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is up " + handler.result().actualPort());
      } else {
        System.out.println("Server is down!!!" + handler.cause());
      }
    });
  }

  //send data to event bus ; http-event bus
  public void sendRequestDataToEventBus() {
    //create server
    HttpServer httpServer = vertx.createHttpServer();

    //start handling request-response in non blocking
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      //read client data;
      request.bodyHandler(buffer -> {
        System.out.println(buffer.toString());
        //send user data to other verticle via event bus
        data = buffer.toString();
        response.end(buffer.toString());
      });
      response.endHandler(event -> {
        vertx.eventBus().send("ibm.news.message", data.toString());

        System.out.println("response committed");
      });

      //  response.end("Hello");
    });
    //start the server
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
    vertx.deployVerticle(new SomeServiceVerticle());
    // createBasicHttpServer();
    //sendJson();
    // readClientData();
    sendRequestDataToEventBus();

  }
}
