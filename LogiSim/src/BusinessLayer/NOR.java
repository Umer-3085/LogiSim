package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

/**
 * NOR gate component used in the digital logic simulator.
 * This component has two input pins and one output pin.
 * The output is HIGH only when both inputs are LOW (output = NOT(input1 OR input2)).
 * 
 * @author HP
 */
public class NOR extends Component {

    /**
     * Constructs a NOR gate at the given position.
     *
     * @param x the x-coordinate of the NOR gate
     * @param y the y-coordinate of the NOR gate
     */
    public NOR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
        initializePins();
    }

    /**
     * Initializes input and output pins of the NOR gate.
     * Two input pins are placed on the left side and
     * one output pin is placed on the right side after the small output circle.
     */
    @Override
    protected void initializePins() {
        // 2 input pins on the left
        inputPins.add(new Pin(x - 20, y + 10, Pin.PinType.INPUT, this));
        inputPins.add(new Pin(x - 20, y + height - 10, Pin.PinType.INPUT, this));

        // 1 output pin on the right (after the circle)
        int circleRadius = 8;
        outputPins.add(new Pin(x + width + circleRadius + 20, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    /**
     * Updates the positions of all pins when the component moves.
     */
    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x - 20, y + 10);
            inputPins.get(1).updatePosition(x - 20, y + height - 10);
            int circleRadius = 8;
            outputPins.get(0).updatePosition(x + width + circleRadius + 20, y + height / 2);
        }
    }

     /**
     * Computes the logical NOR of the two input pins
     * and sets the output pin value accordingly.
     */
    @Override
    public void compute() {
        // NOR logic: output = NOT (input1 OR input2)
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            boolean input1 = inputPins.get(0).getValue();
            boolean input2 = inputPins.get(1).getValue();
            boolean output = !(input1 || input2);
            outputPins.get(0).setValue(output);
        }
    }

    /**
     * Draws the NOR gate on the canvas.
     * The NOR gate is represented as an OR-shaped curve with a small circle at the output,
     * and lines for inputs and output.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        // Main OR curve
        g.drawArc(x, y, width, height, 270, 180);

        // Extra left curve
        g.drawArc(x - 10, y, width, height, 270, 180);

        // Input lines
        g.drawLine(x - 20, y + 10, x + 15, y + 10);
        g.drawLine(x - 20, y + height - 10, x + 15, y + height - 10);

        // Small circle at output
        int circleRadius = 8;
        g.drawOval(x + width, y + height / 2 - circleRadius / 2, circleRadius, circleRadius);

        // Output line
        g.drawLine(x + width + circleRadius, y + height / 2, x + width + circleRadius + 20, y + height / 2);

        // Selection
        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 50, height + 10);
            g.setColor(prev);
        }
    }
    
    /**
     * Creates and returns a deep copy of this NOR component.
     *
     * @return a new NOR component with the same position and size
     */
    @Override
    public Component cloneComponent() {
        NOR copy = new NOR(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
