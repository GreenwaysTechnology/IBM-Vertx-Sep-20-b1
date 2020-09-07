package com.ibm.vertx.fp.principles;

@FunctionalInterface
interface Handler {
    void handle();
}

@FunctionalInterface
interface HttpHandler {
    void handle(Object response);
}

//class
class SocketHandler {
    //pass handler interface
    public void requestHandler(Handler handler) {
        handler.handle();
    }
}

class HttpServer {
    public void requestHandler(HttpHandler httpHandler) {
        String response = "This is Response";
        httpHandler.handle(response);
    }
}

//old way :Object oriented way
class SocketHandlerImpl implements Handler {
    @Override
    public void handle() {
        System.out.println("Socket Handler");
    }
}


public class FunctionAsParameter {
    public static void main(String[] args) {
        SocketHandler socketHandler = null;
        socketHandler = new SocketHandler();
        SocketHandlerImpl socketHandlerImpl = new SocketHandlerImpl();
        socketHandler.requestHandler(socketHandlerImpl);

        //old way : annonmous inner class
        socketHandler.requestHandler(new Handler() {
            @Override
            public void handle() {
                System.out.println("Socket Handler Annomous way");
            }
        });
        //lambda way : function as a parameter
        socketHandler.requestHandler(() -> {
            System.out.println("Socket Handler Lambda Way!!!");
        });
        socketHandler.requestHandler(() -> System.out.println("Socket Handler Lambda Way!!!"));

        ////////////////////////////////////////////////////////////////////////////////////////
        HttpServer httpServer = new HttpServer();
        httpServer.requestHandler((Object response) -> {
            System.out.println(response);
        });
        httpServer.requestHandler(response -> System.out.println(response));

    }
}
