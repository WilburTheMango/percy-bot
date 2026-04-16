package core;
import commands.BaseCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import playback.PlaybackLavalink;

public class SlashListener extends ListenerAdapter {

    private final CommandRegistry registry;
    private final PlaybackLavalink lavalink;
    
    public SlashListener(CommandRegistry registry, PlaybackLavalink lavalink) {
        this.registry = registry;
        this.lavalink = lavalink;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        BaseCommand cmd = registry.get(event.getName());

        if (cmd == null) {
            event.reply("Unknown command").setEphemeral(true).queue();
            return;
        }

        cmd.execute(event, lavalink);
    }
}