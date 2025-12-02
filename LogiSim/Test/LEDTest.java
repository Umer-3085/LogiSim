import BusinessLayer.LED;
import org.junit.Test;
import static org.junit.Assert.*;

public class LEDTest {

    @Test
    public void testPinsInitialization() {
        LED led = new LED(100, 50);
        assertEquals(1, led.getInputPins().size());
        assertEquals(110, led.getInputPins().get(0).getX());
        assertEquals(30, led.getInputPins().get(0).getY());
    }

    @Test
    public void testComputeLogic() {
        LED led = new LED(100, 50);

        led.getInputPins().get(0).setValue(false);
        led.compute();
        assertFalse(led.getState());

        led.getInputPins().get(0).setValue(true);
        led.compute();
        assertTrue(led.getState());
    }

    @Test
    public void testCloneComponent() {
        LED led = new LED(100, 50);
        led.setState(true);
        LED clone = (LED) led.cloneComponent();
        assertNotSame(led, clone);
        assertEquals(led.getX(), clone.getX());
        assertEquals(led.getY(), clone.getY());
        assertEquals(led.getState(), clone.getState());
    }

    @Test
    public void testSelection() {
        LED led = new LED(100, 50);
        led.setSelected(true);
        assertTrue(led.isSelected());
        led.setSelected(false);
        assertFalse(led.isSelected());
    }
}
