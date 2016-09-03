package draw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.util.Vector;
import java.util.ListIterator;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	April 2004
 */
public abstract class Draw implements Drawable,
                                      Cloneable,
                                      Constants,
                                      Selectable {
    
    private Object represents;
	private Point location = new Point(0, 0);
	private Point control  = new Point(0, 0);
	private Point control2 = new Point(0, 0);
	private Dimension dimension = new Dimension(1, 1);
	private double scale = 1;
	private double angle = 0;
	private int orientation = HORIZONTAL;
	private boolean selectable = true;
	private boolean selected = false;
	private String label = "";
	private int labelPosition = NORTH_EAST;
	
//*****************************************************************************
	
	public Draw(){
	}
    
//*****************************************************************************
	
	public abstract Vector getShapes();
	
//*****************************************************************************
	
	/**
	 * Draws in a given Graphics.
	 *
	 * @see   java.awt.Graphics
	 */
	public void draw(Graphics2D g2) {
		
		Vector shapes = getShapes();
		
		ListIterator shps = shapes.listIterator();
		AffineTransform rot = new AffineTransform();
		rot.rotate(this.getAngle()*(-1), location.x, location.y);
		while(shps.hasNext()){
			g2.draw(rot.createTransformedShape((Shape)shps.next()));
		}
		
		if (!this.getLabel().equals("")) {
			double ang = this.getAngle()*(-1);
			double width = this.getBounds().width;
			double x = location.x + Math.cos(ang)*width;
			double y = location.y + Math.sin(ang)*width;
			g2.drawString(this.getLabel(), (int)x, (int)y);
		}
		
		Color currentColor = g2.getColor();
		
		if (isSelected()) {
			g2.setColor(new Color(255, 0, 0));
			Shape s = this.getRotatedBounds();
			g2.draw(s);
		}
        
		g2.setColor(currentColor);
	}
    
//*****************************************************************************
	
	public boolean equals(Object obj) {
        if (obj instanceof Draw) {
            Draw d = (Draw)obj;
            if (d.getLocation().equals(location) &&  d.getSize().equals(dimension) && d.getAngle() == angle){
                return true;
            }
        }
        return false;
    }
    
//*****************************************************************************
	
    /**
     * Sets the object tha this Draw represents
     *
     * @param    obj    The desired object to represent
     */
    public void setRepresents(Object obj) {
        represents = obj;
    }
    
//*****************************************************************************
	
    /**
     * Returns the object that this Draw represents
     *
     * @return    The Object tha this Draw represents
     */
    public Object getRepresents() {
        return represents;
    }
	
//*****************************************************************************
	
    /**
     * Sets the orientation. The orientation can be HORIZONTAL or
     * VERTICAL.
     *
     * @param orient The orientation.
     */
    public void setOrientation(int orient) {
        orientation = orient;
    }
    
//*****************************************************************************
	
    /**
     * Returns the orientation.
     *
     * @return the orientation
     */
    public int getOrientation() {
        return orientation;
    }
	
//*****************************************************************************
	
	/**
	 * Sets the draw position in device coordinates.
	 *
	 * @param loc The new position
	 */
	public void setLocation(Point loc) {
		this.location = loc;
	}
	
//*****************************************************************************
	
	/**
	 * Returns the draw position in device coordinates.
	 *
	 */
	public Point getLocation() {
		return location;
	}
	
//*****************************************************************************
	
	/**
	 * Sets the control point in device coordinates.
	 *
	 * @param ctrl The control point
	 */
	public void setControl(Point ctrl) {
		this.control = ctrl;
	}
	
//*****************************************************************************
	
	/**
	 * Returns the control point in device coordinates.
	 *
	 */
	public Point getControl() {
		return control;
	}
	
//*****************************************************************************
	
	/**
	 * Sets the second control point in device coordinates.
	 *
	 * @param ctrl The second control point
	 */
	public void setControl2(Point ctrl) {
		this.control2 = ctrl;
	}
	
//*****************************************************************************
	
	/**
	 * Returns the second control point in device coordinates.
	 *
	 */
	public Point getControl2() {
		return control2;
	}
	
//*****************************************************************************
	
	/**
	 * Sets the draw position in device coordinates.
	 *
	 * @param dim The new Dimension
	 */
	public void setSize(Dimension dim) {
		this.dimension = dim;
	}
	
//*****************************************************************************
	
	/**
	 * Returns the draw dimension in device coordinates.
	 *
	 * @return The draw dimension
	 */
	public Dimension getSize() {
		return dimension;
	}
	
//*****************************************************************************
	
    /**
     * Returns the draw bounds in device units.
     *
     * @return the draw bounds
     */
    public abstract Rectangle getBounds();
    
//*****************************************************************************
	
    /**
     * Returns the draw bounds in device units.
     *
     * @return the draw bounds
     */
    public Shape getRotatedBounds() {
        AffineTransform rot = new AffineTransform();
        rot.rotate(this.getAngle()*(-1), location.x, location.y);
        return rot.createTransformedShape(getBounds());
    }
	
//*****************************************************************************
	
    /**
     * Returns true if the point is inside draw bounds
     *
     * @return true if bounds contains a point
     */
    public boolean contains(Point p) {
        if (getRotatedBounds().contains(p)){
            return true;
        }else{
            return false;
        }
    }
    
//*****************************************************************************
	
    /**
     * Draws at arbitrary rotation.
     */
    public void setAngle(double theta) {
        this.angle = theta;
    }
    
//*****************************************************************************
	
    /**
     * Returns draw angle.
     *
     * @return The angle of rotation.
     */
    public double getAngle() {
        return angle;
    }
    
//*****************************************************************************
	
    /**
     * Sets the draw scale.
     */
    public void setScale(double scale) {
        this.scale = scale;
    }
    
//*****************************************************************************
	
    /**
     * Returns the draw scale.
     *
     * @return The draw scale.
     */
    public double getScale() {
        return scale;
    }
    
//*****************************************************************************
	
    public boolean isSelected() {
        return selected;
    }
    
//*****************************************************************************
	
    public void setSelected(boolean sel) {
        selected = sel;
    }
    
//*****************************************************************************
	
    public boolean isSelectable() {
        return selectable;
    }
    
//*****************************************************************************
	
    public void setSelectable(boolean sel) {
        selectable = sel;
    }
    
//*****************************************************************************
	
    public String getLabel() {
        return label;
    }
    
//*****************************************************************************
	
    public void setLabel(String label) {
        this.label = label;
    }
    
//*****************************************************************************
	
    public int getLabePosition() {
        return labelPosition;
    }
    
//*****************************************************************************
	
    public void setLabelPosition(int pos) {
        this.labelPosition = pos;
    }
	
//*****************************************************************************
	
    public Object clone() {
        try {
            Draw d = (Draw)super.clone();
            d.location = (Point)location.clone();
            d.dimension = (Dimension)dimension.clone();
            d.label = label;
            return d;
        }catch (CloneNotSupportedException e) {
            throw new Error("Clonning ERROR - This should never happen!");
        }
    }
    
//*****************************************************************************
}
