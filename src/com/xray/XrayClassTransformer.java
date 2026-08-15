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

    private static final String RENDERBLOCKS_CLASS = "bfo";
    private static final String RENDER_STANDARD_BLOCK_METHOD = "p";
    private static final String RENDER_STANDARD_BLOCK_DESC = "(Laqw;III)Z";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (basicClass == null) return null;
        if (!RENDERBLOCKS_CLASS.equals(name)) return basicClass;

        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            for (Iterator it = classNode.methods.iterator(); it.hasNext(); ) {
                MethodNode method = (MethodNode) it.next();
                if (RENDER_STANDARD_BLOCK_METHOD.equals(method.name)
                        && RENDER_STANDARD_BLOCK_DESC.equals(method.desc)) {
                    injectHook(method);
                    System.out.println("[XrayMod] Injected transparent hook into RenderBlocks.renderStandardBlock");
                    break;
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

    private void injectHook(MethodNode method) {
        InsnList toInsert = new InsnList();
        LabelNode continueLabel = new LabelNode();

        // if (XrayHooks.shouldSkip(block)) return false;
        toInsert.add(new VarInsnNode(ALOAD, 1));
        toInsert.add(new MethodInsnNode(INVOKESTATIC, "com/xray/XrayHooks",
                "shouldSkip", "(Laqw;)Z"));
        toInsert.add(new JumpInsnNode(IFEQ, continueLabel));
        toInsert.add(new InsnNode(ICONST_0));
        toInsert.add(new InsnNode(IRETURN));

        toInsert.add(continueLabel);

        method.instructions.insert(toInsert);
    }
}
