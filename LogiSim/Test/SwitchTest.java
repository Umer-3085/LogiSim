import BusinessLayer.Switch;
import org.junit.Test;
import static org.junit.Assert.*;

public class SwitchTest {

    @Test
    public void testPinsInitialization() {
        Switch sw = new Switch(100, 50);
        assertEquals(1, sw.getOutputPins().size());
        assertEquals(150, sw.getOutputPins().get(0).getX());
        assertEquals(60, sw.getOutputPins().get(0).getY());
    }

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

    @Test
    public void testSelection() {
        Switch sw = new Switch(100, 50);
        sw.setSelected(true);
        assertTrue(sw.isSelected());
        sw.setSelected(false);
        assertFalse(sw.isSelected());
    }
}
