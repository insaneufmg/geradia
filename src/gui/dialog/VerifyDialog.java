package gui.dialog;

import javax.swing.*;
import java.util.Locale;
import java.text.*;
import java.awt.*;
import gui.*;
import model.*;

/**
 * A class representing creation dialog on screen.<br>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.Interface
 * @since October 2005
 */
public final class VerifyDialog extends SimpleDialog {

    private Interface parent;
	private ModelState state;
    private JPanel verifyPane = new JPanel();
	
	private DecimalField mxField;
    private DecimalField myField;
    
	private NumberFormat numberFormat;
	
//*****************************************************************************
	
    /**
     * Constructor method, calling super with the especific title.
     *
     * @param parent The parent of the class.
     * @see gui.Interface
     */
    public VerifyDialog(Interface parent, ModelState s) {
        this.parent = parent;
		state = s;
		
        setUpFormat();
		
        //Sets fields according to model
        mxField  = new DecimalField(state.mx, 10, numberFormat);
        myField = new DecimalField(state.my, 10, numberFormat);
        
		// Sets up the settings panel
        JPanel vPane = new JPanel();
        vPane.setLayout(new BoxLayout(vPane, BoxLayout.Y_AXIS));
        vPane.add(verifyPane);
        verifyPane.setLayout(new GridLayout(2,2,2,2));
        verifyPane.setMaximumSize(new Dimension(500,300));
        verifyPane.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("Verify Mx and My"),
        BorderFactory.createEmptyBorder(2,2,2,2)));
        verifyPane.add(new JLabel(" Mx [kN.m]: "));
        verifyPane.add(mxField);
        verifyPane.add(new JLabel(" My [kN.m]: "));
        verifyPane.add(myField);
        
        setPane(verifyPane);
		
    }
	
//*****************************************************************************
    
    public int showDialog() {
        return super.showDialog(parent, "Verify Mx and My");
    }
	
//*****************************************************************************
	
    /**
     * This method is executed on OK button click.
     *
     */
    public void approvedPerform() {
        try {
			 
			state.mx = mxField.getValue();
            state.my = myField.getValue();
            
            this.setVisible(false);
			
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
