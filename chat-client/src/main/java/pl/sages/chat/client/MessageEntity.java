package pl.sages.chat.client;

import pl.sages.chat.client.history.User;

import java.time.LocalDateTime;
import java.util.List;

public record MessageEntity(
        String text,
        List<User> recipients,
        String time
) {
}
