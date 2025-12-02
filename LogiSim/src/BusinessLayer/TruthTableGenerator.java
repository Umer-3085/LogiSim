package BusinessLayer;

import java.util.*;

public class TruthTableGenerator {

    private Circuit circuit;
    private List<Switch> inputs;
    private List<LED> outputs;

    public TruthTableGenerator(Circuit circuit) {
        this.circuit = circuit;
        identifyInputsOutputs();
    }

    private void identifyInputsOutputs() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();

        for (Component comp : circuit.getComponents()) {
            if (comp instanceof Switch) {
                inputs.add((Switch) comp);
            } else if (comp instanceof LED) {
                outputs.add((LED) comp);
            }
        }

        // Sort by position (Top-to-bottom, then Left-to-right)
        Comparator<Component> posComparator = (c1, c2) -> {
            if (Math.abs(c1.y - c2.y) > 20) {
                return Integer.compare(c1.y, c2.y);
            }
            return Integer.compare(c1.x, c2.x);
        };

        inputs.sort(posComparator);
        outputs.sort(posComparator);
    }

    public List<String> getInputLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            labels.add(String.valueOf((char) ('A' + i)));
        }
        return labels;
    }

    public List<String> getOutputLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < outputs.size(); i++) {
            labels.add("Y" + (i + 1));
        }
        return labels;
    }

    public Object[][] generateTruthTable() {
        int numInputs = inputs.size();
        int numRows = 1 << numInputs;
        int numCols = inputs.size() + outputs.size();
        Object[][] data = new Object[numRows][numCols];

        // Save current state
        boolean[] savedStates = new boolean[numInputs];
        for (int i = 0; i < numInputs; i++) {
            savedStates[i] = inputs.get(i).getState();
        }

        try {
            for (int i = 0; i < numRows; i++) {
                // Set inputs
                for (int j = 0; j < numInputs; j++) {
                    boolean val = ((i >> (numInputs - 1 - j)) & 1) == 1;
                    inputs.get(j).setState(val);
                    data[i][j] = val ? "1" : "0";
                }

                // Simulate
                circuit.updateCircuit();

                // Get outputs
                for (int j = 0; j < outputs.size(); j++) {
                    // Assuming LED has a method to get its value, checking LED.java next
                    // If not, we'll need to check its input pin
                    boolean val = false;
                    if (!outputs.get(j).getInputPins().isEmpty()) {
                        val = outputs.get(j).getInputPins().get(0).getValue();
                    }
                    data[i][numInputs + j] = val ? "1" : "0";
                }
            }
        } finally {
            // Restore state
            for (int i = 0; i < numInputs; i++) {
                inputs.get(i).setState(savedStates[i]);
            }
            circuit.updateCircuit();
        }

        return data;
    }

    public Map<String, String> generateExpressions() {
        Map<String, String> expressions = new LinkedHashMap<>();
        List<String> outLabels = getOutputLabels();

        for (int i = 0; i < outputs.size(); i++) {
            LED led = outputs.get(i);
            String expr = "0";
            if (!led.getInputPins().isEmpty()) {
                expr = buildExpression(led.getInputPins().get(0).getConnectedWires(), new HashSet<>());
            }
            expressions.put(outLabels.get(i), expr);
        }
        return expressions;
    }

    private String buildExpression(ArrayList<Connector> wires, Set<Component> visited) {
        if (wires == null || wires.isEmpty()) {
            return "0";
        }

        // Find the source component driving this wire
        Connector wire = wires.get(0); // Assuming single driver for now
        Pin sourcePin = wire.getSourcePin();

        if (sourcePin == null)
            return "0";

        Component sourceComp = sourcePin.getOwner();

        if (visited.contains(sourceComp)) {
            return "(CYCLE)";
        }
        visited.add(sourceComp);

        String result = "";
        if (sourceComp instanceof Switch) {
            int index = inputs.indexOf(sourceComp);
            result = (index >= 0) ? String.valueOf((char) ('A' + index)) : "?";
        } else if (sourceComp instanceof AND) {
            result = "(" + getInputsExpression(sourceComp, " . ", visited) + ")";
        } else if (sourceComp instanceof OR) {
            result = "(" + getInputsExpression(sourceComp, " + ", visited) + ")";
        } else if (sourceComp instanceof NOT) {
            result = "!" + getInputsExpression(sourceComp, "", visited);
        } else if (sourceComp instanceof NAND) {
            result = "!(" + getInputsExpression(sourceComp, " . ", visited) + ")";
        } else if (sourceComp instanceof NOR) {
            result = "!(" + getInputsExpression(sourceComp, " + ", visited) + ")";
        } else if (sourceComp instanceof XOR) {
            result = "(" + getInputsExpression(sourceComp, " ⊕ ", visited) + ")";
        } else {
            result = "?";
        }

        visited.remove(sourceComp);
        return result;
    }

    private String getInputsExpression(Component comp, String delimiter, Set<Component> visited) {
        List<String> parts = new ArrayList<>();
        for (Pin p : comp.getInputPins()) {
            parts.add(buildExpression(p.getConnectedWires(), visited));
        }
        if (parts.isEmpty())
            return "0";
        if (parts.size() == 1)
            return parts.get(0);
        return String.join(delimiter, parts);
    }
}
