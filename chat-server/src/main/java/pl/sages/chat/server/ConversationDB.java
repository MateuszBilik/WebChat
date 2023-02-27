package pl.sages.chat.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.io.*;
import java.util.*;

@Getter
public class ConversationDB {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .create();

    public Conversation getConversation(String nameOfConversation) {
        return conversations.containsKey(nameOfConversation) ?
                conversations.get(nameOfConversation) : null;
    }

    public Conversation createConversation(String name, List<User> users, boolean isPrivate) {
        conversations.put(name, new Conversation(new ArrayList<>(), name, users, isPrivate));
        return conversations.get(name);
    }

    List<MessageEntity> LoadFromDB(String conversationName) throws IOException {
        String path = "src/main/java/pl/sages/chat/server/Db/" + conversationName + ".txt";
//        Scanner scanner = new Scanner(path);
//        String msg = "";
//        for (int i = 0; i < 5; i++) {
//            if (scanner.hasNext()) msg += scanner.nextLine();
//            msg += "/n";
//        }
        List<MessageEntity> msgs = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                msgs.add(gson.fromJson(line, MessageEntity.class));
            }
            return msgs;
        }
    }

        String SaveToDB (String conversationName, MessageEntity message) throws IOException {
            String path = "src/main/java/pl/sages/chat/server/Db/" + conversationName + ".txt";
            File file = new File(path);
            if (file.isFile()) {
                addToFile(message, path);
            } else {
                file.createNewFile();
                addToFile(message, path);
            }
            return gson.toJson(message);
        }

        private void addToFile (MessageEntity message, String path){
            try (PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(path, true)));) {
                pw.println(gson.toJson(message));
                pw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
