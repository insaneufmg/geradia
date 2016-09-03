package gui.command;

import javax.swing.JFrame;
import java.awt.geom.*;
import java.util.*;
import model.geo.*;
import gui.*;
import model.discrete.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class DiscretizeCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Creates new DiscretizeCommand */
    public DiscretizeCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** Execute method. */
	public void execute() {
		
		parent.getModel().getDiscreteModel().getDiscElementsList().clear();
		parent.getModel().getDiscreteModel().getDiscSteelBarsList().clear();
		
		double discPrec = parent.getModel().getState().discPrec;
		
		GeneralPath bound = parent.getModel().getGModel().getBoundary().getGeneralPath();
		ArrayList steelList = parent.getModel().getGModel().getSteelElementsList();
		ArrayList holeList = parent.getModel().getGModel().getHolesList();
		ArrayList steelBarsList = parent.getModel().getGModel().getSteelBarsList();
		
		//Creating out boundary rectangle
		Rectangle2D rec = bound.getBounds2D();
		
		//Defining discrete elements size
		double h = rec.getHeight();
		double w = rec.getWidth();
		double sml = h;
		if (w < sml)
			sml = w;
		double elmSize = sml*discPrec/100;
		
		double y = rec.getMinY() + elmSize/2;
		
		//Creating discrete elements
		while (y<(h+rec.getMinY())) {
			
			double x = rec.getMinX() + elmSize/2;
			
			while (x<(w+rec.getMinX())) {
				
				DiscreteElement delm = new DiscreteElement(x, y, elmSize);
				
				delm.setType(delm.NOTHING);
				
				if (bound.contains(x,y)) delm.setType(delm.CONCRETE);
				
				ListIterator steels = steelList.listIterator();
				while (steels.hasNext()){
					GeneralPath steel = ((IGeneralPath)steels.next()).getGeneralPath();
					if (steel.contains(x,y)) delm.setType(delm.STEEL);
				}
				
				ListIterator holes = holeList.listIterator();
				while (holes.hasNext()){
					GeneralPath hole = ((IGeneralPath)holes.next()).getGeneralPath();
					if (hole.contains(x,y)) delm.setType(delm.NOTHING);
				}
				
				parent.getModel().getDiscreteModel().addDiscElement(delm);
				
				x += elmSize;
			}
			y += elmSize;
		}
		
		//creating discrete steel bars
		ListIterator steelBars = steelBarsList.listIterator();
		while (steelBars.hasNext()){
			SteelBar sb = (SteelBar)steelBars.next();
			DiscreteSteelBar dsb = new DiscreteSteelBar(sb.getX(), sb.getY(), sb.getDiameter());
			parent.getModel().getDiscreteModel().addDiscSteelBar(dsb);
		}
		
		calculateGC();
		calculateCoordsToGC();
		
		this.parent.getSectionView().getController().compose();
		this.parent.getSectionView().getDrawingArea().repaint();
	}
    
//*****************************************************************************
	
	public void calculateGC(){
		
		double sumA = 0;
		double sumAX = 0;
		double sumAY = 0;
		
		ListIterator des = parent.getModel().getDiscreteModel().getDiscElementsList().listIterator();
		while (des.hasNext()){
			DiscreteElement de = (DiscreteElement)des.next();
			double area = de.getDimension()*de.getDimension();
			if (de.getType() == de.NOTHING) {area = 0;}
			double x = de.getX();
			double y = de.getY();
			sumA += area;
			sumAX += (area*x);
			sumAY += (area*y);
		}
		
		double xgc = sumAX / sumA;
		double ygc = sumAY / sumA;
		
		parent.getModel().getDiscreteModel().setGravityCenter(new Point2D.Double(xgc, ygc));
	}
	
//*****************************************************************************
	
	/** Calculate the cooirdinates os all elements with the origin (0,0) at
	* the gravity center.
	*/
	public void calculateCoordsToGC(){
		
		double x0 = parent.getModel().getDiscreteModel().getGravityCenter().getX();
		double y0 = parent.getModel().getDiscreteModel().getGravityCenter().getY();
		
		ListIterator des = parent.getModel().getDiscreteModel().getDiscElementsList().listIterator();
		while (des.hasNext()){
			DiscreteElement de = (DiscreteElement)des.next();
			double x = de.getX() - x0;
			double y = de.getY() - y0;
			de.setXGC(x);
			de.setYGC(y);
		}
		
		ListIterator dsbs = parent.getModel().getDiscreteModel().getDiscSteelBarsList().listIterator();
		while (dsbs.hasNext()){
			DiscreteSteelBar dsb = (DiscreteSteelBar)dsbs.next();
			double x = dsb.getX() - x0;
			double y = dsb.getY() - y0;
			dsb.setXGC(x);
			dsb.setYGC(y);
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
