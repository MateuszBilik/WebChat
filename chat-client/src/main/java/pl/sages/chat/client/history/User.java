package pl.sages.chat.client.history;

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
