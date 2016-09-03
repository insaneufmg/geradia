package draw;

import java.awt.geom.*;
import java.awt.*;
import java.util.Vector;
import java.text.*;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since November 2005
 */
public class Nd0Draw extends Draw {
    
    private String label = "Nd0 = ";
	private double value = 0;
	private Color fillColor = Color.BLACK;
    
//*****************************************************************************
	
	public Nd0Draw() {
	}
	
//*****************************************************************************
	
    public void setValue(double value) {
        this.value = value;
    }
    
//*****************************************************************************
	
    public double getLabel1() {
        return value;
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
		
		//Creating formator
		DecimalFormat fmt = new DecimalFormat();
		DecimalFormatSymbols fsym = new DecimalFormatSymbols();
		fsym.setDecimalSeparator('.');
		fmt.setDecimalFormatSymbols(fsym);
		fmt.applyPattern("####0.00");
		
		g2.setFont(new Font("Arial", Font.BOLD, 10));
		FontMetrics metrics = g2.getFontMetrics();
		AffineTransform saved = g2.getTransform();
		AffineTransform rotate = AffineTransform.getRotateInstance(this.getAngle()*-1, x, y);
		g2.transform(rotate);
		
		label = label + fmt.format(value);
		
		int txtWidth = metrics.stringWidth(label);
		int txtHeight = metrics.getHeight();
		int centreX = x - txtWidth / 2;
		int centreY = y + txtHeight;
		
		Rectangle2D rec = new Rectangle2D.Double(x, y, txtWidth+20, txtHeight+10);
		
		Color currentColor = g2.getColor();
		g2.setColor(fillColor);
		g2.fill(rec);
		g2.setColor(currentColor);
		g2.draw(rec);
		
		g2.drawString(label, x+10, centreY);
		
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
