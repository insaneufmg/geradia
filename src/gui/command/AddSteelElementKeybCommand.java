package gui.command;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JFrame;
import gui.*;
import model.geo.*;
import java.util.StringTokenizer;

/**
 * A command to add a GeneralPath using the keyboard to enter its coords.
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class AddSteelElementKeybCommand implements Command, Cloneable {
    
    private Interface parent;
    private Controller controller;
    private DrawingArea da;
	private StringTokenizer stk;
	private AddSteelElementMouseCommand cmd;
    
//*****************************************************************************
	
    /** Constructor method. */
    public AddSteelElementKeybCommand(JFrame parent) {
        this.parent = (Interface)parent;
		controller = this.parent.getSectionView().getController();
        da = this.parent.getSectionView().getDrawingArea();
		cmd = (AddSteelElementMouseCommand) this.parent.getSectionView().getDrawingArea().getClickedCommand();
    }
    
//*****************************************************************************
	
	/** The execute method. */
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
		
		try {
			Point2D po = new Point2D.Double(x0+Double.parseDouble(stk.nextToken()), y0+Double.parseDouble(stk.nextToken()));	
			
			if (cmd.getInitialPoint() == null){
				if (controller.contains(po)) {
					cmd.setInitialPoint(po);
					cmd.moveTo(po);
					parent.addOutputText("Initial point at: " + cmd.getInitialPoint().getX() + ", " + cmd.getInitialPoint().getY());
					
					PreviewLineCommand pl = new PreviewLineCommand(parent);
					pl.setInitialPoint(da.getViewPoint(cmd.getInitialPoint()));
					da.setMovedCommand(pl);
					da.setRepainting(true);
					da.repaint();
					parent.addOutputText("Specify next point.");
					parent.getConsole().setLastPoint(new Point2D.Double(po.getX(), po.getY()));
				} else {
					parent.addOutputText("The entire steel element must be inside the boundary.");
				}
			}else{
				if (controller.contains(po)) {
					if(po.getX()!=cmd.getInitialPoint().getX() || po.getY()!=cmd.getInitialPoint().getY()) {
						cmd.setFinalPoint(po);
						cmd.lineTo(po);
						parent.addOutputText("Point at: " + cmd.getFinalPoint().getX() + ", " + cmd.getFinalPoint().getY());
						
						cmd.setInitialPoint(cmd.getFinalPoint());
						PreviewLineCommand pl = new PreviewLineCommand(parent);
						pl.setInitialPoint(da.getViewPoint(cmd.getInitialPoint()));
						da.setMovedCommand(pl);
						da.setRepainting(true);
						da.repaint();
						
						parent.addOutputText("Specify next point.");
						parent.addToUndo((Command)cmd.clone());
						parent.getConsole().setLastPoint(new Point2D.Double(po.getX(), po.getY()));
					} else {
						parent.addOutputText("Final point must be different from initial point.");
						parent.addOutputText("Specify next point.");
					}
				} else {
					parent.addOutputText("The entire steel element must be inside the boundary.");
				}
			}
		 } catch (Exception e) {
				parent.addOutputText("Invalid data. Try: \"0.000, 0.000\"");
		}
	}
	 
//*****************************************************************************
	 
	 /** The undo method. */
	 public void undo() {}
	 
//*****************************************************************************
	
	/** The redo method. */
    public void redo() {}
    
//*****************************************************************************
}
