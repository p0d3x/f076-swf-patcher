package pdx.fo76.injection;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class PatcherStage<T extends PatcherStage<?>> {

    protected final T parent;
    protected final String bsaBrowserPath;
    protected final String rabcdasmPath;
    protected final Path buildPath;
    protected final Path templatePath;
    protected PatcherStage<?> next;

    public PatcherStage(T parent) {
        this.parent = parent;
        this.bsaBrowserPath = parent.bsaBrowserPath;
        this.rabcdasmPath = parent.rabcdasmPath;
        this.buildPath = parent.buildPath;
        this.templatePath = parent.templatePath;
        parent.next = this;
    }

    public abstract void execute() throws Exception;

    protected static void executeCommand(List<String> command, File directory) throws Exception {
        log.info("executing: {}", command);
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(directory);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                log.info("{}", line);
            }
        }
        reader.close();

        process.waitFor();
    }

    public PatcherStageSWF<PatcherStage<?>> extractSWFFromBA2(Path archivePath, String fileName) {
        return new PatcherStageSWF.ExtractSWFFromBA2(this, archivePath, fileName);
    }

    public void run() throws Exception {
        if (parent != null) {
            parent.run();
            return;
        }
        PatcherStage<?> current = this;
        while (current != null) {
            current.execute();
            current = current.next;
        }
    }

    public static class PrepareBuildDirectory extends PatcherStage<PatcherStage<?>> {
        public PrepareBuildDirectory(String bsab, String rabcdasm, Path buildPath, Path templatePath) {
            super(null, bsab, rabcdasm, buildPath, templatePath);
        }

        @Override
        public void execute() throws PatcherException {
            try {
                if (Files.exists(buildPath)) {
                    if (Files.exists((buildPath.resolve(".temp-dir")))) {
                        FileUtils.cleanDirectory(buildPath.toFile());
                    } else {
                        throw new PatcherException("directory " + buildPath + " already exists and is not marked as temp-dir!");
                    }
                } else {
                    Files.createDirectories(buildPath);
                }
                Files.createFile(buildPath.resolve(".temp-dir"));
            } catch (IOException e) {
                throw new PatcherException("IOException during prepare: " + e.getMessage(), e);
            }
        }
    }

    public static class MoveFile extends PatcherStage<PatcherStage<?>> {
        private final String sourceFile;
        private final Path targetDirectory;

        public MoveFile(PatcherStage<?> parent, String swfFileName, Path targetDirectory) {
            super(parent);
            this.sourceFile = swfFileName;
            this.targetDirectory = targetDirectory;
        }

        @Override
        public void execute() throws Exception {
            var outFile = targetDirectory.resolve(sourceFile);
            Files.deleteIfExists(outFile);
            Files.createDirectories(outFile.getParent());
            Files.copy(buildPath.resolve(sourceFile), outFile);
            log.info("wrote patched SWF file to {}", outFile);
        }
    }
}
