package pdx.fo76.config;

import lombok.Data;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {
    private String bsabrowserPath;
    private String rabcdasmPath;
    private Path archivePath;
    private Path templatePath;
    private Map<String, PatchConfiguration> patches = new HashMap<>();
}
