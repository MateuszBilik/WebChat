package pl.sages.chat.client.chat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketHandler {

    private WebSocket.Listener webSocketListener;

    public WebSocket.Listener getWebSocketListener() {
        return webSocketListener;
    }



    public WebSocket start(){
        createWebSocketListener();

        WebSocket webSocket = HttpClient
                .newHttpClient()
                .newWebSocketBuilder().
                buildAsync(URI.create("ws://localhost:8080/chat"), webSocketListener)
                .join();



        var shutdownHook = new Thread(() -> webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Exiting")
                .join());
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        return webSocket;
    }

    private void createWebSocketListener() {
        webSocketListener = new WebSocket.Listener() {

            @Override //TODO tu chyba trzeba nadpisac wszystko, kilka metod
            public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                System.out.println(data);
                return WebSocket.Listener.super.onText(webSocket, data, last);
            }

            @Override
            public void onOpen(WebSocket webSocket) {
                WebSocket.Listener.super.onOpen(webSocket);
            }
        };
    }


}
