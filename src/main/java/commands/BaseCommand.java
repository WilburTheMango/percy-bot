package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import playback.PlaybackLavalink;

public abstract class BaseCommand {

    public abstract String name();
    public abstract String description();

    // define slash command structure
    public abstract SlashCommandData build();

    // execution logic
    public abstract void execute(SlashCommandInteractionEvent event, PlaybackLavalink lavalink);
    
}