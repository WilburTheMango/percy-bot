package commands;

import dev.arbjerg.lavalink.client.Link;
import dev.arbjerg.lavalink.client.player.LavalinkLoadResult;
import dev.arbjerg.lavalink.client.player.TrackLoaded;
import dev.arbjerg.lavalink.client.player.LoadFailed;
import dev.arbjerg.lavalink.client.player.NoMatches;
import dev.arbjerg.lavalink.client.player.Track;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import playback.PlaybackLavalink;

public class PlayCommand extends BaseCommand {

    private static final String TEST_URL =
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ";

    @Override
    public String name() {
        return "play";
    }

    @Override
    public String description() {
        return "plays a test track";
    }

    @Override
    public SlashCommandData build() {
        return Commands.slash(name(), description());
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, PlaybackLavalink lavalink) {

        event.deferReply(true).queue();

        Member member = event.getMember();

        if (member == null ||
            member.getVoiceState() == null ||
            member.getVoiceState().getChannel() == null) {

            event.getHook().sendMessage("Join a voice channel first.").queue();
            return;
        }

        VoiceChannel channel = (VoiceChannel) member.getVoiceState().getChannel();

        event.getJDA()
                .getDirectAudioController()
                .connect(channel);

        long guildId = event.getGuild().getIdLong();

        Link link = lavalink.getClient().getOrCreateLink(guildId);

        link.loadItem(TEST_URL).subscribe(result -> {

            if (result instanceof TrackLoaded loaded) {

                var track = loaded.getTrack();

                link.createOrUpdatePlayer()
                        .setTrack(track)
                        .setPaused(false)
                        .subscribe(player -> {
                            event.getHook().sendMessage(
                                    "Now playing: " + track.getInfo().getTitle()
                            ).queue();
                        });

                return;
            }

            if (result instanceof NoMatches) {
                event.getHook().sendMessage("No matches found").queue();
                return;
            }

            if (result instanceof LoadFailed failed) {
                event.getHook().sendMessage(
                        "Load failed: " + failed.getException().getMessage()
                ).queue();
            }

        }, error -> {
            event.getHook().sendMessage("Error: " + error.getMessage()).queue();
        });
    }
}