package gui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.JComponent;
import gui.Transform;

/**
 *
 * @author	Fonseca, Flavio & Pitangueira, Roque
 * @since	January 2004
 */
public abstract class PrintableGridCanvas extends JComponent
	implements Printable, MouseListener, MouseMotionListener {
    
    private Transform transform = new Transform();
    private Point viewPoint = new Point();
    private Point2D worldPoint = new Point2D.Double();
    private Point2D lastWorldPoint = new Point2D.Double();
    protected boolean grid = false;
    private Color gridColor = Color.black;
    protected double gridIncX = 1;
    protected double gridIncY = 1;
    private boolean snap = true;
	private boolean set = true;
    private double[] zoom = new double[32];
	private boolean firstZoom = true;
	protected boolean drawRect = false;
    
//*****************************************************************************
	
    /** The constructor method*/
    public PrintableGridCanvas() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }
    
//*****************************************************************************
	
    public void setPrecision(int precision) {
        transform.setPrecision(precision);
		this.recompose();
    }
    
//*****************************************************************************
	
    public int getPrecision() {
        return transform.getPrecision();
    }
    
//*****************************************************************************
	
	/**
	 * This method will be executed on any trasform change,
	 * it must be implemented by inherited classes in
	 * order to adjust its drawing to the new trasform.
	 */
	public abstract void recompose();
    
//*****************************************************************************
	
    public void setZoom(Rectangle2D rec) {
        
		if (firstZoom) {
	        zoom[0] = rec.getX();
    	    zoom[1] = rec.getY();
        	zoom[2] = rec.getWidth();
        	zoom[3] = rec.getHeight();
			for(int i = 1; i < 9; i++){
		        for(int j = 31; j > 3; j--){
        		    zoom[j] = zoom[j - 4];
        		}
        	}
 			firstZoom = false;
		}
        
        for(int i = 31; i > 3; i--){
            zoom[i] = zoom[i - 4];
        }
        zoom[0] = rec.getX();
        zoom[1] = rec.getY();
        zoom[2] = rec.getWidth();
        zoom[3] = rec.getHeight();
		
        transform.setLimits(rec);
		
		this.recompose();
		
    }
	
//*****************************************************************************
	
    public Rectangle2D getCurrentZoom() {
        return transform.getLimits();
    }
    
//*****************************************************************************
	
    public void lastZoom() {
        for(int i = 0; i < 28; i++){
            zoom[i] = zoom[i + 4];
        }
        Rectangle2D rec = new Rectangle2D.Double(zoom[0], zoom[1], 
			zoom[2], zoom[3]);
        transform.setLimits(rec);
    	this.recompose();
    }
 	
//*****************************************************************************
	
	public void setGrid(boolean gd) {
        grid = gd;
		this.repaint();
    }
    
//*****************************************************************************
	
    public boolean isGridOn() {
        return grid;
    }
    
//*****************************************************************************
	
    public void setGridColor(Color c) {
        gridColor = c;
    }
    
//*****************************************************************************
	
    public Color getGridColor() {
        return gridColor;
    }
    
//*****************************************************************************
	
    public void setGridIncs(double x, double y) {
        gridIncX = x;
        gridIncY = y;
    }
    
//*****************************************************************************
	
    public void setSnap(boolean snap) {
        this.snap = snap;
   }
   
//*****************************************************************************
	
    public boolean isSnapOn() {
        return snap;
    }
	
//*****************************************************************************
   	
    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(getBounds().width,
        getBounds().height, BufferedImage.TYPE_INT_RGB);
        try{
           	Graphics g = image.getGraphics();
			g.setClip(0, 0, image.getWidth(), image.getHeight());
			set = false;
			transform.setView(g.getClipBounds());
			this.recompose();
           	this.paintComponent(g);
			set = true;
        }catch (Exception exc) {
            exc.printStackTrace();
        }
        return image;
    }
    
//*****************************************************************************
	
    public int print(Graphics g,PageFormat pf,int pageIndex) {
        
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
			set = false;
			transform.setView(g2d.getClipBounds());
			this.recompose();
            this.paintComponent(g2d);
			set = true;
            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE;
        }
    }
    
