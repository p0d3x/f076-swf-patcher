package pdx.fo76.asasm.instruction;

import lombok.EqualsAndHashCode;
import pdx.fo76.asasm.Identifier;
import pdx.fo76.asasm.QName;

@EqualsAndHashCode(callSuper = true)
public class CallPropVoid extends Indented2ParamNode<Identifier, Integer> {

    public CallPropVoid(Identifier method, int numArgs) {
        super("callpropvoid", method, numArgs, 20);
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
