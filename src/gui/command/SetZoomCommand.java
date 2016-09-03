package gui.command;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import gui.*;

/** The SetZoom command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 */
public class SetZoomCommand implements Command {
    
    private Interface parent;
    private DrawingArea da;
    private int click = 0;
    private Point2D po;
    
//*****************************************************************************
	
    /** Constructor method. */
    public SetZoomCommand(JFrame parent) {
        this.parent = (Interface)parent;
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        
        Point2D point = da.getWorldPoint();
        
        click ++;
        
        if (click % 2 != 0){
            po = point;
            parent.addOutputText("First corner at: " + da.getWorldPoint().getX() + ", " + da.getWorldPoint().getY());
            PreviewZoomCommand pz = new PreviewZoomCommand(parent);
            pz.setInitialPoint(da.getViewPoint());
            da.setMovedCommand(pz);
            da.setRepainting(true);
			parent.addOutputText("Specify second corner.");
        }else{
            da.setZoom(new Rectangle2D.Double(Math.min(po.getX(), point.getX()),
				Math.min(po.getY(), point.getY()),
				Math.max(po.getX(), point.getX()),
				Math.max(po.getY(), point.getY())
				));
            parent.addOutputText("Second corner at: " + point.getX() + ", " + point.getY() + ".");
            
			parent.getSectionView().getController().compose();
			da.removeAllCommands();
			da.setClickedCommand(null);
			da.setRightClickedCommand(new SelectCommand(parent));
			da.setCursor();
			
			da.setMovedCommand(null);
            da.setRepainting(false);
            da.setShape(null);
            da.repaint();
			
        }
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
