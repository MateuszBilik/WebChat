package pl.sages.chat.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConversationDBTest {

    @Test
    void loadFromDB() throws FileNotFoundException {
        ConversationDB conversationDB = new ConversationDB();
        String expected =  conversationDB.LoadFromDB("test.txt");

        Assertions.assertEquals(expected, "test");
    }

    @Test
    void saveToDB() throws IOException {
        User user1 =  new User("user1");
        ConversationDB conversationDB = new ConversationDB();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(new User("user2"));

        MessageEntity msg = new MessageEntity(
                "sample text", List.copyOf(users), user1, LocalDateTime.now().toString());

        users.add(new User("user3"));
        MessageEntity msg2 = new MessageEntity(
                "sample text", users, user1, LocalDateTime.now().toString());

        for (int i = 0; i < 1; i++) {
            String expectedMsg = conversationDB.SaveToDB("test1.txt", msg);
            String expectedMsg2 = conversationDB.SaveToDB("test.txt", msg2);
        }

      //  Assertions.assertEquals(expectedMsg,
       //                 "\"text\":\"sample text\",\"recipients\":[{\"email\":\"user1\"},{\"email\":\"user2\"}],\"isReadMap\":");
        String expected =  conversationDB.LoadFromDB("test.txt");
        Assertions.assertEquals("user3", expected);

    }


}