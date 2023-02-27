package pl.sages.chat.client;

import pl.sages.chat.client.chat.WebSocketHandler;
import pl.sages.chat.client.view.View;
import pl.sages.chat.client.view.ViewHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;


public class ChatClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View view = new ViewHandler();

        view.viewBuilder().sayHello();
        System.out.println(view.login(scanner.nextLine()));
        System.out.println(view.joinToChat(scanner.nextLine()));

       // new Thread( () -> {
                //System.out.println("in thread write");
         while (true) {
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                String response = view.textHandler(input);
                if (!input.equals("")) System.out.println(response);
            }
        }

  //  });


    }
















    public WebSocket start() {
        var webSocketListener = new WebSocket.Listener() {

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

    private static byte[] decodeBytes(byte[] encodedMessage, byte[] decodingKey) {
        byte[] decoded = new byte[encodedMessage.length];
        for (int i = 0; i < encodedMessage.length; i++) {
            decoded[i] = (byte) (encodedMessage[i] ^ decodingKey[i & 0x3]);
        }
        return decoded;
    }
}