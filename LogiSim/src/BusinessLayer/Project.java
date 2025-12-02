/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.util.*;

import DataAccessLayer.*;
import javax.swing.JOptionPane;

/**
 * Represents a digital project consisting of multiple circuits.
 * Each project has a name, contains multiple circuits, and can load/save its state
 * using a persistent data storage (via the {@link Storage} interface).
 * 
 * Supports operations such as creating new circuits, merging circuits, retrieving
 * components, and managing connectors.
 * 
 * This class acts as the main container in the simulator for all circuits in a project.
 * 
 * @author HP
 */
public class Project {
    
    private ArrayList<Circuit> circuits;
    private String currentCircuit = "";
    private static String name = "";
    Storage data = new DataStorage();
    
    /**
     * Constructs a new Project with a default initial circuit ("Circuit 1").
     */
    public Project(){
        circuits = new ArrayList<Circuit>();
        circuits.add(new Circuit("Circuit 1"));
        currentCircuit = "Circuit 1";
    }
    
     /**
     * Creates a new circuit named "Circuit 1" and sets it as the current circuit.
     */
    public void createNewCircuit(){
        circuits.add(new Circuit("Circuit 1"));
        currentCircuit = "Circuit 1";
    }
    
    /**
     * Gets the project name.
     * 
     * @return the name of the project
     */
    public String getName(){
        return name;
    }
    
     /**
     * Sets the project name.
     * 
     * @param name the new project name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Gets the list of circuits in the project.
     * 
     * @return list of circuits
     */
    public ArrayList<Circuit> getcircuits(){
        return circuits;
    }
    
    /**
     * Sets the list of circuits in the project.
     * 
     * @param c list of circuits to set
     */
    public void setcircuits(ArrayList<Circuit> c){
        circuits = c;
    }
    
    /**
     * Gets the name of the current circuit.
     * 
     * @return current circuit name
     */
    public String getCurrentCircuitName(){
        return this.getLastCircuit().getCircuitName();
    }
    
    /**
     * Sets the current circuit name.
     * 
     * @param name the circuit name to set as current
     */
    public void setCurrentCircuitName(String name){
        this.currentCircuit = name;
    }
    
     /**
     * Gets the currently selected circuit.
     * 
     * @return current {@link Circuit} object
     */
    public Circuit getCurrentCircuit(){
        
        Circuit acircuit = new Circuit();
        
        for( Circuit c : circuits ){
            if( c.getCircuitName().equals(currentCircuit) ){
                acircuit = c;
            }
        }
        
        return acircuit;
    }
    
    /**
     * Gets the last circuit added to the project.
     * 
     * @return last {@link Circuit}, or null if no circuits exist
     */
    public Circuit getLastCircuit(){
        if( this.getcircuits() != null )
        return this.getcircuits().get(this.getcircuits().size()-1);
        return null;
    }
    
