package pl.sages.chat.client.history;

import pl.sages.chat.client.Conversation;
import pl.sages.chat.client.MessageEntity;

public interface DbHistory {

    String put(MessageEntity msg, String id);

    Conversation findConversation(String id);

}
