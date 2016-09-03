package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 */
public class GridCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new GridCommand */
    public GridCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {
        if (parent.getSectionView().getViewState().isGridOn()) {
            parent.getSectionView().getViewState().setGrid(false);
			parent.getSectionView().getDrawingArea().setGrid(false);
			parent.addOutputText("Grid is now off.");
        } else {
            parent.getSectionView().getViewState().setGrid(true);
			parent.getSectionView().getDrawingArea().setGrid(true);
			parent.addOutputText("Grid is now on.");
        }
        parent.getSectionView().getDrawingArea().repaint();
		parent.setStateButtons();
    }
    
//*****************************************************************************
	
    public void undo() {
    }
	
//*****************************************************************************
	
    public void redo() {
    }
    
//*****************************************************************************
}
