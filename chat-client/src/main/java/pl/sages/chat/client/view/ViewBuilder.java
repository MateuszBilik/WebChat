package pl.sages.chat.client.view;

public class ViewBuilder {

     public void sayHello(){
        System.out.println("""
                        Hello in myTelegram app
                        Put your email to log in
                        """);
    }

    public void sayGoodBye(){
        System.out.println("""
                Thanks for using  myTelegram
                Bye!
                """);
    }

    public void findConversation(){
        System.out.println("""
                With who do you want to talk?
                """);
    }

    public void joinToChat() {
        System.out.println( """
                 Logged successfully. Put name of chat or user name to start conversation.
                 """);
    }
}
