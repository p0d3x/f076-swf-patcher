package pdx.fo76.injection;

import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class PatcherStageASASMBundle<T extends PatcherStageABC<?>> extends PatcherStageABC<T> {
    protected final String asmPath;

    protected PatcherStageASASMBundle(T parent, String asmPath) {
        super(parent);
        this.asmPath = asmPath;
    }

    public abstract void execute() throws Exception;

    public PatcherStageASASMFile<PatcherStageASASMBundle<?>> openClass(String className) {
        if (className.contains(".")) {
            var namespace = className.substring(0, className.lastIndexOf("."));
            className = className.substring(className.lastIndexOf(".") + 1);
            return new PatcherStageASASMFile.ManipulateClassASM(this, namespace, className);
        } else {
            return new PatcherStageASASMFile.ManipulateClassASM(this, null, className);
        }
    }

    public PatcherStageASASMFile<PatcherStageASASMBundle<?>> openClass(String namespace, String className) {
        return new PatcherStageASASMFile.ManipulateClassASM(this, namespace, className);
    }

    public PatcherStageABC<PatcherStageASASMBundle<?>> assembleABC() {
        return new AssembleABC(this);
    }

    public static class DisassembleABC extends PatcherStageASASMBundle<PatcherStageABC<?>> {
        public DisassembleABC(PatcherStageABC<?> parent) {
            super(parent, parent.abcFileName.replace(".abc", ""));
        }

        @Override
        public void execute() throws Exception {
            List<String> command = new ArrayList<>();
            command.add(rabcdasmPath + "rabcdasm.exe");
            command.add(abcFileName);
            executeCommand(command, buildPath.toFile());
            log.info("disassembled ASASM from {}", abcFileName);
        }
    }
}
