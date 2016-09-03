package gui.command;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @version 1.0
 * @since November 2003
 */
public class HelpTopicsCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor */
    public HelpTopicsCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {
    	JOptionPane.showMessageDialog(parent, "Sorry, we can't help you by now.", "INSANE Help", JOptionPane.ERROR_MESSAGE);
	}
    
//*****************************************************************************
	
    /**
     * Impossible to undo - not implemented.
     */
    public void undo() {}
    
//*****************************************************************************
	
    /**
     * Impossible to redo - not implemented.
     */
    public void redo() {}
	
//*****************************************************************************

}
