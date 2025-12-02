import BusinessLayer.NOR;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

public class NORTest {

    @Test
    public void testPinsInitialization() {
        NOR norGate = new NOR(100, 50);
        assertEquals(2, norGate.getInputPins().size());
        assertEquals(1, norGate.getOutputPins().size());
    }

    @Test
    public void testComputeLogic() {
        NOR norGate = new NOR(100, 50);

        norGate.getInputPins().get(0).setValue(false);
        norGate.getInputPins().get(1).setValue(false);
        norGate.compute();
        assertTrue(norGate.getOutputPins().get(0).getValue());

        norGate.getInputPins().get(0).setValue(true);
        norGate.compute();
        assertFalse(norGate.getOutputPins().get(0).getValue());

        norGate.getInputPins().get(1).setValue(true);
        norGate.compute();
        assertFalse(norGate.getOutputPins().get(0).getValue());
    }

    @Test
    public void testMoveAndUpdatePins() {
        NOR norGate = new NOR(100, 50);
        norGate.move(200, 100);

        assertEquals(180, norGate.getInputPins().get(0).getX());
        assertEquals(110, norGate.getInputPins().get(0).getY());

        assertEquals(180, norGate.getInputPins().get(1).getX());
        assertEquals(130, norGate.getInputPins().get(1).getY());

        assertEquals(288, norGate.getOutputPins().get(0).getX());
        assertEquals(120, norGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testCloneComponent() {
        NOR norGate = new NOR(100, 50);
        Component clone = norGate.cloneComponent();
        assertNotSame(norGate, clone);
        assertEquals(norGate.getX(), clone.getX());
        assertEquals(norGate.getY(), clone.getY());
    }

    @Test
    public void testSelection() {
        NOR norGate = new NOR(100, 50);
        norGate.setSelected(true);
        assertTrue(norGate.isSelected());
        norGate.setSelected(false);
        assertFalse(norGate.isSelected());
    }
}
