package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Represents a wire connecting component pins.
 * Carries boolean values from output pins to input pins.
 */
public class Connector {
    private int startX, startY; // start point
    private int endX, endY; // end point
    private boolean selected = false;

    // Simulation support
    private Pin sourcePin; // Output pin this wire connects from
    private Pin targetPin; // Input pin this wire connects to
    private boolean value; // Current value being carried

    public Connector(int x, int y) {
        this.startX = x;
        this.startY = y;
        this.endX = x + 50; // default horizontal length
        this.endY = y;
        this.value = false;
        this.sourcePin = null;
        this.targetPin = null;
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
    }

    public boolean isSelected() {
        return selected;
    }

    // Move whole connector
    public void move(int dx, int dy) {
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;
    }

    // Drag only start or end
    public void dragStart(int x, int y) {
        startX = x;
        startY = y;
    }

    public void dragEnd(int x, int y) {
        endX = x;
        endY = y;
    }

    // Check if mouse near start/end points
    public boolean containsStart(int px, int py) {
        int dx = px - startX;
        int dy = py - startY;
        return dx * dx + dy * dy <= 64; // 8px radius
    }

    public boolean containsEnd(int px, int py) {
        int dx = px - endX;
        int dy = py - endY;
        return dx * dx + dy * dy <= 64;
    }

    // Check if mouse is on line for whole-wire drag (approx)
    public boolean containsLine(int px, int py) {
        int dx = endX - startX;
        int dy = endY - startY;
        if (dx == 0 && dy == 0)
            return false;

        // Check if point is within bounding box with tolerance
        int minX = Math.min(startX, endX) - 5;
        int maxX = Math.max(startX, endX) + 5;
        int minY = Math.min(startY, endY) - 5;
        int maxY = Math.max(startY, endY) + 5;

        if (px < minX || px > maxX || py < minY || py > maxY) {
            return false;
        }

        double distance = Math.abs(dy * px - dx * py + endX * startY - endY * startX) / Math.sqrt(dx * dx + dy * dy);
        return distance <= 5; // 5px tolerance
    }

    // Pin connection management
    /**
     * Connect this wire endpoint to a pin.
     * 
     * @param pin     The pin to connect to
     * @param isStart True to connect start point, false for end point
     */
    public void connectToPin(Pin pin, boolean isStart) {
        if (pin == null)
            return;

        if (isStart) {
            // Disconnect from previous pin if any
            if (sourcePin != null) {
                sourcePin.removeWire(this);
            }
            // Only allow connecting start to OUTPUT pins
            if (pin.getType() == Pin.PinType.OUTPUT) {
                sourcePin = pin;
                sourcePin.addWire(this);
                startX = pin.getX();
                startY = pin.getY();
            }
        } else {
            // Disconnect from previous pin if any
            if (targetPin != null) {
                targetPin.removeWire(this);
            }
            // Only allow connecting end to INPUT pins
            if (pin.getType() == Pin.PinType.INPUT) {
                targetPin = pin;
                targetPin.addWire(this);
                endX = pin.getX();
                endY = pin.getY();
            }
        }
    }

    /**
     * Disconnect from a pin.
     * 
     * @param isStart True to disconnect start point, false for end point
     */
    public void disconnectFromPin(boolean isStart) {
        if (isStart && sourcePin != null) {
            sourcePin.removeWire(this);
            sourcePin = null;
        } else if (!isStart && targetPin != null) {
            targetPin.removeWire(this);
            targetPin = null;
        }
    }

    /**
     * Propagate value from source pin to target pin.
     */
    public void propagate() {
        if (sourcePin != null && targetPin != null) {
            value = sourcePin.getValue();
            targetPin.setValue(value);
        }
    }

    /**
     * Check if both ends are connected to pins.
     */
    public boolean isConnected() {
        return sourcePin != null && targetPin != null;
    }

    /**
     * Update wire endpoints to match connected pin positions.
     * Called automatically when a component moves.
     */
    public void updateConnectedEndpoints() {
        if (sourcePin != null) {
            startX = sourcePin.getX();
            startY = sourcePin.getY();
        }
        if (targetPin != null) {
            endX = targetPin.getX();
            endY = targetPin.getY();
        }
    }

    // Getters
    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Pin getSourcePin() {
        return sourcePin;
    }

    public Pin getTargetPin() {
        return targetPin;
    }

    public void draw(Graphics g) {
        // Color wire based on connection status and value
        if (isConnected()) {
            g.setColor(value ? Color.RED : Color.BLACK); // Red for 1, Black for 0
        } else {
            g.setColor(Color.LIGHT_GRAY); // Gray for unconnected
        }

        g.drawLine(startX, startY, endX, endY);

        // Draw endpoints
        g.fillOval(startX - 4, startY - 4, 8, 8);
        g.fillOval(endX - 4, endY - 4, 8, 8);

        // Highlight connected pins
        if (sourcePin != null) {
            g.setColor(Color.GREEN);
            g.fillOval(startX - 3, startY - 3, 6, 6);
        }
        if (targetPin != null) {
            g.setColor(Color.GREEN);
            g.fillOval(endX - 3, endY - 3, 6, 6);
        }

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawOval(startX - 6, startY - 6, 12, 12);
            g.drawOval(endX - 6, endY - 6, 12, 12);
            g.setColor(prev);
        }
    }
    
    public Connector cloneConnector(Pin newSource, Pin newTarget) {
        // Create new connector at the same start point as the original
        Connector copy = new Connector(this.startX, this.startY);

        // Connect it to the new pins
        copy.connectToPin(newSource, true);  // start pin
        copy.connectToPin(newTarget, false); // end pin

        // Copy the value
        copy.setValue(this.getValue());

        return copy;
    }



    // Getters for position
    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
}
