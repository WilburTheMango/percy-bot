
import java.util.List;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import commands.*;
import core.CommandLoader;
import core.CommandRegistry;
import core.SlashListener;

public class Main  {
    public static void main(String[] args) throws Exception {
    	// STEPS:
    	// Load config.env
    	// Create JDA
    	// Init Bot and wait until loaded.
    	// Build Slash Command Registry (defined in CommandLoader)
    	// Add Slash Commands to Specified Guilds
    	try {
    		ConfigLoader.load("config.env");
		} catch (Exception e) {
			System.err.println("Config file failed to load:");
			e.printStackTrace();
			return;
		}
    	
    	System.out.println("Token loaded: " + (BotConfig.DISCORD_TOKEN != null)); // use debug mode to allow displaying token (displaying token should be considered insecure)
    	CommandRegistry registry = new CommandRegistry();
        CommandLoader.load(registry);
        System.out.println("Commands in registry: " + registry.all().size());
        JDA jda = JDABuilder.createDefault(BotConfig.DISCORD_TOKEN)
        .enableIntents(
                GatewayIntent.GUILD_MESSAGES, // reading user messages for jokes //TODO: add ability to disable potentially privacy dangerous behavior
                GatewayIntent.MESSAGE_CONTENT, 
                GatewayIntent.GUILD_VOICE_STATES // required for joining and finding users when music commands are called.
        )
        .addEventListeners(new BotListener())
        .addEventListeners(new SlashListener(registry))
        .build();
        
        jda.awaitReady();

        List<SlashCommandData> data = registry.all()
                .stream()
                .map(BaseCommand::build)
                .toList();
        
        for (String guildId : BotConfig.TARGETED_GUILDS) {
        	System.out.println(guildId);
	        jda.getGuildById(guildId).updateCommands()
	                .addCommands(data)
	                .queue();
        }
        jda.updateCommands().queue(); // Not currently using global slash commands, therefore clear. Global commands take a while to update.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            jda.shutdown();
        }));
    }
    
	
}