package pdx.fo76.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ASMEditConfiguration {
    private String name;
    private Map<String, String> params = new HashMap<>();
}
