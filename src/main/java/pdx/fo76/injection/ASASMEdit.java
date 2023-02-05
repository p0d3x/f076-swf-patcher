package pdx.fo76.injection;

import pdx.fo76.asasm.instruction.Node;

public interface ASASMEdit {
    void execute(PatcherStage<?> context, String namespace, String className, Node codeNode) throws EditException;
}
