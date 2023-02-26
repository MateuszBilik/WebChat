package pl.sages.chat.server;

import com.google.gson.Gson;
import lombok.Getter;

import java.io.*;
import java.util.*;

@Getter
public class ConversationDB {

    private final Map<String, Conversation> conversations = new HashMap<>();
    private final Gson gson = new Gson();

    public Conversation getConversation(String nameOfConversation) {
        return conversations.containsKey(nameOfConversation) ?
                conversations.get(nameOfConversation) : null;
    }

    public Conversation createConversation(String name, List<User> users, boolean isPrivate) {
        conversations.put(name, new Conversation(new ArrayList<>(), name, users, isPrivate));
        return conversations.get(name);
    }

    String LoadFromDB(String conversationName) throws IOException {
        String path = "src/main/java/pl/sages/chat/server/Db/" + conversationName + ".txt";
//        Scanner scanner = new Scanner(path);
//        String msg = "";
//        for (int i = 0; i < 5; i++) {
//            if (scanner.hasNext()) msg += scanner.nextLine();
//            msg += "/n";
//        }
        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(path))) {
            String line;
            long counter = 0;
            //TODO
            while ((line = br.readLine()) != null) {
                counter++;
            }



            System.out.println(counter);

//        try(PrintWriter pw = new PrintWriter(
//                new BufferedWriter(
//                        new FileWriter(path, true)));) {
//            pw.println(gson.toJson(message));
//            pw.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

            return msg;
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
