package gui.command;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;

/**
 * This class ia an implementation of Command interface.
 * Its "execute" method gets the current point of DrawingArea
 * and calls the "select" method of Controller.
 *
 * It does exists to be set as a command of the DrawingArea to do
 * selection on runtime.
 *
 * <br>Example:<br>
 * <pre>
 * SelectCommand selectCmd = new SelectCommandCommand(parent);
 * drawingArea.setClickedCommand(selectCmd);
 *</pre>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see    DrawingArea#getViewPoint
 */
public class SelectCommand implements Command {
    
    private Interface parent;
    private DrawingArea da;
    
//*****************************************************************************
	
    /**
     * Creates new SelectCommand and gets the
     * DrawingArea from parent.
     *
     * @param   parent The parent JFrame
     * @see    Interface#getDrawing
     */
    public SelectCommand(JFrame parent) {
        this.parent = (Interface)parent;
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
    /**
     * Gets the DrawingArea current view point and calls
     * the "select" method on Controller.
     * Repaints drawing in order to color selection.
     *
     * @see    Interface#getController
     */
    public void execute() {
        try{
            Point p = da.getViewPoint();
            parent.getSectionView().getController().select(p);
            da.repaint();
        }catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            System.out.print(e.toString());
            JOptionPane.showMessageDialog(parent, e.toString(), "ERROR Message", JOptionPane.ERROR_MESSAGE);
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
