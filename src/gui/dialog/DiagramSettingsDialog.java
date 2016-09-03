package gui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Toolkit;
import java.text.*;
import java.util.Locale;
import gui.*;
import gui.command.LastZoomCommand;
import model.ModelState;

/**
 * A class representing creation dialog on screen.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.Interface
 * @since November 2005
 */
public final class DiagramSettingsDialog extends SimpleDialog {
	
    private Interface parent;
    private JPanel attributesPane = new JPanel();
	
	private JTextField backgroundColorField = new JTextField();
    private JTextField gridColorField = new JTextField();
    private JTextField axisColorField = new JTextField();
	private JTextField diaLineColorField = new JTextField();
    private JTextField diaPointsColorField = new JTextField();
    private JTextField verifyPointColorField = new JTextField();
	
	private JButton chooseBackgroundColor = new JButton("Choose");
    private JButton chooseGridColor = new JButton("Choose");
    private JButton chooseAxisColor = new JButton("Choose");
    private JButton chooseDiaLineColor = new JButton("Choose");
    private JButton chooseDiaPointsColor = new JButton("Choose");
    private JButton chooseVerifyPointColor = new JButton("Choose");
	
	private JColorChooser colorChooser = new JColorChooser();
    private DiagramViewState state;
	
//*****************************************************************************
	
    /**
     * Constructor method, calling super with the especific title.
     *
     * @param parent The parent of the class.
     * @see gui.Interface
     */
    public DiagramSettingsDialog(Interface parent, DiagramViewState s) {
        this.parent = parent;
        state = s;
		
		//Sets up the attributes panel
		attributesPane.setLayout(new GridLayout(6,3,2,2));
        attributesPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Set Diagram View Colors"),
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
		attributesPane.add(new JLabel(" Grid:"));
        gridColorField.setEditable(false);
        gridColorField.setBackground(state.gridColor);
        attributesPane.add(gridColorField);
        attributesPane.add(chooseGridColor);
        chooseGridColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gridColorActionPerformed(e);
            }
        } );
		attributesPane.add(new JLabel(" Axis:"));
        axisColorField.setEditable(false);
        axisColorField.setBackground(state.axisColor);
        attributesPane.add(axisColorField);
        attributesPane.add(chooseAxisColor);
        chooseAxisColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                axisColorActionPerformed(e);
            }
        } );
        attributesPane.add(new JLabel(" Diagram Line:"));
        diaLineColorField.setEditable(false);
        diaLineColorField.setBackground(state.diagramLineColor);
        attributesPane.add(diaLineColorField);
        attributesPane.add(chooseDiaLineColor);
        chooseDiaLineColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diaLineColorActionPerformed(e);
            }
        } );
        attributesPane.add(new JLabel(" Diagram Points:"));
        diaPointsColorField.setEditable(false);
        diaPointsColorField.setBackground(state.pointsColor);
        attributesPane.add(diaPointsColorField);
        attributesPane.add(chooseDiaPointsColor);
        chooseDiaPointsColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                diaPointsColorActionPerformed(e);
            }
        } );
		attributesPane.add(new JLabel(" Verify Point:"));
        verifyPointColorField.setEditable(false);
        verifyPointColorField.setBackground(state.verifyPointColor);
        attributesPane.add(verifyPointColorField);
        attributesPane.add(chooseVerifyPointColor);
        chooseVerifyPointColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verifyPointColorActionPerformed(e);
            }
        } );
		setPane(attributesPane);
		
    }
	
//*****************************************************************************
    
    public int showDialog() {
        return super.showDialog(parent, "Diagram View Settings");
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
            state.axisColor = axisColorField.getBackground();
			state.diagramLineColor = diaLineColorField.getBackground();
			state.pointsColor = diaPointsColorField.getBackground();
			state.verifyPointColor = verifyPointColorField.getBackground();
			
			parent.setModel(parent.getModel());
            this.setVisible(false);
			
			parent.getDiagramView().getController().compose();
			parent.getDiagramView().getDrawingArea().repaint();
			
        } catch (Exception err) {
            Toolkit.getDefaultToolkit().beep();
            this.setVisible(false);
        }
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
	
  	private void axisColorActionPerformed(ActionEvent e) {
        axisColorField.setBackground(colorChooser.showDialog(this, "Choose Axis Color", axisColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void diaLineColorActionPerformed(ActionEvent e) {
        diaLineColorField.setBackground(colorChooser.showDialog(this, "Choose Diagram Line Color", diaLineColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void diaPointsColorActionPerformed(ActionEvent e) {
        diaPointsColorField.setBackground(colorChooser.showDialog(this, "Choose Diagram Points Point Color", diaPointsColorField.getBackground()));
    }
    
//*****************************************************************************
	
	private void verifyPointColorActionPerformed(ActionEvent e) {
        verifyPointColorField.setBackground(colorChooser.showDialog(this, "Choose Verify Point Color", verifyPointColorField.getBackground()));
    }
    
//*****************************************************************************
}
