package gui;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.Position;
import gui.*;
import model.*;
import gui.command.*;
import gui.dialog.*;

/**
 * A class representing the main window on the screen and the manager of the program.<br>
 * All users' processes come through this class.<br>
 * It's used to manage the whole program in order to have an efficient kind of consistence.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @version 1.0
 * @since November 2003
 */
public class Interface extends JFrame implements ActionListener {
    
	private static Model model;
	private IView sectionView = new SectionView(this);
	private IView diagramView = new DiagramView(this);
    private GeradiaProperties properties = new GeradiaProperties();
	
	private DrawingArea drawingArea1 = sectionView.getDrawingArea();
	private DrawingArea drawingArea2 = diagramView.getDrawingArea();
	
    //Main panels
    private JPanel drawArea1 = new JPanel();
	private JPanel drawArea2 = new JPanel();
	private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawArea1, drawArea2);
    
    //Commands
    
    private Command newFileCmd = new NewFileCommand(this);
    private Command openCmd = new OpenCommand(this);
    private Command saveFileCmd = new SaveFileCommand(this);
    private Command saveFileAsCmd = new SaveFileAsCommand(this);
    private Command printCmd = new PrintCommand(this);
    private Command exportJPEG1Cmd = new ExportJPEG1Command(this);
	private Command exportJPEG2Cmd = new ExportJPEG2Command(this);
    private Command exportPS1Cmd = new ExportPS1Command(this);
	private Command exportPS2Cmd = new ExportPS2Command(this);
	private Command exportTXTCmd = new ExportTXTCommand(this);
    private Command exitCmd = new ExitCommand(this);
    
	private Command gridCmd = new GridCommand(this);
    private Command snapCmd = new SnapCommand(this);
	
	private Command boundaryCmd = new BoundaryCommand(this);
	private Command holeCmd = new HoleCommand(this);
	private Command steelElementCmd = new SteelElementCommand(this);
	private Command steelBarCmd = new SteelBarCommand(this);
	
	private Command showDiscretizedCmd = new ShowDiscretizedCommand(this);
	private Command solveCmd = new SolveCommand(this);
	private Command verifyCmd = new VerifyCommand(this);
	
    private Command settingsCmd = new SettingsCommand(this);
	private Command sectionSettingsCmd = new SectionSettingsCommand(this);
	private Command diagramSettingsCmd = new DiagramSettingsCommand(this);
	
    private Command selectAllCmd = new SelectAllCommand(this);
    private Command deleteCmd = new DeleteCommand(this);
    private Command zoomCmd = new ZoomCommand(this);
    private Command zoomInCmd = new ZoomInCommand(this);
    private Command zoomOutCmd = new ZoomOutCommand(this);
    private Command fitCmd = new FitCommand(this);
    private Command zoomAllCmd = new ZoomAllCommand(this);
    private Command lastZoomCmd = new LastZoomCommand(this);
    private Command redrawCmd = new RedrawCommand(this);
	
	private UndoCommand undoCmd = new UndoCommand(this);
    private RedoCommand redoCmd = new RedoCommand(this);
	
	private Command helpTopicsCmd = new HelpTopicsCommand(this);
    private Command bugReportCmd = new BugReportCommand(this);
	private Command aboutCmd = new AboutCommand(this);
	
	//Handler for Console events
	private ConsoleHandler consoleHandler = new ConsoleHandler(this);
	
    //Menu
    
    private JMenuBar menuBar = new JMenuBar();
    
    private JMenu menuFile = new JMenu("File");
    private CmdMenu itemNew = new CmdMenu("New", new ImageIcon(ClassLoader.getSystemResource("images/New16.gif")));
    private CmdMenu itemOpen = new CmdMenu("Open", new ImageIcon(ClassLoader.getSystemResource("images/Open16.gif")));
    private CmdMenu itemSave = new CmdMenu("Save", new ImageIcon(ClassLoader.getSystemResource("images/Save16.gif")));
    private CmdMenu itemSaveAs = new CmdMenu("Save As", new ImageIcon(ClassLoader.getSystemResource("images/SaveAs16.gif")));
    private CmdMenu itemPrint = new CmdMenu("Print", new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif")));
    private JMenu menuExport = new JMenu("Export");
	private JMenu menuJPEG = new JMenu("JPEG Image");
    private CmdMenu itemJPEG1 = new CmdMenu("Section Drawing", new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));
	private CmdMenu itemJPEG2 = new CmdMenu("Diagram", new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));
	private JMenu menuPS = new JMenu("PostScript");
    private CmdMenu itemPS1 = new CmdMenu("Section Drawing", new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));
	private CmdMenu itemPS2 = new CmdMenu("Diagram", new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));
	private CmdMenu itemTXT = new CmdMenu("TXT File", new ImageIcon(ClassLoader.getSystemResource("images/Export16.gif")));
	private CmdMenu itemExit = new CmdMenu("Exit");
    
    private JMenu menuEdit = new JMenu("Edit");
    private CmdMenu itemUndo = new CmdMenu("Undo", new ImageIcon(ClassLoader.getSystemResource("images/Undo16.gif")));
    private CmdMenu itemRedo = new CmdMenu("Redo", new ImageIcon(ClassLoader.getSystemResource("images/Redo16.gif")));
    private CmdMenu itemCut = new CmdMenu("Cut", new ImageIcon(ClassLoader.getSystemResource("images/Cut16.gif")));
    private CmdMenu itemCopy = new CmdMenu("Copy", new ImageIcon(ClassLoader.getSystemResource("images/Copy16.gif")));
    private CmdMenu itemPaste = new CmdMenu("Paste", new ImageIcon(ClassLoader.getSystemResource("images/Paste16.gif")));
    private CmdMenu itemSelectAll = new CmdMenu("Select All");
    private CmdMenu itemDelete = new CmdMenu("Delete", new ImageIcon(ClassLoader.getSystemResource("images/Delete16.gif")));
    
    private JMenu menuView = new JMenu("View");
    private JMenu menuZoom = new JMenu("Zoom");
    private CmdMenu itemWindow = new CmdMenu("Window", new ImageIcon(ClassLoader.getSystemResource("images/Zoom16.gif")));
    private CmdMenu itemZoomIn = new CmdMenu("In", new ImageIcon(ClassLoader.getSystemResource("images/ZoomIn16.gif")));
    private CmdMenu itemZoomOut = new CmdMenu("Out", new ImageIcon(ClassLoader.getSystemResource("images/ZoomOut16.gif")));
    private CmdMenu itemExtents = new CmdMenu("Extents", new ImageIcon(ClassLoader.getSystemResource("images/ZoomExtents16.gif")));
    private CmdMenu itemAll = new CmdMenu("All", new ImageIcon(ClassLoader.getSystemResource("images/ZoomAll16.gif")));
    private CmdMenu itemLastZoom = new CmdMenu("Last", new ImageIcon(ClassLoader.getSystemResource("images/ZoomPrevious16.gif")));
	private CmdMenu itemRedraw = new CmdMenu("Redraw", new ImageIcon(ClassLoader.getSystemResource("images/Redraw16.gif")));
	private CmdCheckBoxMenu itemViewGlobalAxis = new CmdCheckBoxMenu("View Global Axis");
	
	private JMenu menuSettings = new JMenu("Settings");
    private CmdMenu itemSettings = new CmdMenu("Settings", new ImageIcon(ClassLoader.getSystemResource("images/Settings16.gif")));
	private CmdMenu itemSectionSettings = new CmdMenu("Section View Settings");
	private CmdMenu itemDiagramSettings = new CmdMenu("Diagram View Settings");
	
	private JMenu menuDraw = new JMenu("Draw");
    private CmdMenu itemBoundary = new CmdMenu("Boundary", new ImageIcon(ClassLoader.getSystemResource("images/Boundary16.gif")));
	private CmdMenu itemHole = new CmdMenu("Hole", new ImageIcon(ClassLoader.getSystemResource("images/Hole16.gif")));
	private CmdMenu itemSteelElement = new CmdMenu("Steel Element", new ImageIcon(ClassLoader.getSystemResource("images/Steel16.gif")));
	private CmdMenu itemSteelBar = new CmdMenu("Steel Bar", new ImageIcon(ClassLoader.getSystemResource("images/Point16.gif")));
	
	private JMenu menuSolve = new JMenu("Solve");
    private CmdMenu itemDiscSection = new CmdMenu("Show/Hide Discretized Section", new ImageIcon(ClassLoader.getSystemResource("images/Discretize16.gif")));
	private CmdMenu itemSolve = new CmdMenu("Solve", new ImageIcon(ClassLoader.getSystemResource("images/Solve16.gif")));
	private CmdMenu itemVerify = new CmdMenu("Verify Mx and My", new ImageIcon(ClassLoader.getSystemResource("images/Verify16.gif")));
	
	private JMenu menuHelp = new JMenu("Help");
    private CmdMenu itemHelpTopics = new CmdMenu("Help Topics", new ImageIcon(ClassLoader.getSystemResource("images/Help16.gif")));
    private CmdMenu itemBugReport = new CmdMenu("Bug Report",  new ImageIcon(ClassLoader.getSystemResource("images/Bug16.gif")));
	private CmdMenu itemAbout = new CmdMenu("About",  new ImageIcon(ClassLoader.getSystemResource("images/About16.gif")));
    
    //Tools
    
    private JPanel toolPanel = new JPanel();
    
    private JToolBar fileToolBar = new JToolBar();
    private CmdButton newB = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/New16.gif")));
    private CmdButton open = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Open16.gif")));
    private CmdButton save = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Save16.gif")));
    private CmdButton saveAs = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/SaveAs16.gif")));
    private CmdButton print = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif")));
    
    private JToolBar zoomToolBar = new JToolBar();
    private CmdButton redraw = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Redraw16.gif")));
    private CmdButton fit = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/ZoomExtents16.gif")));
    private CmdButton zoom = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Zoom16.gif")));
    private CmdButton zoomIn = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/ZoomIn16.gif")));
    private CmdButton zoomOut = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/ZoomOut16.gif")));
    private CmdButton zoomAll = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/ZoomAll16.gif")));
	private CmdButton zoomPrevious = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/ZoomPrevious16.gif")));
    
    private JToolBar editToolBar = new JToolBar();
    private CmdButton undo = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Undo16.gif")));
    private CmdButton redo = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Redo16.gif")));
    private CmdButton cut = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Cut16.gif")));
    private CmdButton copy = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Copy16.gif")));
    private CmdButton paste = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Paste16.gif")));
    private CmdButton delete = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Delete16.gif")));
   
    private JToolBar drawToolBar = new JToolBar();
    private CmdButton boundary = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Boundary16.gif")));
	private CmdButton hole = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Hole16.gif")));
	private CmdButton steelElement = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Steel16.gif")));
	private CmdButton steelBar = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Point16.gif")));
	
	private JToolBar solveToolBar = new JToolBar();
    private CmdButton showDiscretized = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Discretize16.gif")));
	private CmdButton solve = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Solve16.gif")));
	private CmdButton verify = new CmdButton(new ImageIcon(ClassLoader.getSystemResource("images/Verify16.gif")));
	
	//In-Out Panel
	private JPanel inOutBar = new JPanel(new BorderLayout(6,0));
    private Console console = new Console(this);
		
    private JPanel statusBar = new JPanel();
    
    private JTextField messageField = new JTextField(21);
    private JTextField coordField = new JTextField("Coords :",1);
    private JPanel state = new JPanel();
    private CmdToggleButton grid = new CmdToggleButton("Grid");
    private CmdToggleButton snap = new CmdToggleButton("Snap");
    