    /**
     * Retrieves a circuit by its name.
     * 
     * @param name the name of the circuit to retrieve
     * @return the {@link Circuit} with the given name, or null if not found
     */
    public Circuit getCircuitByName(String name) {
        for (Circuit c : circuits) {
            if (c.getCircuitName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    
    /**
     * Merges components and connectors from a source circuit into a target circuit.
     * Components are cloned and offset to avoid overlap. Connectors are updated
     * to point to cloned components' pins.
     * 
     * @param source the source circuit to merge from
     * @param target the target circuit to merge into
     * @throws IllegalArgumentException if source and target are the same circuit
     */
    public void mergeCircuits(Circuit source, Circuit target) {
        if (source == target) {
            throw new IllegalArgumentException("Cannot merge a circuit into itself!");
        }

        Map<Component, Component> componentMap = new HashMap<>();

        for (Component comp : new ArrayList<>(source.getComponents())) {
            Component cloned = comp.cloneComponent();        // clone component
            cloned.move(comp.getX() + 100, comp.getY() + 100); // offset so they don't overlap
            target.addComponent(cloned);                      // add to target circuit
            componentMap.put(comp, cloned);                  // map old -> new
        }

        for (Connector oldConn : new ArrayList<>(source.getConnectors())) {
            Component oldSource = oldConn.getSourcePin().getOwner();
            Component oldTarget = oldConn.getTargetPin().getOwner();

            Component newSource = componentMap.get(oldSource);
            Component newTarget = componentMap.get(oldTarget);

            if (newSource == null || newTarget == null) continue; 
            
            int srcPinIndex = oldSource.getOutputPins().indexOf(oldConn.getSourcePin());
            int tgtPinIndex = oldTarget.getInputPins().indexOf(oldConn.getTargetPin());

            Pin newSrcPin = newSource.getOutputPins().get(srcPinIndex);
            Pin newTgtPin = newTarget.getInputPins().get(tgtPinIndex);

            Connector newConn = oldConn.cloneConnector(newSrcPin, newTgtPin);

            target.addConnector(newConn);  // safe add
        }
    }

    /**
     * Loads a project from persistent storage by its name.
     * Rebuilds all circuits, components, pins, and connectors from stored data.
     * 
     * @param projectname the name of the project to load
     */
    public void loadProject(String projectname) {
        
        Map<String,Object> aproject = data.loadAProject(projectname);
        this.setName(aproject.get("projectName").toString());
        this.circuits.clear();
        this.setName(projectname);
        
        List<Map<String,Object>> circuitsList =
            (List<Map<String,Object>>) aproject.get("circuits");
        
        for (Map<String,Object> circuitMap : circuitsList) {

            String cname = (String) circuitMap.get("circuitName");
            Circuit circuit = new Circuit(cname);

            List<Map<String,Object>> componentsList =
                    (List<Map<String,Object>>) circuitMap.get("components");
            List<Map<String,Object>> connectorsList =
                    (List<Map<String,Object>>) circuitMap.get("connectors");
            
            ArrayList<Component> rebuiltComponents = new ArrayList<>();
            for (Map<String,Object> compMap : componentsList) {

                String type = (String) compMap.get("type");
                int x = (int) compMap.get("x");
                int y = (int) compMap.get("y");
                int w = (int) compMap.get("width");
                int h = (int) compMap.get("height");

                Component comp = null;

                switch(type) {
                    case "AND": comp = new AND(x,y); break;
                    case "OR":  comp = new OR(x,y); break;
                    case "NOT": comp = new NOT(x,y); break;
                    case "XOR": comp = new XOR(x,y); break;
                    case "NAND": comp = new NAND(x,y); break;
                    case "NOR": comp = new NOR(x,y); break;

                    case "Switch":
                        comp = new Switch(x,y);
                        ((Switch)comp).setState((boolean) compMap.get("state"));
                        break;

                    case "LED":
                        comp = new LED(x,y);
                        ((LED)comp).setState((boolean) compMap.get("state"));
                        break;
                }

                List<Map<String,Object>> inPins =
                    (List<Map<String,Object>>) compMap.get("inputPins");

                if (inPins != null && comp.getInputPins() != null) {
                    int count = Math.min(inPins.size(), comp.getInputPins().size());

                    for (int i = 0; i < count; i++) {
                        Map<String,Object> p = inPins.get(i);

                        int val = (int) p.get("value");
                        comp.getInputPins().get(i).setValue(val == 1);
                    }
                }

                List<Map<String,Object>> outPins =
                    (List<Map<String,Object>>) compMap.get("outputPins");

                if (outPins != null && comp.getOutputPins() != null) {
                    int count = Math.min(outPins.size(), comp.getOutputPins().size());

                    for (int i = 0; i < count; i++) {
                        Map<String,Object> p = outPins.get(i);

                        int val = (int) p.get("value");
                        comp.getOutputPins().get(i).setValue(val == 1);
                    }
                }


                circuit.addComponent(comp);
                rebuiltComponents.add(comp);
            }

            for (Map<String,Object> connMap : connectorsList) {

                int srcCompIndex = (int) connMap.get("sourceComponentIndex");
                int srcPinIndex  = (int) connMap.get("sourcePinIndex");
                int tgtCompIndex = (int) connMap.get("targetComponentIndex");
                int tgtPinIndex  = (int) connMap.get("targetPinIndex");

                int sx = (int) connMap.get("startX");
                int sy = (int) connMap.get("startY");
                int ex = (int) connMap.get("endX");
                int ey = (int) connMap.get("endY");

                boolean val = (boolean) connMap.get("value");

                Component srcComp = rebuiltComponents.get(srcCompIndex);
                Component tgtComp = rebuiltComponents.get(tgtCompIndex);

                Pin srcPin = srcComp.getOutputPins().get(srcPinIndex);
                Pin tgtPin = tgtComp.getInputPins().get(tgtPinIndex);
                
                Connector conn = new Connector(sx, sy);

                conn.dragEnd(ex, ey);

                conn.connectToPin(srcPin, true);  
                conn.connectToPin(tgtPin, false);
                
                conn.setValue(val);

                
                conn.updateConnectedEndpoints();

                circuit.addConnector(conn);
            }
            
            this.circuits.add(circuit);
        
        }
        
        this.currentCircuit = this.getLastCircuit().getCircuitName();

        System.out.println("Project loaded completely using generic Map structure!");
        
    }

    /**
     * Saves the current project data to persistent storage.
     * Includes all circuits, components, pins, and connectors.
     */
    public void saveData() {
            
        Map<String, Object> projectMap = new HashMap<>();

        projectMap.put("projectName", name);

        List<Map<String, Object>> circuitsList = new ArrayList<>();

        for (Circuit circuit : this.getcircuits()) {
            Map<String, Object> circuitMap = new HashMap<>();
            circuitMap.put("circuitName", circuit.getCircuitName());

            List<Map<String, Object>> componentsList = new ArrayList<>();
            List<Map<String, Object>> connectorsList = new ArrayList<>();

            for (Component comp : circuit.getComponents()) {
                Map<String, Object> compMap = new HashMap<>();
                compMap.put("type", comp.getClass().getSimpleName());
                compMap.put("x", comp.getX());
                compMap.put("y", comp.getY());
                compMap.put("width", comp.getWidth());
                compMap.put("height", comp.getHeight());

                if (comp instanceof Switch) {
                    compMap.put("state", ((Switch) comp).getState());
                } else if (comp instanceof LED) {
                    compMap.put("state", ((LED) comp).getState());
                }

                List<Map<String, Object>> inputPinsList = new ArrayList<>();
                for (Pin pin : comp.getInputPins()) {
                    Map<String, Object> pinMap = new HashMap<>();
                    pinMap.put("x", pin.getX());
                    pinMap.put("y", pin.getY());
                    pinMap.put("type", pin.getType().toString());
                    pinMap.put("value", pin.getValue());
                    inputPinsList.add(pinMap);
                }
                compMap.put("inputPins", inputPinsList);

                List<Map<String, Object>> outputPinsList = new ArrayList<>();
                for (Pin pin : comp.getOutputPins()) {
                    Map<String, Object> pinMap = new HashMap<>();
                    pinMap.put("x", pin.getX());
                    pinMap.put("y", pin.getY());
                    pinMap.put("type", pin.getType().toString());
                    pinMap.put("value", pin.getValue());
                    outputPinsList.add(pinMap);
                }
                compMap.put("outputPins", outputPinsList);

                componentsList.add(compMap);
            }


            for (Connector conn : circuit.getConnectors()) {
                Map<String, Object> connMap = new HashMap<>();

                Pin sourcePin = conn.getSourcePin();
                Pin targetPin = conn.getTargetPin();

                connMap.put("sourceComponentIndex", circuit.getComponents().indexOf(sourcePin.getOwner()));
                connMap.put("sourcePinIndex", sourcePin.getOwner().getOutputPins().indexOf(sourcePin));
                connMap.put("targetComponentIndex", circuit.getComponents().indexOf(targetPin.getOwner()));
                connMap.put("targetPinIndex", targetPin.getOwner().getInputPins().indexOf(targetPin));
                connMap.put("value", conn.getValue());

                // Visual coordinates (for drawing)
                connMap.put("startX", conn.getStartX());
                connMap.put("startY", conn.getStartY());
                connMap.put("endX", conn.getEndX());
                connMap.put("endY", conn.getEndY());

                connectorsList.add(connMap);
            }

            circuitMap.put("components", componentsList);
            circuitMap.put("connectors", connectorsList);
            circuitsList.add(circuitMap);
        }

        projectMap.put("circuits", circuitsList);

        if( data.savePorjects(projectMap) ){
            JOptionPane.showConfirmDialog(null, "Project saved successfully");
        }

    }
    
     /**
     * Gets a list of all project names from storage.
     * 
     * @return list of project names
     */
    public ArrayList<String> getProjectNames(){
        return data.projectNames();
    }
    
    /**
     * Deletes a circuit from a project in storage.
     * 
     * @param project the project name
     * @param circuit the circuit name to delete
     */
    public void removeCircuit(String project , String circuit) {
        if ( data.remove(project,circuit) ){
            JOptionPane.showConfirmDialog(null, "Circuit deleted successfully");
        }
    }

}
