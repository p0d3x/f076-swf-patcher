package pdx.fo76.asasm;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
public class Util {

    public static void readAll(Path dir, BiConsumer<Path, List<String>> consumer) throws IOException {
        Files.list(dir)
                .forEach(f -> {
                    try {
                        if (Files.isDirectory(f) && !dir.equals(f)) {
                            readAll(f, consumer);
                        }
                        if (!f.getFileName().toString().endsWith(".asasm")) {
                            return;
                        }
                        if (f.getFileName().toString().endsWith(".main.asasm")) {
                            // skip main for now
                            return;
                        }
                        try (var stream = Files.lines(f, StandardCharsets.UTF_8)) {
                            var lines = stream.toList();
                            consumer.accept(f, lines);
                        }
                    } catch (Exception e) {
                        log.error("{}", e.getMessage(), e);
                    }
                });
    }

}