//*****************************************************************************
	
    public void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D)g;
        
		if (set) {
        	if ((this.getBounds().width != transform.getView().width) ||
        		(this.getBounds().height != transform.getView().height)) {
            
            	transform.setView(this.getBounds());
				this.recompose();
        	}
		}
        
        g2.setColor(this.getBackground());
        g2.fill(g2.getClipBounds());
        
        RenderingHints qualityHints = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON
        );
        qualityHints.put(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY
        );
        g2.setRenderingHints(qualityHints);
        
        g2.setColor(gridColor);
        
        g2.setStroke(new BasicStroke(0.0f, BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_ROUND
        )
        );
        
        if (grid) {
            
            Rectangle2D r = (Rectangle2D)transform.getView();
			
			Point2D xy = getWorldPoint(new Point((int)r.getX(), (int)r.getY()));
			Point2D wh = getWorldPoint(new Point((int)r.getWidth(), (int)r.getHeight()));
            Rectangle2D.Double r2 = new Rectangle2D.Double(xy.getX(), xy.getY(), wh.getX(), wh.getY());
			
			for ( int i = (int)(r2.getX()/gridIncX);
            i <= (int)(r2.getWidth()/gridIncX); i++ ) {
                
				Point p1 = getViewPoint(new Point2D.Double(gridIncX*i, r2.getY()));
                Point p2 = getViewPoint(new Point2D.Double(gridIncX*i, r2.getHeight()));
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
				
            }
            
            for ( int i = (int)(r2.getHeight()/gridIncY);
            i <= (int)(r2.getY()/gridIncY); i++) {
                
                Point p1 = getViewPoint(new Point2D.Double(r2.getX(), gridIncY *i));
                Point p2 = getViewPoint(new Point2D.Double(r2.getWidth(), gridIncY*i));
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            
        }
        
        if (snap && drawRect) {
			g2.setColor(Color.red);
        	g2.drawRect(viewPoint.x - 5, viewPoint.y - 5, 10, 10);
        }
    }
    
//*****************************************************************************
	
    /**
     * Returns the current mouse position in device coordinates.
     */
    public Point getViewPoint() {
        return viewPoint;
    }
    
//*****************************************************************************
	
    /**
     * Returns the device coordinates of a given user point
     */
    public Point getViewPoint(Point2D p) {
        return transform.toView(p);
    }
	
//*****************************************************************************
	
    /**
     * Returns the mouse current position in user coordinates.
     */
    public Point2D getWorldPoint() {
        return worldPoint;
    }
    
//*****************************************************************************
	
    /**
     * Returns the user coordinates of a given device point.
     */
    public Point2D getWorldPoint(Point p) {
        return transform.toWorld(p);
    }
    
//*****************************************************************************
	
    private void setPoints(Point p) {
        viewPoint = p;
        worldPoint = transform.toWorld(p);
        if (snap) {
            worldPoint = snap(worldPoint);
            viewPoint = getViewPoint(worldPoint);
            if (!worldPoint.equals(lastWorldPoint)) {
                this.repaint();
            }
            lastWorldPoint = worldPoint;
        }
    }
    
//*****************************************************************************
	
    /**
     * Snaps point to Grid
     */
    protected Point2D snap(Point2D po){
        
		Point2D.Double p = (Point2D.Double)po;
        Point2D.Double inc = new Point2D.Double(gridIncX, gridIncY);
		
		double x = p.x / inc.x;
		double y = p.y / inc.y;
		
		x = Math.round(x);
		y = Math.round(y);
		
		p.x = x * inc.x;
		p.y =  y * inc.y;
		
        return (Point2D)p;
    }
    
//*****************************************************************************
	
    /**
     * Gets the mouse coords, transform and sets the message field
     * on Interface (The parent) status bar according to that.
     *
     * @param e The mouse event.
     */
    public void mouseMoved(MouseEvent e) {
        Point mouse = new Point(e.getX(), e.getY());
        this.setPoints(mouse);
    }
	
//*****************************************************************************

}
