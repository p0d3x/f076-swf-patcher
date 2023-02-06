package pdx.fo76.asasm.instruction;

import lombok.Getter;
import pdx.fo76.asasm.QName;
import pdx.fo76.injection.EditException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Node {

    @Getter
    private final List<Node> instructions = new ArrayList<>();

    public void add(Node node) {
        instructions.add(node);
    }

    public int length() {
        return 1 + instructions.stream().mapToInt(Node::length).sum();
    }

    public Stream<String> stream(int indent) {
        return instructions.stream().flatMap(i -> i.stream(indent + 1));
    }

    public Node m(String ... instrs) {
        Node result = this;
        for (String instr : instrs) {
            result = result.getInstructions().stream().filter(i -> i.getName().equals(instr)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("unable to find method via " + Arrays.toString(instrs)));
        }
        return result;
    }

    public Node methodCode(String methodName) {
        return findMethod(methodName).map(m -> m.m("method", "body", "code")).get();
    }

    private Optional<Node> findMethod(String methodName) {
        return instructions.stream()
                .filter(i -> isMethod(i, methodName))
                .findFirst();
    }

    public void replaceScopes(QName[] qNamesToReplace, QName qNameReplacement) {
        for (Node instruction : instructions) {
            instruction.replaceScopes(qNamesToReplace, qNameReplacement);
        }
    }

    public void replaceMethod(String searchName, Node newCode) {
        for (int i = 0; i < instructions.size(); i++) {
            Node instruction = instructions.get(i);
            if (isMethod(instruction, searchName)) {
                instructions.set(i, newCode);
                return;
            }
        }
        throw new IllegalArgumentException(searchName + " not found");
    }

    public void insertAfterMethod(String searchName, Node newCode) {
        for (int i = 0; i < instructions.size(); i++) {
            Node instruction = instructions.get(i);
            if (isMethod(instruction, searchName)) {
                instructions.add(i + 1, newCode);
                return;
            }
        }
        throw new IllegalArgumentException(searchName + " not found");
    }

    public void insertAfter(String searchName, List<Node> nodes) {
        for (int i = 0; i < instructions.size(); i++) {
            Node instruction = instructions.get(i);
            if (instruction.getName().equals(searchName)) {
                instructions.addAll(i + 1, nodes);
                return;
            }
        }
        throw new IllegalArgumentException(searchName + " not found");
    }

    public void insertBeforeLast(String searchName, List<Node> nodes) {
        for (int i = instructions.size() - 1; i >= 0; i--) {
            Node instruction = instructions.get(i);
            if (instruction.getName().equals(searchName)) {
                instructions.addAll(i, nodes);
                return;
            }
        }
        throw new IllegalArgumentException(searchName + " not found");
    }

    public abstract String getName();

    private static boolean isMethod(Node instr, String methodName) {
        if (instr instanceof TraitMethod trait) {
            return trait.getMethodName().equals(methodName);
        }
        return false;
    }
}
