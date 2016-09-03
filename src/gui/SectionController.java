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
 * @since January 2004
 */
public class SectionController implements Controller {

	private Model model;
	private GeometricModel gmodel;
	private DiscreteModel dmodel;
	private IView view;
	private SectionViewState viewState;
	private ArrayList selections = new ArrayList();
	
	private GeneralPathDraw boundary;
    private ArrayList holes = new ArrayList();
	private ArrayList steelElements = new ArrayList();
	private ArrayList steelBars = new ArrayList();
	
	private ArrayList discElms = new ArrayList();
	
//*****************************************************************************
	
    /**
     * The constructor method
     * @param model a model
     * @see Interface
     */
    public SectionController(Model model) {
        this.model = model;
    }
	
    /**
     * The constructor method
     * @param model a model
     * @param vs a IView
     * @see Interface
     */
    public SectionController(Model model, IView vs) {
        this.model = model;
        this.view = vs;
		this.viewState = (SectionViewState)vs.getViewState();
    }
    
    /**
     * The constructor method
     * @param vs a IView
     * @see Interface
     */
    public SectionController(IView vs) {
        this.view = vs;
		this.viewState = (SectionViewState)vs.getViewState();
    }
	
    public SectionController() {}
	
//*****************************************************************************
	
    public void draw(Graphics2D g) {
        
        g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		Draw draw;
		Point2D p;
		Point ctrl;
		DrawingArea da = view.getDrawingArea();
		
		//Draws global axis if selected
		if (viewState.getViewGlobalAxis()) {
			g.setColor(viewState.globalAxisColor);
			draw = new AxisDraw();
			((AxisDraw)draw).setLabel1("X");
			((AxisDraw)draw).setLabel2("Y");
			draw.setLocation(new Point(10, da.getHeight()-10));
			draw.draw(g);
		}
		
		if (!viewState.showDiscretized) {
			
			//Draws the boundary
			g.setColor(viewState.boundaryColor);
			if (this.boundary != null) {
				draw = this.boundary;
				((GeneralPathDraw)draw).setFillColor(viewState.boundaryFillColor);
				draw.draw(g);
			}
			
			//Draws the Steel Elements
			g.setColor(viewState.boundaryColor);
			ListIterator ses = steelElements.listIterator();
			while(ses.hasNext()) {
				draw = (Draw)ses.next();
				((GeneralPathDraw)draw).setFillColor(viewState.steelColor);
				draw.draw(g);
			}
			
			//Draws the Holes
			g.setColor(viewState.boundaryColor);
			ListIterator hs = holes.listIterator();
			while(hs.hasNext()) {
				draw = (Draw)hs.next();
				((GeneralPathDraw)draw).setFillColor(viewState.getBackground());
				draw.draw(g);
			}
			
			//Draws the Steel Bars
			g.setColor(viewState.steelColor);
			ListIterator sbs = steelBars.listIterator();
			while(sbs.hasNext()) {
				draw = (Draw)sbs.next();
				Point2D p1 = da.getViewPoint(new Point(0, 0));
				Point2D p2 = da.getViewPoint(new Point(1, 0));
				double sca = p1.distance(p2);
				draw.setScale(sca/10);
				draw.draw(g);
			}
			
		}else {
			
			//Draws the discrete elements
			g.setColor(viewState.discElmColor);
			ListIterator des = discElms.listIterator();
			while(des.hasNext()) {
				draw = (Draw)des.next();
				draw.draw(g);
			}
			
			//Draws the Steel Bars
			g.setColor(viewState.steelColor);
			ListIterator sbs = steelBars.listIterator();
			while(sbs.hasNext()) {
				draw = (Draw)sbs.next();
				Point2D p1 = da.getViewPoint(new Point(0, 0));
				Point2D p2 = da.getViewPoint(new Point(1, 0));
				double sca = p1.distance(p2);
				draw.setScale(sca/10);
				draw.draw(g);
			}
			
			//Draws the gravity center
			if (dmodel.getGravityCenter()!=null) {
				g.setColor(viewState.gravityCenterColor);
				draw = new GravityCenterDraw();
				Point loc = da.getViewPoint(dmodel.getGravityCenter());
				draw.setLocation(loc);
				draw.setScale(6);
				draw.setLabel("GC");
				draw.draw(g);
			}
			
		}
		
    }
    
//*****************************************************************************
	
    public void fit() {
    }
	
//*****************************************************************************
	
    public void add(Object obj) {
    }
	
//*****************************************************************************
	
