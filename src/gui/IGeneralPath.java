package gui;

import java.io.*;
import java.util.*;
import java.awt.geom.*;

/**
 * A class representing a steel bar.
 *
 * @author 	Fonseca, Flavio & Pitangueira, Roque
 * @since 	October 2005
 */
public class IGeneralPath implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList x = new ArrayList();
	private ArrayList y = new ArrayList();
	private boolean closed = false;
	
//*****************************************************************************
	
	public IGeneralPath() {
	}
	
//*****************************************************************************
	
	public void closePath() {
		closed = true;
	}
	
//*****************************************************************************
	
	public void moveTo(float x, float y){
		this.x.add(new Float(x));
		this.y.add(new Float(y));
	}
	
//*****************************************************************************
	
	public void lineTo(float x, float y){
		this.x.add(new Float(x));
		this.y.add(new Float(y));
	}
	
//*****************************************************************************
	
	public GeneralPath getGeneralPath() {
		GeneralPath g = new GeneralPath();
		ListIterator a = x.listIterator();
		ListIterator b = y.listIterator();
		while (a.hasNext()){
			Float x1 = (Float)a.next();
			Float y1 = (Float)b.next();
			float x = x1.floatValue() ;
			float y = y1.floatValue() ;
			if (a.previousIndex() == 0) {
				g.moveTo(x, y);
			} else if (!a.hasNext() && closed) {
				g.lineTo(x, y);
				g.closePath();
			} else {
				g.lineTo(x, y);
			}
			
		}
		return g;
	}
	
//*****************************************************************************
}
