package pl.sages.chat.client;

import pl.sages.chat.client.history.User;

import java.util.List;

public class Conversation{


    List<MessageEntity> msgs;
    String chatName;
    List<User> participants;
    boolean isPrivate;
}
