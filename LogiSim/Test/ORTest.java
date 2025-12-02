import BusinessLayer.OR;
import BusinessLayer.Component;
import org.junit.Test;
import static org.junit.Assert.*;

public class ORTest {

    @Test
    public void testPinsInitialization() {
        OR orGate = new OR(100, 50);
        assertEquals(2, orGate.getInputPins().size());
        assertEquals(1, orGate.getOutputPins().size());

        assertEquals(85, orGate.getInputPins().get(0).getX());
        assertEquals(60, orGate.getInputPins().get(0).getY());

        assertEquals(85, orGate.getInputPins().get(1).getX());
        assertEquals(80, orGate.getInputPins().get(1).getY());

        assertEquals(180, orGate.getOutputPins().get(0).getX());
        assertEquals(70, orGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testComputeLogic() {
        OR orGate = new OR(100, 50);

        orGate.getInputPins().get(0).setValue(false);
        orGate.getInputPins().get(1).setValue(false);
        orGate.compute();
        assertFalse(orGate.getOutputPins().get(0).getValue());

        orGate.getInputPins().get(0).setValue(true);
        orGate.compute();
        assertTrue(orGate.getOutputPins().get(0).getValue());

        orGate.getInputPins().get(1).setValue(true);
        orGate.compute();
        assertTrue(orGate.getOutputPins().get(0).getValue());
    }

    @Test
    public void testMoveAndUpdatePins() {
        OR orGate = new OR(100, 50);
        orGate.move(200, 100);

        assertEquals(185, orGate.getInputPins().get(0).getX());
        assertEquals(110, orGate.getInputPins().get(0).getY());

        assertEquals(185, orGate.getInputPins().get(1).getX());
        assertEquals(130, orGate.getInputPins().get(1).getY());

        assertEquals(280, orGate.getOutputPins().get(0).getX());
        assertEquals(120, orGate.getOutputPins().get(0).getY());
    }

    @Test
    public void testCloneComponent() {
        OR orGate = new OR(100, 50);
        Component clone = orGate.cloneComponent();
        assertNotSame(orGate, clone);
        assertEquals(orGate.getX(), clone.getX());
        assertEquals(orGate.getY(), clone.getY());
        assertEquals(orGate.getInputPins().size(), clone.getInputPins().size());
        assertEquals(orGate.getOutputPins().size(), clone.getOutputPins().size());
    }

    @Test
    public void testSelection() {
        OR orGate = new OR(100, 50);
        orGate.setSelected(true);
        assertTrue(orGate.isSelected());
        orGate.setSelected(false);
        assertFalse(orGate.isSelected());
    }
}
