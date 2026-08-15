# Xray Transparent Rocks (Minecraft 1.6.2 Forge Mod)

Client-side Minecraft Forge mod that makes all rock blocks transparent, revealing only ores which glow in the dark.

## Features

- **Rock blocks become invisible**: Stone, cobblestone, dirt, gravel, sandstone, brick, stone brick, netherrack, nether brick, obsidian, grass, mycelium, clay, snow, ice, and stone slabs/stairs are rendered transparent
- **Ores remain visible and glow**: Coal, iron, gold, diamond, redstone, lapis lazuli, emerald, and nether quartz ores emit light level 15
- **Other blocks unaffected**: Wood, sand, water, lava, glass, leaves, etc. render normally
- **Client-side only**: No server-side support needed, works in singleplayer

## Requirements

- Minecraft Java Edition **1.6.2**
- Minecraft Forge **1.6.2-9.10.1.871** (or compatible)
- Java 7+

## Installation

1. Install Minecraft Forge 1.6.2 (run the Forge installer, select your 1.6.2 profile)
2. Place `XrayTransparentRocks-1.6.2-1.0.0.jar` into your `.minecraft/mods/` folder
3. Launch Minecraft with the Forge profile
4. Join a world — rocks will be transparent and ores will glow

## How It Works

The mod uses a **coremod (ASM transformer)** to inject a hook into `RenderBlocks.renderStandardBlock()`. When rendering a block registered as transparent, the hook returns early, skipping the block's geometry entirely. This makes the block completely invisible.

Ores are given a light value of 15 via `Block.lightValue[]`, causing them to emit full light.

Transparent blocks also have `opaqueCubeLookup[]` and `lightOpacity[]` set to allow light and adjacent faces to render correctly.

## Building from Source

This mod was built using MCP 8.04 + Forge 9.10.1.871 deobfuscated environment. The source files are in `src/`.

To compile:
1. Set up MCP 8.04 with Forge 1.6.2 patches
2. Copy `src/com/xray/` into the MCP source tree
3. Compile and reobfuscate using MCP's `reobfuscate_srg.sh`
4. Package with the included `META-INF/MANIFEST.MF` (requires `FMLCorePlugin` attribute)

## File Structure

```
src/
├── com/xray/
│   ├── XrayMod.java              # Main mod class, registers blocks
│   ├── XrayHooks.java            # Static hook methods + block registry
│   ├── XrayCorePlugin.java       # IFMLLoadingPlugin (coremod entry)
│   └── XrayClassTransformer.java # ASM transformer for RenderBlocks
├── META-INF/
│   └── MANIFEST.MF               # FMLCorePlugin manifest
mcmod.info                         # Mod metadata
```

## License

MIT
