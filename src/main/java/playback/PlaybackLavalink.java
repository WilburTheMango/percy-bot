package playback;

import dev.arbjerg.lavalink.client.LavalinkClient;
import dev.arbjerg.lavalink.client.LavalinkNode;
import dev.arbjerg.lavalink.client.NodeOptions;
import dev.arbjerg.lavalink.client.event.ReadyEvent;

import java.net.URI;

public class PlaybackLavalink {

    private final LavalinkClient client;
    private volatile LavalinkNode node;

    public PlaybackLavalink(long botUserId) {
        this.client = new LavalinkClient(botUserId);
        registerNode();
    }

    private void registerNode() {
        client.addNode(
            new NodeOptions.Builder()
                .setName("local")
                .setServerUri(URI.create("ws://localhost:2333"))
                .setPassword("youshallnotpass")
                .build()
        ).on(ReadyEvent.class)
         .subscribe(event -> {
             this.node = event.getNode();

             System.out.println(
                 "[Lavalink] Node ready: " + node.getName()
             );
         });
    }

    public LavalinkClient getClient() {
        return client;
    }

    public LavalinkNode getNode() {
        return node;
    }

    public boolean isReady() {
        return node != null;
    }
}