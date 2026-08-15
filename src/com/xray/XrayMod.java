package com.xray;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Block;

@Mod(modid = "xraymod", name = "Xray Transparent Rocks", version = "1.0.0",
        acceptedMinecraftVersions = "1.6.2")
public class XrayMod {

    @Mod.Instance("xraymod")
    public static XrayMod instance;

    @Mod.EventHandler
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {
        registerTransparentBlocks();
        registerGlowingOres();
        System.out.println("[XrayMod] Transparent Rocks mod loaded - rocks are now see-through, ores glow!");
    }

    private void registerTransparentBlocks() {
        XrayHooks.registerTransparent(Block.stone);
        XrayHooks.registerTransparent(Block.grass);
        XrayHooks.registerTransparent(Block.dirt);
        XrayHooks.registerTransparent(Block.cobblestone);
        XrayHooks.registerTransparent(Block.cobblestoneMossy);
        XrayHooks.registerTransparent(Block.gravel);
        XrayHooks.registerTransparent(Block.sandStone);
        XrayHooks.registerTransparent(Block.brick);
        XrayHooks.registerTransparent(Block.stoneBrick);
        XrayHooks.registerTransparent(Block.netherrack);
        XrayHooks.registerTransparent(Block.netherBrick);
        XrayHooks.registerTransparent(Block.obsidian);
        XrayHooks.registerTransparent(Block.stoneDoubleSlab);
        XrayHooks.registerTransparent(Block.stoneSingleSlab);
        XrayHooks.registerTransparent(Block.stairsCobblestone);
        XrayHooks.registerTransparent(Block.stairsBrick);
        XrayHooks.registerTransparent(Block.stairsStoneBrick);
        XrayHooks.registerTransparent(Block.stairsNetherBrick);
        XrayHooks.registerTransparent(Block.mycelium);
        XrayHooks.registerTransparent(Block.blockClay);
        XrayHooks.registerTransparent(Block.snow);
        XrayHooks.registerTransparent(Block.ice);

        makeNonOpaque(Block.stone);
        makeNonOpaque(Block.grass);
        makeNonOpaque(Block.dirt);
        makeNonOpaque(Block.cobblestone);
        makeNonOpaque(Block.cobblestoneMossy);
        makeNonOpaque(Block.gravel);
        makeNonOpaque(Block.sandStone);
        makeNonOpaque(Block.brick);
        makeNonOpaque(Block.stoneBrick);
        makeNonOpaque(Block.netherrack);
        makeNonOpaque(Block.netherBrick);
        makeNonOpaque(Block.obsidian);
    }

    private void registerGlowingOres() {
        int glowLevel = 15;
        XrayHooks.registerGlowing(Block.oreCoal, glowLevel);
        XrayHooks.registerGlowing(Block.oreIron, glowLevel);
        XrayHooks.registerGlowing(Block.oreGold, glowLevel);
        XrayHooks.registerGlowing(Block.oreDiamond, glowLevel);
        XrayHooks.registerGlowing(Block.oreRedstone, glowLevel);
        XrayHooks.registerGlowing(Block.oreRedstoneGlowing, glowLevel);
        XrayHooks.registerGlowing(Block.oreLapis, glowLevel);
        XrayHooks.registerGlowing(Block.oreEmerald, glowLevel);
        XrayHooks.registerGlowing(Block.oreNetherQuartz, glowLevel);
    }

    private void makeNonOpaque(Block block) {
        if (block != null) {
            Block.opaqueCubeLookup[block.blockID] = false;
            Block.lightOpacity[block.blockID] = 0;
        }
    }
}
