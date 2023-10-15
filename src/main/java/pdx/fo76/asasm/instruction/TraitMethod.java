package pdx.fo76.asasm.instruction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.SyntaxConstants;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TraitMethod extends Trait {

    QName qName;
    List<Flag> flags;

    @Override
    public String toString() {
        return getName()
                + " " + getType() + " " + qName
                + (flags.isEmpty() ? "" : " ") + flags.stream().map(Indented1ParamNode::toString).collect(Collectors.joining(" "));
    }

    public String getType() {
        return SyntaxConstants.METHOD;
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        for (QName toReplace : qNamesToReplace) {
            if (toReplace.equals(qName)) {
                qName = qNameReplacement;
                return;
            }
        }
    }

    public String getMethodName() {
        return qName.fieldName();
    }
}
