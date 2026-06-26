import BusinessLayer.OR;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the OR gate component.
 * <p>
 * This class validates the core behavior of the {@link OR} gate, including
 * pin initialization for multiple inputs, logical computation, coordinate updates,
 * cloning behavior, and selection state.
 * </p>
 */
public class ORTest {

    /**
     * Verifies the initialization of input and output pins.
     * <p>
     * Checks that the OR gate is created with exactly two input pins and one output pin.
     * It also asserts that all pins are placed at the correct (x, y) coordinates
     * relative to the component's position.
     * </p>
     */
    @Test
    public void testPinsInitialization() {
        OR orGate = new OR(100, 50);

        // Pin counts
        assertEquals(2, orGate.getInputPins().size());
        assertEquals(1, orGate.getOutputPins().size());

        // Input pin positions
        assertEquals(85, orGate.getInputPins().get(0).getX());
        assertEquals(60, orGate.getInputPins().get(0).getY());

        assertEquals(85, orGate.getInputPins().get(1).getX());
        assertEquals(80, orGate.getInputPins().get(1).getY());

        // Output pin position
        assertEquals(180, orGate.getOutputPins().get(0).getX());
        assertEquals(70, orGate.getOutputPins().get(0).getY());
    }

    /**
     * Verifies the logical computation of the OR gate.
     * <p>
     * Tests the standard truth table for an OR gate:
     * <ul>
     * <li>0 OR 0 &rarr; 0 (False)</li>
     * <li>1 OR 0 &rarr; 1 (True)</li>
     * <li>0 OR 1 &rarr; 1 (True)</li>
     * <li>1 OR 1 &rarr; 1 (True)</li>
     * </ul>
     * </p>
     */
    @Test
    public void testComputeLogic() {
        OR orGate = new OR(100, 50);

        // 0 OR 0 → 0
        orGate.getInputPins().get(0).setValue(false);
        orGate.getInputPins().get(1).setValue(false);
        orGate.compute();
        assertFalse("0 OR 0 should be false", orGate.getOutputPins().get(0).getValue());

        // 1 OR 0 → 1
        orGate.getInputPins().get(0).setValue(true);
        orGate.compute();
        assertTrue("1 OR 0 should be true", orGate.getOutputPins().get(0).getValue());

        // 1 OR 1 → 1
        orGate.getInputPins().get(1).setValue(true);
        orGate.compute();
        assertTrue("1 OR 1 should be true", orGate.getOutputPins().get(0).getValue());
    }

    /**
     * Verifies that all pins update their positions when the component is moved.
     * <p>
     * Moves the component to (200, 100) and ensures both input pins and the output pin
     * move to their expected relative locations.
     * </p>
     */
    @Test
    public void testMoveAndUpdatePins() {
        OR orGate = new OR(100, 50);
        orGate.move(200, 100);

        // Updated input pin positions
        assertEquals(185, orGate.getInputPins().get(0).getX());
        assertEquals(110, orGate.getInputPins().get(0).getY());

        assertEquals(185, orGate.getInputPins().get(1).getX());
        assertEquals(130, orGate.getInputPins().get(1).getY());

        // Updated output pin position
        assertEquals(280, orGate.getOutputPins().get(0).getX());
        assertEquals(120, orGate.getOutputPins().get(0).getY());
    }

    /**
     * Verifies the cloning functionality of the component.
     * <p>
     * Ensures that the clone is a new instance, retains the same position coordinates,
     * and has the same number of input/output pins as the original.
     * </p>
     */
    @Test
    public void testCloneComponent() {
        OR orGate = new OR(100, 50);
        Component clone = orGate.cloneComponent();

        assertNotSame("Clone should be a new instance", orGate, clone);
        assertEquals(orGate.getX(), clone.getX());
        assertEquals(orGate.getY(), clone.getY());
        assertEquals(orGate.getInputPins().size(), clone.getInputPins().size());
        assertEquals(orGate.getOutputPins().size(), clone.getOutputPins().size());
    }

    /**
     * Verifies the selection state toggling.
     * <p>
     * Tests that the component correctly reports its selection state after being
     * programmatically selected and deselected.
     * </p>
     */
    @Test
    public void testSelection() {
        OR orGate = new OR(100, 50);

        orGate.setSelected(true);
        assertTrue(orGate.isSelected());

        orGate.setSelected(false);
        assertFalse(orGate.isSelected());
    }
}