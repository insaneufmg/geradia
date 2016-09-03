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
public class AxisDraw2 extends Draw {
    
    private String label1 = "";
	private String label2 = "";
	private double position = 0;
	private double modX1 = 0;
	private double modX2 = 0;
	private double modY1 = 0;
	private double modY2 = 0;
	private int vGridInc = 0;
	private int gridInc = 0;
    
//*****************************************************************************
	
	public AxisDraw2() {
		this(new Point(0, 0));
	}
	
//*****************************************************************************
	
	public AxisDraw2(Point loc) {
		this(loc, 0);
	}
	
//*****************************************************************************
	
	public AxisDraw2(Point loc, double theta) {
		this(loc, theta, " ", " ");
	}
	
//*****************************************************************************
	
	public AxisDraw2(Point loc, double theta, String label1, String label2) {
		this(loc, theta, label1, label2, null);
	}
	
//*****************************************************************************
	
	public AxisDraw2(Point loc, double theta, String label1, String label2, Object represents) {
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
	
	public void setModX1(double x1) {
		this.modX1 = x1;
	}
	
//*****************************************************************************
	
	public double getModX1() {
		return this.modX1;
	}
	
//*****************************************************************************
	
	public void setModX2(double x2) {
		this.modX2 = x2;
	}
	
//*****************************************************************************
	
	public double getModX2() {
		return this.modX2;
	}
	
//*****************************************************************************
	
	public void setModY1(double y1) {
		this.modY1 = y1;
	}
	
//*****************************************************************************
	
	public double getModY1() {
		return this.modY1;
	}
	
//*****************************************************************************
	
	public void setModY2(double y2) {
		this.modY2 = y2;
	}
	
//*****************************************************************************
	
	public double getModY2() {
		return this.modY2;
	}
	
//*****************************************************************************
	
	public void setGridInc(int i) {
		this.gridInc = i;
	}
	
//*****************************************************************************
	
	public int getGridInc() {
		return this.gridInc;
	}
	
//*****************************************************************************
	
	public void setVGridInc(int i) {
		this.vGridInc = i;
	}
	
//*****************************************************************************
	
	public int getVGridInc() {
		return this.vGridInc;
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
		
		int x1MOD = (int)modX1 - 10;
		int x2MOD = (int)modX2 - 10;
		int y1MOD = (int)modY1 - 10;
		int y2MOD = (int)modY2 - 10;
		int ARROW = 6;
		
		g2.setFont(new Font("Arial", Font.BOLD, 10));
		FontMetrics metrics = g2.getFontMetrics();
		AffineTransform saved = g2.getTransform();
		AffineTransform rotate = AffineTransform.getRotateInstance(this.getAngle()*-1, x, y);
		g2.transform(rotate);
		
		//"X" axis
		g2.drawLine(x+x1MOD, y, x+x1MOD-ARROW, y+(ARROW/2));
		g2.drawLine(x+x1MOD, y, x+x1MOD-ARROW, y-(ARROW/2));
		g2.drawLine(x, y, x+x1MOD, y);
		g2.drawLine(x, y, x-x2MOD, y);
		int txtWidth = metrics.stringWidth(label1);
		int txtHeight = metrics.getHeight();
		int centreX = x+x1MOD - txtWidth / 2;
		int centreY = y + 4;
		g2.drawString(label1, x+x1MOD-45, centreY-10);
		
		//"Y" axis
		g2.drawLine(x, y-y1MOD, x-(ARROW/2), y-y1MOD+ARROW);
		g2.drawLine(x, y-y1MOD, x+(ARROW/2), y-y1MOD+ARROW);
		g2.drawLine(x, y, x, y-y1MOD);
		g2.drawLine(x, y, x, y+y2MOD);
		txtWidth = metrics.stringWidth(label2);
		txtHeight = metrics.getHeight();
		centreX = x - txtWidth / 2;
		centreY = y-y1MOD - (txtHeight/2);
		g2.drawString(label2, centreX+35, y-y1MOD+10);
		
		//X scale marks
		int restX = x1MOD-ARROW-15;
		int scaX = x + vGridInc;
		int number = gridInc;
		String number2 = "";
		while (restX > vGridInc) {
			g2.drawLine(scaX, y-3, scaX, y+3);
			number2 = Integer.toString(number);
			txtWidth = metrics.stringWidth(number2);
			txtHeight = metrics.getHeight();
			centreX = scaX - txtWidth / 2;
			centreY = y - (txtHeight/2);
			g2.drawString(number2, centreX, y+15);
			number = number + gridInc;
			scaX = scaX + vGridInc;
			restX = restX - vGridInc;
		}
		restX = x2MOD-ARROW-15;
		scaX = x - vGridInc;
		number = -gridInc;
		while (restX > vGridInc) {
			g2.drawLine(scaX, y-3, scaX, y+3);
			number2 = Integer.toString(number);
			txtWidth = metrics.stringWidth(number2);
			txtHeight = metrics.getHeight();
			centreX = scaX - txtWidth / 2;
			centreY = y - (txtHeight/2);
			g2.drawString(number2, centreX, y+15);
			number = number - gridInc;
			scaX = scaX - vGridInc;
			restX = restX - vGridInc;
		}
		
		//Y scale marks
		int restY = y1MOD-ARROW-15;
		int scaY = y - vGridInc;
		number = gridInc;
		while (restY > vGridInc) {
			g2.drawLine(x-3, scaY, x+3, scaY);
			number2 = Integer.toString(number);
			txtWidth = metrics.stringWidth(number2);
			txtHeight = metrics.getHeight();
			centreX = x - txtWidth / 2;
			centreY = scaY + (txtHeight/2);
			g2.drawString(number2, centreX+15, centreY);
			number = number + gridInc;
			scaY = scaY - vGridInc;
			restY = restY - vGridInc;
		}
		restY = y2MOD-ARROW-15;
		scaY = y + vGridInc;
		number = -gridInc;
		while (restY > vGridInc) {
			g2.drawLine(x-3, scaY, x+3, scaY);
			number2 = Integer.toString(number);
			txtWidth = metrics.stringWidth(number2);
			txtHeight = metrics.getHeight();
			centreX = x - txtWidth / 2;
			centreY = scaY + (txtHeight/2);
			g2.drawString(number2, centreX+15, centreY);
			number = number - gridInc;
			scaY = scaY + vGridInc;
			restY = restY - vGridInc;
		}
		
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
