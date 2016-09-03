package gui;

import java.io.Serializable;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * A class representing the state of the model.
 * @author	Penna, Samuel & Pitangueira, Roque & Fonseca, Flavio
 * @since   april 2005
 */
public class DiagramViewState extends ViewState implements Serializable {
	
	public Color diagramLineColor = Color.BLACK;
	public Color pointsColor = Color.GREEN;
	public Color verifyPointColor = Color.RED;
	public Color gridColor = Color.BLACK;
	public Color axisColor = Color.BLACK;
	
//*****************************************************************************
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
    
//*****************************************************************************
	
    /**
     * The class constructor
     */
    public DiagramViewState() {
    	 
		// The limits of drawing model
		this.setLimits(new Rectangle2D.Double(-5, -5, 5, 5));
		this.setCurrentZoom(new Rectangle2D.Double(-5, -5, 5, 5));
		this.setXYInc(1, 1);
		this.setGrid(true);
		this.setSnap(false);
		
    }
	
//*****************************************************************************
}