package gui.command;

import java.awt.Event;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import java.util.StringTokenizer;
import gui.*;
import model.geo.*;

/**
 * A command to add points using the keyboard to enter its coords.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 */
public class AddSteelBarKeybCommand implements Command, Cloneable {
    
    private Interface parent;
    private Controller controller;
    private DrawingArea da;
    private SteelBar p;
	private StringTokenizer stk;
	private double dia = 0;
	
//*****************************************************************************
	
    /** Constructor method.
	* @param parent The parent of this command.
	*/
    public AddSteelBarKeybCommand(JFrame parent) {
        this.parent = (Interface)parent;
		controller = this.parent.getSectionView().getController();
        da = this.parent.getSectionView().getDrawingArea();
    }
    
//*****************************************************************************
	
	/** The execute method.<br>
	* It adds points using the keyboard to enter its coords.
	*/
    public void execute() {
        String str = parent.getConsole().getLastCommand();
		double x0 = 0;
		double y0 = 0;
		
		if (str.startsWith("@")) {
			String str2 = str.substring(1);
			stk = new StringTokenizer(str2, ", ");
			Point2D ip = parent.getConsole().getLastPoint();
			x0 = ip.getX();
			y0 = ip.getY();
		} else {
			stk = new StringTokenizer(str, ", ");
		}
		
		if (stk.countTokens() == 2) {
			try {
				Point2D po = new Point2D.Double(x0+Double.parseDouble(stk.nextToken()), y0+Double.parseDouble(stk.nextToken()));
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
			}catch (Exception e) {
				parent.addOutputText("Invalid data. Try: \"0.000, 0.000\"");
			}
		} else {
			parent.addOutputText("Invalid data. Try: \"0.000, 0.000\"");
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
            AddSteelBarKeybCommand cmd = (AddSteelBarKeybCommand)super.clone();
//            cmd.p = (SteelBar)p.clone();
            return cmd;
        }catch (CloneNotSupportedException e) {
            throw new Error("Clonning ERROR - This should never happen!");
        }
    }

//*****************************************************************************
}
