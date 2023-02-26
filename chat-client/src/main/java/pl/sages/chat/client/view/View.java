package pl.sages.chat.client.view;

public interface View {

    String textHandler(String msg);

    ViewBuilder viewBuilder();

    String login(String login);

    String joinToChat(String text);

    String readMsg (CharSequence text);

//    void sayHello();
}
