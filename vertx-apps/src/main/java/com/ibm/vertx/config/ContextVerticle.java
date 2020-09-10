package com.ibm.vertx.config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.example.util.Runner;

public class ContextVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ContextVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    Context context = vertx.getOrCreateContext();
    if (context.isEventLoopContext()) {
      System.out.println("Context attached to Event Loop");
    } else if (context.isWorkerContext()) {
      System.out.println("Context attached to Worker Thread");
    } else if (context.isMultiThreadedWorkerContext()) {
      System.out.println("Context attached to Worker Thread - multi threaded worker");
    } else if (!Context.isOnVertxThread()) {
      System.out.println("Context not attached to a thread managed by vert.x");
    }
    vertx.getOrCreateContext().runOnContext( (v) -> {
      System.out.println("This will be executed asynchronously in the same context");
    });
    final Context context1 = vertx.getOrCreateContext();
    context1.put("data", "hello");
    context1.config().put("name","test");
    context1.runOnContext((v) -> {
      String hello = context1.get("data");
      System.out.println(hello);
      System.out.println(context1.config().getString("name"));
    });
    vertx.close(h -> {
      System.out.println(h.succeeded());
    });
  }
}