//*****************************************************************************
	
    /**
     * The constructor method. <br>
     * Calls the init() method throwing an exception.
     */
    public Interface() {
		Geradia.advanceSplashProgress();
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
		SplashScreen.getInstance().finish();
    }
	
//*****************************************************************************
	
    /**
     * It's called by the constructor. <br>
     * Sets Panels, components and provides a window listener.
     */
    private void init() throws Exception {
        
		Geradia.advanceSplashProgress();
		
        this.setTitle("Geradia " + this.getProperties().getVersion());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(new Dimension(800, 640));
		
		this.messageField.setText("Escola de Engenharia da UFMG");
		
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitCmd.execute();
            }
        } );
        Image ico = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/Insane64.gif"));
        this.setIconImage(ico);
        
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.X_AXIS));
        this.getContentPane().add(toolPanel, BorderLayout.NORTH);
        
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(BorderFactory.createEmptyBorder(2,3,1,3));
		
        drawArea1.setLayout(new BorderLayout());
        drawArea1.setBorder(BorderFactory.createEmptyBorder(0,0,0,3));
		
		drawArea2.setLayout(new BorderLayout());
        drawArea2.setBorder(BorderFactory.createEmptyBorder(0,0,0,3));
		
        this.getContentPane().add(splitPane, BorderLayout.CENTER);
        
        //Setting Menus
        
        //Setting menu file
        
        menuFile.setMnemonic(KeyEvent.VK_F);
        
        menuFile.add(itemNew);
        itemNew.setCommand(newFileCmd);
        itemNew.addActionListener(this);
        itemNew.setMnemonic(KeyEvent.VK_N);
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        
        menuFile.add(itemOpen);
        itemOpen.setCommand(openCmd);
        itemOpen.addActionListener(this);
        itemOpen.setMnemonic(KeyEvent.VK_O);
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        menuFile.add(itemSave);
        itemSave.setCommand(saveFileCmd);
        itemSave.setEnabled(false);
        itemSave.addActionListener(this);
        itemSave.setMnemonic(KeyEvent.VK_S);
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        
        menuFile.add(itemSaveAs);
        itemSaveAs.setCommand(saveFileAsCmd);
        itemSaveAs.setEnabled(false);
        itemSaveAs.addActionListener(this);
		itemSaveAs.setMnemonic(KeyEvent.VK_A);
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
        
        menuFile.addSeparator();
        
        menuFile.add(itemPrint);
        itemPrint.setCommand(printCmd);
        itemPrint.setEnabled(false);
        itemPrint.addActionListener(this);
        itemPrint.setMnemonic(KeyEvent.VK_P);
        itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        
		menuFile.add(menuExport);
        menuExport.setEnabled(false);
		menuExport.setMnemonic(KeyEvent.VK_E);
        
		menuExport.add(menuJPEG);
        menuJPEG.setEnabled(false);
		menuJPEG.setMnemonic(KeyEvent.VK_J);
		
		menuJPEG.add(itemJPEG1);
		itemJPEG1.setCommand(exportJPEG1Cmd);
		itemJPEG1.setMnemonic(KeyEvent.VK_S);
        itemJPEG1.addActionListener(this);
        
		menuJPEG.add(itemJPEG2);
		itemJPEG2.setCommand(exportJPEG2Cmd);
		itemJPEG2.setMnemonic(KeyEvent.VK_D);
        itemJPEG2.addActionListener(this);
		
		menuExport.add(menuPS);
        menuPS.setEnabled(false);
		menuPS.setMnemonic(KeyEvent.VK_S);
		
		menuPS.add(itemPS1);
        itemPS1.setCommand(exportPS1Cmd);
		itemPS1.setMnemonic(KeyEvent.VK_S);
        itemPS1.addActionListener(this);
        
		menuPS.add(itemPS2);
        itemPS2.setCommand(exportPS2Cmd);
		itemPS2.setMnemonic(KeyEvent.VK_D);
        itemPS2.addActionListener(this);
		
		menuExport.add(itemTXT);
        itemTXT.setCommand(exportTXTCmd);
		itemTXT.setMnemonic(KeyEvent.VK_T);
        itemTXT.addActionListener(this);
		
		menuFile.addSeparator();
        
        menuFile.add(itemExit);
        itemExit.setMnemonic(KeyEvent.VK_X);
        itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		itemExit.setCommand(exitCmd);
        itemExit.addActionListener(this);
        
        //Setting menu edit
        
        menuEdit.setMnemonic(KeyEvent.VK_E);
        
        menuEdit.add(itemUndo);
        itemUndo.setEnabled(false);
        itemUndo.setCommand(undoCmd);
        itemUndo.addActionListener(this);
        itemUndo.setMnemonic(KeyEvent.VK_U);
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        
        menuEdit.add(itemRedo);
        itemRedo.setEnabled(false);
        itemRedo.setCommand(redoCmd);
        itemRedo.addActionListener(this);
        itemRedo.setMnemonic(KeyEvent.VK_R);
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        
        menuEdit.addSeparator();
        
        menuEdit.add(itemCut);
        itemCut.setEnabled(false);
        itemCut.addActionListener(this);
        itemCut.setMnemonic(KeyEvent.VK_C);
        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        
        menuEdit.add(itemCopy);
        itemCopy.setEnabled(false);
        itemDelete.addActionListener(this);
        itemCopy.setMnemonic(KeyEvent.VK_O);
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        
        menuEdit.add(itemPaste);
        itemPaste.setEnabled(false);
        itemPaste.addActionListener(this);
        itemPaste.setMnemonic(KeyEvent.VK_P);
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        
        menuEdit.addSeparator();
        
        menuEdit.add(itemSelectAll);
        itemSelectAll.setCommand(selectAllCmd);
        itemSelectAll.addActionListener(this);
        itemSelectAll.setMnemonic(KeyEvent.VK_A);
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        
        menuEdit.add(itemDelete);
        itemDelete.setCommand(deleteCmd);
        itemDelete.addActionListener(this);
        itemDelete.setMnemonic(KeyEvent.VK_D);
        itemDelete.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
        
        //Setting menu view
        menuView.setMnemonic(KeyEvent.VK_V);
        
        menuZoom.add(itemWindow);
        itemWindow.setCommand(zoomCmd);
        itemWindow.addActionListener(this);
        itemWindow.setMnemonic(KeyEvent.VK_W);
        itemWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.SHIFT_MASK));
        
        menuZoom.add(itemZoomIn);
        itemZoomIn.setCommand(zoomInCmd);
        itemZoomIn.addActionListener(this);
        itemZoomIn.setMnemonic(KeyEvent.VK_I);
        itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.SHIFT_MASK));
		
        menuZoom.add(itemZoomOut);
        itemZoomOut.setCommand(zoomOutCmd);
        itemZoomOut.addActionListener(this);
        itemZoomOut.setMnemonic(KeyEvent.VK_O);
		itemZoomOut.setAccelerator(KeyStroke.getKeyStroke("MINUS"));
        
        menuZoom.add(itemAll);
        itemAll.setCommand(zoomAllCmd);
        itemAll.addActionListener(this);
        itemAll.setMnemonic(KeyEvent.VK_A);
        itemAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
		
		menuZoom.add(itemExtents);
        itemExtents.setCommand(fitCmd);
        itemExtents.addActionListener(this);
        itemExtents.setMnemonic(KeyEvent.VK_E);
        itemExtents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.SHIFT_MASK));
        
        menuZoom.add(itemLastZoom);
        itemLastZoom.setCommand(lastZoomCmd);
        itemLastZoom.addActionListener(this);
        itemLastZoom.setMnemonic(KeyEvent.VK_L);
        itemLastZoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK));
		
		menuView.add(menuZoom);
        menuZoom.setMnemonic(KeyEvent.VK_Z);
		
		menuView.addSeparator();
		
		menuView.add(itemRedraw);
        itemRedraw.setMnemonic(KeyEvent.VK_R);
		itemRedraw.setCommand(redrawCmd);
		itemLastZoom.addActionListener(this);
		
		menuView.addSeparator();
		
		//Setting menu draw
		menuDraw.setMnemonic(KeyEvent.VK_D);
		
        menuDraw.add(itemBoundary);
        itemBoundary.setCommand(boundaryCmd);
        itemBoundary.setEnabled(false);
		itemBoundary.setMnemonic(KeyEvent.VK_B);
        itemBoundary.addActionListener(this);
		
		menuDraw.add(itemHole);
        itemHole.setCommand(holeCmd);
        itemHole.setEnabled(false);
		itemHole.setMnemonic(KeyEvent.VK_H);
        itemHole.addActionListener(this);
		
		menuDraw.add(itemSteelElement);
        itemSteelElement.setCommand(boundaryCmd);
        itemSteelElement.setEnabled(false);
		itemSteelElement.setMnemonic(KeyEvent.VK_E);
        itemSteelElement.addActionListener(this);
		
		menuDraw.add(itemSteelBar);
        itemSteelBar.setCommand(steelBarCmd);
        itemSteelBar.setEnabled(false);
		itemSteelBar.setMnemonic(KeyEvent.VK_P);
        itemSteelBar.addActionListener(this);
        
		//Setting menu solve
		menuSolve.setMnemonic(KeyEvent.VK_S);
		
        menuSolve.add(itemDiscSection);
        itemDiscSection.setCommand(showDiscretizedCmd);
        itemDiscSection.setEnabled(false);
		itemDiscSection.setMnemonic(KeyEvent.VK_D);
        itemDiscSection.addActionListener(this);
		
		menuSolve.add(itemSolve);
        itemSolve.setCommand(solveCmd);
        itemSolve.setEnabled(false);
		itemSolve.setMnemonic(KeyEvent.VK_S);
        itemSolve.addActionListener(this);
		
		menuSolve.add(itemVerify);
        itemVerify.setCommand(verifyCmd);
        itemVerify.setEnabled(false);
		itemVerify.setMnemonic(KeyEvent.VK_V);
        itemVerify.addActionListener(this);
		
		//Setting menu settings
        menuSettings.setMnemonic(KeyEvent.VK_T);
		
		menuSettings.add(itemSettings);
		itemSettings.setCommand(settingsCmd);
		itemSettings.setMnemonic(KeyEvent.VK_S);
        itemSettings.addActionListener(this);
		
		menuSettings.add(itemSectionSettings);
		itemSectionSettings.setCommand(sectionSettingsCmd);
		itemSectionSettings.setMnemonic(KeyEvent.VK_C);
        itemSectionSettings.addActionListener(this);
		
		menuSettings.add(itemDiagramSettings);
		itemDiagramSettings.setCommand(diagramSettingsCmd);
		itemDiagramSettings.setMnemonic(KeyEvent.VK_D);
        itemDiagramSettings.addActionListener(this);
		
		//Setting menu help
        menuHelp.setMnemonic(KeyEvent.VK_H);
		
        menuHelp.add(itemHelpTopics);
		itemHelpTopics.setCommand(helpTopicsCmd);
        itemHelpTopics.addActionListener(this);
        itemHelpTopics.setMnemonic(KeyEvent.VK_H);
        itemHelpTopics.setAccelerator(KeyStroke.getKeyStroke("F1"));
		
        menuHelp.addSeparator();
        
        menuHelp.add(itemBugReport);
        itemBugReport.setCommand(bugReportCmd);
		itemBugReport.setMnemonic(KeyEvent.VK_B);
        itemBugReport.addActionListener(this);
		
		menuHelp.add(itemAbout);
        itemAbout.setCommand(aboutCmd);
		itemAbout.setMnemonic(KeyEvent.VK_A);
        itemAbout.addActionListener(this);
        
        this.setJMenuBar(menuBar);
        
        //Setting tools
        
        //Setting file tool bar
        
        fileToolBar.add(newB);
        newB.setToolTipText("New Model");
        newB.setCommand(newFileCmd);
        newB.addActionListener(this);
        
        fileToolBar.add(open);
        open.setToolTipText("Open Model");
        open.setCommand(openCmd);
        open.addActionListener(this);
        
        fileToolBar.add(save);
        save.setToolTipText("Save Model");
        save.setCommand(saveFileCmd);
        save.addActionListener(this);
        
        fileToolBar.add(saveAs);
        saveAs.setToolTipText("Save Model As");
        saveAs.setCommand(saveFileAsCmd);
        saveAs.addActionListener(this);
        
        fileToolBar.add(print);
        print.setToolTipText("Print the canvas");
        print.setCommand(printCmd);
        print.addActionListener(this);
        
        //Setting zoom tool bar
        
        zoomToolBar.add(zoom);
        zoom.setToolTipText("Zoom Window");
        zoom.setCommand(zoomCmd);
        zoom.addActionListener(this);
        
        zoomToolBar.add(zoomIn);
        zoomIn.setToolTipText("Zoom In");
        zoomIn.setCommand(zoomInCmd);
        zoomIn.addActionListener(this);
        
        zoomToolBar.add(zoomOut);
        zoomOut.setToolTipText("Zoom Out");
        zoomOut.setCommand(zoomOutCmd);
        zoomOut.addActionListener(this);
        
        zoomToolBar.add(zoomAll);
        zoomAll.setToolTipText("Zoom All");
        zoomAll.setCommand(zoomAllCmd);
        zoomAll.addActionListener(this);
        
        zoomToolBar.add(fit);
        fit.setToolTipText("Zoom Extents");
        fit.setCommand(fitCmd);
        fit.addActionListener(this);
        
		zoomToolBar.add(zoomPrevious);
        zoomPrevious.setToolTipText("Zoom Last");
        zoomPrevious.setCommand(lastZoomCmd);
        zoomPrevious.addActionListener(this);
		
        zoomToolBar.add(redraw);
        redraw.setToolTipText("Redraw All");
        redraw.setCommand(redrawCmd);
        redraw.addActionListener(this);
        
        //Setting edit tool bar
        
        editToolBar.add(undo);
        undo.setToolTipText("Undo");
        undo.setEnabled(false);
        undo.setCommand(undoCmd);
        undo.addActionListener(this);
        
        editToolBar.add(redo);
        redo.setToolTipText("Redo");
        redo.setEnabled(false);
        redo.setCommand(redoCmd);
        redo.addActionListener(this);
        
        editToolBar.add(cut);
        cut.setEnabled(false);
        cut.setToolTipText("Cut");
        cut.addActionListener(this);
        
        editToolBar.add(copy);
        copy.setEnabled(false);
        copy.setToolTipText("Copy");
        copy.addActionListener(this);
        
        editToolBar.add(paste);
        paste.setEnabled(false);
        paste.setToolTipText("Paste");
        paste.addActionListener(this);
        
        editToolBar.add(delete);
        delete.setToolTipText("Delete");
        delete.setCommand(deleteCmd);
        delete.addActionListener(this);
		
        //Setting draw tool bar
        
        drawToolBar.add(boundary);
        boundary.setToolTipText("Create Boundary");
        boundary.setCommand(boundaryCmd);
        boundary.addActionListener(this);
		
		drawToolBar.add(hole);
        hole.setToolTipText("Add Hole");
        hole.setCommand(holeCmd);
        hole.addActionListener(this);
		
		drawToolBar.add(steelElement);
        steelElement.setToolTipText("Add Steel Element");
        steelElement.setCommand(steelElementCmd);
        steelElement.addActionListener(this);
		
		drawToolBar.add(steelBar);
        steelBar.setToolTipText("Add Steel Bar");
        steelBar.setCommand(steelBarCmd);
        steelBar.addActionListener(this);
        
		//Setting solve tool bar
        
        solveToolBar.add(showDiscretized);
        showDiscretized.setToolTipText("Show/Hide Discretized Section");
        showDiscretized.setCommand(showDiscretizedCmd);
        showDiscretized.addActionListener(this);
		
		solveToolBar.add(solve);
        solve.setToolTipText("Solve");
        solve.setCommand(solveCmd);
        solve.addActionListener(this);
		
		solveToolBar.add(verify);
        verify.setToolTipText("Verify Mx and My");
        verify.setCommand(verifyCmd);
        verify.addActionListener(this);
		
		//Setting status bar
        
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		
        messageField.setEditable(false);
		messageField.setBorder(BorderFactory.createEmptyBorder(2,3,1,3));
        coordField.setEditable(false);
		coordField.setBorder(BorderFactory.createEmptyBorder(2,3,1,3));
        state.setLayout(new GridLayout(1,3,1,1));
        
		grid.setMargin(new Insets(0,1,0,1));
        grid.setCommand(gridCmd);
        grid.addActionListener(this);
        state.add(grid);
        
        snap.setMargin(new Insets(0,1,0,1));
        snap.setCommand(snapCmd);
        snap.addActionListener(this);
        state.add(snap);
        
        statusBar.add(coordField);
        statusBar.add(state);
		statusBar.add(messageField);
        
		console.addListener(consoleHandler);
		
		inOutBar.add(BorderLayout.SOUTH, statusBar);
		inOutBar.add(BorderLayout.NORTH, console);
		
		this.setModel(new Model());
		((SectionController)this.sectionView.getController()).setModel(model);
		((DiagramController)this.diagramView.getController()).setModel(model);
		
        this.getContentPane().add(inOutBar,BorderLayout.SOUTH);
        
		this.setCoord("" + 0.0, "" + 0.0);
		
		this.setInterface();
		
    }
    
