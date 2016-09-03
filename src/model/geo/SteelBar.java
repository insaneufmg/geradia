package model.geo;

import java.io.*;
import java.awt.geom.*;
import java.util.ArrayList;
import draw.*;

/**
 * A class representing a steel bar.
 *
 * @author 	Fonseca, Flavio & Pitangueira, Roque
 * @since 	October 2005
 */
public class SteelBar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double x = 0;
	private double y = 0;
	private double diameter = 0;
	
//*****************************************************************************
	
	/** The constructor method */
	public SteelBar() {
	}
	
//*****************************************************************************
	
	/**
	* The constructor method
	*@param a The centre of the bar
	*@param b The diameter of the bar
	*/
	public SteelBar(Point2D a, double b) {
		x = a.getX();
		y = a.getY();
		diameter = b;
	}
	
//*****************************************************************************
	
	/**
	* The constructor method
	*@param a The coordinate X of the centre of the bar
	*@param b The coordinate Y of the centre of the bar
	*@param c The diameter of the bar
	*/
	public SteelBar(double a, double b, double c) {
		x = a;
		y = b;
		diameter = b;
	}
	
//*****************************************************************************
	
	/** Sets the centre of the bar
	*@param p The centre of the bar
	*/
	public void setCentre(Point2D p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
//*****************************************************************************
	
	/** Sets the centre of the bar
	*@param x The coordinate X of the centre of the bar
	*@param y The coordinate Y of the centre of the bar
	*/
	public void setCentre(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
//*****************************************************************************
	
	/** Sets the diameter of the bar
	*@param d The diameter of the bar
	*/
	public void setDiameter(double d) {
		this.diameter = d;
	}
	
//*****************************************************************************
	
	/** Gets the diameter of the bar
	*@return The diameter of the bar
	*/
	public double getDiameter() {
		return this.diameter;
	}
	
//*****************************************************************************
	
	/**
	*@return The X coordinate of the center ofthe bar.
	*/
	 public double getX() {
        return x;
    }
    
//*****************************************************************************
	
	/**
	*@return The Y coordinate of the center ofthe bar.
	*/
    public double getY() {
        return y;
    }
	
//*****************************************************************************
}
