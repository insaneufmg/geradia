package gui.command;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;

/**
 * This class ia an implementation of Command interface.
 * Its "execute" method gets the current point of DrawingArea
 * and sets its Shape as a line of a given initial point to
 * this new one.
 *
 * The initial point must be set wen this class is instantiated,
 * otherwise an NullPointerException will occur.
 *
 * It does exists to be set as a command of the DrawingArea to do
 * set the Shape at run time in order to create a preview when adding
 * a line to the model.
 *
 * <br>Example:<br>
 * <pre>
 * PreviewLineCommand pl = new PreviewLineCommand(parent);
 * pl.setInitialPoint(pt0);
 * drawingArea.setMovedCommand(pl);
 *</pre>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see    DrawingArea#getViewPoint
 * @see    DrawingArea#setShape
 * @see    DrawingArea#mouseMoved
 */
public class PreviewZoomCommand implements Command {
    
    private Interface parent;
    private DrawingArea da;
    private Point init;
    
//*****************************************************************************
	
    /**
     * Creates new PreviewZoomCommand and gets the
     * DrawingArea from parent.
     *
     * @param   parent The parent JFrame
     * @see   Interface#getDrawing
     */
    public PreviewZoomCommand(JFrame parent) {
        this.parent = (Interface)parent;
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
    /**
     * Gets the DrawingArea current view point and sets
     * its Shape as a line from the initial set point to this one.
     */
    public void execute() {
        try{
            Point fin = da.getViewPoint();
            da.setShape(new Rectangle(Math.min(init.x, fin.x), Math.min(init.y, fin.y), Math.abs(fin.x - init.x), Math.abs(fin.y - init.y)));
        }catch (NullPointerException e) {
            Toolkit.getDefaultToolkit().beep();
            System.out.print(e.toString());
            JOptionPane.showMessageDialog(parent, e.toString(), "ERROR Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
//*****************************************************************************
	
    /**
     * Sets the initial point to the preview line.
     *
     * @param    po    The desired point
     * @see    java.awt.Point
     */
    public void setInitialPoint(Point po) {
        init = po;
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
