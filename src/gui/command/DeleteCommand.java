package gui.command;

import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.JFrame;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since March 2004
 */
public class DeleteCommand implements Command, Cloneable {
    
    private Interface parent;
    private Controller controller;
    private ArrayList shps;
    
//*****************************************************************************
	
    /** Creates new DeleteCommand */
    public DeleteCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        
		controller = this.parent.getSectionView().getController();
        if (controller.isSelected()) {
            shps = (ArrayList)controller.removeSelection();
            parent.getSectionView().getDrawingArea().repaint();
            parent.addToUndo((Command)this.clone());
        }
    }
    
//*****************************************************************************
	
	/** Undo method. */
    public void undo() {
        ListIterator sh = shps.listIterator();
        while (sh.hasNext()) {
            controller.add(sh.next());
        }
    }
    
//*****************************************************************************
	
    /**
     * Impossible to redo - Not implemented.
     */
    public void redo() {}
    
    public Object clone() {
        try {
            DeleteCommand cmd = (DeleteCommand)super.clone();
            cmd.shps = (ArrayList)shps.clone();
            return cmd;
        } catch (CloneNotSupportedException e) {
            throw new Error("Clonning ERROR - This should never happen!");
        }
    }
    
//*****************************************************************************
}
