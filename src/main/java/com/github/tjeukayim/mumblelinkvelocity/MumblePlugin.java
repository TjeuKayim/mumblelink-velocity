package com.github.tjeukayim.mumblelinkvelocity;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;

@Plugin(id = "mumble-link", name = "MumbleLink Plugin", version = "0.1.0",
        url = "https://github.com/TjeuKayim/mumblelink-velocity",
        description = "Broadcast to Fabric MumbleLink mod", authors = {"TjeuKayim"})
public class MumblePlugin {
    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private final MinecraftChannelIdentifier channelId = MinecraftChannelIdentifier.create("fabric-mumblelink-mod", "broadcast_mumble_url");

    // TODO: make separate class for configuration
    private String host;
    private int port;
    private boolean launchOnConnect;
    private boolean registerCommand;

    @Inject
    public MumblePlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        loadConfig(dataDirectory);
        if (registerCommand) {
            server.getCommandManager().register("launchmumble", new LaunchCommand(this));
        }
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        if (!launchOnConnect) return;
        logger.info("Sending MumbleLink packet");
        Player player = event.getPlayer();
        sendLaunchMessage(player);
    }

    public void sendLaunchMessage(Player player) {
        player.sendPluginMessage(channelId, craftPacket(host, port));
    }

    private void loadConfig(Path path) {
        File folder = path.toFile();
        File file = new File(folder, "fabric-mumblelink-mod.toml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            logger.warn("Mumble configuration not found");
            // TODO: Create file with defaults
            return;
        }
        Toml toml = new Toml().read(file);
        host = toml.getString("mumbleServerHost");
        Long portL = toml.getLong("mumbleServerPort");
        port = portL == null ? -1 : portL.intValue();

        launchOnConnect = toml.getBoolean("launchOnConnect", false);
        registerCommand = toml.getBoolean("registerCommand", true);

        logger.info("Mumble configured host '{}', port {}, launchOnConnect {}, registerCommand {}", host, port, launchOnConnect, registerCommand);
    }

    public static byte[] craftPacket(String host, int port) {
        // based on https://github.com/magneticflux-/fabric-mumblelink-mod/blob/b6ffa961f070ae17241ecb9f5d2a1a7fb91c9cdd/src/main/kotlin/com/skaggsm/mumblelinkmod/MumbleLinkMod.kt#L65

        VoipClient voipClient = VoipClient.MUMBLE;
        String path = "";
        String query = "";

        ByteBuf buf = Unpooled.buffer();
        ProtocolUtils.writeEnumConstant(buf, voipClient);
        ProtocolUtils.writeString(buf, host);
        buf.writeInt(port);
        ProtocolUtils.writeString(buf, path);
        ProtocolUtils.writeString(buf, query);

        byte[] data = new byte[buf.writerIndex()];
        buf.getBytes(0, data);
        return data;
    }

    @SuppressWarnings("unused")
    enum VoipClient {
        MUMBLE,
        TEAMSPEAK
    }
}