package gui.command;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Cursor;
import javax.swing.JFrame;
import gui.*;

/** The ZoomKeyb command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 */
public class ZoomKeybCommand implements Command {
    
    private Interface parent;
	private Command cmd;
	
//*****************************************************************************
	
    /** Constructor method. */
    public ZoomKeybCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        String str = parent.getConsole().getLastCommand().toUpperCase();;
		
		if (str.equals("ALL") || str.equals("A")) {cmd = new ZoomAllCommand(parent);}
		else if (str.equals("EXTENTS") || str.equals("E")) {cmd = new FitCommand(parent);}
		else if (str.equals("IN") || str.equals("I")) {cmd = new ZoomInCommand(parent);}
		else if (str.equals("OUT") || str.equals("O")) {cmd = new ZoomOutCommand(parent);}
		else if (str.equals("LAST") || str.equals("L")) {cmd = new LastZoomCommand(parent);}
		
		try {
			cmd.execute();
		}catch (Exception e) {
			parent.getConsole().addOutputText("Invalid Command.");
		}
		
		parent.addOutputText("Click first corner of window or");
		parent.addOutputText("All / Extents / In / Last / Out");
		
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
