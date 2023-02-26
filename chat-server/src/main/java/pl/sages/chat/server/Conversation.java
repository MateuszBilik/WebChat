package pl.sages.chat.server;

import java.util.List;

public record Conversation(
        String Id,
        List<MessageEntity> msgs,
        String chatName,
        List<User> participants,
        boolean isPrivate

) {
}
