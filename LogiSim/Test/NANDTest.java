import BusinessLayer.NAND;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for the NAND gate component.
 * <p>
 * This class validates the core behavior of the {@link NAND} gate, including
 * pin initialization, logical computation (Not-AND), coordinate updates on movement,
 * instance cloning, and selection state management.
 * </p>
 */
public class NANDTest {

    /**
     * Verifies the initialization of input and output pins.
     * <p>
     * Checks that the NAND gate is created with exactly two input pins and one output pin.
     * </p>
     */
    @Test
    public void testPinsInitialization() {
        NAND nandGate = new NAND(100, 50);
        assertEquals(2, nandGate.getInputPins().size());
        assertEquals(1, nandGate.getOutputPins().size());
    }

    /**
     * Verifies the logical computation of the NAND gate.
     * <p>
     * Tests the truth table for NAND:
     * <ul>
     * <li>0 NAND 0 &rarr; 1 (True)</li>
     * <li>1 NAND 0 &rarr; 1 (True)</li>
     * <li>0 NAND 1 &rarr; 1 (True)</li>
     * <li>1 NAND 1 &rarr; 0 (False) - The only case resulting in False.</li>
     * </ul>
     * </p>
     */
    @Test
    public void testComputeLogic() {
        NAND nandGate = new NAND(100, 50);

        // 0 NAND 0 -> 1
        nandGate.getInputPins().get(0).setValue(false);
        nandGate.getInputPins().get(1).setValue(false);
        nandGate.compute();
        assertTrue(nandGate.getOutputPins().get(0).getValue());

        // 1 NAND 0 -> 1
        nandGate.getInputPins().get(0).setValue(true);
        nandGate.compute();
        assertTrue(nandGate.getOutputPins().get(0).getValue());

        // 1 NAND 1 -> 0
        nandGate.getInputPins().get(1).setValue(true);
        nandGate.compute();
        assertFalse(nandGate.getOutputPins().get(0).getValue());
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
        NAND nandGate = new NAND(100, 50);
        nandGate.move(200, 100);

        // Input pins
        assertEquals(180, nandGate.getInputPins().get(0).getX());
        assertEquals(110, nandGate.getInputPins().get(0).getY());

        assertEquals(180, nandGate.getInputPins().get(1).getX());
        assertEquals(130, nandGate.getInputPins().get(1).getY());

        // Output pin
        assertEquals(288, nandGate.getOutputPins().get(0).getX());
        assertEquals(120, nandGate.getOutputPins().get(0).getY());
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
        NAND nandGate = new NAND(100, 50);
        Component clone = nandGate.cloneComponent();
        assertNotSame(nandGate, clone);
        assertEquals(nandGate.getX(), clone.getX());
        assertEquals(nandGate.getY(), clone.getY());
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
        NAND nandGate = new NAND(100, 50);
        nandGate.setSelected(true);
        assertTrue(nandGate.isSelected());
        nandGate.setSelected(false);
        assertFalse(nandGate.isSelected());
    }
}