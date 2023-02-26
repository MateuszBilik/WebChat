package pl.sages.chat.client.chat;

import com.google.gson.Gson;
import pl.sages.chat.client.MessageEntity;
import pl.sages.chat.client.view.View;
import pl.sages.chat.client.view.ViewBuilder;
import pl.sages.chat.client.view.ViewHandler;

import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class ChatService {

    Gson gson = new Gson();
    //WebSocket webSocket;
    WebSocket webSocket = new WebSocketHandler().start();
    ViewBuilder viewBuilder = new ViewBuilder();

//    public ChatService(WebSocket webSocket) {
//        this.webSocket = webSocket;
//    }

    public String sendMsg(String text) {
        MessageEntity message = new MessageEntity(text, new ArrayList<>(), LocalDateTime.now().toString());
        sendToServer(message); //TODO obsluga wiaodmosci i wysweitlanie wiadomosci, wysylac jsona
        return "";
    }

    public String connect() {
        return "";
    }

    public void login(String login) {
        sendToServer(login);
        viewBuilder.joinToChat();
    }

    public String joinToChat(String line) {
        sendToServer(line);
        return "joined";
    }

    public void sendToServer (Object text) {
        webSocket.sendText(gson.toJson(text), false).join();
    }
}