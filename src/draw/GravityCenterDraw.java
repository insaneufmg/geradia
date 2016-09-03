package draw;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;
import java.util.ListIterator;
import model.geo.*;

/**
 * @author 	Fonseca, Flavio & Pitangueira, Roque
 * @since	October 2005
 */
public class GravityCenterDraw extends Draw{
    
//*****************************************************************************
	
    public GravityCenterDraw() {}
    
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
        double scale = this.getScale();
        
        g2.fill(new Ellipse2D.Double((int)(x - scale/2 +1), (int)(y - scale/2), scale, scale));
        if (!this.getLabel().equals("")) {
            g2.drawString(this.getLabel(), (int)(x + scale), (int)(y - scale));
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
        double scale = this.getScale();
		
        int W = (int)(4 + scale);
        int H = (int)(4 + scale); 
        return new Rectangle((int)(x - W/2), (int)(y - H/2), W, H);
    }
	
//*****************************************************************************
}
