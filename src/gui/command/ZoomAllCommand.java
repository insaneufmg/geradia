package gui.command;

import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class ZoomAllCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new ZoomAllCommand */
    public ZoomAllCommand(JFrame parent) {
        this.parent =(Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
		parent.getSectionView().getDrawingArea().setZoom(parent.getSectionView().getViewState().getLimits());
        parent.getSectionView().getDrawingArea().repaint();
    }
	
//*****************************************************************************
	
    public void redo() {}
    
//*****************************************************************************
	
    public void undo() {}
    
//*****************************************************************************
}
