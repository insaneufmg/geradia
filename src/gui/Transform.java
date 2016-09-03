package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.text.*;

/**
 * A class to transform coords.
 *
 * @author	Fonseca, Flavio & Pitangueira, Roque
 * @since	January 2004
 */
public class Transform {
	
    /**The unit to represent X world coord.*/
    private double xUnit = 1;
	
    /**The unit to represent Y world coord.*/
    private double yUnit = 1;
	
    /** The precison of the transformation.
     * In number of zeros after coma.
     */
    private int precision = 2;
	
	private AffineTransform transform = new AffineTransform();
    private AffineTransform inverse = new AffineTransform();
    private AffineTransform aux =  new AffineTransform();
    private Rectangle2D limits = new Rectangle2D.Double();
    private Rectangle view = new Rectangle();
    private NumberFormat form;
	
//*****************************************************************************
	
    /** Constructor method*/
    public Transform() {
        setUpFormat();
    }
	
//*****************************************************************************
	
    /** Constructor method*/
    public Transform(Rectangle view, Rectangle2D limits) {
        setUpFormat();
        setTransform(view, limits);
    }
	
//*****************************************************************************
	
    /**
     * Sets the transform to new view and limits
     */
    public void setTransform(Rectangle view, Rectangle2D limits) {
        this.view = view;
		Rectangle2D.Double lim = (Rectangle2D.Double)limits.clone();
        xUnit = (view.width/(lim.width - lim.x));
        yUnit = (view.height/(lim.height - lim.y));
        
        if (xUnit > yUnit){
			xUnit = yUnit;
			lim.width = ((view.width/xUnit) + lim.x);
        }else{
			yUnit = xUnit;
			lim.height = ((view.height/yUnit) + lim.y);
        }
		
        aux.setToIdentity();
        transform.setToIdentity();
        aux.translate(lim.x, lim.y);
        transform.concatenate(aux);
        aux.setToIdentity();
        aux.scale(1/xUnit, -1/yUnit);
        transform.concatenate(aux);
        aux.setToIdentity();
        aux.translate(0, (-1) * (view.height - 1));
        transform.concatenate(aux);
        try {
            inverse = transform.createInverse();
    	}catch (Exception e){
			System.out.println("Unable to create inverse trasform");
		}
		
        this.limits = limits;
		
    }
	
//*****************************************************************************
	
    public Point toView(Point2D p) {
        Point p2 = new Point();
        p2 = (Point)inverse.transform(p, p2);
        return p2;
    }
	
//*****************************************************************************
	
    public Point2D toWorld(Point p) {
        Point2D p2 = new Point2D.Double();
        p2 = transform.transform(p, p2);
        try {
            p2.setLocation(form.parse(form.format(p2.getX())).doubleValue(), 
				form.parse(form.format(p2.getY())).doubleValue());
    	} catch (ParseException e){}
		
	   	return p2;
    }
	
//*****************************************************************************
	
    public Rectangle2D getLimits() {
        return limits;
    }
	
//*****************************************************************************
	
	public void setLimits(Rectangle2D rect) {
		this.setTransform(view, rect);
	}
	
//*****************************************************************************
	
    public Rectangle getView() {
        return view;
    }
	
//*****************************************************************************
	
	public void setView(Rectangle rect) {
		this.setTransform(rect, limits);
	}
	
//*****************************************************************************
	
    public void setPrecision(int i) {
        precision = i;
        setUpFormat();
    }
	
//*****************************************************************************
	
	public int getPrecision() {
		return precision;
	}
	
//*****************************************************************************
	
    /**Sets the number formater*/
    private void setUpFormat() {
        form = (DecimalFormat)NumberFormat.getNumberInstance();
        form.setMaximumFractionDigits(precision);
    }
	
//*****************************************************************************
}
