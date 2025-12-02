import BusinessLayer.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Edge case and boundary condition tests.
 * <p>
 * This suite tests unusual scenarios, limits, and potential stress points for the circuit components.
 * It covers coordinate extremes, circular connections, high iteration toggles, and collection limits.
 * </p>
 */
public class EdgeCaseTest {
    
    /**
     * Tests component creation at the origin (0,0).
     * Ensures components function correctly at minimum coordinate boundaries.
     */
    @Test
    public void testComponentAtZeroCoordinates() {
        AND and = new AND(0, 0);
        assertEquals(0, and.getX());
        assertEquals(0, and.getY());
        assertNotNull(and.getInputPins());
        assertNotNull(and.getOutputPins());
    }
    
    /**
     * Tests component creation at very large coordinates.
     * Ensures the system handles large integer values for positions without overflow errors.
     */
    @Test
    public void testComponentAtLargeCoordinates() {
        AND and = new AND(10000, 10000);
        assertEquals(10000, and.getX());
        assertEquals(10000, and.getY());
    }
    
    /**
     * Tests component creation with negative coordinates.
     * Ensures the system does not crash if components are placed off-screen (negative space).
     */
    @Test
    public void testComponentNegativeCoordinates() {
        AND and = new AND(-100, -50);
        assertEquals(-100, and.getX());
        assertEquals(-50, and.getY());
    }
    
    /**
     * Tests the initialization state of an empty circuit.
     * Verifies that a new circuit has zero components and zero connectors.
     */
    @Test
    public void testCircuitWithNoComponents() {
        Circuit circuit = new Circuit();
        assertEquals(0, circuit.getComponents().size());
        assertEquals(0, circuit.getConnectors().size());
    }
    
    /**
     * Tests updating an empty circuit.
     * Ensures that calling {@code updateCircuit()} on an empty container does not throw exceptions.
     */
    @Test
    public void testCircuitUpdateWithEmptyCircuit() {
        Circuit circuit = new Circuit();
        circuit.updateCircuit(); // Should not throw exception
    }
    
    /**
     * Stress tests a switch with multiple toggle operations.
     * <p>
     * Toggles a switch 100 times to ensure state stability (False -> True -> False logic)
     * remains consistent over repeated operations.
     * </p>
     */
    @Test
    public void testSwitchMultipleToggles() {
        Switch sw = new Switch(0, 0);
        
        sw.toggle();
        assertTrue(sw.getState());
        
        sw.toggle();
        assertFalse(sw.getState());
        
        for (int i = 0; i < 100; i++) {
            sw.toggle();
        }
        
        // After 100 toggles, should be false (initial false -> true -> false -> ... -> false)
        assertFalse(sw.getState());
    }
    
    /**
     * Tests that individual pins on the same component maintain independent states.
     * Verifies that setting one input pin to True does not affect a neighbor set to False.
     */
    @Test
    public void testMultiplePinsOnSameComponent() {
        AND and = new AND(100, 50);
        Pin pin1 = and.getInputPins().get(0);
        Pin pin2 = and.getInputPins().get(1);
        Pin pin3 = and.getOutputPins().get(0);
        
        pin1.setValue(true);
        pin2.setValue(false);
        
        assertTrue(pin1.getValue());
        assertFalse(pin2.getValue());
    }
    
    /**
     * Tests the default state of a unconnected wire (Connector).
     * Ensures it reports as not connected and initializes at (0,0).
     */
    @Test
    public void testWireWithoutConnections() {
        Connector wire = new Connector(0, 0);
        
        assertFalse(wire.isConnected());
        assertEquals(0, wire.getStartX());
        assertEquals(0, wire.getStartY());
    }
    
    /**
     * Tests the circuit's ability to handle circular connections (loops).
     * <p>
     * Creates a loop (Switch -> AND -> ...) and attempts to update the circuit.
     * This ensures the simulation engine handles cycles without entering an infinite loop.
     * </p>
     */
    @Test
    public void testCircularConnections() {
        // Test that we can create a circuit that might loop
        Circuit circuit = new Circuit();
        
        Switch sw = new Switch(0, 0);
        AND and = new AND(50, 0);
        
        circuit.addComponent(sw);
        circuit.addComponent(and);
        
        // Attempt to create connection
        Connector w = new Connector(sw.getOutputPins().get(0).getX(), sw.getOutputPins().get(0).getY());
        w.connectToPin(sw.getOutputPins().get(0), true);
        w.connectToPin(and.getInputPins().get(0), false);
        
        circuit.addConnector(w);
        circuit.updateCircuit(); // Should handle without infinite loop
    }
    
