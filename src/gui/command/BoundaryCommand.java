package gui.command;

import javax.swing.JFrame;
import gui.*;

/** The Boundary command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class BoundaryCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor method. */
    public BoundaryCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** The execute method. */
    public void execute() {
        parent.getSectionView().getDrawingArea().setClickedCommand(new AddBoundaryMouseCommand(parent));
        parent.getSectionView().getDrawingArea().setRightClickedCommand(new CloseBoundaryCommand(parent));
        parent.getSectionView().getDrawingArea().setCursor();
		parent.getConsoleHandler().setToDataMode();
		parent.getConsoleHandler().setKeybCommand(new AddBoundaryKeybCommand(parent));
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
