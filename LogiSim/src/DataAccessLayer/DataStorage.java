/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author HP
 */
public class DataStorage implements Storage{
    
    @Override
    public boolean resetPorjects(){
        
        int count1 = 0, count2 = 0;
        try {
            
            Connection conn = getConnection();
            String query1 = "Truncate table connectors";
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            count1 = stmt1.executeUpdate();
            String query2 = "Truncate table components";
            PreparedStatement stmt2 = conn.prepareStatement(query2);
            count2 = stmt2.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        if( count1 > 0 && count2 > 0 ){
            return true;
        }
        return false;
    }
        
    @Override
    public boolean savePorjects(Map<String, Object> projectMap){
//        try {
//            
//            Connection conn = getConnection();
//                
//            String projectName = (String) projectMap.get("projectName");
//            List<Map<String, Object>> circuitsList = (List<Map<String, Object>>) projectMap.get("circuits");
//
//            for (Map<String, Object> circuitMap : circuitsList) {
//                String circuitName = (String) circuitMap.get("circuitName");
//
//                // Components
//                List<Map<String, Object>> componentsList = (List<Map<String, Object>>) circuitMap.get("components");
//                int compIndex = 0;
//                for (Map<String, Object> compMap : componentsList) {
//                    String type = (String) compMap.get("type");
//                    int x = (int) compMap.get("x");
//                    int y = (int) compMap.get("y");
//                    int width = (int) compMap.get("width");
//                    int height = (int) compMap.get("height");
//                    String state = compMap.get("state") != null ? compMap.get("state").toString() : "";
//
//                    // Input pins as simple string
//                    List<Map<String, Object>> inputPinsList = (List<Map<String, Object>>) compMap.get("inputPins");
//                    StringBuilder inputPins = new StringBuilder();
//                    for (int i = 0; i < inputPinsList.size(); i++) {
//                        Map<String, Object> pin = inputPinsList.get(i);
//                        inputPins.append(pin.get("x")).append(",")
//                                 .append(pin.get("y")).append(",")
//                                 .append(pin.get("type")).append(",")
//                                 .append(pin.get("value"));
//                        if (i < inputPinsList.size() - 1) inputPins.append("-");
//                    }
//
//                    List<Map<String, Object>> outputPinsList = (List<Map<String, Object>>) compMap.get("outputPins");
//                    StringBuilder outputPins = new StringBuilder();
//                    for (int i = 0; i < outputPinsList.size(); i++) {
//                        Map<String, Object> pin = outputPinsList.get(i);
//                        outputPins.append(pin.get("x")).append(",")
//                                  .append(pin.get("y")).append(",")
//                                  .append(pin.get("type")).append(",")
//                                  .append(pin.get("value"));
//                        if (i < outputPinsList.size() - 1) outputPins.append("-");
//                    }
//
//                    String compSql = "INSERT INTO components " +
//                            "(projectname, circuitname, component_index, type, x, y, width, height, state, input_pins, output_pins) " +
//                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//                    try (PreparedStatement pst = conn.prepareStatement(compSql)) {
//                        pst.setString(1, projectName);
//                        pst.setString(2, circuitName);
//                        pst.setInt(3, compIndex);
//                        pst.setString(4, type);
//                        pst.setInt(5, x);
//                        pst.setInt(6, y);
//                        pst.setInt(7, width);
//                        pst.setInt(8, height);
//                        pst.setString(9, state);
//                        pst.setString(10, inputPins.toString());
//                        pst.setString(11, outputPins.toString());
//                        pst.executeUpdate();
//                    }
//
//                    compIndex++;
//                }
//
//                int value =0;
//                // Connectors
//                List<Map<String, Object>> connectorsList = (List<Map<String, Object>>) circuitMap.get("connectors");
//                int connIndex = 0;
//                for (Map<String, Object> connMap : connectorsList) {
//                    int sourceCompId = (int) connMap.get("sourceComponentIndex");
//                    int sourcePinIndex = (int) connMap.get("sourcePinIndex");
//                    int targetCompId = (int) connMap.get("targetComponentIndex");
//                    int targetPinIndex = (int) connMap.get("targetPinIndex");
//                    boolean val = ((boolean)connMap.get("value"));
//                    if ( val == true ){
//                        value =1;
//                    }else{
//                        value =0;
//                    }
//                    int startX = (int) connMap.get("startX");
//                    int startY = (int) connMap.get("startY");
//                    int endX = (int) connMap.get("endX");
//                    int endY = (int) connMap.get("endY");
//
//                    String connSql = "INSERT INTO connectors " +
//                            "(projectname, circuitname, connector_index,start_x,start_y,end_x,end_y, value , source_component_index, source_pin_index, target_component_index, target_pin_index) " +
//                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//                    try (PreparedStatement pst = conn.prepareStatement(connSql)) {
//                        pst.setString(1, projectName);
//                        pst.setString(2, circuitName);
//                        pst.setInt(3, connIndex);
//                        pst.setInt(9, sourceCompId);
//                        pst.setInt(10, sourcePinIndex);
//                        pst.setInt(11, targetCompId);
//                        pst.setInt(12, targetPinIndex);
//                        pst.setInt(8, value);
//                        pst.setInt(4, startX);
//                        pst.setInt(5, startY);
//                        pst.setInt(6, endX);
//                        pst.setInt(7, endY);
//                        pst.executeUpdate();
//                    }
//
//                    connIndex++;
//                }
//            }
//
//            return true;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
    try (Connection conn = getConnection()) {

            String projectName = (String) projectMap.get("projectName");
            List<Map<String, Object>> circuitsList = (List<Map<String, Object>>) projectMap.get("circuits");

            for (Map<String, Object> circuitMap : circuitsList) {
                String circuitName = (String) circuitMap.get("circuitName");

                // ---------------- Components ----------------
                List<Map<String, Object>> componentsList = (List<Map<String, Object>>) circuitMap.get("components");
                int compIndex = 0;
                for (Map<String, Object> compMap : componentsList) {
                    String type = (String) compMap.get("type");
                    int x = (int) compMap.get("x");
                    int y = (int) compMap.get("y");
                    int width = (int) compMap.get("width");
                    int height = (int) compMap.get("height");
                    String state = compMap.get("state") != null ? compMap.get("state").toString() : "";

                    // Input pins as simple string
                    List<Map<String, Object>> inputPinsList = (List<Map<String, Object>>) compMap.get("inputPins");
                    StringBuilder inputPins = new StringBuilder();
                    for (int i = 0; i < inputPinsList.size(); i++) {
                        Map<String, Object> pin = inputPinsList.get(i);
                        inputPins.append(pin.get("x")).append(",")
                                 .append(pin.get("y")).append(",")
                                 .append(pin.get("type")).append(",")
                                 .append(pin.get("value"));
                        if (i < inputPinsList.size() - 1) inputPins.append("-");
                    }

                    // Output pins as simple string
                    List<Map<String, Object>> outputPinsList = (List<Map<String, Object>>) compMap.get("outputPins");
                    StringBuilder outputPins = new StringBuilder();
                    for (int i = 0; i < outputPinsList.size(); i++) {
                        Map<String, Object> pin = outputPinsList.get(i);
                        outputPins.append(pin.get("x")).append(",")
                                  .append(pin.get("y")).append(",")
                                  .append(pin.get("type")).append(",")
                                  .append(pin.get("value"));
                        if (i < outputPinsList.size() - 1) outputPins.append("-");
                    }

                    // Check if component already exists
                    String checkCompSql = "SELECT COUNT(*) FROM components WHERE projectname=? AND circuitname=? AND component_index=?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkCompSql)) {
                        checkStmt.setString(1, projectName);
                        checkStmt.setString(2, circuitName);
                        checkStmt.setInt(3, compIndex);
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);

                        if (count == 0) { // only insert if not exists
                            String compSql = "INSERT INTO components " +
                                    "(projectname, circuitname, component_index, type, x, y, width, height, state, input_pins, output_pins) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement pst = conn.prepareStatement(compSql)) {
                                pst.setString(1, projectName);
                                pst.setString(2, circuitName);
                                pst.setInt(3, compIndex);
                                pst.setString(4, type);
                                pst.setInt(5, x);
                                pst.setInt(6, y);
                                pst.setInt(7, width);
                                pst.setInt(8, height);
                                pst.setString(9, state);
                                pst.setString(10, inputPins.toString());
                                pst.setString(11, outputPins.toString());
                                pst.executeUpdate();
                            }
                        }
                    }

                    compIndex++;
                }

                // ---------------- Connectors ----------------
                List<Map<String, Object>> connectorsList = (List<Map<String, Object>>) circuitMap.get("connectors");
                int connIndex = 0;
                for (Map<String, Object> connMap : connectorsList) {
                    int sourceCompId = (int) connMap.get("sourceComponentIndex");
                    int sourcePinIndex = (int) connMap.get("sourcePinIndex");
                    int targetCompId = (int) connMap.get("targetComponentIndex");
                    int targetPinIndex = (int) connMap.get("targetPinIndex");
                    boolean val = (boolean) connMap.get("value");
                    int value = val ? 1 : 0;
                    int startX = (int) connMap.get("startX");
                    int startY = (int) connMap.get("startY");
                    int endX = (int) connMap.get("endX");
                    int endY = (int) connMap.get("endY");

                    // Check if connector already exists
                    String checkConnSql = "SELECT COUNT(*) FROM connectors WHERE projectname=? AND circuitname=? AND connector_index=?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkConnSql)) {
                        checkStmt.setString(1, projectName);
                        checkStmt.setString(2, circuitName);
                        checkStmt.setInt(3, connIndex);
                        ResultSet rs = checkStmt.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);

                        if (count == 0) { // only insert if not exists
                            String connSql = "INSERT INTO connectors " +
                                    "(projectname, circuitname, connector_index, start_x, start_y, end_x, end_y, value, source_component_index, source_pin_index, target_component_index, target_pin_index) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement pst = conn.prepareStatement(connSql)) {
                                pst.setString(1, projectName);
                                pst.setString(2, circuitName);
                                pst.setInt(3, connIndex);
                                pst.setInt(4, startX);
                                pst.setInt(5, startY);
                                pst.setInt(6, endX);
                                pst.setInt(7, endY);
                                pst.setInt(8, value);
                                pst.setInt(9, sourceCompId);
                                pst.setInt(10, sourcePinIndex);
                                pst.setInt(11, targetCompId);
                                pst.setInt(12, targetPinIndex);
                                pst.executeUpdate();
                            }
                        }
                    }

                    connIndex++;
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public ArrayList<String> projectNames(){

        ArrayList<String> arr = new ArrayList<>(); ;
        try {
            
            Connection conn = getConnection();
            String query1 = "Select distinct dbo.components.projectname from components" + " Union " + "Select distinct dbo.connectors.projectname from connectors";
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            ResultSet rs = stmt1.executeQuery();
            
            while( rs.next() ){
                arr.add(rs.getString("projectname"));
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        if( arr.isEmpty() ){
            return null;
        }
        return arr;
        
    }
    
    public Map<String, Object> loadAProject(String projectName){
        
        Map<String, Object> projectMap = new HashMap<>();
        projectMap.put("projectName", projectName);
        
        List<Map<String, Object>> circuitsList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> circuitComponents = new HashMap<>();
        Map<String, List<Map<String, Object>>> circuitConnectors = new HashMap<>();
        
        try{
            Connection conn = getConnection();
            String query1 = "Select * from components where projectname = ?";
            PreparedStatement stmt1 = conn.prepareStatement(query1);
            stmt1.setString(1, projectName);
            ResultSet s1 = stmt1.executeQuery();
            
            while(s1.next()){
                String circuitName = s1.getString("circuitname");
                String type = s1.getString("type");

                int x = s1.getInt("x");
                int y = s1.getInt("y");
                int w = s1.getInt("width");
                int h = s1.getInt("height");

                boolean state = s1.getBoolean("state");

                String inputPinsStr = s1.getString("input_pins");
                String outputPinsStr = s1.getString("output_pins");

                // ---- Create circuit bucket if not exists ----
                if (!circuitComponents.containsKey(circuitName)) {
                    circuitComponents.put(circuitName, new ArrayList<>());
                    circuitConnectors.put(circuitName, new ArrayList<>());
                }

                // ---- Component Map ----
                Map<String, Object> compMap = new HashMap<>();
                compMap.put("type", type);
                compMap.put("x", x);
                compMap.put("y", y);
                compMap.put("width", w);
                compMap.put("height", h);

                if (type.equals("Switch") || type.equals("LED")) {
                    compMap.put("state", state);
                }

                // ---- Parse Pins ----
                compMap.put("input_Pins", parsePins(inputPinsStr));
                compMap.put("output_Pins", parsePins(outputPinsStr));

                circuitComponents.get(circuitName).add(compMap);
            
            }
            
            String query2 = "Select * from connectors where projectname = ?";
            PreparedStatement stmt2 = conn.prepareStatement(query2);
            stmt2.setString(1, projectName);
            ResultSet s2 = stmt2.executeQuery();
            
            while (s2.next()) {

                String circuitName = s2.getString("circuitname");

                Map<String, Object> connMap = new HashMap<>();

                connMap.put("sourceComponentIndex", s2.getInt("source_component_index"));
                connMap.put("sourcePinIndex", s2.getInt("source_pin_index"));

                connMap.put("targetComponentIndex", s2.getInt("target_component_index"));
                connMap.put("targetPinIndex", s2.getInt("target_pin_index"));

                connMap.put("value", s2.getBoolean("value"));

                connMap.put("startX", s2.getInt("start_x"));
                connMap.put("startY", s2.getInt("start_y"));
                connMap.put("endX", s2.getInt("end_x"));
                connMap.put("endY", s2.getInt("end_y"));

                circuitConnectors.get(circuitName).add(connMap);
            }
            
            for (String cName : circuitComponents.keySet()) {

                Map<String, Object> circuitMap = new HashMap<>();
                circuitMap.put("circuitName", cName);
                circuitMap.put("components", circuitComponents.get(cName));
                circuitMap.put("connectors", circuitConnectors.get(cName));

                circuitsList.add(circuitMap);
            }
            
            projectMap.put("circuits", circuitsList);
            return projectMap;
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    private List<Map<String, Object>> parsePins(String pinStr) {

        List<Map<String, Object>> pins = new ArrayList<>();
        if (pinStr == null || pinStr.trim().isEmpty()) return pins;

        String[] allPins = pinStr.split("-");

        for (String p : allPins) {
            String[] parts = p.split(",");

            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            String type = parts[2];
            boolean value = Boolean.parseBoolean(parts[3]);

            Map<String, Object> pinMap = new HashMap<>();
            pinMap.put("x", x);
            pinMap.put("y", y);
            pinMap.put("type", type);
            pinMap.put("value", value);

            pins.add(pinMap);
        }

        return pins;
    }
    
    @Override
    public boolean remove(String project,String circuit){
        String deleteComponentsSQL = "DELETE FROM components WHERE projectname=? AND circuitname=?";
        String deleteConnectorsSQL = "DELETE FROM connectors WHERE projectname=? AND circuitname=?";

        try (Connection conn = getConnection()) {
            try (PreparedStatement pst = conn.prepareStatement(deleteComponentsSQL)) {
                pst.setString(1, project);
                pst.setString(2, circuit);
                pst.executeUpdate();
            }
            try (PreparedStatement pst = conn.prepareStatement(deleteConnectorsSQL)) {
                pst.setString(1, project);
                pst.setString(2, circuit);
                pst.executeUpdate();
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private static Connection getConnection() throws SQLException {
        
        return DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=LogisimDB;encrypt=false;integratedSecurity=true;");
        
    }
    
}
