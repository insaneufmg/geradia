package draw;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Vector;
import gui.DrawingArea;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	October 2005
 */
public class GeneralPathDraw extends Draw {
	
	private GeneralPath gp;
	private Color fillColor;
	private DrawingArea da;
	
//*****************************************************************************
	
    public GeneralPathDraw(GeneralPath gp, DrawingArea da) {
        this.gp = gp;
		this.da = da;
    }
	
//*****************************************************************************
	
	public GeneralPathDraw(GeneralPath gp) {
        this.gp = gp;
    }
	
//*****************************************************************************
	
	public GeneralPathDraw(Rectangle2D rec) {
        if (rec != null) {
			gp = new GeneralPath();
			float x = (float)rec.getX();
			float y = (float)rec.getY();
			float h = (float)rec.getHeight();
			float w = (float)rec.getWidth();
			gp.moveTo(x, y);
			gp.lineTo(x, y+h);
			gp.lineTo(x+w, y+h);
			gp.lineTo(x+w, y);
			gp.closePath();
		}
    }
	
//*****************************************************************************
	
    public GeneralPathDraw() {
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
	
	public Color getFillColor() {
        return fillColor;
    }
	
//*****************************************************************************
	
	public void setFillColor(Color c) {
        fillColor = c;
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
		
		GeneralPath gp2 = new GeneralPath();
		PathIterator pi = gp.getPathIterator(new AffineTransform());
		float[] coords = new float[6];
		int segType = 0;
		while (!pi.isDone()){
			segType = pi.currentSegment(coords);
			
			if (segType == pi.SEG_MOVETO) {
				Point p = da.getViewPoint(new Point2D.Float(coords[0], coords[1]));
				gp2.moveTo((float)p.getX(), (float)p.getY());
			}
			
			if (segType == pi.SEG_LINETO) {
				Point p = da.getViewPoint(new Point2D.Float(coords[0], coords[1]));
				gp2.lineTo((float)p.getX(), (float)p.getY());
			}
			
			if (segType == pi.SEG_QUADTO) {
				Point p1 = da.getViewPoint(new Point2D.Float(coords[0], coords[1]));
				Point p2 = da.getViewPoint(new Point2D.Float(coords[2], coords[3]));
				gp2.quadTo((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY());
			}
			
			if (segType == pi.SEG_CUBICTO) {
				Point p1 = da.getViewPoint(new Point2D.Float(coords[0], coords[1]));
				Point p2 = da.getViewPoint(new Point2D.Float(coords[2], coords[3]));
				Point p3 = da.getViewPoint(new Point2D.Float(coords[4], coords[5]));
				gp2.curveTo((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY(), (float)p3.getX(), (float)p3.getY());
			}
			
			if (segType == pi.SEG_CLOSE) {
				gp2.closePath();
			}
			
			pi.next();
		}
		
		if (fillColor != null) {
			Color currentColor = g2.getColor();
			g2.setColor(fillColor);
			g2.fill(gp2);
			g2.setColor(currentColor);
		}
		
		AffineTransform rot = new AffineTransform();
		rot.rotate(this.getAngle()*(-1), getLocation().x, getLocation().y);
		g2.draw(rot.createTransformedShape(gp2));
		
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
