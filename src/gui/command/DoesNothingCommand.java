package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 * @see gui.command.Command
 */
public class DoesNothingCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /**
     * Creates new DoesNothingCommand
     *
     * @see gui.command.Command#execute()
     * @param parent The JFrame parent.
     */
    public DoesNothingCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {}
	
//*****************************************************************************
	
    public void undo() {}
	
//*****************************************************************************
	
    public void redo() {}
    
//*****************************************************************************
}
