package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FindPropStrict extends Node {
    Identifier param;

    @Override
    public String toString() {
        return "findpropstrict      " + param;
    }

    @Override
    public String getName() {
        return "findpropstrict";
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }

    @Override
    public boolean paramsEquals(String testValue) {
        return param.toString().equals(testValue);
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        if (param != null) {
            for (QName toReplace : qNamesToReplace) {
                if (param.equals(toReplace)) {
                    param = qNameReplacement;
                }
            }
        }
        super.replaceScopes(qNamesToReplace, qNameReplacement);
    }
}
