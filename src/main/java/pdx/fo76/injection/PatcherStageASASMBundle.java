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

    public abstract void execute() throws PatcherException;

    public PatcherStageASASMFile<PatcherStageASASMBundle<?>> openClass(String className) {
        if (className.contains(".")) {
            var namespace = className.substring(0, className.lastIndexOf("."));
            className = className.substring(className.lastIndexOf(".") + 1);
            return openClass(namespace, className);
        } else {
            return openClass(null, className);
        }
    }

    public PatcherStageASASMFile<PatcherStageASASMBundle<?>> openClass(String namespace, String className) {
        return new PatcherStageASASMFile.ManipulateClassASM(this, namespace, className);
    }

    public PatcherStageABC<PatcherStageASASMBundle<?>> assembleABC() {
        return new AssembleABC(this);
    }

    public static class DisassembleABC<T extends PatcherStageSWF<?>> extends PatcherStageASASMBundle<PatcherStageABC<T>> {
        public DisassembleABC(PatcherStageABC<T> parent) {
            super(parent, parent.abcFileName.replace(".abc", ""));
        }

        @Override
        public void execute() throws PatcherException {
            List<String> command = new ArrayList<>();
            command.add(rabcdasmPath + "rabcdasm.exe");
            command.add(abcFileName);
            executeCommand(command, buildPath.toFile());
            log.info("disassembled ASASM from {}", abcFileName);
        }
    }
}
