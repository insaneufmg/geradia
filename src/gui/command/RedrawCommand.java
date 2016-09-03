package gui.command;

import javax.swing.JFrame;
import gui.*;

/** The redraw command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 */
public class RedrawCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Constructor method */
    public RedrawCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** The execute method. */
    public void execute() {
		parent.getSectionView().getController().compose();
		parent.getSectionView().getDrawingArea().removeAllCommands();
		parent.getSectionView().getDrawingArea().setClickedCommand(new SelectCommand(parent));
		parent.getSectionView().getDrawingArea().setRightClickedCommand(new SelectCommand(parent));
        parent.getSectionView().getDrawingArea().repaint();
		parent.getSectionView().getDrawingArea().setCursor();
		parent.getDiagramView().getController().compose();
		parent.getDiagramView().getDrawingArea().removeAllCommands();
		parent.getDiagramView().getDrawingArea().repaint();
		parent.getConsoleHandler().setToCommandMode();
		parent.getConsoleHandler().setKeybCommand(null);
		parent.getConsole().addOutputText("**CANCEL**");
	}
	
//*****************************************************************************
	
	/** Impossible to redo - Not implemented */
    public void redo() {
    }
    
//*****************************************************************************
	
	/** Impossible to undo - Not implemented */
    public void undo() {
    }
    
//*****************************************************************************
}
