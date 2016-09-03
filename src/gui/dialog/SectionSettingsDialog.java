package gui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Toolkit;
import java.text.*;
import java.util.Locale;
import gui.*;
import gui.command.ZoomAllCommand;
import gui.command.LastZoomCommand;
import model.ModelState;

/**
 * A class representing creation dialog on screen.<br>
 * This dialog is provided by some tabs like limits,
 * grid and General settings.<br>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.Interface
 * @since November 2005
 */
public final class SectionSettingsDialog extends TabbedDialog {

    private Interface parent;
    private JPanel attributesPane = new JPanel();
    private JPanel gridPane = new JPanel();
	private JPanel limPane = new JPanel();
    
	private DecimalField xField;
    private DecimalField yField;
    private DecimalField widthField;
    private DecimalField heightField;
    private DecimalField gridwidthField;
    private DecimalField gridheightField;
	
	private JTextField backgroundColorField = new JTextField();
    private JTextField gridColorField = new JTextField();
	private JTextField globalAxisColorField = new JTextField();
	private JTextField boundaryColorField = new JTextField();
    private JTextField boundaryFillColorField = new JTextField();
	private JTextField steelColorField = new JTextField();
    private JTextField discElmsColorField = new JTextField();
    private JTextField gravityCenterColorField = new JTextField();
    
	private JButton chooseBackgroundColor = new JButton("Choose");
	private JButton chooseGridColor = new JButton("Choose");
    private JButton chooseGlobalAxisColor = new JButton("Choose");
	private JButton chooseBoundaryColor = new JButton("Choose");
    private JButton chooseBoundaryFillColor = new JButton("Choose");
    private JButton chooseSteelColor = new JButton("Choose");
    private JButton chooseDiscElmsColor = new JButton("Choose");
    private JButton chooseGravityCenterColor = new JButton("Choose");
	
	private java.awt.geom.Rectangle2D.Double limits = new java.awt.geom.Rectangle2D.Double();
    private NumberFormat numberFormat;
    private SectionViewState state;
	
	private JColorChooser colorChooser = new JColorChooser();
	
//*****************************************************************************
	
    /**
     * Constructor method, calling super with the especific title.
     *
     * @param parent The parent of the class.
     * @see gui.Interface
     */
    public SectionSettingsDialog(Interface parent, SectionViewState s) {
        this.parent = parent;
        state = s;
		
        setUpFormat();
		
        //Sets fields according to model
        limits.setRect(state.getLimits());
        xField = new DecimalField(limits.x, 10, numberFormat);
        yField = new DecimalField(limits.y, 10, numberFormat);
        widthField  = new DecimalField(limits.width, 10, numberFormat);
        heightField = new DecimalField(limits.height, 10, numberFormat);
        gridheightField  = new DecimalField(state.getYInc(), 10, numberFormat);
        gridwidthField  = new DecimalField(state.getXInc(), 10, numberFormat);
        
        //Sets up the attributes panel
		attributesPane.setLayout(new GridLayout(7,3,2,2));
        attributesPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Section View colors"),
        BorderFactory.createEmptyBorder(2,2,2,2)));
		attributesPane.setMaximumSize(new Dimension(500,300));
        attributesPane.add(new JLabel(" Background:"));
        backgroundColorField.setEditable(false);
        backgroundColorField.setBackground(state.getBackground());
        attributesPane.add(backgroundColorField);
        attributesPane.add(chooseBackgroundColor);
        chooseBackgroundColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backgroundColorActionPerformed(e);
            }
        } );
        attributesPane.add(new JLabel(" Global Axis:"));
        globalAxisColorField.setEditable(false);
        globalAxisColorField.setBackground(state.globalAxisColor);
        attributesPane.add(globalAxisColorField);
        attributesPane.add(chooseGlobalAxisColor);
        chooseGlobalAxisColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                globalAxisColorActionPerformed(e);
            }
        } );
        attributesPane.add(new JLabel(" Boundary:"));
        boundaryColorField.setEditable(false);
        boundaryColorField.setBackground(state.boundaryColor);
        attributesPane.add(boundaryColorField);
        attributesPane.add(chooseBoundaryColor);
        chooseBoundaryColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boundaryColorActionPerformed(e);
            }
        } );
		attributesPane.add(new JLabel(" Boundary Filling:"));
        boundaryFillColorField.setEditable(false);
        boundaryFillColorField.setBackground(state.boundaryFillColor);
        attributesPane.add(boundaryFillColorField);
        attributesPane.add(chooseBoundaryFillColor);
        chooseBoundaryFillColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boundaryFillColorActionPerformed(e);
            }
        } );
		attributesPane.add(new JLabel(" Steel:"));
        steelColorField.setEditable(false);
        steelColorField.setBackground(state.steelColor);
        attributesPane.add(steelColorField);
        attributesPane.add(chooseSteelColor);
        chooseSteelColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                steelColorActionPerformed(e);
            }
        } );
		attributesPane.add(new JLabel(" Discrete Elements:"));
        discElmsColorField.setEditable(false);
        discElmsColorField.setBackground(state.discElmColor);
        attributesPane.add(discElmsColorField);
        attributesPane.add(chooseDiscElmsColor);
        chooseDiscElmsColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                discElmsColorActionPerformed(e);
            }
        } );
		attributesPane.add(new JLabel(" Gravity Center:"));
        gravityCenterColorField.setEditable(false);
        gravityCenterColorField.setBackground(state.gravityCenterColor);
        attributesPane.add(gravityCenterColorField);
        attributesPane.add(chooseGravityCenterColor);
        chooseGravityCenterColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gravityCenterColorActionPerformed(e);
            }
        } );
		
        // Sets up the limits panel
        JPanel gPane = new JPanel();
        gPane.setLayout(new BoxLayout(gPane, BoxLayout.Y_AXIS));
        gPane.add(limPane);
        limPane.setLayout(new GridLayout(4,2,2,2));
        limPane.setMaximumSize(new Dimension(500,120));
        limPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Limits bounds"),
        BorderFactory.createEmptyBorder(2,2,2,2)));
        limPane.add(new JLabel(" X Start:"));
        limPane.add(new JLabel(" Y Start:"));
        limPane.add(xField);
        limPane.add(yField);
        limPane.add(new JLabel(" Width:"));
        limPane.add(new JLabel(" Height:"));
        limPane.add(widthField);
        limPane.add(heightField);
        
		// Sets up the grid panel
        gPane.add(gridPane);
        gridPane.setLayout(new GridLayout(4,2,2,2));
        gridPane.setMaximumSize(new Dimension(500,120));
        gridPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Grid settings"),
        BorderFactory.createEmptyBorder(2,2,2,2)));
        gridPane.add(new JLabel(" Width Space:"));
        gridPane.add(new JLabel(" Heigt Space:"));
        gridPane.add(gridwidthField);
        gridPane.add(gridheightField);
        gridPane.add(new JLabel(" Color:"));
        gridPane.add(new JLabel());
        gridColorField.setEditable(false);
        gridColorField.setBackground(state.gridColor);
        gridPane.add(gridColorField);
        gridPane.add(chooseGridColor);
        chooseGridColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridColorActionPerformed(e);
            }
        } );
		
        addTab("Attributes", attributesPane);
        addTab("Limits and Grid", gPane);
        
    }
	
