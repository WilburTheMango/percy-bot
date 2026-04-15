package commands;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class PingCommand extends BaseCommand {

    @Override
    public String name() {
        return "ping";
    }

    @Override
    public String description() {
        return "Check if the Bot is recieving commands from the API with a ping!";
    }

    @Override
    public SlashCommandData build() {
        return Commands.slash(name(), description());
    }

    @Override
    public void execute(SlashCommandInteraction event) {
        event.reply("pong").setEphemeral(true).queue();
    }
}