package model;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * A class representing the state of the model.
 * @author	Fonseca, Flavio & Pitangueira, Roque
 * @see     gui.Interface
 * @see     gui.DrawingArea
 * @since   January 2004
 */
public class ModelState implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String name = new String("untitled.isn");
    private transient String path = new String();
	
	public double nd0 = 0;
	public double fck = 25;
	public double fyk = 500;
	public double steelE = 205000;
	public double gamaC = 1.4;
	public double gamaS = 1.15;
	public double discPrec = 5;
	public double deltaTheta = 10;
	public double resultPrec = 5;
	public int maxIteration = 3000;
	
	public double mx = 0;
	public double my = 0;
	
//*****************************************************************************
	
	public ModelState() {
	}
	
//*****************************************************************************
	
	public void setName(String s) {
        String ext = new String();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
                ext = s.substring(i+1).toLowerCase();
        }
        if (!ext.equals("isn")){
                s = s + ".isn";
        }
        name = s;
    }
    
//*****************************************************************************
	
    public String getName() {
        return name;
    }
    
//*****************************************************************************
	
    public void setPath(String s) {
        path = s;
    }
    
//*****************************************************************************
	
    public String getPath() {
        return path;
    }
    
//*****************************************************************************
}
