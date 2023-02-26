package pl.sages.chat.server;

import lombok.ToString;

public record User(
        String email
) {

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