//*****************************************************************************
	
    /**
     * Returns the current model.
     * @return The current model.
     */
    public Model getModel() {
        return model;
    }
    
//*****************************************************************************
	
	/** Sets the Model.
	*@param mdl The Model to be set.
	*/
    public void setModel(Model mdl) {
        model = mdl;
		
		((SectionController)this.sectionView.getController()).setModel(model);
		((DiagramController)this.diagramView.getController()).setModel(model);
		
		drawingArea1.setZoom(sectionView.getViewState().getLimits());
        drawingArea1.setSnap(sectionView.getViewState().getSnap());
        drawingArea1.setGridColor(sectionView.getViewState().gridColor);
        drawingArea1.setGrid(sectionView.getViewState().isGridOn());
		drawingArea1.setGridIncs(sectionView.getViewState().getXInc(), sectionView.getViewState().getYInc());
        drawingArea1.setBackground(sectionView.getViewState().getBackground());
		
		drawingArea2.setZoom(diagramView.getViewState().getLimits());
        drawingArea2.setSnap(diagramView.getViewState().getSnap());
        drawingArea2.setGridColor(diagramView.getViewState().gridColor);
        drawingArea2.setGrid(diagramView.getViewState().isGridOn());
		drawingArea2.setGridIncs(diagramView.getViewState().getXInc(), diagramView.getViewState().getYInc());
        drawingArea2.setBackground(diagramView.getViewState().getBackground());
	}
    
