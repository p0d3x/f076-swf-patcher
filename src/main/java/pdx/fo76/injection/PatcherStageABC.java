package pdx.fo76.injection;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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

    public PatcherStageASASMBundle<PatcherStageABC<T>> disassembleABC() {
        return new PatcherStageASASMBundle.DisassembleABC<>(this);
    }

    public PatcherStageSWF<PatcherStageSWF<T>> replaceABC(String targetFile) {
        return new ReplaceABC<>(this, targetFile);
    }

    public static class ExtractABC<T extends PatcherStage<?>> extends PatcherStageABC<PatcherStageSWF<T>> {
        public ExtractABC(PatcherStageSWF<T> parent) {
            super(parent);
        }

        @Override
        public void execute() throws PatcherException {
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
        public void execute() throws PatcherException {
            try {
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
            } catch (IOException e) {
                throw new PatcherException("IOException during assembly: " + e.getMessage(), e);
            }
        }
    }
}
