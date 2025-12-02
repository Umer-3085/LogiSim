/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Abstract base class for all circuit components.
 * 
 * <p>
 * Each component has a position, dimensions, and a list of input/output pins.
 * Components can be drawn on a canvas, moved, selected, and can compute their outputs
 * based on their input values.
 * </p>
 * 
 * <p>
 * Subclasses must implement the drawing logic, computation logic, and pin management.
 * </p>
 * 
 * @author HP
 */
public abstract class Component {

    /** X Y coordinate of the top-left corner */
    protected int x, y;
    /** Width of the component (default 60 pixels) */
    protected int width = 60;
      /** Height of the component (default 40 pixels) */
    protected int height = 40;
    /** True if the component is currently selected */
    protected boolean selected = false;

     /** List of input pins */
    protected ArrayList<Pin> inputPins;
     /** List of output pins */
    protected ArrayList<Pin> outputPins;

    /**
     * Constructs a component at a given position.
     *
     * @param x X coordinate of the top-left corner
     * @param y Y coordinate of the top-left corner
     */
    public Component(int x, int y) {
        this.x = x;
        this.y = y;
        this.inputPins = new ArrayList<>();
        this.outputPins = new ArrayList<>();
    }
    
    /**
     * Returns the X coordinate of the component.
     *
     * @return X coordinate
     */
    public int getX(){
        return x;
    }
    
    /**
     * Returns the Y coordinate of the component.
     *
     * @return Y coordinate
     */
    public int getY(){
        return y;
    }
    
    /**
     * Returns the width of the component.
     *
     * @return Width in pixels
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height of the component.
     *
     * @return height in pixels
     */
    public int getHeight(){
        return height;
    }

    /**
     * Draws the component on the given graphics context.
     * 
     * @param g Graphics context
     */
    public abstract void draw(Graphics g);

    /**
     * Compute output values based on input values.
     * Each component type implements its own logic.
     */
    public abstract void compute();

    /**
     * Initialize pins for this component.
     * Called by subclass constructors.
     */
    protected abstract void initializePins();

    /**
     * Update pin positions when component moves.
     * Called automatically from move().
     */
    protected abstract void updatePinPositions();

    /**
     * Checks if a given point is within the component bounds (with a small padding).
     *
     * @param p Point to test
     * @return True if point is inside the component
     */
    public boolean contains(Point p) {
        // Reduced padding to 5px to prevent overlapping hit boxes
        Rectangle r = new Rectangle(x - 5, y - 5, width + 10, height + 10);
        return r.contains(p);
    }

    /**
     * Moves the component to a new position.
     * Also updates all connected wires and pins.
     *
     * @param nx New X coordinate
     * @param ny New Y coordinate
     */

    public void move(int nx, int ny) {
        this.x = nx;
        this.y = ny;
        updatePinPositions();
        updateConnectedWires();
    }

    /**
     * Update all wires connected to this component's pins.
     * Called automatically when component moves.
     */
    protected void updateConnectedWires() {
        // Update all wires connected to input pins
        for (Pin pin : inputPins) {
            for (Connector wire : pin.getConnectedWires()) {
                wire.updateConnectedEndpoints();
            }
        }
        // Update all wires connected to output pins
        for (Pin pin : outputPins) {
            for (Connector wire : pin.getConnectedWires()) {
                wire.updateConnectedEndpoints();
            }
        }
    }
    
    /**
     * Sets the selection state of the component.
     *
     * @param s True if selected, false otherwise
     */
    public void setSelected(boolean s) {
        this.selected = s;
    }

    /**
     * Returns whether the component is currently selected.
     *
     * @return True if selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Returns the list of input pins.
     *
     * @return ArrayList of input pins
     */
    public ArrayList<Pin> getInputPins() {
        return inputPins;
    }

    /**
     * Returns the list of output pins.
     *
     * @return ArrayList of output pins
     */
    public ArrayList<Pin> getOutputPins() {
        return outputPins;
    }

    /**
     * Find the nearest pin to the given coordinates within threshold.
     * 
     * @param px        X coordinate
     * @param py        Y coordinate
     * @param threshold Maximum distance in pixels
     * @return Nearest pin or null if none within threshold
     */
    public Pin findNearestPin(int px, int py, int threshold) {
        Pin nearest = null;
        double minDistance = threshold;

        // Check all pins
        for (Pin pin : inputPins) {
            double dist = pin.distanceTo(px, py);
            if (dist < minDistance) {
                minDistance = dist;
                nearest = pin;
            }
        }

        for (Pin pin : outputPins) {
            double dist = pin.distanceTo(px, py);
            if (dist < minDistance) {
                minDistance = dist;
                nearest = pin;
            }
        }

        return nearest;
    }
    
    /**
     * Creates a deep copy of this component.
     * Subclasses must implement to return an exact copy of themselves.
     *
     * @return Cloned component
     */
    public abstract Component cloneComponent();

}
