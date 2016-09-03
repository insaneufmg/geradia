package draw;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;
import model.discrete.*;
import gui.*;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	October 2005
 */
public class DiscreteElementDraw extends Draw {
	
	private DiscreteElement de;
	private SectionViewState viewState;
	private DrawingArea da;
	
//*****************************************************************************
	
    public DiscreteElementDraw(DiscreteElement de, SectionViewState scs, DrawingArea da) {
        this.de = de;
		viewState = scs;
		this.da = da;
    }
	
//*****************************************************************************
	
    public DiscreteElementDraw() {
    }
	
//*****************************************************************************
	
    public boolean equals(Object obj) {
        return false;
    }
	
//*****************************************************************************
	
    public Vector getShapes() {
        return null;
    }
	
//*****************************************************************************
	
    /**
     * Draws in a given Graphics.
     *
     * @see   java.awt.Graphics
     */
    public void draw(Graphics2D g2) {
        
		int x1 = this.getLocation().x;
        int y1 = this.getLocation().y;
		
		Color currentColor = g2.getColor();
		
		double l = de.getDimension();
		double x = de.getX() - l/2;
		double y = de.getY() + l/2;
		
		Point2D p = new Point2D.Double(x, y);
		Point po = da.getViewPoint(p);
		
		Point2D p1 = da.getViewPoint(new Point(0, 0));
		Point po2 = new Point();
		po2.setLocation(l, 0);
		Point2D p2 = da.getViewPoint(po2);
		double dist = p1.distance(p2);
		
		Rectangle2D elm = new Rectangle2D.Double(po.getX(), po.getY(), dist, dist);
		
		Color typeColor = currentColor;
		if (de.getType() == de.NOTHING) typeColor = viewState.getBackground();
		if (de.getType() == de.CONCRETE) typeColor = viewState.boundaryFillColor;
		if (de.getType() == de.STEEL) typeColor = viewState.steelColor;
		
		g2.setColor(typeColor);
		g2.fill(elm);
		g2.setColor(currentColor);
		
		g2.draw(elm);
    }
    
//*****************************************************************************
	
    /**
     * Returns the draw bounds in device units.
     *
     * @return the draw bounds
     */
    public Rectangle getBounds() {
        
        int x1 = this.getLocation().x;
        int y1 = this.getLocation().y;
        int x2 = this.getSize().width;
        int y2 = this.getSize().height;
        
        Rectangle rect = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        
        return rect;
    }
    
//*****************************************************************************
}
