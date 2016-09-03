package gui;

import javax.swing.JFrame;
import gui.SectionController;

public class SectionView extends IView{
	
	private Interface parent;
	
	public SectionView(Interface parent){
		this.parent = parent;
		super.setViewState(new SectionViewState());
		super.setDrawingArea(new DrawingArea((IView)this));
		super.setController(new SectionController(this));
	}
	
	public  JFrame getInterface(){
    	return this.parent;
    }
    
    public void setInterface(JFrame par){
    	this.parent = (Interface)par;
    }
	
    public void setCoord(String x, String y){
    	this.parent.setCoord(x, y);
    }
    
    public void setMessage(String m){
    	this.parent.setMessage(m);
    }
	
}