//*****************************************************************************
	
	/** Return the SplitPane.
	*@return The SplitPane.
	*/
    public JSplitPane getSplitPane() {
        return splitPane;
    }
	
//*****************************************************************************
	
	/** Return the current ModelState.
	*@return The current ModelState.
	*/
    public ModelState getModelState() {
        return model.getState();
    }
    
//*****************************************************************************
	
	/** Returns the properties.
	*@return The properties.
	*/
    public GeradiaProperties getProperties() {
        return properties;
    }
    
//*****************************************************************************
	
	/** Sets the Interface. */
    public void setInterface() {
        menuBar.removeAll();
        menuBar.add(menuFile);
        itemSave.setEnabled(true);
        itemSaveAs.setEnabled(true);
        itemPrint.setEnabled(true);
        menuExport.setEnabled(true);
		menuJPEG.setEnabled(true);
		menuPS.setEnabled(true);
        menuBar.add(menuEdit);
        menuBar.add(menuView);
        menuBar.add(menuDraw);
		itemBoundary.setEnabled(true);
		itemHole.setEnabled(false);
		itemSteelElement.setEnabled(false);
		itemSteelBar.setEnabled(false);
		menuBar.add(menuSolve);
		itemDiscSection.setEnabled(false);
		itemSolve.setEnabled(false);
		itemVerify.setEnabled(false);
		menuBar.add(menuSettings);
		menuBar.add(menuHelp);
		
        toolPanel.removeAll();
        toolPanel.add(fileToolBar);
        save.setEnabled(true);
        saveAs.setEnabled(true);
        print.setEnabled(true);
		delete.setEnabled(true);
        toolPanel.add(editToolBar);
        toolPanel.add(zoomToolBar);
		toolPanel.add(drawToolBar);
		boundary.setEnabled(true);
		hole.setEnabled(false);
		steelElement.setEnabled(false);
		steelBar.setEnabled(false);
		toolPanel.add(solveToolBar);
		showDiscretized.setEnabled(false);
		solve.setEnabled(false);
		verify.setEnabled(false);
		
		menuBar.add(menuHelp);
		
		console.setVisible(true);
		console.clear();
		
        grid.setEnabled(true);
        snap.setEnabled(true);
		
		consoleHandler.setToCommandMode();
		consoleHandler.setKeybCommand(null);
		
		this.setStateButtons();
		
		sectionView.getViewState().setSnap(true);
		
        drawArea1.removeAll();
        drawArea1.add(drawingArea1, BorderLayout.CENTER);
		drawingArea1.setSnap(true);
		drawingArea1.setRepainting(true);
		drawingArea1.removeAllCommands();
        drawingArea1.setClickedCommand(new SelectCommand(this));
        drawingArea1.setCursor();
		drawingArea1.setGridColor(sectionView.getViewState().gridColor);
        drawArea1.repaint();
        drawArea1.updateUI();
		
		drawArea2.removeAll();
        drawArea2.add(drawingArea2, BorderLayout.CENTER);
		drawingArea2.setSnap(false);
		drawingArea2.setRepainting(true);
		drawingArea2.removeAllCommands();
		drawingArea2.setGridColor(diagramView.getViewState().gridColor);
        drawArea2.repaint();
        drawArea2.updateUI();
		
		splitPane.setDividerLocation(this.getWidth()/2 - this.splitPane.getDividerSize());
		setMessage("Escola de Engenharia da UFMG");
		this.setCoord("" + 0.0, "" + 0.0);
		this.getJMenuBar().updateUI();
		
		this.repaint();
		
    }
	