    public boolean contains(Point2D po) {
		
		GeneralPath gp = gmodel.getBoundary().getGeneralPath();
		boolean answer = false;
		
		//Verifying if the point is inside the boundary
		if (gp.contains(po)) {
			answer = true;
		}
		
		//Verifying if the point is over the boundary
		PathIterator pi = gp.getPathIterator(new AffineTransform());
		double[] coords = new double[6];
		int segType = 0;
		double iniX = 0;
		double iniY = 0;
		double lastX = 0;
		double lastY = 0;
		
		while (!pi.isDone()){
			segType = pi.currentSegment(coords);
			
			if (segType == pi.SEG_MOVETO) {
				Point2D p = new Point2D.Double(coords[0], coords[1]);
				iniX = p.getX();
				iniY = p.getY();
				lastX = coords[0];
				lastY = coords[1];
			}
			
			if (segType == pi.SEG_LINETO) {
				Point2D p = new Point2D.Double(coords[0], coords[1]);
				Line2D.Double line = new Line2D.Double(lastX, lastY, p.getX(), p.getY());
				if (lineContainsPoint(line, po)) {
					answer = true;
				}
				lastX = coords[0];
				lastY = coords[1];
				
			}
			
			if (segType == pi.SEG_QUADTO) {
				//Point p1 = da.getViewPoint(new Point2D.Float(coords[0], coords[1]));
				//Point p2 = da.getViewPoint(new Point2D.Float(coords[2], coords[3]));
				//gp2.quadTo((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY());
			}
			
			if (segType == pi.SEG_CUBICTO) {
				//Point p1 = da.getViewPoint(new Point2D.Float(coords[0], coords[1]));
				//Point p2 = da.getViewPoint(new Point2D.Float(coords[2], coords[3]));
				//Point p3 = da.getViewPoint(new Point2D.Float(coords[4], coords[5]));
				//gp2.curveTo((float)p1.getX(), (float)p1.getY(), (float)p2.getX(), (float)p2.getY(), (float)p3.getX(), (float)p3.getY());
			}
			
			if (segType == pi.SEG_CLOSE) {
				Line2D.Double line = new Line2D.Double(lastX, lastY, iniX, iniY);
				if (lineContainsPoint(line, po)) {
					answer = true;
				}
			}
			
			pi.next();
		}
		
		return answer;
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
		boundary = null;
		holes.clear();
		steelElements.clear();
		steelBars.clear();
		discElms.clear();
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
		boundary = null;
		holes.clear();
		steelElements.clear();
		steelBars.clear();
		discElms.clear();
		
		DrawingArea da = view.getDrawingArea();
		
		boundary = new GeneralPathDraw(gmodel.getBoundary().getGeneralPath(), view.getDrawingArea());
		
		ListIterator hs = gmodel.getHolesList().listIterator();
		while (hs.hasNext()) {
			GeneralPath h = ((IGeneralPath)hs.next()).getGeneralPath();
			holes.add(new GeneralPathDraw(h, view.getDrawingArea()));
		}
		
		ListIterator ses = gmodel.getSteelElementsList().listIterator();
		while (ses.hasNext()) {
			GeneralPath se = ((IGeneralPath)ses.next()).getGeneralPath();
			steelElements.add(new GeneralPathDraw(se, view.getDrawingArea()));
		}
		
		ListIterator sbs = gmodel.getSteelBarsList().listIterator();
		while (sbs.hasNext()) {
			SteelBar sb = (SteelBar)sbs.next();
			SteelBarDraw sbd = new SteelBarDraw(sb);
			sbd.setLocation(da.getViewPoint(new Point((int)sb.getX(), (int)sb.getY())));
			steelBars.add(sbd);
		}
		
		ListIterator delms = dmodel.getDiscElementsList().listIterator();
		while (delms.hasNext()) {
			DiscreteElement de = (DiscreteElement)delms.next();
			DiscreteElementDraw ded = new DiscreteElementDraw(de, viewState, view.getDrawingArea());
			discElms.add(ded);
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
		this.gmodel = mod.getGModel();
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
	
	private boolean lineContainsPoint(Line2D line, Point2D point) {
		
		boolean tgOK = false;
		boolean xOK = false;
		boolean answer = false;
		
		Point2D p1 = line.getP1();
		Point2D p2 = line.getP2();
		double lineTg = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
		double ptTg = (point.getY() - p1.getY()) / (point.getX() - p1.getX());
		
		if (lineTg == ptTg) tgOK = true;
		if (point.getX() >= p1.getX() && point.getX() <= p2.getX()) xOK = true;
		
		if (tgOK && xOK) answer = true;
		
		return answer;
		
	}
	
//*****************************************************************************
}
