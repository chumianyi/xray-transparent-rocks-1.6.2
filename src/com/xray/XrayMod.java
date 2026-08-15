package com.xray;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Block;

@Mod(modid = "xraymod", name = "Xray Transparent World", version = "2.0.0",
        acceptedMinecraftVersions = "1.6.2")
public class XrayMod {

    @Mod.Instance("xraymod")
    public static XrayMod instance;

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("[XrayMod] Starting initialization...");

        registerOres();
        makeAllNonOresTransparent();

        XrayHooks.markInitialized();
        System.out.println("[XrayMod] Initialization complete - only ores are visible, everything else is transparent!");
    }

    private void registerOres() {
        int glowLevel = 15;
        XrayHooks.registerOre(Block.oreCoal, glowLevel);
        XrayHooks.registerOre(Block.oreIron, glowLevel);
        XrayHooks.registerOre(Block.oreGold, glowLevel);
        XrayHooks.registerOre(Block.oreDiamond, glowLevel);
        XrayHooks.registerOre(Block.oreRedstone, glowLevel);
        XrayHooks.registerOre(Block.oreRedstoneGlowing, glowLevel);
        XrayHooks.registerOre(Block.oreLapis, glowLevel);
        XrayHooks.registerOre(Block.oreEmerald, glowLevel);
        XrayHooks.registerOre(Block.oreNetherQuartz, glowLevel);
        System.out.println("[XrayMod] Registered 9 ore types with glow level " + glowLevel);
    }

    private void makeAllNonOresTransparent() {
        int count = 0;
        for (int i = 0; i < Block.blocksList.length; i++) {
            Block block = Block.blocksList[i];
            if (block != null && !XrayHooks.isOre(block)) {
                Block.opaqueCubeLookup[block.blockID] = false;
                Block.lightOpacity[block.blockID] = 0;
                count++;
            }
        }
        System.out.println("[XrayMod] Made " + count + " non-ore blocks non-opaque and light-transparent");
    }
}
