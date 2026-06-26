import BusinessLayer.Circuit;
import BusinessLayer.TruthTableGenerator;
import BusinessLayer.Switch;
import BusinessLayer.LED;
import BusinessLayer.AND;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Unit tests for the {@link TruthTableGenerator} class.
 * <p>
 * This test suite verifies the initialization, labeling,
 * and truth table generation functionalities of the truth table generator.
 * It ensures correct handling of circuits with different numbers of inputs
 * and outputs, including boundary cases with no components.
 * </p>
 *
 * Usage example:
 * <pre>
 *     Circuit circuit = new Circuit("Test");
 *     TruthTableGenerator gen = new TruthTableGenerator(circuit);
 *     Object[][] table = gen.generateTruthTable();
 * </pre>
 */
public class TruthTableGeneratorTest {
    
    private Circuit circuit;
    private TruthTableGenerator generator;
    
    /**
     * Initializes a fresh circuit before each test case.
     */
    @Before
    public void setUp() {
        circuit = new Circuit("Truth Table Test");
    }
    
    /**
     * Tests that the truth table generator can be created
     * successfully for an empty circuit.
     */
    @Test
    public void testGeneratorInitialization() {
        generator = new TruthTableGenerator(circuit);
        assertNotNull("Generator should be created", generator);
    }
    
    /**
     * Tests whether input labels (A, B, C, ...) are correctly generated
     * based on the number and order of input switches.
     */
    @Test
    public void testGetInputLabels() {
        Switch switch1 = new Switch(50, 50);
        Switch switch2 = new Switch(50, 100);
        circuit.addComponent(switch1);
        circuit.addComponent(switch2);
        
        generator = new TruthTableGenerator(circuit);
        List<String> inputLabels = generator.getInputLabels();
        
        assertEquals("Should have 2 input labels", 2, inputLabels.size());
        assertEquals("First label should be 'A'", "A", inputLabels.get(0));
        assertEquals("Second label should be 'B'", "B", inputLabels.get(1));
    }
    
    /**
     * Tests whether output labels (Y1, Y2, ...) are correctly generated
     * based on the number and ordering of LED components.
     */
    @Test
    public void testGetOutputLabels() {
        LED led1 = new LED(300, 50);
        LED led2 = new LED(300, 100);
        circuit.addComponent(led1);
        circuit.addComponent(led2);
        
        generator = new TruthTableGenerator(circuit);
        List<String> outputLabels = generator.getOutputLabels();
        
        assertEquals("Should have 2 output labels", 2, outputLabels.size());
        assertEquals("First label should be 'Y1'", "Y1", outputLabels.get(0));
        assertEquals("Second label should be 'Y2'", "Y2", outputLabels.get(1));
    }
    
    /**
     * Tests truth table generation for a circuit containing no inputs or outputs.
     * The result should be a single row (2^0) with zero columns.
     */
    @Test
    public void testGenerateTruthTableWithNoComponents() {
        generator = new TruthTableGenerator(circuit);
        Object[][] table = generator.generateTruthTable();
        
        assertEquals("Truth table should have 1 row (2^0)", 1, table.length);
        assertEquals("Truth table should have 0 columns", 0, table[0].length);
    }
    
    /**
     * Tests truth table generation for a circuit with one input switch
     * and one LED output. Validates correct number of rows (2^1)
     * and columns (1 input + 1 output).
     */
    @Test
    public void testGenerateTruthTableWithOneInput() {
        Switch switch1 = new Switch(50, 50);
        LED led1 = new LED(300, 50);
        circuit.addComponent(switch1);
        circuit.addComponent(led1);
        
        generator = new TruthTableGenerator(circuit);
        Object[][] table = generator.generateTruthTable();
        
        assertEquals("Truth table should have 2 rows (2^1)", 2, table.length);
        assertEquals("Each row should have 2 columns (1 input + 1 output)", 2, table[0].length);
    }
}
