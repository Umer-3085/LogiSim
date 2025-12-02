/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import java.util.*;

import DataAccessLayer.*;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Project {
    
    private ArrayList<Circuit> circuits;
    private String currentCircuit = "";
    private static String name = "";
    Storage data = new DataStorage();
    
    public Project(){
        circuits = new ArrayList<Circuit>();
        circuits.add(new Circuit("Circuit 1"));
        currentCircuit = "Circuit 1";
    }
    
    public void createNewCircuit(){
        circuits.add(new Circuit("Circuit 1"));
        currentCircuit = "Circuit 1";
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public ArrayList<Circuit> getcircuits(){
        return circuits;
    }
    
    public void setcircuits(ArrayList<Circuit> c){
        circuits = c;
    }
    
    public String getCurrentCircuitName(){
        return this.getLastCircuit().getCircuitName();
    }
    
    public void setCurrentCircuitName(String name){
        this.currentCircuit = name;
    }
    
    public Circuit getCurrentCircuit(){
        
        Circuit acircuit = new Circuit();
        
        for( Circuit c : circuits ){
            if( c.getCircuitName().equals(currentCircuit) ){
                acircuit = c;
            }
        }
        
        return acircuit;
    }
    
    public Circuit getLastCircuit(){
        if( this.getcircuits() != null )
        return this.getcircuits().get(this.getcircuits().size()-1);
        return null;
    }
    
    public Circuit getCircuitByName(String name) {
        for (Circuit c : circuits) {
            if (c.getCircuitName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    
    public void mergeCircuits(Circuit source, Circuit target) {
        if (source == target) {
            throw new IllegalArgumentException("Cannot merge a circuit into itself!");
        }

        // Map to track original -> cloned components
        Map<Component, Component> componentMap = new HashMap<>();

        // 1️⃣ Clone all components safely
        for (Component comp : new ArrayList<>(source.getComponents())) {
            Component cloned = comp.cloneComponent();        // clone component
            cloned.move(comp.getX() + 100, comp.getY() + 100); // offset so they don't overlap
            target.addComponent(cloned);                      // add to target circuit
            componentMap.put(comp, cloned);                  // map old -> new
        }

        // 2️⃣ Clone all connectors safely
        for (Connector oldConn : new ArrayList<>(source.getConnectors())) {
            Component oldSource = oldConn.getSourcePin().getOwner();
            Component oldTarget = oldConn.getTargetPin().getOwner();

            Component newSource = componentMap.get(oldSource);
            Component newTarget = componentMap.get(oldTarget);

            if (newSource == null || newTarget == null) continue; // skip if source/target missing

            // Find the same pins in the cloned components
            int srcPinIndex = oldSource.getOutputPins().indexOf(oldConn.getSourcePin());
            int tgtPinIndex = oldTarget.getInputPins().indexOf(oldConn.getTargetPin());

            Pin newSrcPin = newSource.getOutputPins().get(srcPinIndex);
            Pin newTgtPin = newTarget.getInputPins().get(tgtPinIndex);

            Connector newConn = oldConn.cloneConnector(newSrcPin, newTgtPin);

            target.addConnector(newConn);  // safe add
        }
    }

    
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
    
    public ArrayList<String> getProjectNames(){
        return data.projectNames();
    }
    
    public void removeCircuit(String project , String circuit) {
        if ( data.remove(project,circuit) ){
            JOptionPane.showConfirmDialog(null, "Circuit deleted successfully");
        }
    }

}