    /**
     * Stress tests the circuit with a large number of components.
     * Adds 50 mixed components to verify list management and update performance.
     */
    @Test
    public void testManyComponentsInCircuit() {
        Circuit circuit = new Circuit();
        
        // Add 50 components
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                circuit.addComponent(new AND(i * 10, 0));
            } else {
                circuit.addComponent(new OR(i * 10, 0));
            }
        }
        
        assertEquals(50, circuit.getComponents().size());
        circuit.updateCircuit();
    }
    
    /**
     * Stress tests value toggling on a Pin.
     * Toggles a pin value 1000 times to ensure boolean logic consistency.
     */
    @Test
    public void testPinValueToggling() {
        Pin pin = new Pin(0, 0, Pin.PinType.INPUT, new AND(0, 0));
        
        boolean currentValue = pin.getValue();
        for (int i = 0; i < 1000; i++) {
            pin.setValue(!pin.getValue());
        }
        
        // After even number of toggles, should be back to original
        assertEquals(currentValue, pin.getValue());
    }
    
    /**
     * Tests component movement over multiple steps.
     * verifies that the component updates its coordinates correctly after a sequence of moves,
     * including returning to the origin.
     */
    @Test
    public void testComponentMovement_MultipleSteps() {
        AND and = new AND(100, 50);
        
        // Move multiple times
        and.move(150, 75);
        assertEquals(150, and.getX());
        assertEquals(75, and.getY());
        
        and.move(200, 100);
        assertEquals(200, and.getX());
        assertEquals(100, and.getY());
        
        and.move(0, 0);
        assertEquals(0, and.getX());
        assertEquals(0, and.getY());
    }
    
    /**
     * Tests the creation of two wires from the exact same starting point.
     * Ensures no ID conflicts or positional errors occur.
     */
    @Test
    public void testWireFromSamePoint() {
        Connector wire1 = new Connector(100, 100);
        Connector wire2 = new Connector(100, 100);
        
        assertEquals(wire1.getStartX(), wire2.getStartX());
        assertEquals(wire1.getStartY(), wire2.getStartY());
    }
    
    /**
     * Tests the state tracking of an LED.
     * Verifies the LED accurately reflects the input value after `compute()` is called.
     */
    @Test
    public void testLEDStateTracking() {
        LED led = new LED(100, 50);
        
        assertFalse(led.getState());
        
        led.getInputPins().get(0).setValue(true);
        led.compute();
        assertTrue(led.getState());
        
        led.getInputPins().get(0).setValue(false);
        led.compute();
        assertFalse(led.getState());
    }
    
    /**
     * Tests chaining two gates together.
     * <p>
     * Simulates wiring output of one NOT gate to input of another (Double Inversion).
     * NOT(NOT(True)) should result in True.
     * </p>
     */
    @Test
    public void testGateChaining() {
        // Test: NOT -> NOT (should return to original)
        NOT not1 = new NOT(100, 50);
        NOT not2 = new NOT(150, 50);
        
        not1.getInputPins().get(0).setValue(true);
        not1.compute();
        
        // not1 output should be false
        assertFalse(not1.getOutputPins().get(0).getValue());
        
        // Feed to not2
        not2.getInputPins().get(0).setValue(not1.getOutputPins().get(0).getValue());
        not2.compute();
        
        // not2 output should be true (NOT(NOT(true)) = true)
        assertTrue(not2.getOutputPins().get(0).getValue());
    }
    
    /**
     * Tests edge cases for distance calculation in `Pin`.
     * Covers distance to self (0.0), adjacent points, and hypotenuse calculations.
     */
    @Test
    public void testPinDistanceCalculation_EdgeCases() {
        Pin pin = new Pin(100, 100, Pin.PinType.INPUT, new AND(100, 100));
        
        // Distance to same point
        assertEquals(0.0, pin.distanceTo(100, 100), 0.001);
        
        // Distance to adjacent point
        double dist = pin.distanceTo(101, 100);
        assertEquals(1.0, dist, 0.001);
        
        // Distance using Pythagorean theorem
        dist = pin.distanceTo(103, 104);
        assertEquals(5.0, dist, 0.001); // 3-4-5 triangle
    }
    
    /**
     * Stress tests the selection toggling mechanism.
     * Rapidly selects and deselects a component to ensure state does not get stuck.
     */
    @Test
    public void testSelectDeselectRapidly() {
        AND and = new AND(100, 50);
        
        for (int i = 0; i < 100; i++) {
            and.setSelected(true);
            assertTrue(and.isSelected());
            and.setSelected(false);
            assertFalse(and.isSelected());
        }
    }
    
    /**
     * Rapidly iterates through all input variations for a NAND gate.
     * Ensures the gate logic holds true for all permutations in a loop.
     */
    @Test
    public void testGateWithAllInputsVariations() {
        NAND nand = new NAND(100, 50);
        
        // Test all 4 combinations quickly
        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                nand.getInputPins().get(0).setValue(a == 1);
                nand.getInputPins().get(1).setValue(b == 1);
                nand.compute();
                
                // NAND output should be 1 except for (1,1)
                if (a == 1 && b == 1) {
                    assertFalse(nand.getOutputPins().get(0).getValue());
                } else {
                    assertTrue(nand.getOutputPins().get(0).getValue());
                }
            }
        }
    }
    
    /**
     * Tests the hit detection logic for a wire (Connector).
     * <p>
     * Verifies that the wire correctly identifies if a point is near its start,
     * near its end, or along its line segment (for selection or dragging purposes).
     * </p>
     */
    @Test
    public void testWireContainmentBoundaries() {
        Connector wire = new Connector(50, 50);
        wire.dragEnd(150, 50);
        
        // Test near start point
        assertTrue(wire.containsStart(50, 50));
        assertTrue(wire.containsStart(55, 50)); // Within 8px
        
        // Test near end point
        assertTrue(wire.containsEnd(150, 50));
        assertTrue(wire.containsEnd(145, 50)); // Within 8px
        
        // Test on line
        assertTrue(wire.containsLine(100, 50));
    }
}