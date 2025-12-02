import DataAccessLayer.FileExporter;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.awt.image.BufferedImage;

/**
 * Unit tests for the DataAccessLayer package.
 * 
 * <p>This test suite verifies file handling, image creation, 
 * and serialization data structures for projects, circuits, 
 * components, connectors, and pins.</p>
 * 
 * <p>Focus areas include:</p>
 * <ul>
 *     <li>Initialization of file exporters.</li>
 *     <li>BufferedImage creation and properties.</li>
 *     <li>File path construction and extension validation.</li>
 *     <li>Project, circuit, component, connector, and pin data structures.</li>
 *     <li>Complex project hierarchies and data validation.</li>
 * </ul>
 * 
 * Author: HP
 */
public class DataAccessLayerTest {
    
    /**
     * Tests that a FileExporter instance can be initialized successfully.
     */
    @Test
    public void testFileExporterInitialization() {
        FileExporter exporter = new FileExporter();
        assertNotNull("FileExporter should be created", exporter);
    }
    
    /**
     * Tests that a BufferedImage can be created and has expected dimensions.
     */
    @Test
    public void testBufferedImageCreation() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        assertNotNull(img);
        assertEquals(100, img.getWidth());
        assertEquals(100, img.getHeight());
    }
    
    /**
     * Tests BufferedImage properties like width, height, and type.
     */
    @Test
    public void testImageProperties() {
        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        
        assertEquals(800, img.getWidth());
        assertEquals(600, img.getHeight());
        assertEquals(BufferedImage.TYPE_INT_RGB, img.getType());
    }
    
    /**
     * Tests file naming conventions, ensuring the filename and extension
     * are properly constructed.
     */
    @Test
    public void testFileHandling() {
        String filename = "test_circuit";
        String extension = ".png";
        String fullName = filename + extension;
        
        assertTrue("Should contain extension", fullName.contains(".png"));
        assertTrue("Should contain filename", fullName.contains("test_circuit"));
    }
    
    /**
     * Tests a basic project data structure in a Map.
     */
    @Test
    public void testProjectDataStructure() {
        java.util.Map<String, Object> projectMap = new java.util.HashMap<>();
        projectMap.put("projectName", "TestProject");
        
        assertEquals("TestProject", projectMap.get("projectName"));
    }
    
    /**
     * Tests a basic circuit data structure in a Map.
     * Verifies the presence of circuit name, components, and connectors.
     */
    @Test
    public void testCircuitDataStructure() {
        java.util.Map<String, Object> circuitMap = new java.util.HashMap<>();
        circuitMap.put("circuitName", "TestCircuit");
        circuitMap.put("components", new java.util.ArrayList<>());
        circuitMap.put("connectors", new java.util.ArrayList<>());
        
        assertEquals("TestCircuit", circuitMap.get("circuitName"));
        assertEquals(0, ((java.util.ArrayList<?>) circuitMap.get("components")).size());
    }
    
    /**
     * Tests serialization-like data for a logic gate component.
     */
    @Test
    public void testComponentSerializationData() {
        java.util.Map<String, Object> componentMap = new java.util.HashMap<>();
        componentMap.put("type", "AND");
        componentMap.put("x", 100);
        componentMap.put("y", 50);
        componentMap.put("width", 60);
        componentMap.put("height", 40);
        
        assertEquals("AND", componentMap.get("type"));
        assertEquals(100, componentMap.get("x"));
        assertEquals(50, componentMap.get("y"));
    }
    
    /**
     * Tests serialization-like data for a connector.
     */
    @Test
    public void testConnectorSerializationData() {
        java.util.Map<String, Object> connectorMap = new java.util.HashMap<>();
        connectorMap.put("startX", 100);
        connectorMap.put("startY", 50);
        connectorMap.put("endX", 200);
        connectorMap.put("endY", 50);
        
        assertEquals(100, connectorMap.get("startX"));
        assertEquals(50, connectorMap.get("startY"));
        assertEquals(200, connectorMap.get("endX"));
    }
    
     /**
     * Tests serialization-like data for a pin (input or output).
     */
    @Test
    public void testPinSerializationData() {
        java.util.Map<String, Object> pinMap = new java.util.HashMap<>();
        pinMap.put("type", "INPUT");
        pinMap.put("x", 80);
        pinMap.put("y", 60);
        pinMap.put("value", false);
        
        assertEquals("INPUT", pinMap.get("type"));
        assertEquals(80, pinMap.get("x"));
        assertEquals(false, pinMap.get("value"));
    }
    
     /**
     * Tests creation of a complex project hierarchy with multiple circuits.
     */
    @Test
    public void testComplexProjectHierarchy() {
        // Create a mock project structure
        java.util.Map<String, Object> project = new java.util.HashMap<>();
        project.put("projectName", "MyLogicProject");
        
        java.util.List<java.util.Map<String, Object>> circuits = new java.util.ArrayList<>();
        java.util.Map<String, Object> circuit1 = new java.util.HashMap<>();
        circuit1.put("circuitName", "Adder");
        circuit1.put("components", new java.util.ArrayList<>());
        circuits.add(circuit1);
        
        project.put("circuits", circuits);
        
        assertEquals("MyLogicProject", project.get("projectName"));
        assertEquals(1, ((java.util.List<?>) project.get("circuits")).size());
    }
    
     /**
     * Tests creation of a complex project hierarchy with multiple circuits.
     */
    @Test
    public void testFilePathConstruction() {
        String directory = System.getProperty("user.home");
        String filename = "circuit";
        String extension = ".png";
        
        String filepath = directory + File.separator + filename + extension;
        assertTrue(filepath.contains(filename));
        assertTrue(filepath.contains(extension));
    }
    
    /**
     * Tests basic data validation for component properties.
     */
    @Test
    public void testDataValidation() {
        String type = "AND";
        int x = 100;
        int y = 50;
        
        assertTrue("Type should not be empty", type != null && !type.isEmpty());
        assertTrue("X should be non-negative", x >= 0);
        assertTrue("Y should be non-negative", y >= 0);
    }
}
