package draw;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	April 2004
 */
public class LineDraw extends Draw {
	
//*****************************************************************************
	
    public LineDraw(int x1, int y1, int x2, int y2 ) {
        this(new Point(x1, y1), new Point(x2, y2));
    }
	
//*****************************************************************************
	
    public LineDraw(Point p1, Point p2) {
        this(p1, p2, null);
    }
	
//*****************************************************************************
	
    public LineDraw(Point p1, Point p2, Object represents) {
        this.setLocation(p1);
        this.setSize(new Dimension(p2.x, p2.y));
        this.setRepresents(represents);
    }
	
//*****************************************************************************
	
    public boolean equals(Object obj) {
        if (super.equals(obj) && obj instanceof LineDraw) {
            LineDraw d = (LineDraw)obj;
            if (d.getP2().equals(getP2())){
                return true;
            }
        }
        return false;
    }
	
//*****************************************************************************
	
    public Point getP1() {
        return this.getLocation();
    }
	
//*****************************************************************************
	
    public Point getP2() {
        Point po = new Point(this.getSize().width, this.getSize().height);
        return po;
    }
	
//*****************************************************************************
	
	public double getLength() {
    	double dx = this.getX2() - this.getX1();
		double dy = this.getY2() - this.getY1();
 		double l = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return l;
	}
	
//*****************************************************************************
	
    public double getX1() {
        return getLocation().x;
    }
	
//*****************************************************************************
	
    public double getY1() {
        return getLocation().y;
    }
	
//*****************************************************************************
	
    public double getX2() {
        return getSize().width;
    }
	
//*****************************************************************************
	
    public double getY2() {
        return getSize().height;
    }
	
//*****************************************************************************
	
    public Vector getShapes() {
        return null;
    }
	
//*****************************************************************************
	
	public double getAngle() {
		double theta;
		double dx = getX2() - getX1();
		double dy = getY2() - getY1();
		theta = Math.atan2(dy, dx);
		return theta;
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
        int x2 = this.getSize().width;
        int y2 = this.getSize().height;
        
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
        if (!this.getLabel().equals("")) {
            double ang = this.getAngle();
            double ang2 = Math.atan2(15, (this.getLength()/2));
            double l = 15/Math.sin(ang2);
            double x = x1 + l*Math.cos(ang+ang2);
            double y = y1 + l*Math.sin(ang+ang2);
            g2.drawString(this.getLabel(), (int)x, (int)y);
        }
            
        
        Color currentColor = g2.getColor();
        
        if (isSelected()) {
            g2.setColor(new Color(255, 0, 0));
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
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
        
        int x1 = this.getLocation().x;
        int y1 = this.getLocation().y;
        int x2 = this.getSize().width;
        int y2 = this.getSize().height;
        
        Rectangle rect = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        
        return rect;
    }
    
//*****************************************************************************
	
    public boolean contains(Point p) {
        
        int x1 = this.getLocation().x;
        int y1 = this.getLocation().y;
        int x2 = this.getSize().width;
        int y2 = this.getSize().height;
        
        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);
        return line.intersects(p.x - 3, p.y -3, 6, 6);
    }
	
//*****************************************************************************
}
