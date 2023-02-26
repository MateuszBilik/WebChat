package pl.sages.chat.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class MessageEntity {
    private String text;
    private List<User> recipients;
    private Map<User, Boolean> isReadMap;
    private String time;

    public MessageEntity(String text, List<User> recipients, User fromUser, String time) {
        this.text = text;
        this.recipients = recipients;
        this.isReadMap = createMap(recipients, fromUser);
        this.time = time;
    }

    private Map<User, Boolean> createMap (List<User> users, User fromUser){
        Map<User, Boolean> isReadMap = new HashMap<>();
        for (User user : users) {
            if (user.email() == fromUser.email()){
                isReadMap.put(user, true);
            } else {
                isReadMap.put(user, false);
            }
        }
        return isReadMap;
    }

    public void readByUser (User user) throws Exception {
        if (this.isReadMap.containsKey(user) && this.isReadMap.get(user) == false){
            this.isReadMap.put(user, true);
        } else {
            throw new Exception ("This user already read this msg or this user shouldn't read this this msg");
        }
    }

}
