import BusinessLayer.Circuit;
import BusinessLayer.Component;
import BusinessLayer.Connector;
import BusinessLayer.AND;
import BusinessLayer.OR;
import BusinessLayer.NOT;
import BusinessLayer.Switch;
import BusinessLayer.LED;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Circuit class.
 * Tests circuit creation, component/connector management, and simulation.
 */
public class CircuitTest {
    
    private Circuit circuit;
    
    @Before
    public void setUp() {
        circuit = new Circuit("TestCircuit");
    }
    
    @Test
    public void testCircuitCreation() {
        Circuit emptyCircuit = new Circuit();
        assertEquals("", emptyCircuit.getCircuitName());
        assertEquals(0, emptyCircuit.getComponents().size());
        assertEquals(0, emptyCircuit.getConnectors().size());
    }
    
    @Test
    public void testCircuitWithName() {
        assertEquals("TestCircuit", circuit.getCircuitName());
    }
    
    @Test
    public void testSetCircuitName() {
        circuit.setCircuitName("NewName");
        assertEquals("NewName", circuit.getCircuitName());
    }
    
    @Test
    public void testAddComponent() {
        Component and = new AND(100, 50);
        circuit.addComponent(and);
        
        assertEquals(1, circuit.getComponents().size());
        assertTrue(circuit.getComponents().contains(and));
    }
    
    @Test
    public void testAddMultipleComponents() {
        Component and = new AND(100, 50);
        Component or = new OR(200, 50);
        Component not = new NOT(300, 50);
        
        circuit.addComponent(and);
        circuit.addComponent(or);
        circuit.addComponent(not);
        
        assertEquals(3, circuit.getComponents().size());
    }
    
    @Test
    public void testDuplicateComponentNotAdded() {
        Component and = new AND(100, 50);
        circuit.addComponent(and);
        circuit.addComponent(and); // Try to add same component
        
        assertEquals(1, circuit.getComponents().size());
    }
    
    @Test
    public void testRemoveComponent() {
        Component and = new AND(100, 50);
        circuit.addComponent(and);
        assertEquals(1, circuit.getComponents().size());
        
        circuit.removeComponent(and);
        assertEquals(0, circuit.getComponents().size());
    }
    
    @Test
    public void testAddConnector() {
        Connector wire = new Connector(0, 0);
        circuit.addConnector(wire);
        
        assertEquals(1, circuit.getConnectors().size());
        assertTrue(circuit.getConnectors().contains(wire));
    }
    
    @Test
    public void testAddMultipleConnectors() {
        Connector wire1 = new Connector(0, 0);
        Connector wire2 = new Connector(50, 50);
        
        circuit.addConnector(wire1);
        circuit.addConnector(wire2);
        
        assertEquals(2, circuit.getConnectors().size());
    }
    
    @Test
    public void testDuplicateConnectorNotAdded() {
        Connector wire = new Connector(0, 0);
        circuit.addConnector(wire);
        circuit.addConnector(wire); // Try to add same wire
        
        assertEquals(1, circuit.getConnectors().size());
    }
    
    @Test
    public void testRemoveConnector() {
        Connector wire = new Connector(0, 0);
        circuit.addConnector(wire);
        assertEquals(1, circuit.getConnectors().size());
        
        circuit.removeConnector(wire);
        assertEquals(0, circuit.getConnectors().size());
    }
    
    @Test
    public void testSetComponents() {
        java.util.ArrayList<Component> components = new java.util.ArrayList<>();
        components.add(new AND(100, 50));
        components.add(new OR(200, 50));
        
        circuit.setComponents(components);
        assertEquals(2, circuit.getComponents().size());
    }
    
    @Test
    public void testSetConnectors() {
        java.util.ArrayList<Connector> connectors = new java.util.ArrayList<>();
        connectors.add(new Connector(0, 0));
        connectors.add(new Connector(50, 50));
        
        circuit.setConnectors(connectors);
        assertEquals(2, circuit.getConnectors().size());
    }
    
