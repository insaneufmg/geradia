package gui.command;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @version 1.0
 * @since July 2004
 */
public class BugReportCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor */
    public BugReportCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {
		JOptionPane.showMessageDialog(parent,
			"For comments, feedback and bug reports, please send a mail to:\n<html><a href:=\"insane@dees.ufmg.br\">insane@dees.ufmg.br</a></html>",
			"INSANE Bug Report", JOptionPane.INFORMATION_MESSAGE);
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
