package model;

import java.io.*;
import java.awt.geom.*;
import model.geo.*;
import model.discrete.*;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see     gui.Interface
 * @see     gui.DrawingArea
 * @since   January 2004
 */
public class Model implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private GeometricModel geometricModel = new GeometricModel();
    private ModelState state = new ModelState();
    private DiscreteModel discreteModel = new DiscreteModel();
	
//*****************************************************************************
	
    /** Constructor method */
    public Model() {
    }
	
//*****************************************************************************
	
    public GeometricModel getGModel() {
        return geometricModel;
    }
    
//*****************************************************************************
	
    public void setGModel(GeometricModel geo) {
        geometricModel = geo;
    }
	
//*****************************************************************************
	
    public ModelState getState() {
		return state;
    }
    
//*****************************************************************************
	
	public void setState(ModelState ste) {
        state = ste;
    }
	
//*****************************************************************************
    
    public DiscreteModel getDiscreteModel() {
        return discreteModel;
    }
    
//*****************************************************************************
	
    public void setDiscreteModel(DiscreteModel disc) {
        discreteModel = disc;
    }
	
//*****************************************************************************
}