    @Test
    public void testSimpleCircuitSimulation() {
        // Create: Switch -> AND gate, with both inputs true
        Switch sw1 = new Switch(0, 0);
        Switch sw2 = new Switch(50, 0);
        AND and = new AND(100, 0);
        
        circuit.addComponent(sw1);
        circuit.addComponent(sw2);
        circuit.addComponent(and);
        
        // Connect switches to AND inputs
        Connector w1 = new Connector(sw1.getOutputPins().get(0).getX(), sw1.getOutputPins().get(0).getY());
        Connector w2 = new Connector(sw2.getOutputPins().get(0).getX(), sw2.getOutputPins().get(0).getY());
        
        w1.connectToPin(sw1.getOutputPins().get(0), true);
        w1.connectToPin(and.getInputPins().get(0), false);
        
        w2.connectToPin(sw2.getOutputPins().get(0), true);
        w2.connectToPin(and.getInputPins().get(1), false);
        
        circuit.addConnector(w1);
        circuit.addConnector(w2);
        
        // Set switch states
        sw1.setState(true);
        sw2.setState(true);
        
        // Simulate
        circuit.updateCircuit();
        
        // Verify AND output is true
        assertTrue(and.getOutputPins().get(0).getValue());
    }
    
    @Test
    public void testCircuitWithNOTGate() {
        Switch sw = new Switch(0, 0);
        NOT not = new NOT(50, 0);
        LED led = new LED(100, 0);
        
        circuit.addComponent(sw);
        circuit.addComponent(not);
        circuit.addComponent(led);
        
        // Connect: Switch -> NOT -> LED
        Connector w1 = new Connector(sw.getOutputPins().get(0).getX(), sw.getOutputPins().get(0).getY());
        Connector w2 = new Connector(not.getOutputPins().get(0).getX(), not.getOutputPins().get(0).getY());
        
        w1.connectToPin(sw.getOutputPins().get(0), true);
        w1.connectToPin(not.getInputPins().get(0), false);
        
        w2.connectToPin(not.getOutputPins().get(0), true);
        w2.connectToPin(led.getInputPins().get(0), false);
        
        circuit.addConnector(w1);
        circuit.addConnector(w2);
        
        // Test with switch OFF
        sw.setState(false);
        circuit.updateCircuit();
        assertTrue("LED should be ON when switch is OFF", led.getState());
        
        // Test with switch ON
        sw.setState(true);
        circuit.updateCircuit();
        assertFalse("LED should be OFF when switch is ON", led.getState());
    }
    
    @Test
    public void testORGateCircuit() {
        Switch sw1 = new Switch(0, 0);
        Switch sw2 = new Switch(50, 0);
        OR or = new OR(100, 0);
        
        circuit.addComponent(sw1);
        circuit.addComponent(sw2);
        circuit.addComponent(or);
        
        Connector w1 = new Connector(sw1.getOutputPins().get(0).getX(), sw1.getOutputPins().get(0).getY());
        Connector w2 = new Connector(sw2.getOutputPins().get(0).getX(), sw2.getOutputPins().get(0).getY());
        
        w1.connectToPin(sw1.getOutputPins().get(0), true);
        w1.connectToPin(or.getInputPins().get(0), false);
        
        w2.connectToPin(sw2.getOutputPins().get(0), true);
        w2.connectToPin(or.getInputPins().get(1), false);
        
        circuit.addConnector(w1);
        circuit.addConnector(w2);
        
        // Test: 0 OR 0 = 0
        sw1.setState(false);
        sw2.setState(false);
        circuit.updateCircuit();
        assertFalse(or.getOutputPins().get(0).getValue());
        
        // Test: 0 OR 1 = 1
        sw1.setState(false);
        sw2.setState(true);
        circuit.updateCircuit();
        assertTrue(or.getOutputPins().get(0).getValue());
    }
    
    @Test
    public void testRemoveConnectorDisconnectsPins() {
        Switch sw = new Switch(0, 0);
        AND and = new AND(50, 0);
        Connector w = new Connector(sw.getOutputPins().get(0).getX(), sw.getOutputPins().get(0).getY());
        
        circuit.addComponent(sw);
        circuit.addComponent(and);
        circuit.addConnector(w);
        
        w.connectToPin(sw.getOutputPins().get(0), true);
        w.connectToPin(and.getInputPins().get(0), false);
        
        assertTrue(sw.getOutputPins().get(0).isConnected());
        assertTrue(and.getInputPins().get(0).isConnected());
        
        circuit.removeConnector(w);
        
        assertFalse(sw.getOutputPins().get(0).isConnected());
        assertFalse(and.getInputPins().get(0).isConnected());
    }
}
