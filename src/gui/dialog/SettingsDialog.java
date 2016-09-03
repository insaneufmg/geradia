package gui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Toolkit;
import java.text.*;
import java.util.*;
import gui.*;
import gui.command.LastZoomCommand;
import gui.command.DiscretizeCommand;
import model.ModelState;
import model.discrete.*;

/**
 * A class representing creation dialog on screen.<br>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.Interface
 * @since October 2005
 */
public final class SettingsDialog extends SimpleDialog {

    private Interface parent;
    private JPanel settingsPane = new JPanel();
	
	private DecimalField nd0Field;
    private DecimalField fckField;
    private DecimalField fykField;
	private DecimalField steelEField;
    private DecimalField gamaCField;
    private DecimalField gamaSField;
    private DecimalField discPrecField;
    private DecimalField deltaThetaField;
    private DecimalField resultPrecField;
	private DecimalField iteMaxField;
	
	private NumberFormat numberFormat;
    private ModelState state;
	
//*****************************************************************************
	
    /**
     * Constructor method, calling super with the especific title.
     *
     * @param parent The parent of the class.
     * @see gui.Interface
     */
    public SettingsDialog(Interface parent, ModelState s) {
        this.parent = parent;
        state = s;
		
        setUpFormat();
		
        //Sets fields according to model
        nd0Field  = new DecimalField(state.nd0, 10, numberFormat);
        fckField = new DecimalField(state.fck, 10, numberFormat);
        fykField = new DecimalField(state.fyk, 10, numberFormat);
		steelEField = new DecimalField(state.steelE, 10, numberFormat);
        gamaCField = new DecimalField(state.gamaC, 10, numberFormat);
        gamaSField = new DecimalField(state.gamaS, 10, numberFormat);
        discPrecField = new DecimalField(state.discPrec, 10, numberFormat);
        deltaThetaField = new DecimalField(state.deltaTheta, 10, numberFormat);
        resultPrecField = new DecimalField(state.resultPrec, 10, numberFormat);
		iteMaxField = new DecimalField(state.maxIteration, 10, numberFormat);
        
		// Sets up the settings panel
        JPanel scsPane = new JPanel();
        scsPane.setLayout(new BoxLayout(scsPane, BoxLayout.Y_AXIS));
        scsPane.add(settingsPane);
        settingsPane.setLayout(new GridLayout(10,2,2,2));
        settingsPane.setMaximumSize(new Dimension(500,300));
        settingsPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Settings"),
        BorderFactory.createEmptyBorder(2,2,2,2)));
        settingsPane.add(new JLabel(" Nd0 [kN]: "));
        settingsPane.add(nd0Field);
        settingsPane.add(new JLabel(" fck [MPa]: "));
        settingsPane.add(fckField);
        settingsPane.add(new JLabel(" fyk [MPa]: "));
        settingsPane.add(fykField);
        settingsPane.add(new JLabel(" Steel Elasticity [MPa]: "));
        settingsPane.add(steelEField);
		settingsPane.add(new JLabel(" \u03B3c : "));
        settingsPane.add(gamaCField);
        settingsPane.add(new JLabel(" \u03B3s : "));
        settingsPane.add(gamaSField);
		settingsPane.add(new JLabel(" Discretization precision (%) : "));
        settingsPane.add(discPrecField);
		settingsPane.add(new JLabel(" \u0394\u03B8 [\u00B0]: "));
        settingsPane.add(deltaThetaField);
		settingsPane.add(new JLabel(" Result Precision (%) : "));
        settingsPane.add(resultPrecField);
		settingsPane.add(new JLabel(" Maximum number of iterations : "));
        settingsPane.add(iteMaxField);
		
        setPane(settingsPane);
		
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
            state.nd0 = nd0Field.getValue();
            state.fck = fckField.getValue();
            state.fyk = fykField.getValue();
			state.steelE = steelEField.getValue();
            state.gamaC = gamaCField.getValue();
            state.gamaS = gamaSField.getValue();
            state.discPrec = discPrecField.getValue();
            state.deltaTheta = deltaThetaField.getValue();
            state.resultPrec = resultPrecField.getValue();
			state.maxIteration = (int)iteMaxField.getValue();
			
			double ac = 0;
			GeneralPath bound = parent.getModel().getGModel().getBoundary().getGeneralPath();
			double w = bound.getBounds2D().getWidth();
			double h = bound.getBounds2D().getHeight();
			ac = w * h;
			
			double fc = 0.85 * state.fck / state.gamaC;;
			double ndmax = ac * fc;
			String mes = "Beware!!! The specified Nd0 value is almost the limit for this section.\nThe diagram can be incorrect.";
			
			if (state.nd0 >= ndmax) {
				JOptionPane.showMessageDialog(parent, mes, "Nd0 Warning", JOptionPane.WARNING_MESSAGE);
			}
			
			parent.setModel(parent.getModel());
            this.setVisible(false);
			
			parent.getSectionView().getController().compose();
			parent.getSectionView().getDrawingArea().repaint();
			parent.getDiagramView().getController().compose();
			parent.getDiagramView().getDrawingArea().repaint();
			
			DiscretizeCommand cmd2 = new DiscretizeCommand(parent);
			cmd2.execute();
			
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
}
