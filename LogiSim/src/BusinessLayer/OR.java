package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class OR extends Component {

    public OR(int x, int y) {
        super(x, y);
        this.width = 60;
        this.height = 40;
        initializePins();
    }

    @Override
    protected void initializePins() {
        // 2 input pins on the left
        inputPins.add(new Pin(x - 15, y + 10, Pin.PinType.INPUT, this));
        inputPins.add(new Pin(x - 15, y + height - 10, Pin.PinType.INPUT, this));

        // 1 output pin on the right
        outputPins.add(new Pin(x + width + 20, y + height / 2, Pin.PinType.OUTPUT, this));
    }

    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 2 && outputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x - 15, y + 10);
            inputPins.get(1).updatePosition(x - 15, y + height - 10);
            outputPins.get(0).updatePosition(x + width + 20, y + height / 2);
        }
    }

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
    
    @Override
    public Component cloneComponent() {
        OR copy = new OR(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        return copy;
    }
}
