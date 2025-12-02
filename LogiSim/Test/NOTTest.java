import BusinessLayer.NOT;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

public class NOTTest {

    @Test
    public void testPinsInitialization() {
        NOT notGate = new NOT(100, 50);
        assertEquals(1, notGate.getInputPins().size());
        assertEquals(1, notGate.getOutputPins().size());

        assertEquals(80, notGate.getInputPins().get(0).getX());
        assertEquals(70, notGate.getInputPins().get(0).getY());

        assertEquals(168, notGate.getOutputPins().get(0).getX());
        assertEquals(70, notGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testComputeLogic() {
        NOT notGate = new NOT(100, 50);

        notGate.getInputPins().get(0).setValue(false);
        notGate.compute();
        assertTrue(notGate.getOutputPins().get(0).getValue());

        notGate.getInputPins().get(0).setValue(true);
        notGate.compute();
        assertFalse(notGate.getOutputPins().get(0).getValue());
    }

    @Test
    public void testMoveAndUpdatePins() {
        NOT notGate = new NOT(100, 50);
        notGate.move(200, 100);

        assertEquals(180, notGate.getInputPins().get(0).getX());
        assertEquals(120, notGate.getInputPins().get(0).getY());

        assertEquals(268, notGate.getOutputPins().get(0).getX());
        assertEquals(120, notGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testCloneComponent() {
        NOT notGate = new NOT(100, 50);
        Component clone = notGate.cloneComponent();
        assertNotSame(notGate, clone);
        assertEquals(notGate.getX(), clone.getX());
        assertEquals(notGate.getY(), clone.getY());
    }

    @Test
    public void testSelection() {
        NOT notGate = new NOT(100, 50);
        notGate.setSelected(true);
        assertTrue(notGate.isSelected());
        notGate.setSelected(false);
        assertFalse(notGate.isSelected());
    }
}
