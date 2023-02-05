package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pdx.fo76.asasm.QName;

import java.util.stream.Stream;

@Data
@Slf4j
@AllArgsConstructor
public class NotImplemented extends Node {
    String instruction;
    String params;

    @Override
    public String toString() {
        return instruction + (params != null ? ' ' + params : "");
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }

    @Override
    public String getName() {
        return instruction;
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        if (params != null) {
            for (QName toReplace : qNamesToReplace) {
                if (params.contains(toReplace.toString())) {
                    params = params.replace(toReplace.toString(), qNameReplacement.toString());
                }
            }
        }
        super.replaceScopes(qNamesToReplace, qNameReplacement);
    }

    public boolean paramsEquals(String testValue) {
        return params != null && params.trim().equals(testValue);
    }
}
