import BusinessLayer.NOT;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the NOT gate component.
 * <p>
 * This class validates the core behavior of the {@link NOT} gate, including
 * pin initialization, logical computation (inversion), coordinate updates on movement,
 * instance cloning, and selection state management.
 * </p>
 */
public class NOTTest {

    /**
     * Verifies the initialization of input and output pins.
     * <p>
     * Checks that the NOT gate is created with exactly one input pin and one output pin.
     * It also asserts that the pins are placed at the correct (x, y) coordinates
     * relative to the component's position.
     * </p>
     */
    @Test
    public void testPinsInitialization() {
        NOT notGate = new NOT(100, 50);

        // Pin counts
        assertEquals(1, notGate.getInputPins().size());
        assertEquals(1, notGate.getOutputPins().size());

        // Input pin position
        assertEquals(80, notGate.getInputPins().get(0).getX());
        assertEquals(70, notGate.getInputPins().get(0).getY());

        // Output pin position
        assertEquals(168, notGate.getOutputPins().get(0).getX());
        assertEquals(70, notGate.getOutputPins().get(0).getY());
    }

    /**
     * Verifies the logical computation of the NOT gate.
     * <p>
     * Tests the truth table:
     * <ul>
     * <li>Input False (0) &rarr; Output True (1)</li>
     * <li>Input True (1) &rarr; Output False (0)</li>
     * </ul>
     * </p>
     */
    @Test
    public void testComputeLogic() {
        NOT notGate = new NOT(100, 50);

        // NOT 0 → 1
        notGate.getInputPins().get(0).setValue(false);
        notGate.compute();
        assertTrue("NOT(false) should be true", notGate.getOutputPins().get(0).getValue());

        // NOT 1 → 0
        notGate.getInputPins().get(0).setValue(true);
        notGate.compute();
        assertFalse("NOT(true) should be false", notGate.getOutputPins().get(0).getValue());
    }

    /**
     * Verifies that pins update their positions when the component is moved.
     * <p>
     * Moves the component to a new coordinate (200, 100) and asserts that
     * the input and output pins have shifted to the correct new locations.
     * </p>
     */
    @Test
    public void testMoveAndUpdatePins() {
        NOT notGate = new NOT(100, 50);
        notGate.move(200, 100);

        // Updated input pin position
        assertEquals(180, notGate.getInputPins().get(0).getX());
        assertEquals(120, notGate.getInputPins().get(0).getY());

        // Updated output pin position
        assertEquals(268, notGate.getOutputPins().get(0).getX());
        assertEquals(120, notGate.getOutputPins().get(0).getY());
    }

    /**
     * Verifies the cloning functionality of the component.
     * <p>
     * Ensures that the cloned object is a new instance (not the same reference)
     * but retains the same state (coordinates) as the original component.
     * </p>
     */
    @Test
    public void testCloneComponent() {
        NOT notGate = new NOT(100, 50);
        Component clone = notGate.cloneComponent();

        assertNotSame("Clone should be a distinct instance", notGate, clone);
        assertEquals(notGate.getX(), clone.getX());
        assertEquals(notGate.getY(), clone.getY());
    }

    /**
     * Verifies the selection state toggling.
     * <p>
     * Tests setting the selection state to true and false, ensuring the
     * component reports the correct status via {@code isSelected()}.
     * </p>
     */
    @Test
    public void testSelection() {
        NOT notGate = new NOT(100, 50);

        notGate.setSelected(true);
        assertTrue(notGate.isSelected());

        notGate.setSelected(false);
        assertFalse(notGate.isSelected());
    }
}