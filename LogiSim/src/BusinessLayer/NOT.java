package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;
/**
 * NOT gate (Inverter) component used in the digital logic simulator.
 * This component has one input pin and one output pin.
 * The output is the logical negation of the input (output = NOT input).
 * 
 * @author HP
 */
public class NOT extends Component {

    /**
     * Constructs a NOT gate at the given position.
     *
     * @param x the x-coordinate of the NOT gate
     * @param y the y-coordinate of the NOT gate
     */
    public NOT(int x, int y) {
        super(x, y);
        this.width = 50;
        this.height = 40;
        initializePins();
    }

    /**
     * Initializes input and output pins of the NOT gate.
     * One input pin is placed on the left side and
     * one output pin is placed on the right side after the small output circle.
     */
    @Override
    protected void initializePins() {
        // 1 input pin on the left
        inputPins.add(new Pin(x - 20, y + height / 2, Pin.PinType.INPUT, this));

        // 1 output pin on the right (after the circle)
        int circleRadius = 8;
        outputPins.add(new Pin(x + width - 10 + circleRadius + 20, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    /**
     * Updates the positions of all pins when the component moves.
     */
    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 1 && outputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x - 20, y + height / 2);
            int circleRadius = 8;
            outputPins.get(0).updatePosition(x + width - 10 + circleRadius + 20, y + height / 2);
        }
    }

    /**
     * Computes the logical NOT of the input pin
     * and sets the output pin value accordingly.
     */
    @Override
    public void compute() {
        // NOT logic: output = NOT input
        if (inputPins.size() >= 1 && outputPins.size() >= 1) {
            boolean input = inputPins.get(0).getValue();
            boolean output = !input;
            outputPins.get(0).setValue(output);
        }
    }

     /**
     * Draws the NOT gate on the canvas.
     * The NOT gate is represented as a triangle with a small circle at the output,
     * and lines for input and output connections.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        // Triangle
        g.drawLine(x, y, x, y + height);
        g.drawLine(x, y, x + width - 10, y + height / 2);
        g.drawLine(x, y + height, x + width - 10, y + height / 2);

        // Small circle
        int circleRadius = 8;
        g.drawOval(x + width - 10, y + height / 2 - circleRadius / 2, circleRadius, circleRadius);

        // Input line
        g.drawLine(x - 20, y + height / 2, x, y + height / 2);

        // Output line
        g.drawLine(x + width - 10 + circleRadius, y + height / 2, x + width - 10 + circleRadius + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 40, height + 10);
            g.setColor(prev);
        }
    }
    
     /**
     * Creates and returns a deep copy of this NOT component.
     *
     * @return a new NOT component with the same position and size
     */
    @Override
    public Component cloneComponent() {
        NOT copy = new NOT(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
