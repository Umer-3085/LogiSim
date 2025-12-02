package BusinessLayer;

import java.awt.Graphics;
import java.awt.Color;

public class LED extends Component {
    private boolean state; // true = ON (1), false = OFF (0)

    public LED(int x, int y) {
        super(x, y);
        this.width = 20; // narrower for vertical look
        this.height = 40; // taller
        this.state = false;
        initializePins();
    }

    @Override
    protected void initializePins() {
        // LED has only 1 input pin (it's a sink component)
        inputPins.add(new Pin(x + width / 2, y - 20, Pin.PinType.INPUT, this));
    }

    @Override
    protected void updatePinPositions() {
        if (inputPins.size() >= 1) {
            inputPins.get(0).updatePosition(x + width / 2, y - 20);
        }
    }

    @Override
    public void compute() {
        // LED state = its input value
        if (inputPins.size() >= 1) {
            state = inputPins.get(0).getValue();
        }
    }
    
    @Override
    public Component cloneComponent() {
        LED copy = new LED(this.x, this.y);
        copy.width = this.width;
        copy.height = this.height;
        copy.state = this.state;
        return copy;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

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
