package com.ibm.vertx.microservices.servicediscovery;

import com.ibm.vertx.microservices.common.BaseMicroServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;

class HttpServerVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    vertx.createHttpServer().
      requestHandler(req -> req.response().end("Hello I am coming from Discovery"))
      .listen(3000);
  }
}

class PublisherVerticle extends BaseMicroServiceVerticle {
  public void publish() {
    publishRecord(config().getString("name"),
      config().getString("host"), config().getBoolean("ssl"),
      config().getInteger("port"), config().getString("path"));
  }

  @Override
  public void start() throws Exception {
    super.start();
    publish();
  }
}

class ConsumerVerticle extends BaseMicroServiceVerticle {

  public void getRecord() {
    vertx.setTimer(2000, ar -> {
      //Get The Service Instance from the Service
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "greeter-service"), sar -> {
        WebClient client = sar.result();
        client.get("/").send(res -> {
          System.out.println("Response is ready!");
          System.out.println(res.result().bodyAsString());
          //remove /release discovery record
          ServiceDiscovery.releaseServiceObject(discovery, client);
        });
      });

    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    getRecord();
  }
}


public class ServiceDiscoveryMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceDiscoveryMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new HttpServerVerticle());
    JsonObject serviceConfig = new JsonObject();
    serviceConfig.put("name", "greeter-service");
    serviceConfig.put("port", 3000);
    serviceConfig.put("host", "localhost");
    serviceConfig.put("ssl", false);
    serviceConfig.put("path", "/");
    DeploymentOptions options = new DeploymentOptions().setConfig(serviceConfig);
    vertx.deployVerticle(new PublisherVerticle(), options);
    vertx.deployVerticle(new ConsumerVerticle(), options);
  }
}
