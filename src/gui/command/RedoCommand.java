package gui.command;

import java.util.Vector;
import javax.swing.JFrame;
import gui.*;

/** The redo command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 */
public class RedoCommand implements Command {
	
    private Vector redoList = new Vector();
    private Interface parent;
    
//*****************************************************************************
	
    /** Constructor method. */
    public RedoCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** Adds a command to the redo list.
	*@param cmd The command to be added.
	*/
    public void add(Command cmd) {
        if (!(cmd instanceof RedoCommand)) {
            redoList.add(cmd);
        }
    }
    
//*****************************************************************************
	
	/** Clears the redo list.*/
    public void clear() {
        redoList.clear();
    }
    
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        int index = redoList.size() - 1;
        if (index >= 0) {
            Command cmd = (Command)redoList.get(index);
            parent.addToUndo(cmd);
            cmd.redo();
            redoList.remove(index);
            parent.getSectionView().getDrawingArea().repaint();
        }
        else {
			parent.addOutputText("Nothing to redo.");
		}
		
        if (redoList.size() == 0) {
            parent.nothingToRedo();
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
