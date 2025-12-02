package BusinessLayer;

import java.util.*;

/**
 * Circuit simulation engine.
 * Manages components and wires, and propagates values through the circuit.
 */
public class Circuit {

    private ArrayList<Component> components;
    private ArrayList<Connector> connectors;
    
    private String circuitName = "";
    
    public Circuit(){
        this.circuitName = "";
        this.components = new ArrayList<>();
        this.connectors = new ArrayList<>();
    }
    
    public Circuit(String name){
        this.circuitName = name;
        this.components = new ArrayList<>();
        this.connectors = new ArrayList<>();
    }
    
    public ArrayList<Component> getComponents(){
        return components;
    }
    
    public ArrayList<Connector> getConnectors(){
        return connectors;
    }
    
    public void setComponents(ArrayList<Component> components){
        this.components = components;
    }
    
    public void setConnectors(ArrayList<Connector> connectors){
        this.connectors = connectors;
    }
    
    public ArrayList<Connector> setConnectors(){
        return connectors;
    }
    
    public String getCircuitName(){
        return this.circuitName;
    }
    
    public void setCircuitName(String name){
        this.circuitName = name;
    }

    // Component management
    public void addComponent(Component component) {
        if ( components == null || !components.contains(component)) {
            components.add(component);
        }
    }

    public void removeComponent(Component component) {
        components.remove(component);
    }

    // Connector management
    public void addConnector(Connector connector) {
        if (!connectors.contains(connector)) {
            connectors.add(connector);
        }
    }

    public void removeConnector(Connector connector) {
        // Disconnect from pins first
        connector.disconnectFromPin(true); // start
        connector.disconnectFromPin(false); // end
        connectors.remove(connector);
    }

    /**
     * Main circuit update method.
     * Propagates values through the entire circuit using BFS.
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
     * Propagate values starting from a specific component.
     * Useful when a switch is toggled.
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
     * Detect if there are any cycles in the circuit.
     * 
     * @return true if cycles exist, false otherwise
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
     * Clear all component and wire values.
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
