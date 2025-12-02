package BusinessLayer;

import java.util.ArrayList;

/**
 * Represents an input or output pin on a component.
 * Pins have a position, value, and can be connected to wires.
 */
public class Pin {
    
    public enum PinType {
        INPUT,
        OUTPUT
    }
    
    private int x, y; // Absolute position on canvas
    private boolean value; // Current boolean value (true = 1, false = 0)
    private PinType type;
    private Component owner; // Component this pin belongs to
    private ArrayList<Connector> connectedWires;
    
    public Pin(int x, int y, PinType type, Component owner) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.owner = owner;
        this.value = false;
        this.connectedWires = new ArrayList<>();
    }
    
    // Get/Set value
    public boolean getValue() {
        return value;
    }
    
    public void setValue(boolean value) {
        this.value = value;
    }
    
    // Position management
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Type and owner
    public PinType getType() {
        return type;
    }
    
    public Component getOwner() {
        return owner;
    }
    
    // Wire connection management
    public void addWire(Connector wire) {
        if (!connectedWires.contains(wire)) {
            connectedWires.add(wire);
        }
    }
    
    public void removeWire(Connector wire) {
        connectedWires.remove(wire);
    }
    
    public ArrayList<Connector> getConnectedWires() {
        return new ArrayList<>(connectedWires); // Return copy for safety
    }
    
    public boolean isConnected() {
        return !connectedWires.isEmpty();
    }
    
    // Distance calculation for wire snapping
    public double distanceTo(int px, int py) {
        int dx = px - x;
        int dy = py - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public Pin clonePin(Component newOwner) {
        Pin p = new Pin(this.getX(), this.getY(), this.getType(), newOwner);
        p.setValue(this.getValue());
        return p;
    }

}
