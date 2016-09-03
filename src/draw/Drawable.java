package draw;

import java.awt.*;
import java.awt.geom.*;

/**
 * Defines the methods that implement a drawer.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since April 2004
 */
public interface Drawable {
	
//*****************************************************************************
	
    /**
     * Draws in a given Graphics.
     *
     * @see   java.awt.Graphics
     */
    public void draw(Graphics2D g);
	
//*****************************************************************************
	
    /**
     * Sets the orientation. The orientation can be HORIZONTAL or
     * VERTICAL.
     *
     * @param orient The orientation.
     */
    public void setOrientation(int orient);
    
//*****************************************************************************
	
	/**
     * Returns the orientation.
     *
     * @return the orientation
     */
    public int getOrientation();
    
//*****************************************************************************
	
	/**
     * Sets the draw position in device coordinates.
     *
     * @param loc draw position
     */
    public void setLocation(Point loc);
    
//*****************************************************************************
	
	/**
     * Returns the draw position in device coordinates.
     *
     * @return the draw position
     */
    public Point getLocation();
    
//*****************************************************************************
	
	/**
     * Sets the draw dimension in divice coordinates
     *
     * @param dim The new dimension
     */
    public void setSize(Dimension dim);
    
//*****************************************************************************
	
	/**
     * Returns the draw dimension
     *
     * @return The draw dimension
     */
    public Dimension getSize();
    
//*****************************************************************************
	
	/**
     * Returns the draw bounds in device units.
     *
     * @return the draw bounds
     */
    public Rectangle getBounds();
    
//*****************************************************************************
	
	/**
     * Returns the draw bounds in device units.
     *
     * @return the draw bounds
     */
    public Shape getRotatedBounds();
    
//*****************************************************************************
	
	/**
     * Draws at arbitrary rotation.
     */
    public void setAngle(double theta);
    
//*****************************************************************************
	
	/**
     * Returns draw angle.
     *
     * @return The angle of rotation.
     */
    public double getAngle();
    
//*****************************************************************************
	
	/**
     * Sets the draw scale.
     */
    public void setScale(double scale);
    
//*****************************************************************************
	
	/**
     * Returns the draw scale.
     *
     * @return The draw scale.
     */
    public double getScale();
	
//*****************************************************************************
}