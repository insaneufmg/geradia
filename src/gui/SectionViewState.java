package gui;

import java.io.Serializable;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * A class representing the state of the model.
 * @author	Penna, Samuel & Pitangueira, Roque & Fonseca, Flavio
 * @since   april 2005
 */
public class SectionViewState extends ViewState implements Serializable {
	
	public boolean showDiscretized = false;
	
	public Color boundaryColor = Color.BLACK;
	public Color boundaryFillColor = new Color(153, 102, 0);
	public Color steelColor = Color.BLUE;
	public Color discElmColor = Color.BLACK;
	public Color gravityCenterColor = Color.RED;
	public Color gridColor = Color.BLACK;
	public Color globalAxisColor = Color.BLACK;
	
//*****************************************************************************
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
    
//*****************************************************************************
	
    /**
     * The class constructor
     */
    public SectionViewState() {
    	
    }
	
//*****************************************************************************
}