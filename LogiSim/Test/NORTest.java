import BusinessLayer.NOR;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the NOR gate component.
 * <p>
 * This class validates the core behavior of the {@link NOR} gate, including
 * pin initialization, logical computation (Not-OR), coordinate updates on movement,
 * instance cloning, and selection state management.
 * </p>
 */
public class NORTest {

    /**
     * Verifies the initialization of input and output pins.
     * <p>
     * Checks that the NOR gate is created with exactly two input pins and one output pin.
     * </p>
     */
    @Test
    public void testPinsInitialization() {
        NOR norGate = new NOR(100, 50);
        assertEquals(2, norGate.getInputPins().size());
        assertEquals(1, norGate.getOutputPins().size());
    }

    /**
     * Verifies the logical computation of the NOR gate.
     * <p>
     * Tests the truth table for NOR (True only if all inputs are False):
     * <ul>
     * <li>0 NOR 0 &rarr; 1 (True)</li>
     * <li>1 NOR 0 &rarr; 0 (False)</li>
     * <li>1 NOR 1 &rarr; 0 (False)</li>
     * </ul>
     * </p>
     */
    @Test
    public void testComputeLogic() {
        NOR norGate = new NOR(100, 50);

        // 0 NOR 0 -> 1 (True)
        norGate.getInputPins().get(0).setValue(false);
        norGate.getInputPins().get(1).setValue(false);
        norGate.compute();
        assertTrue(norGate.getOutputPins().get(0).getValue());

        // 1 NOR 0 -> 0 (False)
        norGate.getInputPins().get(0).setValue(true);
        norGate.compute();
        assertFalse(norGate.getOutputPins().get(0).getValue());

        // 1 NOR 1 -> 0 (False)
        norGate.getInputPins().get(1).setValue(true);
        norGate.compute();
        assertFalse(norGate.getOutputPins().get(0).getValue());
    }

    /**
     * Verifies that pins update their positions when the component is moved.
     * <p>
     * Moves the component to (200, 100) and asserts that both input pins and
     * the output pin have shifted to the correct relative locations.
     * </p>
     */
    @Test
    public void testMoveAndUpdatePins() {
        NOR norGate = new NOR(100, 50);
        norGate.move(200, 100);

        // Input pins
        assertEquals(180, norGate.getInputPins().get(0).getX());
        assertEquals(110, norGate.getInputPins().get(0).getY());

        assertEquals(180, norGate.getInputPins().get(1).getX());
        assertEquals(130, norGate.getInputPins().get(1).getY());

        // Output pin
        assertEquals(288, norGate.getOutputPins().get(0).getX());
        assertEquals(120, norGate.getOutputPins().get(0).getY());
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
        NOR norGate = new NOR(100, 50);
        Component clone = norGate.cloneComponent();
        assertNotSame(norGate, clone);
        assertEquals(norGate.getX(), clone.getX());
        assertEquals(norGate.getY(), clone.getY());
    }

    /**
     * Verifies the selection state toggling.
     * <p>
     * Tests setting the selection state to true and false, ensuring the
     * component reports the correct status.
     * </p>
     */
    @Test
    public void testSelection() {
        NOR norGate = new NOR(100, 50);
        norGate.setSelected(true);
        assertTrue(norGate.isSelected());
        norGate.setSelected(false);
        assertFalse(norGate.isSelected());
    }
}