//*****************************************************************************
	
	/** Adds a Command to the undoList.*/
    public void addToUndo(Command cmd) {
        undoCmd.add(cmd);
        itemUndo.setEnabled(true);
        undo.setEnabled(true);
    }
    
//*****************************************************************************
	
	/** Adds a Command to the redoList.*/
    public void addToRedo(Command cmd) {
        if (!(cmd instanceof DeleteCommand)){
            redoCmd.add(cmd);
            itemRedo.setEnabled(true);
            redo.setEnabled(true);
        }
    }
	
//*****************************************************************************
	
	/** Disables the redo command.*/
    public void nothingToRedo() {
        redoCmd.clear();
        itemRedo.setEnabled(false);
        redo.setEnabled(false);
    }
	
//*****************************************************************************
	
	/** Disables the undo command.*/
    public void nothingToUndo() {
        undoCmd.clear();
        itemUndo.setEnabled(false);
        undo.setEnabled(false);
	}
	
//*****************************************************************************
	
    /**
     * Sets the Status Bar coord text.
     * @param x The first "X" coordinate.
     * @param y The second "Y" coordinate.
     */
    public void setCoord(String x, String y) {
        this.coordField.setText("Coords: " + x + ", " + y);
    }
    
//*****************************************************************************
	
    /**
     * Sets the Status Bar message.
     * @param str The text to be shown
     */
    public void setMessage(String str) {
        this.messageField.setText(str);
	}
	
