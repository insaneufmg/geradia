package model.geo;

import java.io.*;
import java.awt.geom.*;
import java.util.ArrayList;
import gui.*;

/**
 * A class representing a graphics model.
 *
 * @author 	Fonseca, Flavio & Pitangueira, Roque
 * @since 	October 2005
 * @see     gui.Interface
 * @see     gui.DrawingArea
 */
public class GeometricModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IGeneralPath boundary = new IGeneralPath();
	private ArrayList holes = new ArrayList();
	private ArrayList steelBars = new ArrayList();
	private ArrayList steelElements = new ArrayList();
	
//*****************************************************************************
	
	/** Sets the Boundary
	*@param gp The Boundary.
	*/
	public void setBoundary(IGeneralPath gp) {
		this.boundary = gp;
	}
	
//*****************************************************************************
	
	/** Returns the Boundary
	*@return The Boundary.
	*/
	public IGeneralPath getBoundary() {
		return this.boundary;
	}
	
//*****************************************************************************
	
	/** Adds a Hole
	*@param gp The Hole to be added
	*/
	public void addHole(IGeneralPath gp) {
		this.holes.add(gp);
	}
	
//*****************************************************************************
	
	/** Gets the Holes list
	*@return The Holes list
	*/
	public ArrayList getHolesList() {
		return holes;
	}
	
//*****************************************************************************
	
	/** Gets the last object to be added to the Holes list
	*@return The last object to be added to the Holes list
	*/
	public IGeneralPath getLastHole() {
		IGeneralPath a = (IGeneralPath)holes.get(holes.size()-1);
		return a;
	}
	
//*****************************************************************************
	
	/** Gets the Steel Elements list
	*@return The Steel Elements list
	*/
	public ArrayList getSteelElementsList() {
		return steelElements;
	}
	
//*****************************************************************************
	
	/** Adds a Steel Element
	*@param gp The Steel Element to be added
	*/
	public void addSteelElement(IGeneralPath gp) {
		this.steelElements.add(gp);
	}
	
//*****************************************************************************
	
	/** Gets the last object to be added to the Steel Elements list
	*@return The last object to be added to the Steel Elements list
	*/
	public IGeneralPath getLastSteelElement() {
		IGeneralPath a = (IGeneralPath)steelElements.get(steelElements.size()-1);
		return a;
	}
	
//*****************************************************************************

	/** Gets the Steel Bars list
	*@return The Steel Bars list
	*/
	public ArrayList getSteelBarsList() {
		return steelBars;
	}
	
//*****************************************************************************
	
	/** Adds a Steel Bar
	*@param sb The Steel Bar to be added
	*/
	public void addSteelBar(SteelBar sb) {
		this.steelBars.add(sb);
	}
	
//*****************************************************************************
}
