
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        
        Message message = event.getMessage();
        // easy way to check if he's alive
        String messageContent = event.getMessage().getContentRaw().toLowerCase();
        if (messageContent.equals("percy, ping") || messageContent.equals("hi percy")) {
            event.getChannel().sendMessage("Hi, I am here and active.").queue();
        }
        
//		if (messageContent.contains("percy") && !(messageContent.contains("say"))) {
//    		event.getChannel().sendMessage("hi"); // change to list of quippy responses
//    		return;
//   	}
        // percy says stuff when people tell him to
        if (messageContent.contains("percy, say")) {
        	message.delete().queue();
        	event.getChannel().sendMessage(messageContent.substring(10)).queue();
        }
        // percy's memory
        if (messageContent.startsWith("percy") && messageContent.contains("remember") && !(messageContent.contains("remember?")) ) {
        	String data = messageContent.substring(messageContent.indexOf("remember") + 9);
        	writeToRememberFile(data);
        	event.getChannel().sendMessage("i'll remember that: " + data).queue();
        }
        if ( (messageContent.startsWith("percy") && messageContent.contains("remember?") ) || ( messageContent.contains("percy") && (messageContent.contains("what do you remember?") || messageContent.contains("percy remembers?")) ) ) {
        	event.getChannel().sendMessage("i remember that:\n" + readRememberFile()).queue();
        }
    }
    private static void writeToRememberFile(String data) {
        try (FileWriter fw = new FileWriter("data/remember.txt", true)) {
            fw.write(data + System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String readRememberFile() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("data/remember.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e){
        	e.printStackTrace();
        }

        return sb.toString();
    }
}