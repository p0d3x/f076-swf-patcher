package pdx.fo76.injection;

import lombok.extern.slf4j.Slf4j;
import pdx.fo76.asasm.instruction.InstructionReader;
import pdx.fo76.asasm.instruction.Namespace;
import pdx.fo76.asasm.instruction.QName;
import pdx.fo76.asasm.instruction.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Slf4j
public class ASASMEdits {

    private ASASMEdits() {}

    public static ASASMEdit injectSFE() {
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node classNode) throws EditException {
                var instance = classNode.m("instance");
                var slotQName = QName.ofPackage("__SFCodeObj");
                var sfCodeObj = new Slot(slotQName);
                var typeQName = QName.ofPackage("Object");
                var object = new Type(typeQName);
                // declare and initialize __SFCodeObj
                // after iinit
                instance.insertAfter("iinit", List.of(
                        new TraitSlot(sfCodeObj, null, object, null)
                ));
                // after first: pushscope
                instance.m("iinit", "body", "code")
                        .insertAfter("pushscope", List.of(
                                new BlankLine(),
                                new GetLocal0(),
                                new FindPropStrict(typeQName),
                                new ConstructProp(typeQName, 0),
                                new InitProperty(slotQName)
                        ));
                log.info("injected __SFCodeObj into {}", className);
            }
        };
    }

    public static ASASMEdit injectLoader(String modFile, String asmFile) {
        Objects.requireNonNull(modFile);
        Objects.requireNonNull(asmFile);
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node classNode) throws EditException {
                try {
                    var instance = classNode.m("instance");
                    // declare and call loadMod
                    // before end
                    var loaderNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve(asmFile), className);
                    instance.insertBeforeLast("end", loaderNode.getInstructions());
                    // before last: returnvoid
                    var loadMod = QName.of(Namespace.ofPrivate(className), "loadMod");
                    instance.m("iinit", "body", "code")
                            .insertBeforeLast("returnvoid", List.of(
                                    new GetLocal0(),
                                    new PushString(new StringLiteral(modFile)),
                                    new CallPropVoid(loadMod, 1),
                                    new BlankLine()
                            ));
                    log.info("injected loadMod('{}') into {}", modFile, className);
                } catch (IOException e) {
                    throw new EditException("IOException during edit: " + e.getMessage(), e);
                }
            }
        };
    }

    public static ASASMEdit changePrivateScopeToPackage(String fieldName) {
        Objects.requireNonNull(fieldName);
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node codeNode) throws EditException {
                // Replace the text in each line
                QName[] scopesToReplace = {
                        QName.of(Namespace.ofPrivate(className), fieldName),
                        QName.of(Namespace.ofPrivate(null, className), fieldName),
                };
                var replacementScope = QName.ofPackage(fieldName);
                codeNode.replaceScopes(scopesToReplace, replacementScope);
                log.info("made {} readable in {}", fieldName, className);
            }
        };
    }

    public static ASASMEdit patchQuantityMenu() {
        return new ASASMEdit() {
            @Override
            public void execute(PatcherStage<?> context, String namespace, String className, Node classNode) throws EditException {

                try {
                    var fqName = (namespace != null ? namespace + ":" : "") + className;
                    var instance = classNode.m("instance");

                    // before first trait slot
                    var slot = new Slot(QName.of(Namespace.ofPrivate(null, fqName), "isEditingValue"));
                    var type = new Type(QName.ofPackage("Boolean"));
                    instance.insertAfter("iinit", List.of(
                            new TraitSlot(slot, null, type, new Value("False()"))
                    ));

                    var loaderNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve("quantitymenu_ctor.asasm"), fqName);
                    instance.m("iinit", "body", "code")
                            .insertBeforeLast("returnvoid", loaderNode.getInstructions());

                    var openMenuNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve("quantitymenu_openmenu.asasm"), fqName);
                    instance.methodCode("OpenMenu")
                            .insertBeforeLast("returnvoid", openMenuNode.getInstructions());

                    var closeMenuNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve("quantitymenu_closemenu.asasm"), fqName);
                    instance.methodCode("CloseMenu")
                            .insertBeforeLast("returnvoid", closeMenuNode.getInstructions());

                    // replace class/instance/onKeyDowwn
                    var onKeyDownNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve("quantitymenu_onKeyDown.asasm"), fqName);
                    instance.replaceMethod("onKeyDown", onKeyDownNode.getInstructions().get(0));

                    // insert class/instance/updateValueInput
                    var updateValueInputNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve("quantitymenu_updateValueInput.asasm"), fqName);
                    instance.insertAfterMethod("onKeyDown", updateValueInputNode.getInstructions().get(0));

                    // insert class/instance/onValueClicked
                    var onValueClickedNode = InstructionReader.readNodeFromTemplate(context.templatePath.resolve("quantitymenu_onValueClicked.asasm"), fqName);
                    instance.insertAfterMethod("updateValueInput", onValueClickedNode.getInstructions().get(0));

                    log.info("added TextField input to QuantityMenu");
                } catch (IOException e){
                    throw new EditException("IOException during edit: " + e.getMessage(), e);
                }
            }
        };
    }
}
