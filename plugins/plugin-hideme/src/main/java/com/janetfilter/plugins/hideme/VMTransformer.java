package com.janetfilter.plugins.hideme;

import com.janetfilter.core.Environment;
import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.tree.*;

import java.util.Iterator;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class VMTransformer implements MyTransformer {
    private final Environment environment;

    public VMTransformer(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getHookClassName() {
        return "sun/management/VMManagementImpl";
    }

    @Override
    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        VmArgumentFilter.setEnvironment(environment);

        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(ASM5);
        reader.accept(node, 0);

        for (MethodNode mn : node.methods) {
            if ("getVmArguments".equals(mn.name) && "()Ljava/util/List;".equals(mn.desc)) {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new VarInsnNode(ALOAD, 0));
                list.add(new FieldInsnNode(GETFIELD, "sun/management/VMManagementImpl", "vmArgs", "Ljava/util/List;"));
                list.add(new MethodInsnNode(INVOKESTATIC, "com/janetfilter/plugins/hideme/VmArgumentFilter", "testArgs", "(Ljava/util/List;)Ljava/util/List;", false));
                list.add(new FieldInsnNode(PUTFIELD, "sun/management/VMManagementImpl", "vmArgs", "Ljava/util/List;"));

                Iterator<AbstractInsnNode> it = mn.instructions.iterator();
                while (it.hasNext()) {
                    AbstractInsnNode in = it.next();

                    if (AbstractInsnNode.INSN == in.getType() && ARETURN == in.getOpcode()) {
                        mn.instructions.insert(in.getPrevious().getPrevious(), list);
                        break;
                    }
                }
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);

        return writer.toByteArray();
    }
}
