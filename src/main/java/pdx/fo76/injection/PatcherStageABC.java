package pdx.fo76.injection;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class PatcherStageABC<T extends PatcherStageSWF<?>> extends PatcherStageSWF<T> {
    protected final String abcFileName;

    protected PatcherStageABC(T parent) {
        super(parent, parent.archivePath, parent.swfFileName);
        this.abcFileName = swfFileName.replace(".swf", "-0.abc");
    }

    public PatcherStageASASMBundle<PatcherStageABC<?>> disassembleABC() {
        return new PatcherStageASASMBundle.DisassembleABC(this);
    }

    public PatcherStageSWF<PatcherStageSWF<?>> replaceABC(String targetFile) {
        return new ReplaceABC(this, targetFile);
    }

    public static class ExtractABC extends PatcherStageABC<PatcherStageSWF<?>> {
        public ExtractABC(PatcherStageSWF<?> parent) {
            super(parent);
        }

        @Override
        public void execute() throws Exception {
            List<String> command = new ArrayList<>();
            command.add(rabcdasmPath + "abcexport.exe");
            command.add(swfFileName);
            executeCommand(command, buildPath.toFile());
            log.info("extracted ABC from {}", swfFileName);
        }
    }

    public static class AssembleABC extends PatcherStageABC<PatcherStageASASMBundle<?>> {
        public AssembleABC(PatcherStageASASMBundle<?> parent) {
            super(parent);
        }

        @Override
        public void execute() throws Exception {
            var path = Path.of(parent.asmPath);
            var fname = path.getFileName().toString();
            var asmFile = path + "/" + fname + ".main.asasm";
            List<String> command = new ArrayList<>();
            command.add(rabcdasmPath + "rabcasm.exe");
            command.add(asmFile);
            executeCommand(command, buildPath.toFile());
            var sourceFile = buildPath.resolve(Path.of(parent.asmPath, fname + ".main.abc"));
            var targetFile = buildPath.resolve(parent.asmPath + ".abc.patched");
            Files.createDirectories(targetFile.getParent());
            Files.copy(sourceFile, targetFile);
            log.info("assembled patched ABC from {} into {}", asmFile, targetFile);
        }
    }
}
