package com.github.tjeukayim.mumblelinkvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(id = "mumble-link", name = "MumbleLink Plugin", version = "0.1.0",
        url = "https://github.com/TjeuKayim/mumblelink-velocity",
        description = "Broadcast to Fabric MumbleLink mod", authors = {"TjeuKayim"})
public class MumblePlugin {
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public MumblePlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Hello there! I made my first plugin with Velocity.");
    }
}
