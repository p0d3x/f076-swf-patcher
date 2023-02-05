package pdx.fo76.config;

import lombok.Data;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PatchConfiguration {
    private Path modDir;
    private Path workingDir;
    private String swfPath;
    private String targetPatchFile;
    private Path targetDir;
    private Map<String, List<ASMEditConfiguration>> edits = new HashMap<>();
}
