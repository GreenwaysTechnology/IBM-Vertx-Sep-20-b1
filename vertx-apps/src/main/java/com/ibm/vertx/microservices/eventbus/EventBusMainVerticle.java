package com.ibm.vertx.microservices.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;

class Address {
  public static String PUBSUBADDRESS = "news.in.covid";
  public static String POINTOPOINT = "fund.in.covid.request";
  public static String REQUESTREPLY = "report.in.covid";
}
////////////////////////////////////////////////////////////////////////////////////////////////////////
//request-reply

class LabVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.REQUESTREPLY);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request -  : " + news.body());
      //sending reply /ack
      news.reply("Patient is Crictal, Need More attention");
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class ReportVerticle extends AbstractVerticle {

  public void sendReport() {
    vertx.setTimer(5000, ar -> {
      String message = "Report of Mr.x";

      vertx.eventBus().request(Address.REQUESTREPLY, message, asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result().body());
        } else {
          System.out.println(asyncResult.cause());
        }
      });
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    sendReport();
  }
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////
//point to point
class CenertalFinanceVerticle extends AbstractVerticle {

  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.POINTOPOINT);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request -  : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

//point to point publisher
class FinanceRequestVerticle extends AbstractVerticle {

  public void requestFinance() {
    //
    System.out.println("Finance Request started....");
    vertx.setTimer(5000, ar -> {
      //point to point : send method
      String message = "Dear Team, We request that we want 1 Billion Money for Covid";
      //point to p
      vertx.eventBus().send(Address.POINTOPOINT, message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    requestFinance();
  }
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////
//pub-sub one to many
class NewsSevenVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUBSUBADDRESS);
    messageConsumer.handler(news -> {
      System.out.println(this.getClass().getName() + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class BBCVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUBSUBADDRESS);
    messageConsumer.handler(news -> {
      System.out.println(this.getClass().getName() + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class NDTVVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUBSUBADDRESS);
    messageConsumer.handler(news -> {
      System.out.println(this.getClass().getName() + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

//publisher publish news to all news tv channels
class PublisherVerticle extends AbstractVerticle {

  public void publish() {
    //Get EventBus Object reference
    EventBus eventBus = vertx.eventBus();
    //publish a message : pub-sub
    String message = "Last 24 Hrs covid count is 80000";
    vertx.setTimer(1000, ar -> {
      eventBus.publish(Address.PUBSUBADDRESS, message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    publish();
  }
}

public class EventBusMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusMainVerticle.class);
  }

  public void pubsub() {
    vertx.deployVerticle(new PublisherVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("PublisherVerticle " + handler.result() + " Successfully deployed");
      }
    });
    vertx.deployVerticle(new BBCVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("BBCVerticle " + handler.result() + " Successfully deployed");
      }
    });
    vertx.deployVerticle(new NewsSevenVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("NewsSevenVerticle " + handler.result() + " Successfully deployed");
      }
    });
    vertx.deployVerticle(new NDTVVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("NDTVVerticle " + handler.result() + " Successfully deployed");
      }
    });

  }

  public void pointToPoint() {
    vertx.deployVerticle(new CenertalFinanceVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("CenertalFinanceVerticle " + handler.result() + " Successfully deployed");
      }
    });
    vertx.deployVerticle(new FinanceRequestVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("FinanceRequestVerticle " + handler.result() + " Successfully deployed");
      }
    });
  }

  public void requestReply() {
    vertx.deployVerticle(new LabVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("LabVerticle " + handler.result() + " Successfully deployed");
      }
    });
    vertx.deployVerticle(new ReportVerticle(), handler -> {
      if (handler.succeeded()) {
        //each verticle is assigned with unique deployment id;
        System.out.println("ReportVerticle " + handler.result() + " Successfully deployed");
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //pubsub();
    // pointToPoint();
    requestReply();
  }
}
