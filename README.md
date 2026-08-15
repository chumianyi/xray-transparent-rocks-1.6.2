# Xray Transparent World (Minecraft 1.6.2 Forge Mod)

Client-side Minecraft Forge mod that makes **ALL blocks transparent except ores**, which glow at full brightness. The entire world becomes invisible, leaving only floating glowing ore blocks.

## Features

- **Everything is invisible except ores**: Stone, dirt, grass, wood, sand, water, lava, glass, leaves, and all other blocks are rendered completely transparent
- **Ores remain visible and glow**: Coal, iron, gold, diamond, redstone (normal + glowing), lapis lazuli, emerald, and nether quartz ores emit light level 15
- **Client-side only**: No server-side support needed, works in singleplayer

## Requirements

- Minecraft Java Edition **1.6.2**
- Minecraft Forge **1.6.2-9.10.1.871** (or compatible)
- Java 7+

## Installation

1. Install Minecraft Forge 1.6.2 (run the Forge installer, select your 1.6.2 profile)
2. Place `XrayTransparentWorld-1.6.2-2.0.0.jar` into your `.minecraft/mods/` folder
3. Launch Minecraft with the Forge profile
4. Join a world — everything except ores will be invisible, ores will glow

## How It Works

The mod uses a **coremod (ASM transformer)** to inject a hook into `RenderBlocks.renderBlockByRenderType()` (the main rendering dispatcher) and `RenderBlocks.renderStandardBlock()`. When rendering a block that is not a registered ore, the hook returns early, skipping the block's geometry entirely. This makes the block completely invisible.

The transformer matches both **notch names** (`bfo`, `b`, `p`) and **SRG names** (`net/minecraft/src/RenderBlocks`, `func_78612_b`, `func_78570_q`), ensuring it works regardless of FML deobfuscation ordering.

Ores are given a light value of 15 via `Block.lightValue[]`, causing them to emit full light.

All non-ore blocks also have `opaqueCubeLookup[]` and `lightOpacity[]` set to false/0 to allow light and adjacent faces to render correctly.

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
│   ├── XrayMod.java              # Main mod class, registers ores + makes all non-ores transparent
│   ├── XrayHooks.java            # Static hook methods + ore registry
│   ├── XrayCorePlugin.java       # IFMLLoadingPlugin (coremod entry)
│   └── XrayClassTransformer.java # ASM transformer for RenderBlocks (matches notch + SRG names)
├── META-INF/
│   └── MANIFEST.MF               # FMLCorePlugin manifest
mcmod.info                         # Mod metadata
```

## License

MIT
