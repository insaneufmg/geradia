package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 */
public class SnapCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new SnapCommand */
    public SnapCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        if (parent.getSectionView().getViewState().getSnap()) {
            parent.getSectionView().getViewState().setSnap(false);
            parent.getSectionView().getDrawingArea().setRepainting(false);
        	parent.getSectionView().getDrawingArea().setSnap(false);
			parent.addOutputText("Snap is now off.");
        } else {
            parent.getSectionView().getViewState().setSnap(true);
        	parent.getSectionView().getDrawingArea().setSnap(true);
			parent.addOutputText("Snap is now on.");
        }
        parent.getSectionView().getDrawingArea().repaint();
		parent.setStateButtons();
    }
	
//*****************************************************************************
	
	/** Impossible to undo - Not implemented. */
    public void redo() {}
    
//*****************************************************************************
	
	/** Impossible to redo - Not implemented. */
    public void undo() {}
    
//*****************************************************************************
}
