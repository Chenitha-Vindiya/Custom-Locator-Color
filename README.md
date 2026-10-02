# CustomLocatorColor

CustomLocatorColor is a client-side Fabric mod that allows players to
customize the color of player locator markers by username.

## Minecraft

- Minecraft: 26.3
- Fabric Loader: 0.19.5+
- Fabric API: 0.161.0+
- Java: 25+
- Mod Menu: 21.0.0+
- Cloth Config: 26.3.158+

## Features

- Configure locator colors by player username
- Custom color picker
- Case-insensitive player-name matching
- Client-side configuration
- Mod Menu integration

## Configuration

Open the mod configuration through Mod Menu.

Add a player's Minecraft username and select the desired color.

Example:

```text
PlayerName: ExamplePlayer
Color: #FF0000
```
## Development

Clone the repository and run:

`````./gradlew build`````

On Windows:

```gradlew.bat build```

The compiled JAR will be generated in: `build/libs/`
