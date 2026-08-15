package com.xray;

import net.minecraft.src.Block;

import java.util.HashSet;
import java.util.Set;

public class XrayHooks {
    private static final Set<Integer> transparentBlocks = new HashSet<Integer>();
    private static final Set<Integer> glowingBlocks = new HashSet<Integer>();

    public static void registerTransparent(Block block) {
        if (block != null) transparentBlocks.add(block.blockID);
    }

    public static void registerGlowing(Block block, int level) {
        if (block != null) {
            glowingBlocks.add(block.blockID);
            Block.lightValue[block.blockID] = level;
        }
    }

    public static boolean shouldSkip(Block block) {
        if (block == null) return false;
        return transparentBlocks.contains(block.blockID);
    }
}
