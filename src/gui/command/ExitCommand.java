package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 * This class is a implementation of the interface Command.
 * its unabled to undo or redo.
 * its "execute" method simple closes the current model and
 * exits the system by calling <pre>System.exit(0);</pre>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.command.Command
 */
public class ExitCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /**
     * Creates new ExitCommand
     *
     * @see gui.command.Command#execute()
     * @param parent The JFrame parent.
     */
    public ExitCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    /**
     * Closes the model and exits the system.
     */
    public void execute() {
        if (parent.closeModel()) {
            System.exit(0);
        }
    }
	
//*****************************************************************************
	
    /**
     * Impossible to undo - Not implemented.
     */
    public void undo() {
    }
	
//*****************************************************************************
	
    /**
     * Impossible to redo - Not implemented.
     */
    public void redo() {
    }
    
//*****************************************************************************
}