//*****************************************************************************
	
    /**
     * Appends a message to the output panel.
     * @param str The message to be appended.
     */
    public void addOutputText(String str) {
        this.console.addOutputText(str);
	}
    
//*****************************************************************************
	
    /**
     * Closes the current model and returns true if successfull.
     * @return   A boolean if the current model was closed or not.
     */
    public boolean closeModel() {
        
		boolean ret = true;
        if (model != null) {
            JOptionPane op = new JOptionPane();
            int value = 10;
            value = op.showConfirmDialog(this,
            "Would you like to save " + model.getState().getName()+ " ?", "Warning",
            op.YES_NO_CANCEL_OPTION, op.QUESTION_MESSAGE);
            if (value == op.YES_OPTION){
                saveFileCmd.execute();
            }else if(value == op.CANCEL_OPTION){
                ret = false;
            }
        }
        if (ret) {
            model = null;
            this.setMessage(" ");
            this.setCoord(" ", " ");
            //this.setInterface();
        }
        return ret;
    }
    
//*****************************************************************************
	
	/** Method which executes the desired action to each command.*/
    public void actionPerformed(ActionEvent evt) {
        CommandHolder obj = (CommandHolder)evt.getSource();
		obj.getCommand().execute();
    }
    
//*****************************************************************************
	
	/** Sets the StateButtons of this Interface. */
	public void setStateButtons() {
		grid.setSelected(sectionView.getViewState().isGridOn());
		snap.setSelected(sectionView.getViewState().getSnap());
	};
	
