package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

/**
 * OR gate component used in the digital logic simulator.
 * This component has two input pins and one output pin.
 * The output is HIGH if at least one of the inputs is HIGH (output = input1 OR input2).
 * 
 * @author HP
 */
public class OR extends Component {

    /**
     * Constructs an OR gate at the given position.
     *
     * @param x the x-coordinate of the OR gate
     * @param y the y-coordinate of the OR gate
     */
    public OR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
        initializePins();
    }

     /**
     * Initializes input and output pins of the OR gate.
     * Two input pins are placed on the left side and
     * one output pin is placed on the right side.
     */
    @Override
    protected void initializePins() {
        // 2 input pins on the left
        inputPins.add(new Pin(x - 15, y + 10, Pin.PinType.INPUT, this));
        inputPins.add(new Pin(x - 15, y + height - 10, Pin.PinType.INPUT, this));

        // 1 output pin on the right
        outputPins.add(new Pin(x + width + 20, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    /**
     * Updates the positions of all pins when the component moves.
     */
    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x - 15, y + 10);
            inputPins.get(1).updatePosition(x - 15, y + height - 10);
            outputPins.get(0).updatePosition(x + width + 20, y + height / 2);
        }
    }

    /**
     * Computes the logical OR of the two input pins
     * and sets the output pin value accordingly.
     */
    @Override
    public void compute() {
        // OR logic: output = input1 OR input2
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            boolean input1 = inputPins.get(0).getValue();
            boolean input2 = inputPins.get(1).getValue();
            boolean output = input1 || input2;
            outputPins.get(0).setValue(output);
        }
    }

    /**
     * Draws the OR gate on the canvas.
     * The OR gate is represented as overlapping curves with lines for inputs and output.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        // Right OR curve
        g.drawArc(x, y, width, height, 270, 180);

        // Left curve (smaller, overlapping slightly)
        g.drawArc(x - 15, y, width, height, 270, 180);

        // Input lines close to gate
        g.drawLine(x - 15, y + 10, x + 15, y + 10);
        g.drawLine(x - 15, y + height - 10, x + 15, y + height - 10);

        // Output line
        g.drawLine(x + width, y + height / 2, x + width + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 20, y - 5, width + 40, height + 10);
            g.setColor(prev);
        }
    }
    
    /**
     * Creates and returns a deep copy of this OR component.
     *
     * @return a new OR component with the same position and size
     */
    @Override
    public Component cloneComponent() {
        OR copy = new OR(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
