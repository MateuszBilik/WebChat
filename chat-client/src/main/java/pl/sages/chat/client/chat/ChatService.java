package pl.sages.chat.client.chat;

import com.google.gson.Gson;
import lombok.Getter;
import pl.sages.chat.client.MessageEntity;
import pl.sages.chat.client.view.View;
import pl.sages.chat.client.view.ViewBuilder;
import pl.sages.chat.client.view.ViewHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.http.WebSocket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class ChatService {

    Gson gson = new Gson();
    //WebSocket webSocket;

    WebSocketHandler webSocketHandler = new WebSocketHandler();
    WebSocket webSocket = webSocketHandler.start();
    ViewBuilder viewBuilder = new ViewBuilder();

    public String sendMsg(String text) {
        //MessageEntity message = new MessageEntity(text, new ArrayList<>(),null, LocalDateTime.now().toString());
        sendToServer(text);
        return "-";
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

    public void sendToServer (String text) {
        var msg = new MessageEntity(text, null, null, LocalDateTime.now().toString());
        webSocket.sendText(gson.toJson(msg), false).join();

    }

    public String readFromServer (CharSequence text) {
//       var in = new BufferedReader(new InputStreamReader(
//                webSocketHandler.getWebSocketListener().onText(webSocket, text, false).);

    return webSocketHandler.getWebSocketListener().onText(webSocket, text, false).toString();

    }



}
