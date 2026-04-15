package commands;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class BaseCommand {

    public abstract String name();
    public abstract String description();

    // define slash command structure
    public abstract SlashCommandData build();

    // execution logic
    public abstract void execute(SlashCommandInteraction event);
}