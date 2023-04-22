package pdx.fo76;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import pdx.fo76.config.ASMEditConfiguration;
import pdx.fo76.config.Configuration;
import pdx.fo76.config.PatchConfiguration;
import pdx.fo76.injection.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            log.error("missing config file argument");
            return;
        }

        var configFile = Path.of(args[0]);
        var objectMapper = new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
        var config = objectMapper.readValue(configFile.toFile(), Configuration.class);

        for (Map.Entry<String, PatchConfiguration> patch : config.getPatches().entrySet()) {
            applyPatch(config, patch);
        }
    }

    private static void applyPatch(Configuration config, Map.Entry<String, PatchConfiguration> patch) throws Exception {
        log.info("applying patch '{}'", patch.getKey());
        var patchConfig = patch.getValue();
        var modPath = patchConfig.getModDir();
        var workingDir = modPath.resolve(patchConfig.getWorkingDir());

        PatcherStageABC<?> abcStage = new PatcherStage.PrepareBuildDirectory(config.getBsabrowserPath(),
                config.getRabcdasmPath(), workingDir, config.getTemplatePath())
                .extractSWFFromBA2(config.getArchivePath(), patchConfig.getSwfPath())
                .extractABC()
                .disassembleABC();

        if (patchConfig.getEdits() != null && !patchConfig.getEdits().isEmpty()) {
            PatcherStageASASMFile<PatcherStageASASMBundle<?>> curStage = null;
            for (var edit : patchConfig.getEdits().entrySet()) {

                var className = edit.getKey();
                if (curStage == null) {
                    curStage = ((PatcherStageASASMBundle<?>) abcStage).openClass(className);
                } else {
                    curStage = curStage.openClass(className);
                }
                applyEdit(curStage, edit, className);
            }
            abcStage = curStage.assembleABC();
        }

        abcStage.replaceABC(patchConfig.getTargetPatchFile())
                .movePatchedSWF(modPath.resolve(patchConfig.getTargetDir()))
                .run();
    }

    private static void applyEdit(PatcherStageASASMFile<PatcherStageASASMBundle<?>> curStage,
                                  Map.Entry<String, List<ASMEditConfiguration>> edit, String className) {
        var classEdits = edit.getValue();
        for (ASMEditConfiguration classEdit : classEdits) {
            var params = classEdit.getParams();
            switch (classEdit.getName()) {
                case "injectSFE" -> curStage.addEdit(ASASMEdits.injectSFE());
                case "injectLoader" ->
                        curStage.addEdit(ASASMEdits.injectLoader(params.get("modFile"), params.get("template")));
                case "makePackagePrivate" ->
                        curStage.addEdit(ASASMEdits.changePrivateScopeToPackage(params.get("fieldName")));
                case "patchQuantityMenu" -> curStage.addEdit(ASASMEdits.patchQuantityMenu());
                default ->
                        throw new IllegalArgumentException("unrecognized edit " + classEdit.getName() + " in " + className + " config");
            }
        }
    }
}
