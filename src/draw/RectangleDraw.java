package draw;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	October 2005
 */
public class RectangleDraw extends Draw {
	
	private Rectangle2D rec;
	
//*****************************************************************************
	
    public RectangleDraw(Rectangle2D rec) {
        this.rec = rec;
    }
	
//*****************************************************************************
	
    public RectangleDraw() {
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
		
		double x = (float)rec.getX();
		double y = (float)rec.getY();
		double h = (float)rec.getHeight();
		double w = (float)rec.getWidth();
		
		g2.draw(new Line2D.Double(x, y, x, y+h));
		g2.draw(new Line2D.Double(x, y+h, x+w, y+h));
		g2.draw(new Line2D.Double(x+w, y+h, x+w, y));
		g2.draw(new Line2D.Double(x+w, y, x, y));
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
