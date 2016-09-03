package gui;

import java.io.*;

/**
 * A class representing a steel bar.
 *
 * @author 	Fonseca, Flavio & Pitangueira, Roque
 * @since 	November 2005
 */
public class IPoint implements Serializable {
	
	private double x;
	private double y;
	
//*****************************************************************************
	
	/**Constructor.*/
	public IPoint() {
	}
	
//*****************************************************************************
	
	/** Constructor.
	*@param x The coordinate X.
	*@param y The coordinate Y.
	*/
	public IPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
//*****************************************************************************
	
	/** Sets the coordinate X of this IPoint.
	*@param a The coordinate X of this IPoint.
	*/
	public void setX(double a) {
		x = a;
	}
	
//*****************************************************************************
	
	/** Returns the coordinate X of this IPoint.
	*@return The coordinate X of this IPoint.
	*/
	public double getX() {
		return x;
	}
	
//*****************************************************************************
	
	/** Sets the coordinate Y of this IPoint.
	*@param a The coordinate Y of this IPoint.
	*/
	public void setY(double a) {
		y = a;
	}
	
//*****************************************************************************
	
	/** Returns the coordinate Y of this IPoint.
	*@return The coordinate Y of this IPoint.
	*/
	public double getY() {
		return y;
	}
	
//*****************************************************************************
}