//*****************************************************************************
    
    public int showDialog() {
        return super.showDialog(parent, "Settings");
    }
	
//*****************************************************************************
	
    /**
     * This method is executed on OK button click.
     *
     */
    public void approvedPerform() {
        try {
            state.setBackground(backgroundColorField.getBackground());
            state.gridColor = gridColorField.getBackground();
            state.globalAxisColor = globalAxisColorField.getBackground();
			state.boundaryColor = boundaryColorField.getBackground();
			state.boundaryFillColor = boundaryFillColorField.getBackground();
			state.steelColor = steelColorField.getBackground();
			state.discElmColor = discElmsColorField.getBackground();
			state.gravityCenterColor = gravityCenterColorField.getBackground();
			
			limits.x = xField.getValue();
            limits.y = yField.getValue();
            limits.height = heightField.getValue();
            limits.width = widthField.getValue();
            
			state.setXYInc(gridwidthField.getValue(), gridheightField.getValue());
            
			state.setLimits(limits);
            
			parent.setModel(parent.getModel());
            this.setVisible(false);
			
			parent.getSectionView().getController().compose();
			parent.getSectionView().getDrawingArea().repaint();
			parent.getDiagramView().getController().compose();
			parent.getDiagramView().getDrawingArea().repaint();
			
			
        } catch (Exception err) {
            Toolkit.getDefaultToolkit().beep();
            this.setVisible(false);
        }
    }
    
//*****************************************************************************
	
    /**
     * Sets the number formater
     */
    private void setUpFormat() {
        numberFormat = (DecimalFormat)NumberFormat.getNumberInstance(Locale.UK);
        numberFormat.setMaximumFractionDigits(3);
    }
	
//*****************************************************************************

    private void backgroundColorActionPerformed(ActionEvent e) {
        backgroundColorField.setBackground(colorChooser.showDialog(this, "Choose Background Color", backgroundColorField.getBackground()));
    }
    
//*****************************************************************************
	
    private void gridColorActionPerformed(ActionEvent e) {
        gridColorField.setBackground(colorChooser.showDialog(this, "Choose Grid Color", gridColorField.getBackground()));
    }
	
//*****************************************************************************
	
  	private void globalAxisColorActionPerformed(ActionEvent e) {
        globalAxisColorField.setBackground(colorChooser.showDialog(this, "Choose Global Axis Color", globalAxisColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void boundaryColorActionPerformed(ActionEvent e) {
        boundaryColorField.setBackground(colorChooser.showDialog(this, "Choose Boundary Color", boundaryColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void boundaryFillColorActionPerformed(ActionEvent e) {
        boundaryFillColorField.setBackground(colorChooser.showDialog(this, "Choose Boundary Filling Color", boundaryFillColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void steelColorActionPerformed(ActionEvent e) {
        steelColorField.setBackground(colorChooser.showDialog(this, "Choose Steel Color", steelColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void discElmsColorActionPerformed(ActionEvent e) {
        discElmsColorField.setBackground(colorChooser.showDialog(this, "Choose Discrete Elements Color", discElmsColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void gravityCenterColorActionPerformed(ActionEvent e) {
        gravityCenterColorField.setBackground(colorChooser.showDialog(this, "Choose Gravity Center Color", gravityCenterColorField.getBackground()));
    }
	
//*****************************************************************************
}
