package pl.sages.chat.server;

import com.google.gson.Gson;

public class ChatService {

    Gson gson = new Gson();

    public MessageEntity createMsg(String jsonMsg){
        return gson.fromJson(jsonMsg, MessageEntity.class);
    }

    public String sendToConversation(String jsonMag){
        MessageEntity msg = createMsg(jsonMag);

        return "";
    }
}
