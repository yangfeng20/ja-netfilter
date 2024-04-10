package com.janetfilter.plugins.power;

import com.janetfilter.core.models.FilterRule;
import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.*;

import java.util.List;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class ArgsTransformer implements MyTransformer {
    public ArgsTransformer(List<FilterRule> rules) {
        ArgsFilter.setRules(rules);
    }

    @Override
    public String getHookClassName() {
        return "java/math/BigInteger";
    }

    @Override
    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(ASM5);
        reader.accept(node, 0);

        for (MethodNode mn : node.methods) {
            if ("oddModPow".equals(mn.name) && "(Ljava/math/BigInteger;Ljava/math/BigInteger;)Ljava/math/BigInteger;".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new VarInsnNode(ALOAD, 1));
                list.add(new VarInsnNode(ALOAD, 2));
                list.add(new MethodInsnNode(INVOKESTATIC, "com/janetfilter/plugins/power/ArgsFilter", "testFilter", "(Ljava/math/BigInteger;Ljava/math/BigInteger;Ljava/math/BigInteger;)[Ljava/math/BigInteger;", false));
                list.add(new VarInsnNode(ASTORE, 3));
                list.add(new InsnNode(ACONST_NULL));
                list.add(new VarInsnNode(ALOAD, 3));

                LabelNode label0 = new LabelNode();
                list.add(new JumpInsnNode(IF_ACMPEQ, label0));
                list.add(new VarInsnNode(ALOAD, 3));
                list.add(new InsnNode(ICONST_0));
                list.add(new InsnNode(AALOAD));
                list.add(new VarInsnNode(ASTORE, 1));
                list.add(new VarInsnNode(ALOAD, 3));
                list.add(new InsnNode(ICONST_1));
                list.add(new InsnNode(AALOAD));
                list.add(new VarInsnNode(ASTORE, 2));
                list.add(label0);

                mn.instructions.insert(list);
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);

        return writer.toByteArray();
    }
}
