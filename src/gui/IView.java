package gui;
import javax.swing.JFrame;

import gui.Controller;

public abstract class IView{
	
    private DrawingArea da;
    private ViewState 	vs;
    private Controller  controller;
	
    public IView(){
    
    } 
    
    /**
     * @return the current drawing area
     */
    public DrawingArea getDrawingArea(){
    	return this.da;
    }
	
    /**
     * @param da The Drawing Area
     */
    public void setDrawingArea(DrawingArea da){
    	this.da = da;
    }
    
    /**
     * @return vs 
     */
    public ViewState getViewState(){
    	return this.vs;
    }
    
    /**
     * @param vs set the state of this view
     */
    public void setViewState(ViewState vs){
    	this.vs = vs;
    }
	
    /**
     * @return controller return the controller of this view
     */
    public Controller getController(){
    	return this.controller;
    }
    
    public void setController(Controller controller){
    	this.controller = controller;
    }
    
    public abstract JFrame getInterface();
    
    public abstract void setInterface(JFrame j);
	
    public abstract void setCoord(String x, String y);
    
    public abstract void setMessage(String m);
    
}

