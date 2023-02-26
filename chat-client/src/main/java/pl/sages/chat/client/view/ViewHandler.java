package pl.sages.chat.client.view;

import pl.sages.chat.client.chat.ChatService;

import java.net.http.WebSocket;
import java.util.Scanner;

public class ViewHandler implements View{

    WebSocket webSocket;
    private final ViewBuilder viewBuilder = new ViewBuilder();
    private final ChatService chatService = new ChatService();
    public ViewBuilder viewBuilder() {
        return viewBuilder;
    }

    public ViewHandler() {
        this.webSocket = webSocket;
    }



    @Override
    public String login(String login) {
        chatService.login(login);
        return "";
    }

    @Override
    public String joinToChat(String line) {
        return chatService.joinToChat(line);
    }

    public void sayHello(String login) {
        viewBuilder.sayHello();
        chatService.login(login);
    }

    @Override
    public String textHandler(String msg) {
        String result = "";
        switch (msg){
            case "@@@exit"  -> System.exit(0);
            case "@@@option" -> result = "options...";
            default -> result = chatService.sendMsg(msg);
        }
        return result;
    }
}
