package gui;

import java.awt.*;
import java.awt.geom.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import draw.*;
import model.*;
import model.geo.*;
import model.discrete.*;

/** The controller for this application.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2004
 */
public class DiagramController implements Controller {

	private Model model;
	private DiscreteModel dmodel;
	private IView view;
	private DiagramViewState viewState;
	private ArrayList selections = new ArrayList();
	
	private GeneralPathDraw diagram;
	private ArrayList points = new ArrayList();
	private PointDraw verifyPoint;
	
	private int inc = 1;
	private boolean showPoints = true;
	
//*****************************************************************************
	    
    /**
     * The constructor method
     * @param model a model
     * @see Interface
     */
    public DiagramController(Model model) {
        this.model = model;
    }
	
    /**
     * The constructor method
     * @param model a model
     * @param vs a IView
     * @see Interface
     */
    public DiagramController(Model model, IView vs) {
        this.model = model;
        this.view = vs;
		this.viewState = (DiagramViewState)vs.getViewState();
    }
    
    /**
     * The constructor method
     * @param vs a IView
     * @see Interface
     */
    public DiagramController(IView vs) {
        this.view = vs;
		this.viewState = (DiagramViewState)vs.getViewState();
    }
	
    public DiagramController() {}
	
//*****************************************************************************
	
    public void draw(Graphics2D g) {
        
        g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		Draw draw;
		DrawingArea da = view.getDrawingArea();
		Point p0 = da.getViewPoint(new Point2D.Double(0, 0));
		Point p1 = da.getViewPoint(new Point2D.Double(inc, 0));
		int vInc = (int)(p1.getX() - p0.getX());
		
		//Draws axis
		g.setColor(viewState.axisColor);
		Point c = da.getViewPoint(new Point2D.Double(0,0));
		double l = da.getSize().getWidth();
		double h = da.getSize().getHeight();
		draw = new AxisDraw2();
		((AxisDraw2)draw).setLabel1("Mx [kN.m]");
		((AxisDraw2)draw).setLabel2("My [kN.m]");
		((AxisDraw2)draw).setModX1(l - c.getX());
		((AxisDraw2)draw).setModX2(c.getX());
		((AxisDraw2)draw).setModY1(c.getY());
		((AxisDraw2)draw).setModY2(h - c.getY());
		((AxisDraw2)draw).setVGridInc(vInc);
		((AxisDraw2)draw).setGridInc(inc);
		draw.setLocation(c);
		draw.draw(g);
		
		//Draws the diagram line
		g.setColor(viewState.diagramLineColor);
		if (this.diagram != null) {
				draw = this.diagram;
				draw.draw(g);
			}
		
		//Draws the diagrams points
		if (showPoints) {
			g.setColor(viewState.pointsColor);
			ListIterator dps = points.listIterator();
			while(dps.hasNext()) {
				draw = (Draw)dps.next();
				draw.setScale(6);
				draw.draw(g);
			}
		}
		
		//Draws the verify point
		if (verifyPoint != null) {
			g.setColor(viewState.verifyPointColor);
			draw = this.verifyPoint;
			draw.setScale(6);
			draw.draw(g);
		}
		
		//Draws the gravity center
		g.setColor(viewState.axisColor);
		draw = new Nd0Draw();
		draw.setLocation(new Point(35, 35));
		((Nd0Draw)draw).setValue(model.getState().nd0);
		((Nd0Draw)draw).setFillColor(viewState.bgcolor);
		draw.draw(g);
		
    }
    
//*****************************************************************************
	
    public void fit() {
    }
	
//*****************************************************************************
	
    public void add(Object obj) {
    }
	
//*****************************************************************************
	
    public boolean contains(Point2D po) {
		return false;
	}
   
//*****************************************************************************	
	
    public boolean contains(Line2D l) {
		return false;
	}
	
//*****************************************************************************
	
	public boolean contains(QuadCurve2D q) {
		return false;
	}
	
//*****************************************************************************
	
	public boolean contains(CubicCurve2D c) {
		return false;
	}
	
//*****************************************************************************
	
