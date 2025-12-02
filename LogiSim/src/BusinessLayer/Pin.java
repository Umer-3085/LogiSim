package BusinessLayer;

import java.util.ArrayList;

/**
 * Represents an input or output pin on a digital component.
 * Pins have a position on the canvas, a boolean value, a type (INPUT or OUTPUT),
 * and can be connected to wires (Connectors).
 * They belong to a specific Component.
 * 
 * Pins are used to transfer boolean values between components via connectors.
 * Each pin maintains a list of connected wires for simulation and rendering.
 * 
 * @author HP
 */
public class Pin {
    
    /**
     * Type of pin: INPUT or OUTPUT.
     */
    public enum PinType {
        INPUT,
        OUTPUT
    }
    
    private int x, y; // Absolute position on canvas
    private boolean value; // Current boolean value (true = 1, false = 0)
    private PinType type;
    private Component owner; // Component this pin belongs to
    private ArrayList<Connector> connectedWires;
    
     /**
     * Constructs a Pin at the given position, with type and owner component.
     *
     * @param x the x-coordinate of the pin
     * @param y the y-coordinate of the pin
     * @param type the type of pin (INPUT or OUTPUT)
     * @param owner the component this pin belongs to
     */
    public Pin(int x, int y, PinType type, Component owner) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.owner = owner;
        this.value = false;
        this.connectedWires = new ArrayList<>();
    }
    
    /**
     * Gets the current boolean value of the pin.
     *
     * @return true if HIGH (1), false if LOW (0)
     */
    public boolean getValue() {
        return value;
    }
    
    /**
     * Sets the boolean value of the pin.
     *
     * @param value true for HIGH, false for LOW
     */
    public void setValue(boolean value) {
        this.value = value;
    }
    
    /**
     * Gets the x-coordinate of the pin.
     *
     * @return x-coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Gets the x-coordinate of the pin.
     *
     * @return x-coordinate
     */
    public int getY() {
        return y;
    }
    
     /**
     * Updates the pin position on the canvas.
     *
     * @param x new x-coordinate
     * @param y new y-coordinate
     */
    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the type of the pin (INPUT or OUTPUT).
     *
     * @return pin type
     */
    public PinType getType() {
        return type;
    }
    
    /**
     * Gets the owner component of this pin.
     *
     * @return the component this pin belongs to
     */
    public Component getOwner() {
        return owner;
    }
    
    /**
     * Adds a connector (wire) to this pin.
     * Ensures no duplicate connections are added.
     *
     * @param wire the connector to attach
     */
    public void addWire(Connector wire) {
        if (!connectedWires.contains(wire)) {
            connectedWires.add(wire);
        }
    }
    
    /**
     * Removes a connector (wire) from this pin.
     *
     * @param wire the connector to remove
     */
    public void removeWire(Connector wire) {
        connectedWires.remove(wire);
    }
    
     /**
     * Returns a list of all connectors connected to this pin.
     *
     * @return a copy of the list of connected wires
     */
    public ArrayList<Connector> getConnectedWires() {
        return new ArrayList<>(connectedWires); // Return copy for safety
    }
    
     /**
     * Checks if the pin has any connections.
     *
     * @return true if connected to at least one wire
     */
    public boolean isConnected() {
        return !connectedWires.isEmpty();
    }
    
    /**
     * Calculates the Euclidean distance from this pin to a given point.
     * Useful for snapping wires to pins.
     *
     * @param px x-coordinate of the point
     * @param py y-coordinate of the point
     * @return distance to the point
     */
    public double distanceTo(int px, int py) {
        int dx = px - x;
        int dy = py - y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Creates and returns a copy of this pin for a new owner component.
     *
     * @param newOwner the new owner component
     * @return a new Pin with the same type, position, and value
     */
    public Pin clonePin(Component newOwner) {
        Pin p = new Pin(this.getX(), this.getY(), this.getType(), newOwner);
        p.setValue(this.getValue());
        return p;
    }

}
