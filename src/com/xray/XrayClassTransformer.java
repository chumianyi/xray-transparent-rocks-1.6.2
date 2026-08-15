package com.xray;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.Iterator;

public class XrayClassTransformer implements IClassTransformer, Opcodes {

    private static final String[] RENDERBLOCKS_NAMES = {
        "bfo",
        "net/minecraft/src/RenderBlocks"
    };

    private static final String[] RENDER_DISPATCH_METHODS = {
        "b",
        "func_78612_b",
        "renderBlockByRenderType"
    };

    private static final String[] RENDER_STANDARD_METHODS = {
        "p",
        "func_78570_q",
        "renderStandardBlock"
    };

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;

        boolean isRenderBlocks = false;
        for (String n : RENDERBLOCKS_NAMES) {
            if (n.equals(name) || n.equals(transformedName)) {
                isRenderBlocks = true;
                break;
            }
        }
        if (!isRenderBlocks) return basicClass;

        System.out.println("[XrayMod] Transformer hit RenderBlocks! name=" + name + " transformedName=" + transformedName);

        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            int transformed = 0;

            for (Iterator it = classNode.methods.iterator(); it.hasNext(); ) {
                MethodNode method = (MethodNode) it.next();

                boolean isDispatch = matchesAny(method.name, RENDER_DISPATCH_METHODS);
                boolean isStandard = matchesAny(method.name, RENDER_STANDARD_METHODS);

                if ((isDispatch || isStandard) && method.desc != null && method.desc.startsWith("(L") && method.desc.endsWith("III)Z")) {
                    String blockType = extractBlockType(method.desc);
                    injectHook(method, blockType);
                    System.out.println("[XrayMod] Injected hook into method: " + method.name + method.desc + " (blockType=" + blockType + ")");
                    transformed++;
                }
            }

            if (transformed == 0) {
                System.out.println("[XrayMod] WARNING: No matching methods found! Methods in class:");
                for (Iterator it = classNode.methods.iterator(); it.hasNext(); ) {
                    MethodNode m = (MethodNode) it.next();
                    System.out.println("[XrayMod]   " + m.name + m.desc);
                }
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);
            return writer.toByteArray();
        } catch (Exception e) {
            System.err.println("[XrayMod] Failed to transform RenderBlocks: " + e);
            e.printStackTrace();
            return basicClass;
        }
    }

    private boolean matchesAny(String name, String[] candidates) {
        for (String c : candidates) {
            if (c.equals(name)) return true;
        }
        return false;
    }

    private String extractBlockType(String desc) {
        int start = desc.indexOf('L') + 1;
        int end = desc.indexOf(';');
        return desc.substring(start, end);
    }

    private void injectHook(MethodNode method, String blockType) {
        String hookDesc = "(L" + blockType + ";)Z";

        InsnList toInsert = new InsnList();
        LabelNode continueLabel = new LabelNode();

        toInsert.add(new VarInsnNode(ALOAD, 1));
        toInsert.add(new MethodInsnNode(INVOKESTATIC, "com/xray/XrayHooks",
                "shouldSkip", hookDesc));
        toInsert.add(new JumpInsnNode(IFEQ, continueLabel));
        toInsert.add(new InsnNode(ICONST_0));
        toInsert.add(new InsnNode(IRETURN));

        toInsert.add(continueLabel);

        method.instructions.insert(toInsert);
    }
}
