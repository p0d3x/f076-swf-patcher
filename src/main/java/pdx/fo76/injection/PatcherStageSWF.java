package pdx.fo76.injection;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class PatcherStageSWF<T extends PatcherStage<?>> extends PatcherStage<T> {
    protected final Path archivePath;
    protected final String swfFileName;

    protected PatcherStageSWF(T parent, Path archivePath, String fileName) {
        super(parent);
        this.archivePath = archivePath;
        this.swfFileName = fileName;
    }

    public PatcherStageABC<PatcherStageSWF<?>> extractABC() {
        return new PatcherStageABC.ExtractABC(this);
    }

    public PatcherStage<PatcherStage<?>> movePatchedSWF(Path targetDirectory) {
        return new MoveFile(this, swfFileName, targetDirectory);
    }

    public static class ExtractSWFFromBA2 extends PatcherStageSWF<PatcherStage<?>> {
        public ExtractSWFFromBA2(PatcherStage<?> parent, Path archivePath, String fileName) {
            super(parent, archivePath, fileName);
        }

        @Override
        public void execute() throws PatcherException {
            List<String> command = new ArrayList<>();
            command.add(bsaBrowserPath + "bsab.exe");
            command.add("--regex");
            command.add(swfFileName);
            command.add("-e");
            command.add("-o");
            command.add(archivePath.toString());
            command.add(buildPath.toString());
            executeCommand(command, buildPath.toFile());
        }
    }

    public static class ReplaceABC extends PatcherStageSWF<PatcherStageSWF<?>> {
        private final String targetFile;

        public ReplaceABC(PatcherStageSWF<?> parent, String targetFile) {
            super(parent, parent.archivePath, parent.swfFileName);
            this.targetFile = targetFile;
        }

        @Override
        public void execute() throws PatcherException {
            List<String> command = new ArrayList<>();
            command.add(rabcdasmPath + "abcreplace.exe");
            command.add(swfFileName);
            command.add("0");
            command.add(targetFile);
            executeCommand(command, buildPath.toFile());
        }
    }
}
