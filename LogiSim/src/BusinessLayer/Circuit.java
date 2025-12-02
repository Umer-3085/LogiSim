package BusinessLayer;

import java.util.*;

/**
 * Represents a digital circuit with components and connectors.
 * Provides methods for simulation, propagation, and cycle detection.
 * 
 * <p>
 * This class is responsible for managing all components and connectors in a circuit,
 * updating their states, propagating signals, and detecting cycles. It can also
 * reset the circuit to its initial state.
 * </p>
 * 
 * @author HP
 */
public class Circuit {

    /** component list currently on canvas*/
    private ArrayList<Component> components;
     /** connectors list currently on canvas*/
    private ArrayList<Connector> connectors;
    
     /** variable to store circuit name*/
    private String circuitName = "";
    
    /**
     * Default constructor. Initializes empty component and connector lists.
     */
    public Circuit(){
        this.circuitName = "";
        this.components = new ArrayList<>();
        this.connectors = new ArrayList<>();
    }
    
    /**
     * Constructs a circuit with a given name.
     *
     * @param name Name of the circuit
     */
    public Circuit(String name){
        this.circuitName = name;
        this.components = new ArrayList<>();
        this.connectors = new ArrayList<>();
    }
    
    /**
     * Returns the list of components in the circuit.
     *
     * @return ArrayList of components
     */
    public ArrayList<Component> getComponents(){
        return components;
    }
    
    /**
     * Return a list of connectors in the circuit
     * 
     * @return 
     */
    public ArrayList<Connector> getConnectors(){
        return connectors;
    }
    
    /**
     * Sets the list of components in the circuit.
     *
     * @param components ArrayList of components
     */
    public void setComponents(ArrayList<Component> components){
        this.components = components;
    }
    
    /**
     * Sets the list of connectors (wires) in the circuit.
     *
     * @param connectors ArrayList of connectors
     */
    public void setConnectors(ArrayList<Connector> connectors){
        this.connectors = connectors;
    }
    
    /**
     * Returns the name of the circuit.
     *
     * @return Circuit name
     */
    public String getCircuitName(){
        return this.circuitName;
    }
    
    /**
     * Sets the name of the circuit.
     *
     * @param name Circuit name
     */
    public void setCircuitName(String name){
        this.circuitName = name;
    }

     /**
     * Adds a component to the circuit if it doesn't already exist.
     *
     * @param component Component to add
     */
    public void addComponent(Component component) {
        if ( components == null || !components.contains(component)) {
            components.add(component);
        }
    }

    /**
     * Removes a component from the circuit.
     *
     * @param component Component to remove
     */
    public void removeComponent(Component component) {
        components.remove(component);
    }

    /**
     * Adds a connector (wire) to the circuit if it doesn't already exist.
     *
     * @param connector Connector to add
     */
    public void addConnector(Connector connector) {
        if (!connectors.contains(connector)) {
            connectors.add(connector);
        }
    }

    /**
     * Removes a connector (wire) from the circuit.
     *
     * @param connector Connector to remove
     */
    public void removeConnector(Connector connector) {
        // Disconnect from pins first
        connector.disconnectFromPin(true); // start
        connector.disconnectFromPin(false); // end
        connectors.remove(connector);
    }

    /**
     * Updates the entire circuit by propagating values through all components
     * and connectors using a BFS approach.
     */
    public void updateCircuit() {
        // Step 1: Propagate all wire values
        for (Connector wire : connectors) {
            if (wire.isConnected()) {
                wire.propagate();
            }
        }

        // Step 2: Build dependency graph and perform BFS propagation
        Set<Component> visited = new HashSet<>();
        Queue<Component> queue = new LinkedList<>();

        // Start with all source components (components with no inputs or all inputs
        // set)
        for (Component comp : components) {
            if (comp instanceof Switch) {
                // Switches are always sources
                queue.add(comp);
                visited.add(comp);
            }
        }

        // BFS propagation
        int maxIterations = 1000; // Prevent infinite loops
        int iterations = 0;

        while (!queue.isEmpty() && iterations < maxIterations) {
            iterations++;
            Component current = queue.poll();

            // Compute this component's outputs
            current.compute();

            // Find all components connected to this component's outputs
            for (Pin outputPin : current.getOutputPins()) {
                // Get all wires connected to this output pin
                for (Connector wire : outputPin.getConnectedWires()) {
                    // Propagate through the wire
                    wire.propagate();

                    // Get the target component
                    Pin targetPin = wire.getTargetPin();
                    if (targetPin != null) {
                        Component targetComponent = targetPin.getOwner();

                        // Add to queue if not already visited in this iteration
                        if (!visited.contains(targetComponent)) {
                            queue.add(targetComponent);
                            visited.add(targetComponent);
                        }
                    }
                }
            }
        }

        // Step 3: Final pass to ensure all components are computed
        // This handles any components that might have been missed
        for (Component comp : components) {
            comp.compute();
        }
    }

   /**
     * Propagates signals starting from a specific component.
     * Useful when toggling a switch.
     *
     * @param source Component to start propagation from
     */
    public void propagateFrom(Component source) {
        Set<Component> visited = new HashSet<>();
        Queue<Component> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);

        int maxIterations = 1000;
        int iterations = 0;

        while (!queue.isEmpty() && iterations < maxIterations) {
            iterations++;
            Component current = queue.poll();

            // Compute this component
            current.compute();

            // Propagate to connected components
            for (Pin outputPin : current.getOutputPins()) {
                for (Connector wire : outputPin.getConnectedWires()) {
                    wire.propagate();

                    Pin targetPin = wire.getTargetPin();
                    if (targetPin != null) {
                        Component targetComponent = targetPin.getOwner();

                        if (!visited.contains(targetComponent)) {
                            queue.add(targetComponent);
                            visited.add(targetComponent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Detects whether the circuit contains any cycles.
     *
     * @return true if a cycle exists, false otherwise
     */
    public boolean detectCycles() {
        Set<Component> visited = new HashSet<>();
        Set<Component> recursionStack = new HashSet<>();

        for (Component comp : components) {
            if (hasCycleDFS(comp, visited, recursionStack)) {
                return true;
            }
        }

        return false;
    }

    /**
     * DFS helper method for cycle detection.
     */
    private boolean hasCycleDFS(Component comp, Set<Component> visited, Set<Component> recursionStack) {
        if (recursionStack.contains(comp)) {
            return true; // Cycle detected
        }

        if (visited.contains(comp)) {
            return false; // Already processed
        }

        visited.add(comp);
        recursionStack.add(comp);

        // Check all components this one connects to
        for (Pin outputPin : comp.getOutputPins()) {
            for (Connector wire : outputPin.getConnectedWires()) {
                Pin targetPin = wire.getTargetPin();
                if (targetPin != null) {
                    Component targetComp = targetPin.getOwner();
                    if (hasCycleDFS(targetComp, visited, recursionStack)) {
                        return true;
                    }
                }
            }
        }

        recursionStack.remove(comp);
        return false;
    }

    /**
     * Resets all component and connector values and clears connections.
     * Useful before reloading or simulating a fresh circuit.
     */
    public void reset() {
        for (Component comp : components) {
            for (Pin pin : comp.getInputPins()) {
                pin.setValue(false);
            }
            for (Pin pin : comp.getOutputPins()) {
                pin.setValue(false);
            }
        }

        for (Connector wire : connectors) {
            wire.setValue(false);
        }
    }
}
