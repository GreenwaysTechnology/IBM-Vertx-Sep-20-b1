package com.ibm.vertx.core;

import io.vertx.core.Vertx;

public class CreateVertxInstanceUsingFactory {
  public static void main(String[] args) {
    //create Vertx Container object
    Vertx vertx = Vertx.vertx();
    System.out.println(vertx.toString());
  }
}
