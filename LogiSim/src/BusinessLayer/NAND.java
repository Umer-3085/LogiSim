package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

/**
 * NAND gate component used in the digital logic simulator.
 * This component has two input pins and one output pin.
 * The output is HIGH except when both inputs are HIGH (output = NOT(input1 AND input2)).
 * 
 * @author HP
 */
public class NAND extends Component {

     /**
     * Constructs a NAND gate at the given position.
     *
     * @param x the x-coordinate of the NAND gate
     * @param y the y-coordinate of the NAND gate
     */
    public NAND(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
        initializePins();
    }

    /**
     * Initializes input and output pins of the NAND gate.
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
     * Computes the logical NAND of the two input pins
     * and sets the output pin value accordingly.
     */
    @Override
    public void compute() {
        // NAND logic: output = NOT (input1 AND input2)
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            boolean input1 = inputPins.get(0).getValue();
            boolean input2 = inputPins.get(1).getValue();
            boolean output = !(input1 && input2);
            outputPins.get(0).setValue(output);
        }
    }

    /**
     * Draws the NAND gate on the canvas.
     * The NAND gate is represented as a rectangle on the left with a semicircle on the right,
     * a small output circle, and lines for inputs and output.
     *
     * @param g the Graphics object used for rendering
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);

        int rectWidth = width / 2;

        // Left rectangle
        g.drawRect(x, y, rectWidth, height);

        // Right semicircle
        g.drawArc(x + rectWidth - 1, y, width - rectWidth, height, -90, 180);

        // Input lines
        g.drawLine(x - 20, y + 10, x, y + 10);
        g.drawLine(x - 20, y + height - 10, x, y + height - 10);

        // Small circle at output
        int circleRadius = 8;
        g.drawOval(x + width, y + height / 2 - circleRadius / 2, circleRadius, circleRadius);

        // Output line
        g.drawLine(x + width + circleRadius, y + height / 2, x + width + circleRadius + 20, y + height / 2);

        if (selected) {
            Color prev = g.getColor();
            g.setColor(Color.BLUE);
            g.drawRect(x - 25, y - 5, width + 50, height + 10);
            g.setColor(prev);
        }
    }
    
    /**
     * Creates and returns a deep copy of this NAND component.
     *
     * @return a new NAND component with the same position and size
     */
    @Override
    public Component cloneComponent() {
        NAND copy = new NAND(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
