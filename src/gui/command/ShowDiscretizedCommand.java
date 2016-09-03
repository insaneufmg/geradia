package gui.command;

import javax.swing.JFrame;
import gui.*;
import gui.command.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class ShowDiscretizedCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Creates new ShowDiscretizedCommand */
    public ShowDiscretizedCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** Execute method. */
	public void execute() {
		
		if (((SectionViewState)this.parent.getSectionView().getViewState()).showDiscretized == false) {
			((SectionViewState)this.parent.getSectionView().getViewState()).showDiscretized = true;
			Command cmd = new DiscretizeCommand(parent);
			cmd.execute();
		} else {
			((SectionViewState)this.parent.getSectionView().getViewState()).showDiscretized = false;
		}
		
		this.parent.getSectionView().getController().compose();
		this.parent.getSectionView().getDrawingArea().repaint();
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
