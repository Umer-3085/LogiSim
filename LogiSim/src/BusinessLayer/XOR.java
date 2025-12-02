package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Represents a 2-input XOR (Exclusive OR) logic gate component.
 * <p>
 * The XOR gate outputs true (1) only when exactly one of its inputs is true.
 * This class handles pin initialization, logic computation, drawing, and cloning.
 * </p>
 * 
 * Example usage:
 * <pre>
 *     XOR xorGate = new XOR(100, 50);
 *     xorGate.compute();  // updates output pin based on inputs
 * </pre>
 * 
 * @author HP
 */
public class XOR extends Component {

    /**
     * Constructs a XOR gate at the specified coordinates.
     * Initializes its input and output pins.
     * 
     * @param x The X-coordinate of the gate
     * @param y The Y-coordinate of the gate
     */
    public XOR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
        initializePins();
    }

    /**
     * Initializes the input and output pins for the XOR gate.
     * The gate has 2 input pins on the left and 1 output pin on the right.
     */
    @Override
    protected void initializePins() {
        // 2 input pins on the left
        inputPins.add(new Pin(x - 20, y + 10, Pin.PinType.INPUT, this));
        inputPins.add(new Pin(x - 20, y + height - 10, Pin.PinType.INPUT, this));

        // 1 output pin on the right
        outputPins.add(new Pin(x + width + 20, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    /**
     * Updates the positions of input and output pins when the gate is moved.
     */
    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x - 20, y + 10);
            inputPins.get(1).updatePosition(x - 20, y + height - 10);
            outputPins.get(0).updatePosition(x + width + 20, y + height / 2);
        }
    }

    /**
     * Computes the XOR logic for this gate.
     * Sets the output pin to true if exactly one input is true, otherwise false.
     */
    @Override
    public void compute() {
        // XOR logic: output = input1 XOR input2
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            boolean input1 = inputPins.get(0).getValue();
            boolean input2 = inputPins.get(1).getValue();
            boolean output = input1 ^ input2;
            outputPins.get(0).setValue(output);
        }
    }

    /**
     * Draws the XOR gate on the provided graphics context.
     * Includes arcs for the gate shape, input/output lines, and selection highlight.
     *
     * @param g The Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawArc(x - 30, y, width, height, 270, 180);

        // Extra small curve for XOR
        g.drawArc(x - 20, y, width, height, 270, 180);

        // Main OR curve
        g.drawArc(x - 2, y, width, height, 270, 180);

        // Input lines closer to gate
        g.drawLine(x - 20, y + 10, x + 15, y + 10);
        g.drawLine(x - 20, y + height - 10, x + 15, y + height - 10);

        // Output line
        g.drawLine(x + width, y + height / 2, x + width + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 50, height + 10);
            g.setColor(prev);
        }
    }
    
    /**
     * Creates a copy of this XOR gate.
     * The clone has the same size and position but no connections.
     *
     * @return A new XOR component with identical properties
     */
    @Override
    public Component cloneComponent() {
        XOR copy = new XOR(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
