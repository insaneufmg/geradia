package gui;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * A class representing the state of the view.
 * @author	Penna, Samuel & Pitangueira, Roque & Fonseca, Flavio
 * @since   april 2005
 */
public class ViewState implements Serializable {
	
	//  The view background color
    public Color bgcolor = Color.WHITE;
	
	private int precision = 2;
	
    //The grid and boundary color
    public Color gridColor = Color.darkGray;
    public Color boundaryColor = gridColor;
	
    // The limits of drawing model
	private double x = 0;
	private double y = 0;
	private double width = 50;
	private double height = 50;
	
	// The Current zoom of drawing model
	private double zx = 0;
	private double zy = 0;
	private double zwidth = 10;
	private double zheight = 10;
	
	//The grid increments
	private double xInc = 5;
	private double yInc = 5;
	
	private boolean viewGlobalAxis = true;
	private boolean snap = true;
	private boolean grid = true;
	
//*****************************************************************************
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
//*****************************************************************************
	
	public ViewState() {
    }
	
//*****************************************************************************

    public void setLimits(Rectangle2D lim) {
        Rectangle2D.Double limits = new Rectangle2D.Double();
        limits.setRect(lim);
        x = limits.x;
        y = limits.y;
        width = limits.width;
        height = limits.height;
    }
    
//*****************************************************************************
	
    public Rectangle2D.Double getLimits() {
        Rectangle2D.Double limits = new Rectangle2D.Double();
        limits.x = x;
        limits.y = y;
        limits.width = width;
        limits.height = height;
		return limits;
    }
    
//*****************************************************************************
    
    public void setCurrentZoom(Rectangle2D lim) {
        Rectangle2D.Double limits = new Rectangle2D.Double();
        limits.setRect(lim);
        zx = limits.x;
        zy = limits.y;
        zwidth = limits.width;
        zheight = limits.height;
    }
    
//*****************************************************************************
	
    public Rectangle2D.Double getCurrentZoom() {
        Rectangle2D.Double limits = new Rectangle2D.Double();
        limits.x = zx;
        limits.y = zy;
        limits.width = zwidth;
        limits.height = zheight;
        return limits;
    }
	
//*****************************************************************************
	
    /**
     * Sets the Background color.
     * @param c The desired color to be set.
     */
    public void setBackground(Color c) {
        bgcolor = c;
    }
    
//*****************************************************************************
	
    /**
     * Returns the Background color.
     * @return The current background color
     */
    public Color getBackground() {
        return bgcolor;
    }
	
//*****************************************************************************
    
    /**
     * The setOn method
     */
    public void setGrid(boolean is_on) {
        grid = is_on;
    }
    
//*****************************************************************************
	
    /**
     * The getOn method
     */
    public boolean isGridOn() {
        return grid;
    }
    
//*****************************************************************************
	
    /**
     * The setInc method
     */
    public void setXYInc(double x, double y) {
        xInc = x;
        yInc = y;
    }
    
//*****************************************************************************
	
    /**
     * The getXInc method
     */
    public double getXInc() {
        return xInc;
    }
    
//*****************************************************************************
	
    /**
     * The getYInc method
     */
    public double getYInc() {
        return yInc;
    }
    
//*****************************************************************************
	
    /**
     * The setSnap method
     */
    public void setSnap(boolean b) {
        snap = b;
    }
    
//*****************************************************************************
	
    /**
     * The getSnap method
     */
    public boolean getSnap() {
        return snap;
    }
    
//*****************************************************************************
	
    /**
     * The setViewGlobalAxis method
     */
    public void setViewGlobalAxis(boolean b) {
        viewGlobalAxis = b;
    }
    
//*****************************************************************************
	
    /**
     * The getViewGlobalAxis method
     */
    public boolean getViewGlobalAxis() {
        return viewGlobalAxis;
    }
	
//*****************************************************************************
	
	public void setPrecision(int a) {
		precision = a;
	}
	
//*****************************************************************************
	
	public int getPrecision() {
		return precision;
	}
	
//*****************************************************************************
}
