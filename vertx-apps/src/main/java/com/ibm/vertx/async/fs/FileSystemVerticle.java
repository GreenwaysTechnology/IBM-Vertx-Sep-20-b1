package com.ibm.vertx.async.fs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.FileSystem;
import io.vertx.example.util.Runner;

public class FileSystemVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(FileSystemVerticle.class);
  }

  public void readFile() {
    FileSystem fileSystem = vertx.fileSystem();
    //read file in non blocking way
    fileSystem.readFile("assets/info.txt", fileHandler -> {
      if (fileHandler.succeeded()) {
        System.out.println(fileHandler.result().toString());
      } else {
        System.out.println(fileHandler.cause());
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("start");
    readFile();
    System.out.println("end");
  }
}
