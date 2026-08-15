package com.xray;

import net.minecraft.src.Block;

import java.util.HashSet;
import java.util.Set;

public class XrayHooks {
    private static final Set<Integer> oreBlocks = new HashSet<Integer>();
    private static boolean initialized = false;

    public static void registerOre(Block block, int glowLevel) {
        if (block != null) {
            oreBlocks.add(block.blockID);
            Block.lightValue[block.blockID] = glowLevel;
        }
    }

    public static boolean isOre(Block block) {
        if (block == null) return false;
        return oreBlocks.contains(block.blockID);
    }

    public static boolean shouldSkip(Block block) {
        if (block == null) return false;
        if (!initialized) return false;
        return !oreBlocks.contains(block.blockID);
    }

    public static void markInitialized() {
        initialized = true;
        System.out.println("[XrayMod] Hooks initialized - " + oreBlocks.size() + " ore types registered, all other blocks will be transparent");
    }

    public static boolean isReady() {
        return initialized;
    }
}
