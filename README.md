# MumbleLink Velocity

This plugin targets the [Velocity](https://velocitypowered.com) Minecraft server proxy.
It should be used with the
[mumble-link-fabric](https://www.curseforge.com/minecraft/mc-mods/mumble-link-fabric)
client mod v0.7.5 (don't also install the Fabric MumbleLink mod server-side, this is a replacement).
Installing this allows clients to automatically join a VoIP server of your choosing.

## Install

Download plugin JAR [here](https://github.com/TjeuKayim/mumblelink-velocity/releases)
or build from source with `gradlew build`.
Move it into the Velocity plugin folder.

## Configuration

Create a file `./plugins/mumble-link/fabric-mumblelink-mod.toml` like this:

```toml
# required
mumbleServerHost = ":myPassword@example.com"
# optional port number
mumbleServerPort = -1

# Uncomment one of these options
# registerCommand = true # auto launch with `/launchmumble`
# launchOnConnect = true # auto launch when players log in
```

## To Do

- [ ] Change context for multi-world support
- [ ] Implement voipClient, mumbleServerPath and mumbleServerQuery

## Open Source

Credits to Mitchell Skaggs `@magneticflux-` for developing the original
[fabric-mumblelink-mod](https://github.com/magneticflux-/fabric-mumblelink-mod)
under the LGPLv3 license.
