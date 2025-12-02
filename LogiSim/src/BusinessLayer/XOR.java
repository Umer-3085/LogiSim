package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class XOR extends Component {

    public XOR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
        initializePins();
    }

    @Override
    protected void initializePins() {
        // 2 input pins on the left
        inputPins.add(new Pin(x - 20, y + 10, Pin.PinType.INPUT, this));
        inputPins.add(new Pin(x - 20, y + height - 10, Pin.PinType.INPUT, this));

        // 1 output pin on the right
        outputPins.add(new Pin(x + width + 20, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x - 20, y + 10);
            inputPins.get(1).updatePosition(x - 20, y + height - 10);
            outputPins.get(0).updatePosition(x + width + 20, y + height / 2);
        }
    }

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
    
    @Override
    public Component cloneComponent() {
        XOR copy = new XOR(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
