/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaceLayer;

import java.util.ArrayList;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.basic.BasicScrollBarUI;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.image.BufferedImage;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

import BusinessLayer.Canvas;
import BusinessLayer.Project;
import BusinessLayer.ExportService;
import DataAccessLayer.FileExporter;


/**
 * Main application window for the LOGISIM-like circuit design software.
 * <p>
 * This JFrame contains the main GUI components:
 * <ul>
 *     <li>Toolbar with buttons: New Project, Save Project, Load Project, Export, Truth Table</li>
 *     <li>Canvas for circuit design with scroll support</li>
 *     <li>Palette panel for selecting components/tools</li>
 *     <li>Circuit Explorer for managing multiple circuits within a project</li>
 *     <li>Header with project name and software title</li>
 * </ul>
 * <p>
 * Handles events such as creating a new project, saving/loading projects,
 * exporting circuit images, and generating truth tables.
 * </p>
 * 
 * Example usage:
 * <pre>
 *     MainWindow window = new MainWindow();
 *     window.setVisible(true);
 * </pre>
 * 
 * @author HP
 */
public class MainWindow extends JFrame {

    /** Canvas for circuit drawing and editing */
    private static Canvas canvas;
    /** Toolbar buttons */
    private static JButton newButton;
    private static JButton saveButton;
    private static JButton exportButton;
    private static JButton loadButton;
    private static JButton truthTableButton;
    /** Toolbar containing buttons */
    private static JToolBar toolBar;
    /** Panel for central design area */
    private static JPanel designPanel;
     /** Palette panel containing circuit components/tools */
    private Palette palette;
     /** Text field to display/edit project name */
    private static JTextField projectNameField;
    /** Current project instance */
    private static Project project;
    /** Circuit Explorer panel for managing circuits */
    private static CircuitExplorer circuitExplorer;
    
    /**
     * Constructs the main application window.
     * Initializes the project, canvas, palette, circuit explorer,
     * header panels, and sets up event handlers.
     */
    public MainWindow() {

        project = new Project();
        palette = new Palette();
        canvas = new Canvas(project.getCurrentCircuit());
        circuitExplorer = new CircuitExplorer(project,canvas);
        setInitialGUIComponent(this.getContentPane());
        getContentPane().add(palette, BorderLayout.LINE_START);
        //getContentPane().add(canvas, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 1000, 600);
        setEventHandlers();

        palette.addToolSelectionListener(ae -> {
            String tool = ae.getActionCommand();
            canvas.setActiveTool(tool);
        });

    }

    /**
     * Sets up the initial GUI layout with header panels, canvas scroll pane,
     * and circuit explorer panel.
     * 
     * @param pane the content pane of the JFrame
     */
    public static void setInitialGUIComponent(Container pane) {

        JPanel panel1 = setHeaderFirstPanel();
        JPanel panel2 = setHeaderSecondPanel();

        JPanel BPanel1 = new JPanel();
        BPanel1.setLayout(new BoxLayout(BPanel1, BoxLayout.Y_AXIS));
        BPanel1.add(panel1);
        BPanel1.add(panel2);

        pane.add(BPanel1, BorderLayout.PAGE_START);
        canvas.setPreferredSize(new Dimension(1000, 1000)); // set a large size to enable scrolling
        JScrollPane canvasScrollPane = new JScrollPane(canvas);
        canvasScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        canvasScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        canvasScrollPane.getViewport().setBackground(Color.WHITE);

        canvasScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(100, 149, 237);      // thumb color
                trackColor = new Color(220, 220, 220);      // background track
            }
        });
        canvasScrollPane.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(100, 149, 237);
                trackColor = new Color(220, 220, 220);
            }
        });

    
        pane.add(canvasScrollPane, BorderLayout.CENTER);

        pane.add(circuitExplorer, BorderLayout.LINE_END);

    }

     /**
     * Creates the top header panel displaying the project name
     * and software title.
     * 
     * @return JPanel containing header information
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
     * Creates the second header panel containing the toolbar
     * with buttons for project operations.
     * 
     * @return JPanel containing the toolbar
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
     * Creates a central design panel with a titled border.
     * 
     * @return JComponent representing the design area
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
     * Sets up all event handlers for toolbar buttons.
     * Handles creating/loading/saving projects, exporting images,
     * and showing truth tables.
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
                try {
                    ExportService exportService = new ExportService();
                    FileExporter fileExporter = new FileExporter();

                    BufferedImage img = exportService.renderComponent(canvas);

                    fileExporter.saveImage(img, project.getLastCircuit().getCircuitName());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                ArrayList<String> projects = project.getProjectNames();

                JComboBox<String> projectCombo;

                // If no projects exist
                if (projects == null || projects.isEmpty()) {
                    projectCombo = new JComboBox<>(new String[]{"No projects available"});
                    projectCombo.setEnabled(false);   // user cannot select
                } 
                // If projects exist
                else {
                    projectCombo = new JComboBox<>(projects.toArray(new String[0]));
                }

                int option = JOptionPane.showConfirmDialog(
                        MainWindow.this,
                        projectCombo,
                        "Select Project to Load",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                // Only proceed if OK was clicked AND real projects exist
                if (option == JOptionPane.OK_OPTION && projectCombo.isEnabled()) {

                    String selectedProjectName = (String) projectCombo.getSelectedItem();

                    if (selectedProjectName != null && !selectedProjectName.isEmpty()) {

                        project.loadProject(selectedProjectName);

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
