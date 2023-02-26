package pl.sages.chat.server;

import java.util.List;

public record Conversation(

        List<MessageEntity> msgs,
        String chatName,
        List<User> participants,
        boolean isPrivate

) {
}