//*****************************************************************************
	
	/** Returns the Console of this Interface.
	*@return The Console of this Interface.
	*/
	public Console getConsole() {
		return console;
	};
	
//*****************************************************************************
	
	/** Returns the ConsoleHandler of this Interface.
	*@return The ConsoleHandler of this Interface.
	*/
	public ConsoleHandler getConsoleHandler() {
		return consoleHandler;
	}
	
//*****************************************************************************
	
	/** Returns the UndoCommand of this Interface.
	*@return The UndoCommand of this Interface.
	*/
	public UndoCommand getUndoCommand() {
		return undoCmd;
	}
	
//*****************************************************************************
	
	/** Returns the RedoCommand of this Interface.
	*@return The RedoCommand of this Interface.
	*/
	public RedoCommand getRedoCommand() {
		return redoCmd;
	}
	
//*****************************************************************************
	
	/** Cleans the results stored at Model.
	*/
	public void cleanResults() {
		//model.getDiscreteModel().getDriver().getFemModel().cleanResults();
	}
	
//*****************************************************************************
	
	/** Returns the SectionView
	*@return The SectionView.
	*/
    public IView getSectionView() {
        return sectionView;
    }
	
//*****************************************************************************
	
	/** Returns the DiagramView
	*@return The DiagramView.
	*/
    public IView getDiagramView() {
        return diagramView;
    }
	
//*****************************************************************************
	
	/** Enables the hole, steelElement, steelBars, showDiscretized and solve buttons.
	* Disables boundary button.
	*/
	public void boundaryClosed() {
		itemBoundary.setEnabled(false);
		itemHole.setEnabled(true);
		itemSteelElement.setEnabled(true);
		itemSteelBar.setEnabled(true);
		itemDiscSection.setEnabled(true);
		itemSolve.setEnabled(true);
		boundary.setEnabled(false);
		hole.setEnabled(true);
		steelElement.setEnabled(true);
		steelBar.setEnabled(true);
		showDiscretized.setEnabled(true);
		solve.setEnabled(true);
	}
	
//*****************************************************************************
	
	/** Enables the verify button.
	*/
	public void problemSolved() {
		verify.setEnabled(true);
		itemVerify.setEnabled(true);
	}
	
//*****************************************************************************
}
