package gui;

import javax.swing.JFrame;
import java.awt.geom.Rectangle2D;
import gui.DiagramController;

public class DiagramView extends IView{
	
	private Interface parent;
	
//*****************************************************************************
	
	public DiagramView(Interface parent){
		this.parent = parent;
		super.setViewState(new DiagramViewState());
		super.setDrawingArea(new DrawingArea((IView)this));
		super.setController(new DiagramController(this));
	}
	
//*****************************************************************************
	
	public  JFrame getInterface(){
    	return this.parent;
    }
    
//*****************************************************************************
	
    public void setInterface(JFrame par){
    	this.parent = (Interface)par;
    }
	
//*****************************************************************************
	
    public void setCoord(String x, String y){
    	this.parent.setCoord(x, y);
    }
    
//*****************************************************************************
	
    public void setMessage(String m){
    	this.parent.setMessage(m);
    }
	
//*****************************************************************************
	
	public void setInitialState() {
		DrawingArea da = super.getDrawingArea();
		DiagramViewState state = (DiagramViewState)super.getViewState();
		
		state.setLimits(new Rectangle2D.Double(-5, -5, 5, 5));
		state.setCurrentZoom(new Rectangle2D.Double(-5, -5, 5, 5));
		state.setXYInc(1, 1);
		state.setGrid(true);
		state.setSnap(false);
		
		da.setZoom(state.getLimits());
	}
	
//*****************************************************************************
}
