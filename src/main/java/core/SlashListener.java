package core;
import commands.BaseCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashListener extends ListenerAdapter {

    private final CommandRegistry registry;

    public SlashListener(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        BaseCommand cmd = registry.get(event.getName());

        if (cmd == null) {
            event.reply("Unknown command").setEphemeral(true).queue();
            return;
        }

        cmd.execute(event);
    }
}