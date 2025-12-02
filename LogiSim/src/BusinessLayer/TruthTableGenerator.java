package BusinessLayer;

import java.util.*;

/**
 * Generates the truth table and Boolean expressions for a given circuit.
 * <p>
 * This class identifies input switches and output LEDs from the circuit,
 * simulates all input combinations, and produces the corresponding truth table.
 * It can also generate Boolean expressions for each output by tracing back
 * through connected components.
 * </p>
 * 
 * Usage example:
 * <pre>
 *     Circuit circuit = ...;
 *     TruthTableGenerator ttGen = new TruthTableGenerator(circuit);
 *     Object[][] truthTable = ttGen.generateTruthTable();
 *     Map<String, String> expressions = ttGen.generateExpressions();
 * </pre>
 * 
 * @author
 */
public class TruthTableGenerator {

    private Circuit circuit;
    private List<Switch> inputs;
    private List<LED> outputs;

    /**
     * Constructs a TruthTableGenerator for the specified circuit.
     * Identifies all input switches and output LEDs automatically.
     *
     * @param circuit The circuit to analyze
     */
    public TruthTableGenerator(Circuit circuit) {
        this.circuit = circuit;
        identifyInputsOutputs();
    }

     /**
     * Identifies input and output components in the circuit.
     * Inputs are instances of {@link Switch} and outputs are instances of {@link LED}.
     * Components are sorted top-to-bottom, then left-to-right for consistent labeling.
     */
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

    /**
     * Returns a list of input labels (A, B, C, ...).
     * @return List of input labels
     */
    public List<String> getInputLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            labels.add(String.valueOf((char) ('A' + i)));
        }
        return labels;
    }

    /**
     * Returns a list of output labels (Y1, Y2, ...).
     * @return List of output labels
     */
    public List<String> getOutputLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < outputs.size(); i++) {
            labels.add("Y" + (i + 1));
        }
        return labels;
    }

    /**
     * Generates the truth table for the circuit.
     * Each row corresponds to an input combination, and each column corresponds to
     * an input or output. "1" represents ON/true, "0" represents OFF/false.
     *
     * @return 2D array representing the truth table
     */
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

    /**
     * Generates Boolean expressions for each output LED.
     * Expressions are constructed by tracing back the driving components.
     *
     * @return Map of output labels to Boolean expressions
     */
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

     /**
     * Recursively builds a Boolean expression from the given wires.
     *
     * @param wires Wires driving a pin
     * @param visited Set of visited components to avoid infinite loops
     * @return Boolean expression as a string
     */
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

    /**
     * Combines input expressions for a component using a specified delimiter.
     *
     * @param comp Component to analyze
     * @param delimiter String delimiter between inputs (e.g., " . ", " + ")
     * @param visited Set of visited components to avoid cycles
     * @return Combined Boolean expression
     */
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
