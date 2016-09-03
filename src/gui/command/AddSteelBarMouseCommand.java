package gui.command;

import java.awt.Event;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import gui.*;
import model.geo.*;

/**
 * A command to add points using the mouse to enter its coords.
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class AddSteelBarMouseCommand implements Command, Cloneable {
    
    private Interface parent;
    private Controller controller;
    private DrawingArea da;
    private SteelBar p;
	private double dia = 0;
	
//*****************************************************************************
	
    /** Constructor method. */
    public AddSteelBarMouseCommand(JFrame parent) {
        this.parent = (Interface)parent;
		controller = this.parent.getSectionView().getController();
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
	/** The execute method.<br>
	* It adds steel bars using the mouse to enter its coords.
	*/
    public void execute() {
		Point2D po = da.getWorldPoint();
        if (controller.contains(po)) {
            p = new SteelBar(po, dia);
			((SectionController)controller).getModel().getGModel().addSteelBar(p);
            parent.addOutputText("Steel bar (d= " + p.getDiameter()+ " mm) created at: "+ p.getX() + ", " + p.getY()+ ".");
            da.repaint();
            parent.addToUndo((Command)this.clone());
			parent.getConsole().setLastPoint(new Point2D.Double(p.getX(), p.getY()));
        } else {
            parent.addOutputText("The bar must be inside the boundary.");
        }
		controller.compose();
		parent.addOutputText("Specify the center of the bar.");
    }
    
//*****************************************************************************
	
	public void setDiameter(double d) {
		dia = d;
	}
	
//*****************************************************************************
	
	/** The undo method. */
    public void undo() {
        controller.remove(p);
    }
    
//*****************************************************************************
	
	/** The redo method. */
    public void redo() {
        controller.add(p);
    }
    
//*****************************************************************************
	
	/** The clone method. */
   public Object clone() {
        try {
            AddSteelBarMouseCommand cmd = (AddSteelBarMouseCommand)super.clone();
//            cmd.p = (SteelBar)p.clone();
            return cmd;
        }catch (CloneNotSupportedException e) {
            throw new Error("Clonning ERROR - This should never happen!");
        }
    }

//*****************************************************************************
}