    public Point pick(Point p) {
		return null;
    }
    
//*****************************************************************************
	
	public void remove(Object obj) {
		this.compose();
	}
	
//*****************************************************************************
	
    public List removeSelection() {
		return null;
	}
    
//*****************************************************************************
	
	public void removeAll() {
		diagram = null;
		points.clear();
	}
	
//*****************************************************************************
    
    public void select(Point p) {
	}
	
//*****************************************************************************
    
	public void selectAll() {
	}
	
//*****************************************************************************
	
	public void unSelectAll() {
	}
	
//*****************************************************************************
	
    public ArrayList getSelection() {
        return selections;
    }
	
//*****************************************************************************
	
	public void compose() {
		
		diagram = null;
		points.clear();
		verifyPoint = null;
		
		DrawingArea da = view.getDrawingArea();
		Point2D p0 = da.getWorldPoint(new Point(0, 0));
		Point2D p1 = da.getWorldPoint(new Point(100, 0));
		double dist = (p1.getX() - p0.getX());
		
		//Setting zoom to show entire diagram
		double a = dmodel.getMaxAbsMx();
		if (dmodel.getMaxAbsMy() > a) 
			a = dmodel.getMaxAbsMy();
		if (a!=0) {
			a = a/1000 + dist;//Converting from N.m to kN.m and adding some border
			Rectangle2D lim = new Rectangle2D.Double(-a, -a, a, a);
			viewState.setLimits(lim);
			viewState.setCurrentZoom(lim);
			
			//Setting scales and grid
			int pow = 0;
			double rest = dmodel.getMaxAbsMx()/1000;
			if ((dmodel.getMaxAbsMy()/1000) > rest) 
				rest = dmodel.getMaxAbsMy()/1000;
			while (rest >10 ) {
				pow++;
				rest = rest / 10;
			}
			inc = (int)Math.pow(10, pow);
			
		}
		da.setGridIncs(inc, inc);
		
		//Setting diagram General Path
		GeneralPath dia = new GeneralPath();
		ListIterator dpl = dmodel.getDiagramPointsList().listIterator();
		while (dpl.hasNext()) {
			IPoint p = (IPoint)dpl.next();
			if (dpl.previousIndex() == 0) {
				dia.moveTo((float)p.getX()/1000, (float)p.getY()/1000);
			} else if (!dpl.hasNext()) {
				dia.lineTo((float)p.getX()/1000, (float)p.getY()/1000);
				dia.closePath();
			} else {
				dia.lineTo((float)p.getX()/1000, (float)p.getY()/1000);
			}
		}
		diagram = new GeneralPathDraw(dia, da);
		
		//Adding calculated points
		ListIterator dps = dmodel.getDiagramPointsList().listIterator();
		while (dps.hasNext()) {
			IPoint dp = (IPoint)dps.next();
			PointDraw dpd = new PointDraw(dp);
			dpd.setLocation(da.getViewPoint(new Point2D.Double(dp.getX()/1000, dp.getY()/1000)));
			points.add(dpd);
		}
		
		//Setting verify point
		IPoint dp = new IPoint(model.getState().mx, model.getState().my);
		if (dp.getX()!=0 || dp.getY()!=0) {
			PointDraw vpd = new PointDraw(dp);
			vpd.setLocation(da.getViewPoint(new Point2D.Double(dp.getX(), dp.getY())));
			verifyPoint = vpd;
		}
		
	}
	
//*****************************************************************************
	
	public Object get(Point2D po) {
		return null;
	}
	
//*****************************************************************************
	
	public boolean isSelected() {
		return false;
	}
	
//*****************************************************************************
	
	/** Sets the Model
	*@param mod The Model to be set.
	*/
	public void setModel(Model mod){
		this.model = mod;
		this.dmodel = mod.getDiscreteModel();
	}
	
//*****************************************************************************
	
	/** Returns the Model
	*@return The Model.
	*/
	public Model getModel(){
		return (this.model);
	}
	
//*****************************************************************************
}
