package gui.command;

import javax.swing.JFrame;
import gui.*;

/** The close steel element command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since Octobtre 2005
 */
public class CloseSteelElementCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Constructor method */
    public CloseSteelElementCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** The execute method. */
    public void execute() {
		((SectionController)parent.getSectionView().getController()).getModel().getGModel().getLastSteelElement().closePath();
		
		parent.getSectionView().getController().compose();
		parent.getSectionView().getDrawingArea().removeAllCommands();
		parent.getSectionView().getDrawingArea().setClickedCommand(new SelectCommand(parent));
		parent.getSectionView().getDrawingArea().setRightClickedCommand(new SelectCommand(parent));
        parent.getSectionView().getDrawingArea().repaint();
		parent.getSectionView().getDrawingArea().setCursor();
		parent.getConsoleHandler().setToCommandMode();
		parent.getConsoleHandler().setKeybCommand(null);
		parent.getConsole().addOutputText("Steel Element closed.");
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
