import BusinessLayer.LED;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the LED component.
 * <p>
 * This class validates the behavior of the {@link LED}, which acts as an output indicator.
 * Tests cover pin initialization, state updates based on input, cloning, and selection.
 * </p>
 */
public class LEDTest {

    /**
     * Verifies the initialization of the LED pins.
     * <p>
     * Checks that the LED has exactly one input pin and asserts its default position relative to the LED.
     * </p>
     */
    @Test
    public void testPinsInitialization() {
        LED led = new LED(100, 50);
        assertEquals(1, led.getInputPins().size());
        assertEquals(110, led.getInputPins().get(0).getX());
        assertEquals(30, led.getInputPins().get(0).getY());
    }

    /**
     * Verifies the state update logic of the LED.
     * <p>
     * Ensures that the internal state of the LED matches the value received at its input pin.
     * </p>
     */
    @Test
    public void testComputeLogic() {
        LED led = new LED(100, 50);

        // Input False -> State False
        led.getInputPins().get(0).setValue(false);
        led.compute();
        assertFalse(led.getState());

        // Input True -> State True
        led.getInputPins().get(0).setValue(true);
        led.compute();
        assertTrue(led.getState());
    }

    /**
     * Verifies the cloning functionality of the LED.
     * <p>
     * Ensures that the clone is a deep copy regarding attributes like state and position,
     * but remains a distinct object instance.
     * </p>
     */
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

    /**
     * Verifies the selection state toggling.
     * <p>
     * Tests the ability to select and deselect the LED component.
     * </p>
     */
    @Test
    public void testSelection() {
        LED led = new LED(100, 50);
        led.setSelected(true);
        assertTrue(led.isSelected());
        led.setSelected(false);
        assertFalse(led.isSelected());
    }
}