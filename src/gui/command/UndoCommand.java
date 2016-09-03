package gui.command;

import java.util.Vector;
import javax.swing.JFrame;
import gui.*;

/** The undo command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 */
public class UndoCommand implements Command {
    
    private Vector undoList = new Vector();
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new UndoCommand */
    public UndoCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
	/** Adds a command to the undo list.
	*@param cmd The command to be added.
	*/
    public void add(Command cmd) {
        if (!(cmd instanceof UndoCommand)) {
            undoList.add(cmd);
        }
    }
    
//*****************************************************************************
	
	/** Clears the undo list.*/
    public void clear() {
        undoList.clear();
    }
    
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        int index = undoList.size() - 1;
        if (index >= 0) {
            Command cmd = (Command)undoList.get(index);
            parent.addToRedo(cmd);
            cmd.undo();
            undoList.remove(index);
            parent.getSectionView().getDrawingArea().repaint();
        }
		else {
			parent.addOutputText("Nothing to undo.");
		}
		
        if (undoList.size() == 0) {
            parent.nothingToUndo();
        }
    }
    
//*****************************************************************************
	
	/** Impossible to undo - not implemented. */
    public void undo() {
    }
    
//*****************************************************************************
	
	/** Impossible to redo - not implemented. */
    public void redo() {
    }
    
//*****************************************************************************
}
