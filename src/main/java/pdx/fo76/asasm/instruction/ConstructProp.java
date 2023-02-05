package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ConstructProp extends Node {
    Identifier object;
    int numArgs;

    @Override
    public String toString() {
        return "constructprop       " + object + ", " + numArgs;
    }

    @Override
    public String getName() {
        return "constructprop";
    }

    @Override
    public Stream<String> stream(int indent) {
        return Stream.concat(Stream.of(StringUtils.repeat(" ", indent) + this), super.stream(indent));
    }

    @Override
    public boolean paramsEquals(String testValue) {
        return object.toString().equals(testValue);
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        if (object != null) {
            for (QName toReplace : qNamesToReplace) {
                if (object.equals(toReplace)) {
                    object = qNameReplacement;
                }
            }
        }
        super.replaceScopes(qNamesToReplace, qNameReplacement);
    }
}
