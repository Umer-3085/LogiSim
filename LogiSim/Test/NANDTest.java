import BusinessLayer.NAND;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

public class NANDTest {

    @Test
    public void testPinsInitialization() {
        NAND nandGate = new NAND(100, 50);
        assertEquals(2, nandGate.getInputPins().size());
        assertEquals(1, nandGate.getOutputPins().size());
    }

    @Test
    public void testComputeLogic() {
        NAND nandGate = new NAND(100, 50);

        nandGate.getInputPins().get(0).setValue(false);
        nandGate.getInputPins().get(1).setValue(false);
        nandGate.compute();
        assertTrue(nandGate.getOutputPins().get(0).getValue());

        nandGate.getInputPins().get(0).setValue(true);
        nandGate.compute();
        assertTrue(nandGate.getOutputPins().get(0).getValue());

        nandGate.getInputPins().get(1).setValue(true);
        nandGate.compute();
        assertFalse(nandGate.getOutputPins().get(0).getValue());
    }

    @Test
    public void testMoveAndUpdatePins() {
        NAND nandGate = new NAND(100, 50);
        nandGate.move(200, 100);

        assertEquals(180, nandGate.getInputPins().get(0).getX());
        assertEquals(110, nandGate.getInputPins().get(0).getY());

        assertEquals(180, nandGate.getInputPins().get(1).getX());
        assertEquals(130, nandGate.getInputPins().get(1).getY());

        assertEquals(288, nandGate.getOutputPins().get(0).getX());
        assertEquals(120, nandGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testCloneComponent() {
        NAND nandGate = new NAND(100, 50);
        Component clone = nandGate.cloneComponent();
        assertNotSame(nandGate, clone);
        assertEquals(nandGate.getX(), clone.getX());
        assertEquals(nandGate.getY(), clone.getY());
    }

    @Test
    public void testSelection() {
        NAND nandGate = new NAND(100, 50);
        nandGate.setSelected(true);
        assertTrue(nandGate.isSelected());
        nandGate.setSelected(false);
        assertFalse(nandGate.isSelected());
    }
}
