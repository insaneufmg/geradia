package draw;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.geom.*;
import java.util.Vector;
import java.awt.FontMetrics;
import java.awt.Font;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since June 2004
 */
public class AxisDraw extends Draw {
    
    private String label1 = "";
	private String label2 = "";
	private double position = 0;
    
//*****************************************************************************
	
	public AxisDraw() {
		this(new Point(0, 0));
	}
	
//*****************************************************************************
	
	public AxisDraw(Point loc) {
		this(loc, 0);
	}
	
//*****************************************************************************
	
	public AxisDraw(Point loc, double theta) {
		this(loc, theta, " ", " ");
	}
	
//*****************************************************************************
	
	public AxisDraw(Point loc, double theta, String label1, String label2) {
		this(loc, theta, label1, label2, null);
	}
	
//*****************************************************************************
	
	public AxisDraw(Point loc, double theta, String label1, String label2, Object represents) {
		this.setLocation(loc);
		this.setAngle(theta);
		this.setLabel1(label1);
		this.setLabel2(label2);
		this.setRepresents(represents);
		this.setSize(new Dimension(18, 4));
	}
	
//*****************************************************************************
	
    public void setLabel1(String label) {
        this.label1 = label;
    }
    
//*****************************************************************************
	
    public String getLabel1() {
        return label1;
    }
	
//*****************************************************************************
	
    public void setLabel2(String label) {
        this.label2 = label;
    }
    
//*****************************************************************************
	
    public String getLabel2() {
        return label2;
    }
	
//*****************************************************************************
	
	public void setPosition(double position) {
		this.position = position;
	}
	
//*****************************************************************************
	
	public double getPosition() {
		return this.position;
	}
    
//*****************************************************************************
	
	public boolean equals(Object obj) {
		if (super.equals(obj) && obj instanceof AxisDraw) {
			AxisDraw d = (AxisDraw)obj;
		}
		return false;
	}
	
//*****************************************************************************
	
	public Vector getShapes() {return null;}
	
//*****************************************************************************
	
	public void draw(Graphics2D g2) {
		
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		int width = this.getSize().width;
		int height = this.getSize().height;
		
		int MOD = 20;
		int ARROW = 6;
		
		g2.setFont(new Font("Arial", Font.BOLD, 10));
		FontMetrics metrics = g2.getFontMetrics();
		AffineTransform saved = g2.getTransform();
		AffineTransform rotate = AffineTransform.getRotateInstance(this.getAngle()*-1, x, y);
		g2.transform(rotate);
		
		//"X" axis
		g2.drawLine(x+MOD, y, x+MOD-ARROW, y+(ARROW/2));
		g2.drawLine(x+MOD, y, x+MOD-ARROW, y-(ARROW/2));
		g2.drawLine(x, y, x+MOD, y);
		int txtWidth = metrics.stringWidth(label1);
		int txtHeight = metrics.getHeight();
		int centreX = x+MOD - txtWidth / 2;
		int centreY = y + 4;
		g2.drawString(label1, x+MOD+5, centreY);
		
		//"Y" axis
		g2.drawLine(x, y-MOD, x-(ARROW/2), y-MOD+ARROW);
		g2.drawLine(x, y-MOD, x+(ARROW/2), y-MOD+ARROW);
		g2.drawLine(x, y, x, y-MOD);
		txtWidth = metrics.stringWidth(label2);
		txtHeight = metrics.getHeight();
		centreX = x - txtWidth / 2;
		centreY = y-MOD - (txtHeight/2);
		g2.drawString(label2, centreX, y-MOD-5);
		
		g2.setTransform(saved);
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
		
		int W = 10;
		int H = (int)(W/7);
		return new Rectangle(x, (int)(y - H/2), W, H);
	}
	
//*****************************************************************************
}
