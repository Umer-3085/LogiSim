import BusinessLayer.Switch;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the {@link Switch} component.
 * <p>
 * This test class verifies correct initialization of pins, logic behavior,
 * cloning functionality, and selection handling for the Switch component.
 * The Switch represents a manual input source in the circuit, providing
 * a single output pin whose value depends on the switch's ON/OFF state.
 * </p>
 *
 * Usage example:
 * <pre>
 *     Switch sw = new Switch(100, 50);
 *     sw.setState(true);
 *     boolean out = sw.getOutputPins().get(0).getValue();
 * </pre>
 */
public class SwitchTest {

    /**
     * Tests that the switch initializes with correct pin configuration.
     * Ensures exactly one output pin is created and positioned properly
     * relative to the switch's x and y coordinates.
     */
    @Test
    public void testPinsInitialization() {
        Switch sw = new Switch(100, 50);
        assertEquals(1, sw.getOutputPins().size());
        assertEquals(150, sw.getOutputPins().get(0).getX());
        assertEquals(60, sw.getOutputPins().get(0).getY());
    }

    /**
     * Tests the logic behavior of the switch.
     * Validates that changing the switch state and toggling
     * correctly updates the output pin's value.
     */
    @Test
    public void testComputeLogicAndState() {
        Switch sw = new Switch(100, 50);
        sw.setState(false);
        assertFalse(sw.getOutputPins().get(0).getValue());

        sw.setState(true);
        assertTrue(sw.getOutputPins().get(0).getValue());

        sw.toggle();
        assertFalse(sw.getOutputPins().get(0).getValue());
    }

    /**
     * Tests cloning functionality of the switch component.
     * Ensures the cloned object is a separate instance with identical
     * position and state properties.
     */
    @Test
    public void testCloneComponent() {
        Switch sw = new Switch(100, 50);
        sw.setState(true);
        Switch clone = (Switch) sw.cloneComponent();

        assertNotSame(sw, clone);
        assertEquals(sw.getX(), clone.getX());
        assertEquals(sw.getY(), clone.getY());
        assertEquals(sw.getState(), clone.getState());
    }

    /**
     * Tests selection behavior of the switch.
     * Ensures the component correctly tracks its selected status.
     */
    @Test
    public void testSelection() {
        Switch sw = new Switch(100, 50);

        sw.setSelected(true);
        assertTrue(sw.isSelected());

        sw.setSelected(false);
        assertFalse(sw.isSelected());
    }
}
