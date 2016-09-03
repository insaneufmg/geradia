package gui.command;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Cursor;
import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class LastZoomCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Creates new LastZoomCommand */
    public LastZoomCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {
        parent.getSectionView().getDrawingArea().lastZoom();
		parent.getDiagramView().getDrawingArea().lastZoom();
    }
    
//*****************************************************************************
	
    /**
     * Impossible to undo - not implemented.
     */
    public void undo() {}
    
//*****************************************************************************
	
    /**
     * Impossible to redo - not implemented.
     */
    public void redo() {}
	
//*****************************************************************************
}
