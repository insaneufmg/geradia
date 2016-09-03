package gui.command;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.QuadCurve2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;

/**
 * This class is an implementation of Command interface.
 * Its "execute" method gets the current point of DrawingArea
 * and sets its Shape as a quad of a given initial point to
 * this new one.
 *
 * The initial point must be set wen this class is instantiated,
 * otherwise an NullPointerException will occur.
 *
 * It does exists to be set as a command of the DrawingArea to do
 * set the Shape at run time in order to create a preview when adding
 * a quad to the model.
 *
 * <br>Example:<br>
 * <pre>
 * PreviewQuadCommand pl = new PreviewQuadCommand(parent);
 * pl.setInitialPoint(pt0);
 * drawingArea.setMovedCommand(pl);
 *</pre>
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see    DrawingArea#getViewPoint
 * @see    DrawingArea#setShape
 * @see    DrawingArea#mouseMoved
 */
public class PreviewQuadCommand implements Command {
	
	private Interface parent;
	private DrawingArea da;
	private Point init;
	private Point ctrl;
	
//*****************************************************************************
	
	/**
	 * Creates new PreviewQuadCommand and gets the
	 * DrawingArea from parent.
	 *
	 * @param   parent The parent JFrame
	 * @see   Interface#getDrawing
	 */
	public PreviewQuadCommand(JFrame parent) {
		this.parent = (Interface)parent;
		da = this.parent.getSectionView().getDrawingArea();
	}
	
//*****************************************************************************
	
	/**
	 * Gets the DrawingArea current view point and sets
	 * its Shape as a quad from the initial set point to this one.
	 */
	public void execute() {
		try{
			Point fin = da.getViewPoint();
			da.setShape(new QuadCurve2D.Double(init.x, init.y, ctrl.x, ctrl.y, fin.x, fin.y));
		}catch (NullPointerException e) {
			Toolkit.getDefaultToolkit().beep();
			System.out.print(e.toString());
			JOptionPane.showMessageDialog(parent, e.toString(), "ERROR Message", JOptionPane.ERROR_MESSAGE);
		}
	}
	
//*****************************************************************************
	
	/**
	 * Sets the initial point to the preview quad.
	 *
	 * @param    po    The desired point
	 * @see    java.awt.Point
	 */
	public void setInitialPoint(Point po) {
		init = po;
	}
	
//*****************************************************************************
	
	/**
	 * Sets the control point to the preview quad.
	 *
	 * @param    po    The desired point
	 * @see    java.awt.Point
	 */
	public void setCtrlPoint(Point po) {
		ctrl = po;
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