/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

import BusinessLayer.Canvas;
import BusinessLayer.Project;
import BusinessLayer.Circuit;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author HP
 */
public class MainWindow extends JFrame {

    private static Canvas canvas;
    private static JButton newButton;
    private static JButton saveButton;
    private static JButton exportButton;
    private static JButton loadButton;
    private static JButton truthTableButton;
    private static JToolBar toolBar;
    private static JPanel designPanel;
    private Palette palette;
    private static JTextField projectNameField;
    private static Project project;
    private static CircuitExplorer circuitExplorer;
    /**
     * 
     */
    public MainWindow() {

        project = new Project();
        palette = new Palette();
        canvas = new Canvas(project.getCurrentCircuit());
        circuitExplorer = new CircuitExplorer(project,canvas);
        setInitialGUIComponent(this.getContentPane());
        getContentPane().add(palette, BorderLayout.LINE_START);
        getContentPane().add(canvas, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 1000, 600);
        setEventHandlers();

        palette.addToolSelectionListener(ae -> {
            String tool = ae.getActionCommand();
            canvas.setActiveTool(tool);
        });

    }

    /**
     * 
     * @param pane
     */
    public static void setInitialGUIComponent(Container pane) {

        JPanel panel1 = setHeaderFirstPanel();
        JPanel panel2 = setHeaderSecondPanel();

        JPanel BPanel1 = new JPanel();
        BPanel1.setLayout(new BoxLayout(BPanel1, BoxLayout.Y_AXIS));
        BPanel1.add(panel1);
        BPanel1.add(panel2);

        pane.add(BPanel1, BorderLayout.PAGE_START);
        pane.add(createCenterCanvas(), BorderLayout.CENTER);
        pane.add(circuitExplorer, BorderLayout.LINE_END);

    }

    /**
     * 
     * @return
     */
    public static JPanel setHeaderFirstPanel() {

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        JLabel projectName = new JLabel("Project: ");
        projectName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        projectName.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        projectNameField = new JTextField("New Project"); 
        projectNameField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        projectNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        projectNameField.setColumns(12); // optional, width
        JLabel softwareName = new JLabel("LOGISIM", SwingConstants.CENTER);
        softwareName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(softwareName);
        JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.setBackground(Color.WHITE);
        westPanel.add(projectName);
        westPanel.add(projectNameField);
        panel1.add(westPanel, BorderLayout.WEST);
        panel1.add(centerPanel, BorderLayout.CENTER);
        panel1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        return panel1;

    }

    /**
     * 
     * @return
     */
    public static JPanel setHeaderSecondPanel() {

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        newButton = new JButton("   New Project   ");
        newButton.setToolTipText("Create a new Project");
        saveButton = new JButton("   Save Project  ");
        saveButton.setToolTipText("Save this Project");
        loadButton = new JButton("   Load   ");
        loadButton.setToolTipText("Load a existing Project");
        exportButton = new JButton("   Export as PNG/JPG   ");
        exportButton.setToolTipText("Export this project as a PNG/JPG");
        truthTableButton = new JButton("   Truth Table   ");
        truthTableButton.setToolTipText("Generate Truth Table & Boolean Expression");

        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(newButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(saveButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(loadButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(exportButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.add(truthTableButton);
        toolBar.add(Box.createHorizontalStrut(25));
        toolBar.setBackground(Color.WHITE);

        panel2.add(toolBar);
        panel2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        panel2.setBackground(Color.WHITE);
        return panel2;

    }

    /**
     * 
     * @return
     */
    public static JComponent createCenterCanvas() {
        designPanel = new JPanel();
        designPanel.setBackground(Color.WHITE);
        TitledBorder titleBorder = BorderFactory.createTitledBorder("Circuit Design Area");
        titleBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        titleBorder.setTitleColor(Color.darkGray);
        designPanel.setBorder(titleBorder);
        return designPanel;
    }

    /**
     * 
     */
    public void setEventHandlers() {

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JTextField nameField = new JTextField();

                Object[] message = {
                    "Project Name:", nameField
                };

                int option = JOptionPane.showOptionDialog(
                        null,                     // parent component
                        message,                  // message (label + text field)
                        "New Project",           // title
                        JOptionPane.YES_NO_OPTION,// buttons type
                        JOptionPane.QUESTION_MESSAGE,
                        null,                     // icon
                        new String[]{"Save", "No"}, // custom button texts
                        "Save"                    // default button
                );
                
                if (option == 0) { // Save button
                    String projectName = nameField.getText().trim();
                    if (!projectName.isEmpty()) {
                        if (project.getProjectNames().contains(projectName)){
                            JOptionPane.showMessageDialog(null, "A project with this name already exists!");
                            return;
                        }
                        projectNameField.setText(projectName);
                        circuitExplorer.clearList();
                        project.getcircuits().clear();  // remove all circuits
                        project.setCurrentCircuitName("");
                        canvas.clearCanvas();
                        project.createNewCircuit();
                        canvas.setCircuit(project.getLastCircuit());
                    } else {
                        JOptionPane.showMessageDialog(null, "Project name cannot be empty!");
                    }
                } else {
                    System.out.println("User clicked No / cancelled");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                project.setName(projectNameField.getText().trim());
                project.saveData();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ArrayList<String> projects = project.getProjectNames();
                if (projects.isEmpty()) {
                    JOptionPane.showMessageDialog(MainWindow.this, "No projects found!");
                    return;
                }
                
                String[] projectsArray = projects.toArray(new String[0]);
                JComboBox<String> projectCombo = new JComboBox<>(projectsArray);

                int option = JOptionPane.showConfirmDialog(
                        MainWindow.this,
                        projectCombo,
                        "Select Project to Load",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );
                
                if (option == JOptionPane.OK_OPTION) {
                    String selectedProjectName = (String) projectCombo.getSelectedItem();
                    if (selectedProjectName != null && !selectedProjectName.isEmpty()) {
                        
                        project.loadProject(selectedProjectName);
                        
                        //Circuit mainCircuit = project.getCurrentCircuit();
                        circuitExplorer.clearList();
                        circuitExplorer.addCircuitsInList();
                        
                        canvas.clearCanvas();
                        
                        projectNameField.setText(project.getName());
                        project.createNewCircuit();
                        canvas.setCircuit(project.getLastCircuit());
                        canvas.syncWithCircuit();
                    }
                }
                
            }
            
        });

        truthTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (canvas != null && canvas.getCircuit() != null) {
                    TruthTableDialog dialog = new TruthTableDialog(MainWindow.this, canvas.getCircuit());
                    dialog.setVisible(true);
                }
            }
        });

    }

}
