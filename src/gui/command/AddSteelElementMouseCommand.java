package gui.command;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.JFrame;
import gui.*;
import model.geo.*;

/**
 * A command to add a GeneralPath using the mouse to enter its coords.
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class AddSteelElementMouseCommand implements Command, Cloneable {
    
    private Interface parent;
    private Controller controller;
    private DrawingArea da;
    private Point2D ini;
    private Point2D fin;
	private Point vp;
	private IGeneralPath gp = new IGeneralPath();
    
//*****************************************************************************
	
    /** Constructor method. */
    public AddSteelElementMouseCommand(JFrame parent) {
        this.parent = (Interface)parent;
		controller = this.parent.getSectionView().getController();
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
	/** The execute method.*/
    public void execute() {
        
		Point2D po = da.getWorldPoint();
        
        if (ini == null){
			if (controller.contains(po)) {
				ini = po;
				parent.addOutputText("First point at: " + ini.getX() + ", " + ini.getY());
				
				vp = da.getViewPoint(ini);
				gp.moveTo((float)ini.getX(), (float)ini.getY());
				PreviewLineCommand pl = new PreviewLineCommand(parent);
				pl.setInitialPoint(da.getViewPoint(ini));
				da.setMovedCommand(pl);
				da.setRepainting(true);
				da.repaint();
				parent.addOutputText("Specify next point.");
				parent.getConsole().setLastPoint(new Point2D.Double(ini.getX(), ini.getY()));
			} else {
				parent.addOutputText("The entire steel element must be inside the boundary.");
			}
        }else{
			if (controller.contains(po)) {
				if (po.getX()!=ini.getX() || po.getY()!=ini.getY()) {
					fin = po;
					parent.addOutputText("Point at: " + fin.getX() + ", " + fin.getY());
					
					vp = da.getViewPoint(fin);
					gp.lineTo((float)fin.getX(), (float)fin.getY());
					((SectionController)controller).getModel().getGModel().addSteelElement(gp);
					controller.compose();
					ini = fin;
					PreviewLineCommand pl = new PreviewLineCommand(parent);
					pl.setInitialPoint(da.getViewPoint(ini));
					da.setMovedCommand(pl);
					da.setRepainting(true);
					da.repaint();
					parent.getConsole().setLastPoint(new Point2D.Double(fin.getX(), fin.getY()));
					
					parent.addOutputText("Specify next point.");
					parent.addToUndo((Command)this.clone());
				} else {
					parent.addOutputText("Final point must be different from initial point.");
					parent.addOutputText("Specify next point.");
				}
			} else {
				parent.addOutputText("The entire steel element must be inside the boundary.");
			}
        }
     }
	 
//*****************************************************************************
	 
	 /** The undo method.*/
	 public void undo() {
		 controller.remove(gp);
	 }
	 
//*****************************************************************************
	
	/** The redo method.*/
    public void redo() {
		 controller.add(gp);
    }
    
//*****************************************************************************
	
	/** The clone method.*/
    public Object clone() {
        try {
            AddSteelElementMouseCommand cmd = (AddSteelElementMouseCommand)super.clone();
			return cmd;
        }catch (CloneNotSupportedException e) {
            throw new Error("Clonning ERROR - This should never happen!");
        }
    }
	
//*****************************************************************************
	
	/** Returns the initial point of the line being created.<br>
	* It's used to allow the use of both methods of inputing data (mouse and keyboard).
	* @return The initial point of the line being created.
	*/
	public Point2D getInitialPoint() {
		return ini;
	}
	
//*****************************************************************************
	
	/** Sets the initial point of the line being created.<br>
	* It's used to avoid errors when mixing both methods of inputing data (mouse and keyboard).
	* @param a The initial point of the line being created.
	*/
	public void setInitialPoint(Point2D a) {
		ini = a;
	}
	
//*****************************************************************************
	
	/** Sets the final point of the line being created.<br>
	* It's used to avoid errors when mixing both methods of inputing data (mouse and keyboard).
	*@param a The final point of the line being created.
	*/
	public void setFinalPoint(Point2D a) {
		fin = a;
	}
	
//*****************************************************************************
	
	/** Returns the final point of the line being created.<br>
	* It's used to avoid errors when mixing both methods of inputing data (mouse and keyboard).
	*@return The final point of the line being created.
	*/
	public Point2D getFinalPoint() {
		return fin;
	}
	
//*****************************************************************************
	
	/** Sets the initial point of the general path being created.<br>
	* It's used to avoid errors when mixing both methods of inputing data (mouse and keyboard).
	* @param a The initial point of the general path being created.
	*/
	public void moveTo(Point2D a) {
		gp.moveTo((float)a.getX(), (float)a.getY());
	}
	
//*****************************************************************************
	
	/** Adds a line to the general path being created.<br>
	* It's used to avoid errors when mixing both methods of inputing data (mouse and keyboard).
	* @param a The final point of the line being added.
	*/
	public void lineTo(Point2D a) {
		gp.lineTo((float)a.getX(), (float)a.getY());
		((SectionController)controller).getModel().getGModel().addSteelElement(gp);
		controller.compose();
	}
	
//*****************************************************************************

}
