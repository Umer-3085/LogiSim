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
 *
 * @author HP
 */
public abstract class Component {

    protected int x, y;
    protected int width = 60;
    protected int height = 40;
    protected boolean selected = false;

    // Simulation support
    protected ArrayList<Pin> inputPins;
    protected ArrayList<Pin> outputPins;

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
        this.inputPins = new ArrayList<>();
        this.outputPins = new ArrayList<>();
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

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

    public boolean contains(Point p) {
        // Reduced padding to 5px to prevent overlapping hit boxes
        Rectangle r = new Rectangle(x - 5, y - 5, width + 10, height + 10);
        return r.contains(p);
    }

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

    public void setSelected(boolean s) {
        this.selected = s;
    }

    public boolean isSelected() {
        return selected;
    }

    // Pin accessors
    public ArrayList<Pin> getInputPins() {
        return inputPins;
    }

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
    
    public abstract Component cloneComponent();

}
