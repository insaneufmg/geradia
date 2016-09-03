package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class FitCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new FitCommand */
    public FitCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
		parent.getSectionView().getController().fit();
        parent.getSectionView().getDrawingArea().repaint();
    }
	
//*****************************************************************************
	
    public void redo() {
    }
    
//*****************************************************************************
	
    public void undo() {
    }
    
//*****************************************************************************
}
