package commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import playback.PlaybackLavalink;

public class PingCommand extends BaseCommand {

    @Override
    public String name() {
        return "ping";
    }

    @Override
    public String description() {
        return "Check bot responsiveness";
    }

    @Override
    public SlashCommandData build() {
        return Commands.slash(name(), description());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, PlaybackLavalink lavalink) {
        event.reply("pong").setEphemeral(true).queue();
    }
}