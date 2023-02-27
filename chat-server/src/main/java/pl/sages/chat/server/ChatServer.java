package pl.sages.chat.server;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChatServer {

    static byte[] decodingKeyPattern;
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        Gson gson = new Gson();
        ServerSocket server = new ServerSocket(8080);

        System.out.println("""
                Server has started on 127.0.0.1:8080
                Waiting for a connection…
                """);
        try (server) {
                try (Socket client = server.accept()) { //TODO tylko 1 klient moze sie teraz laczyc, watki 38min
                    System.out.println("A client connected!");
                    try (InputStream in = client.getInputStream();
                         OutputStream out = client.getOutputStream()
                         //PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    ) {
                        //PrintWriter printWriter = new PrintWriter(out, true);
                        validateWebsocketConnection(in, out);
                        while (true) {
                            System.out.println("Waiting for a message!");
                            readResponse(in);
                            writeResponse(out, "test Mat");
                            System.out.println("-----------------------------------------------");
                        }
                    }
                }
        }
    }

    private static OutputStream writeResponse(OutputStream out, String msg) throws IOException {
        System.out.println("Writing msg: " + msg);
        var decoded = msg.getBytes(StandardCharsets.UTF_8);
        int len = decoded.length;
        len = switch (len) {
            case 127 -> throw new RuntimeException("Very long message!");
            case 126 -> throw new RuntimeException("Longer message!");
            default -> len + 128;
        };

        byte[] encoded = new byte[len];
        encoded[0] = 0x1;
        encoded[1] = Integer.valueOf(len).byteValue();
        encoded[2] = decodingKeyPattern[0];
        encoded[3] = decodingKeyPattern[1];
        encoded[4] = decodingKeyPattern[2];
        encoded[5] = decodingKeyPattern[3];


        for (int i = 6; i < len + 6; i++) {
            encoded[i] = (byte) (decoded[i] ^ 1/decodingKeyPattern[i & 0x3]);
        }


//        out.write(0x1);
//        out.write(len);
//        out.write(decodingKeyPattern[0]);
//        out.write(decodingKeyPattern[1]);
//        out.write(decodingKeyPattern[2]);
//        out.write(decodingKeyPattern[3]);
//        out.write(encoded);
        System.out.println("Successfully sent the msg: " + msg);
        return out;
    }

    private static void readResponse(InputStream in) throws IOException {
        int opcode = in.read(); // it should always be 0x1 (for now)
        int messageLength = in.read() - 128;
        messageLength = switch (messageLength) {
            case 127 -> throw new RuntimeException("Very long message!");
            case 126 -> throw new RuntimeException("Longer message!");
            default -> messageLength;
        };
        byte[] decodingKey = new byte[]{(byte) in.read(), (byte) in.read(), (byte) in.read(), (byte) in.read()};
        decodingKeyPattern = decodingKey;
        System.out.println(decodingKey);
        byte[] encodedMessage = in.readNBytes(messageLength);
        System.out.println(encodedMessage);
        var decodedMessage = new String(decodeBytes(encodedMessage, decodingKey));
        System.out.println("Incoming message: " + decodedMessage); //TODO obsluga wiadomosci, przechowywanie
        if (decodedMessage.contains("\u0003�Exiting")) {
            System.out.println("Client has disconnected!");
            //System.exit(0);
        }
    }

    private static byte[] decodeBytes(byte[] encodedMessage, byte[] decodingKey) {
        byte[] decoded = new byte[encodedMessage.length];
        for (int i = 0; i < encodedMessage.length; i++) {
            decoded[i] = (byte) (encodedMessage[i] ^ decodingKey[i & 0x3]);
        }
        return decoded;
    }

    private static void validateWebsocketConnection(InputStream inputStream, OutputStream outputStream) throws IOException, NoSuchAlgorithmException {
        Scanner s = new Scanner(inputStream, StandardCharsets.UTF_8);
        String data = s.useDelimiter("\\r\\n\\r\\n").next();
        Matcher get = Pattern.compile("^GET").matcher(data);
        if (get.find()) {
            handshake(data, outputStream);
        } else {
            throw new RuntimeException("Not a proper websocket protocol!");
        }
    }

    private static void handshake(String data, OutputStream out) throws IOException, NoSuchAlgorithmException {
        Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
        match.find();
        byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                + "Connection: Upgrade\r\n"
                + "Upgrade: websocket\r\n"
                + "Sec-WebSocket-Accept: "
                + encodeHashAndBase64(match)
                + "\r\n\r\n").getBytes(StandardCharsets.UTF_8);
        out.write(response, 0, response.length);
    }

    private static String encodeHashAndBase64(Matcher match) throws NoSuchAlgorithmException {
        byte[] digest = MessageDigest.getInstance("SHA-1")
                .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                        .getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(digest);
    }

}
