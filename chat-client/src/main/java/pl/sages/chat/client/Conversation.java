package pl.sages.chat.client;

import pl.sages.chat.client.history.User;

import java.util.List;

public record Conversation(
        String Id,
        List<MessageEntity> msgs,
        String chatName,
        List<User> participants,
        boolean isPrivate

) {
}