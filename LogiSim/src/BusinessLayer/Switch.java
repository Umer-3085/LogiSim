package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

/**
 * Represents a digital switch component in a circuit.
 * 
 * The switch acts as a source component with a single output pin.
 * Its state can be ON (true / 1) or OFF (false / 0), and it can toggle
 * between these states. The output pin always reflects the current state.
 * 
 * The component can be drawn on a canvas with a graphical representation
 * showing its base, lever, and 0/1 labels.
 */
public class Switch extends Component {
    private boolean state; // true = 1/ON, false = 0/OFF

    /**
     * Constructs a new Switch at the given (x, y) coordinates.
     * 
     * @param x the x-coordinate of the switch
     * @param y the y-coordinate of the switch
     */
    public Switch(int x, int y) {
        super(x, y);
        this.width = 40;
        this.height = 20;
        this.state = false;
        initializePins();
    }

    /**
     * Initializes the output pin of the switch.
     * A switch has only one output pin located on its right side.
     */
    @Override
    protected void initializePins() {
        // Switch has only 1 output pin (it's a source component)
        outputPins.add(new Pin(x + width + 10, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    /**
     * Updates the output pin position when the switch is moved.
     */
    @Override
    protected void updatePinPositions() {
        if (outputPins.size() >= 1) {
            outputPins.get(0).updatePosition(x + width + 10, y + height / 2);
        }
    }

    /**
     * Updates the output pin value to match the switch state.
     */
    @Override
    public void compute() {
        // Switch output = its state
        if (outputPins.size() >= 1) {
            outputPins.get(0).setValue(state);
        }
    }

     /**
     * Toggles the switch state between ON and OFF.
     * Also updates the output pin value to reflect the new state.
     */
    public void toggle() {
        state = !state;
        compute(); // Update output pin value
    }

    /**
     * Sets the switch state explicitly.
     * 
     * @param state true for ON, false for OFF
     */
    public void setState(boolean state) {
        this.state = state;
        compute(); // Update output pin value
    }

    /**
     * Returns the current state of the switch.
     * 
     * @return true if ON, false if OFF
     */
    public boolean getState() {
        return state;
    }

    /**
     * Creates a clone of this switch, including its state and selection status.
     * 
     * @return a cloned Switch component
     */
    @Override
    public Component cloneComponent() {
        Switch copy = new Switch(this.x, this.y);
        copy.setState(this.state);   // copy ON/OFF state
        copy.setSelected(this.selected);
        return copy;
    }

    /**
     * Draws the switch on a graphics canvas.
     * Includes the base rectangle, lever, 0/1 labels, and input/output lines.
     * Also draws a highlight if the switch is selected.
     * 
     * @param g the Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {
        // Draw switch base
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Draw lever
        int leverWidth = width / 2;
        g.setColor(Color.WHITE);
        if (state) {
            g.fillRect(x + width / 2, y, leverWidth, height); // Right = 1
        } else {
            g.fillRect(x, y, leverWidth, height); // Left = 0
        }

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width / 2, height); // left part border
        g.drawRect(x + width / 2, y, width / 2, height); // right part border

        // Draw 0/1 text
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.BLACK);
        g.drawString("0", x + 8, y + height - 6);
        g.drawString("1", x + width / 2 + 8, y + height - 6);

        // Input/output lines
        g.drawLine(x - 10, y + height / 2, x, y + height / 2);
        g.drawLine(x + width, y + height / 2, x + width + 10, y + height / 2);

        // Selection highlight
        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 12, y - 5, width + 24, height + 10);
            g.setColor(prev);
        }
    }
}
