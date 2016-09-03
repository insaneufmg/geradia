package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class SelectAllCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new SelectAllCommand */
    public SelectAllCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
		parent.getSectionView().getController().selectAll();
        parent.getSectionView().getDrawingArea().repaint();
    }
	
//*****************************************************************************
	
    public void undo() {
		parent.getSectionView().getController().unSelectAll();
        parent.getSectionView().getDrawingArea().repaint();
    }
    
//*****************************************************************************
	
    public void redo() {
		this.execute();
    }
    
//*****************************************************************************
}
