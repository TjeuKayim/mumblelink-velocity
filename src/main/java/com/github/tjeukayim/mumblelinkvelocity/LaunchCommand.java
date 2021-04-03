package com.github.tjeukayim.mumblelinkvelocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.Player;

public final class LaunchCommand implements RawCommand {
    private final MumblePlugin mumblePlugin;

    public LaunchCommand(MumblePlugin mumblePlugin) {
        this.mumblePlugin = mumblePlugin;
    }

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        if (source instanceof Player) {
            Player player = (Player) source;
            mumblePlugin.sendLaunchMessage(player);
        }
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().getPermissionValue("command.launchmumble") != Tristate.FALSE;
    }
}