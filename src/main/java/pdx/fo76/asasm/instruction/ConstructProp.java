package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class ConstructProp extends Indented2ParamNode<Identifier, Integer> {

    public ConstructProp(Identifier object, int numArgs) {
        super("constructprop", object, numArgs, 20);
    }

    @Override
    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        if (getParam1() != null) {
            for (QName toReplace : qNamesToReplace) {
                if (getParam1().equals(toReplace)) {
                    setParam1(qNameReplacement);
                    break;
                }
            }
        }
        super.replaceScopes(qNamesToReplace, qNameReplacement);
    }
}
