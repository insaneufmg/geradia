package draw;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;
import java.util.ListIterator;
import gui.IPoint;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	April 2004
 */
public class PointDraw extends Draw{
    
//*****************************************************************************
	
    public PointDraw() {
        this(new Point(0, 0));
    }
    
//*****************************************************************************
	
    public PointDraw(Point2D p) {
        this(new Point((int)p.getX(), (int)p.getY()), null);
    }
	
//*****************************************************************************
	
	public PointDraw(IPoint p) {
        this(new Point((int)p.getX(), (int)p.getY()), null);
    }
	
//*****************************************************************************
	
	public PointDraw(Point loc) {
        this(loc, null);
    }
	
//*****************************************************************************
	
    public PointDraw(Point loc, Object represents) {
        this.setLocation(loc);
        this.setRepresents(represents);
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
        
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        int width = this.getSize().width;
        int height = this.getSize().height;
        double scale = this.getScale();
        
        int W = (int)(width * scale);
        int H = (int)(height * scale); 
        
        g2.fill(new Ellipse2D.Double((int)(x - W/2), (int)(y - H/2), W, H));
        if (!this.getLabel().equals("")) {
            g2.drawString(this.getLabel(), (int)(x + W), (int)(y - H));
        }
        
        Color currentColor = g2.getColor();
        
        if (isSelected()) {
            g2.setColor(new Color(255, 0, 0));
            g2.draw(getBounds());
        }
        
        g2.setColor(currentColor);
    } 
    
//*****************************************************************************
	
    /**
     * Returns the draw bounds in device units.
     *
     * @return the draw bounds
     */
    public Rectangle getBounds() {
        
        int x = this.getLocation().x;
        int y = this.getLocation().y;
        int width = this.getSize().width;
        int height = this.getSize().height;
        double scale = this.getScale();
		
        int W = (int)(4 + width * scale);
        int H = (int)(4+ height * scale); 
        return new Rectangle((int)(x - W/2), (int)(y - H/2), W, H);
    }
	
//*****************************************************************************
}
