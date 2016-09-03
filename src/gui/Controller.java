package gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/** 
 * A interface defining a model controller.
 *
 * @author Penna, Samuel & Pitangueira, Roque & Fonseca, Flavio
 * @since april 2005
 */
public interface Controller{
    
    /**
     * Creates all drawings necessary to display the model
     */
    public void compose();
	
//*****************************************************************************
    
    /**
     * Draw the componding model - The draws list.
     */
    public void draw(Graphics2D g);
    
//*****************************************************************************
	
    /**
     * Fits the space to the drawing boundaries.
     */
    public void fit();
    
//*****************************************************************************
	
    /**
     * Adds a Draw to the draws list and the necessary part to the model.
     */
    public void add(Object obj);
    
//*****************************************************************************
	
    /**
     * Receives a Draw object and removes it from the
	 * draws list and removes the referent part of the model
     */
    public void remove(Object obj);
    
//*****************************************************************************
	
    /**
     * Remove all items from the draws list and the model.
     */
    public void removeAll();
    
//*****************************************************************************
	
    /**
     * Selects a draw that contains the given point.
     */
    public void select(Point p);
    
//*****************************************************************************
	
    /**
     * Selects all draw in the draws list.
     */
    public void selectAll();
	
//*****************************************************************************
	
    /**
     * Unselects all draw in the draws list.
     */
    public void unSelectAll();
    
//*****************************************************************************
	
    /**
     * Removes all the selected draw in the list and
     * the referent part of the model.
     */
    public List removeSelection();
	
//*****************************************************************************
	
    /**
     * Returns true if there is any selected draw in the list.
     */
    public boolean isSelected();
    
//*****************************************************************************
	
    /**
     * Returns true if some draw of the list contains the
     * given point.
     */
    public boolean contains(Point2D po);
	
//*****************************************************************************
				
	public Object get(Point2D po);
 	
//*****************************************************************************
	
	/**
     * Returns the list of a draw selected.
     */
	public ArrayList getSelection();
	
//*****************************************************************************
	
    /**
     * Returns the location of a draw that contains the given point.
     */
    public Point pick(Point p);
	
//*****************************************************************************
}
