package pl.sages.chat.client;

import pl.sages.chat.client.history.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record MessageEntity(
        String text,
        List<User> recipients,
        Map<User, Boolean> isReadMap,
        String time
) {

}
