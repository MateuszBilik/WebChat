package pl.sages.chat.server;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class ChatService {

    Gson gson = new Gson();
    ConversationDB conversationDB = new ConversationDB();

    public MessageEntity createMsg(String jsonMsg){
        return gson.fromJson(jsonMsg, MessageEntity.class);
    }

    public List<MessageEntity> getAllMsg (String nameOfConversation) throws IOException {
        return conversationDB.loadFromDB(nameOfConversation);
    }

    public String sendToRecipients (String nameOfConversation, MessageEntity msg) {
        var isReadMap = msg.getIsReadMap();
        for (User u : isReadMap.keySet()) {
            if (isReadMap.get(u) == false){
                if (sendToUser(msg, u)) {
                    isReadMap.put(u, true);
                }
            }
        }
        saveToDB(nameOfConversation, msg);
        return "";
    }

    private boolean sendToUser(MessageEntity msg, User u) {
        //TODO how to find user and send to him?
        return false;
    }

    public void saveToDB(String nameOfConversation, MessageEntity msg) {
        try {
            conversationDB.saveToDB(nameOfConversation, msg);
        } catch (IOException e) {
            System.out.println("-----Error during saving: " + msg + " error: " + e.getMessage());
        }
    }
    public String sendToConversation(String jsonMag){
        MessageEntity msg = createMsg(jsonMag);

        return "";
    }
}
