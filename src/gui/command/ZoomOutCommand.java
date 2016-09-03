package gui.command;

import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class ZoomOutCommand implements Command {
    
    private Interface parent;
    private DrawingArea da;
    
//*****************************************************************************
	
    /** Creates new ZoomOutCommand */
    public ZoomOutCommand(JFrame parent) {
        this.parent = (Interface)parent;
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
    public void execute() {
        Rectangle2D curr = parent.getSectionView().getDrawingArea().getCurrentZoom();
        double w = (curr.getWidth() - curr.getX())/4;
        double h = (curr.getHeight() - curr.getY())/4;
        curr = new Rectangle2D.Double(curr.getX() - w, curr.getY() - h, curr.getWidth() + w, curr.getHeight() + h);
		parent.getSectionView().getDrawingArea().setZoom(curr);
    }
    
//*****************************************************************************
	
    /**
     * Impossible to undo - not implemented.
     */
    public void undo() {}
    
//*****************************************************************************
	
    /**
     * Impossible to redo - not implemented.
     */
    public void redo() {}
	
//*****************************************************************************
}
