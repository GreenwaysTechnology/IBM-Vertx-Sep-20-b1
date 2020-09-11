package com.ibm.vertx.microservices.common;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

public class BaseMicroServiceVerticle extends AbstractVerticle {
  //ServiceDiscovery Options which helps configure zoo keeper information
  protected ServiceDiscoveryOptions discoveryOptions;
  //Service Discovery Instance creation which helps publish , discover and unpublish
  protected ServiceDiscovery discovery;

  @Override
  public void start() throws Exception {
    super.start();
    discoveryOptions = new ServiceDiscoveryOptions();
    //enable discovery server : apache zoo keeper
    discoveryOptions.setBackendConfiguration(new JsonObject()
      .put("connection", "127.0.0.1:2181")
      .put("ephemeral", true)
      .put("guaranteed", true)
      .put("basePath", "/services/my-backend")
    );
    discovery = ServiceDiscovery.create(vertx, discoveryOptions);
  }

  protected void publishRecord(String name, String host, boolean ssl, int port, String root) {
    Record httpEndPointRecord = HttpEndpoint.createRecord(name,
      ssl, host, port, root, new JsonObject());
    //publish HttpEndpoint
    discovery.publish(httpEndPointRecord, ar -> {
      if (ar.succeeded()) {
        System.out.println("Successfully published to Zookeeper...>>>>" + ar.result().toJson());
      } else {
        System.out.println(" Not Published " + ar.cause());
      }
    });
  }
}
