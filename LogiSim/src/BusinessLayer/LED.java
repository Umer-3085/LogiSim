package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Represents a Light Emitting Diode (LED) component in a circuit simulation.
 * <p>
 * The LED acts as a sink component with a single input pin. Its state 
 * (ON or OFF) is determined by the input pin's value. The LED can be drawn 
 * vertically with visual feedback for its current state.
 * </p>
 * @author HP
 */
public class LED extends Component {
    private boolean state; // true = ON (1), false = OFF (0)

    /**
     * Constructs an LED at the specified coordinates.
     * 
     * @param x the x-coordinate of the LED's top-left corner
     * @param y the y-coordinate of the LED's top-left corner
     */
    public LED(int x, int y) {
        super(x, y);
        this.width = 20; // narrower for vertical look
        this.height = 40; // taller
        this.state = false;
        initializePins();
    }

    /**
     * Initializes the input pins for the LED.
     * <p>
     * An LED has only one input pin located at the top-center of the component.
     * </p>
     */
    @Override
    protected void initializePins() {
        // LED has only 1 input pin (it's a sink component)
        inputPins.add(new Pin(x + width / 2, y - 20, Pin.PinType.INPUT, this));
    }

    /**
     * Updates the positions of the LED's pins based on its current location.
     */
    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x + width / 2, y - 20);
        }
    }

    /**
     * Computes the LED's state based on its input pin value.
     * <p>
     * The LED is ON if the input pin value is true, otherwise it is OFF.
     * </p>
     */
    @Override
    public void compute() {
        // LED state = its input value
        if (inputPins.size() >= 1) {
            state = inputPins.get(0).getValue();
        }
    }
    
    /**
     * Creates a copy of this LED component.
     * 
     * @return a new LED instance with the same position, size, and state
     */
    @Override
    public Component cloneComponent() {
        LED copy = new LED(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        copy.state = this.state;
        return copy;
    }

    /**
     * Sets the state of the LED.
     * 
     * @param state true for ON, false for OFF
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Gets the current state of the LED.
     * 
     * @return true if ON, false if OFF
     */
    public boolean getState() {
        return state;
    }

    /**
     * Draws the LED on the given Graphics context.
     * <p>
     * The LED is represented as a vertical rectangle. It displays its state 
     * with green (ON) or gray (OFF) fill, a border, the value (0/1), and a 
     * highlight if selected.
     * </p>
     * 
     * @param g the Graphics context to draw on
     */
    @Override
    public void draw(Graphics g) {
        // LED vertical rectangle
        g.setColor(state ? Color.GREEN : Color.GRAY);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // Single input line at the top
        g.drawLine(x + width / 2, y - 20, x + width / 2, y);

        // Draw 0/1 text on LED
        g.setColor(Color.BLACK);
        g.drawString(state ? "1" : "0", x + 4, y + height / 2 + 4);

        // Selection highlight
        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 5, y - 5, width + 10, height + 10);
            g.setColor(prev);
        }
    }
}
