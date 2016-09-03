package model.discrete;

import java.awt.geom.*;
import java.util.*;
import gui.*;

/** Class representing a discrete model.
* @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
* @since October 2005
*/
public class DiscreteModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList discElms = new ArrayList();
	private ArrayList discSteelBars = new ArrayList();
	private ArrayList diagramPoints = new ArrayList();
	private double xgc;
	private double ygc;
	private double epsilonCD;
	
//*****************************************************************************
	
	public DiscreteModel() {
	}
	
//*****************************************************************************
	
	/** Adds a DiscreteElement to the list.
	*@param delm The DiscreteElement to be added.
	*/
	public void addDiscElement(DiscreteElement delm){
		discElms.add(delm);
	}
	
//*****************************************************************************
	
	/** Gets the Discrete Elements list.
	*@return The Discrete Elements list.
	*/
	public ArrayList getDiscElementsList(){
		return (discElms);
	}
	
//*****************************************************************************
	
	/** Adds a DiscreteSteelBar to the list.
	*@param delm The DiscreteSteelBar to be added.
	*/
	public void addDiscSteelBar(DiscreteSteelBar delm){
		discSteelBars.add(delm);
	}
	
//*****************************************************************************
	
	/** Gets the Discrete Steel Bars list.
	*@return The Discrete Steel Bars list.
	*/
	public ArrayList getDiscSteelBarsList(){
		return (discSteelBars);
	}
	
//*****************************************************************************
	
	/** Adds a Point to the diagram points list.
	*@param p The point to be added.
	*/
	public void addDiagramPoint(IPoint p){
		diagramPoints.add(p);
	}
	
//*****************************************************************************
	
	/** Gets the Diagram Points list.
	*@return The Diagram Points list.
	*/
	public ArrayList getDiagramPointsList(){
		return (diagramPoints);
	}
	
//*****************************************************************************
	
	/** Sets the gravity center.
	*@param p The gravity center.
	*/
	public void setGravityCenter(Point2D p){
		xgc = p.getX();
		ygc = p.getY();
	}
	
//*****************************************************************************
	
	/** Gets the gravity center.
	*@return The gravity center.
	*/
	public Point2D getGravityCenter(){
		return new Point2D.Double(xgc, ygc);
	}
	
//*****************************************************************************
	
	/** Sets the epsilonCD variable.
	*@param epsilonCD The epsilonCD variable.
	*/
	public void setEpsilonCD(double epsilonCD) {
		this.epsilonCD = epsilonCD;
	}
	
//*****************************************************************************
	
	/** Gets this DiscreteElement's epsilonCD variable.
	*@return This DiscreteElement's epsilonCD variable.
	*/
	public double getEpsilonCD() {
		return epsilonCD;
	}
	
//*****************************************************************************
	
	/** Gets the limits of the diagram.
	*@return The limits of the diagram.
	*/
	public Rectangle2D getDiagramLimits() {
		double maxMx =0;
		double maxMy =0;
		double minMx =0;
		double minMy =0;
		
		ListIterator dps = this.diagramPoints.listIterator();
		while (dps.hasNext()) {
			IPoint dp = (IPoint)dps.next();
			if (dps.previousIndex() == 0) {
				maxMx = dp.getX();
				maxMy = dp.getY();
				minMx = dp.getX();
				minMy = dp.getY();
			}
			if (dp.getX() > maxMx) maxMx = dp.getX();
			if (dp.getY() > maxMy) maxMy = dp.getY();
			if (dp.getX() < minMx) minMx = dp.getX();
			if (dp.getY() < minMy) minMy = dp.getY();
		}
		
		double w = maxMx - minMx;
		double h = maxMy - minMy;
		
		Rectangle2D.Double rec = new Rectangle2D.Double(minMx/1000, minMy/1000, w/1000, h/1000); //Converting from N.m to KN.m
		
		return rec;
	}
	
//*****************************************************************************
	
	/** Gets the maximum absolute value of Mx in N.m.
	*@return The maximum absolute value of Mx in N.m.
	*/
	public double getMaxAbsMx() {
		double maxMx =0;
		
		ListIterator dps = this.diagramPoints.listIterator();
		while (dps.hasNext()) {
			IPoint dp = (IPoint)dps.next();
			if (dps.previousIndex() == 0) {
				maxMx = dp.getX();
			}
			if (Math.abs(dp.getX()) > maxMx) maxMx = Math.abs(dp.getX());
		}
		
		return maxMx;
	}
	
//*****************************************************************************
	
	/** Gets the maximum absolute value of My.
	*@return The maximum absolute value of My.
	*/
	public double getMaxAbsMy() {
		double maxMy =0;
		
		ListIterator dps = this.diagramPoints.listIterator();
		while (dps.hasNext()) {
			IPoint dp = (IPoint)dps.next();
			if (dps.previousIndex() == 0) {
				maxMy = dp.getY();
			}
			if (Math.abs(dp.getY()) > maxMy) maxMy = Math.abs(dp.getY());
		}
		
		return maxMy;
	}
	
//*****************************************************************************
}
