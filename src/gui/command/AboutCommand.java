package gui.command;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;
import gui.dialog.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @version 1.0
 * @since November 2003
 */
public class AboutCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor */
    public AboutCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {
    	new AboutDialog(parent);
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
