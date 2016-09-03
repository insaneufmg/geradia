package gui.command;

import javax.swing.JFrame;
import gui.*;

/** The SteelElement command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class SteelElementCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor method. */
    public SteelElementCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** The execute method. */
    public void execute() {
        parent.getSectionView().getDrawingArea().setClickedCommand(new AddSteelElementMouseCommand(parent));
        parent.getSectionView().getDrawingArea().setRightClickedCommand(new CloseSteelElementCommand(parent));
        parent.getSectionView().getDrawingArea().setCursor();
		parent.getConsoleHandler().setToDataMode();
		parent.getConsoleHandler().setKeybCommand(new AddSteelElementKeybCommand(parent));
		parent.addOutputText("Specify initial point.");
    }
    
//*****************************************************************************
	
    /** Impossible to undo - not implemented.*/
    public void undo() {}
    
//*****************************************************************************
	
    /** Impossible to redo - not implemented.*/
    public void redo() {}
	
//*****************************************************************************
}
