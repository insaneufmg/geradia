package gui.command;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Cursor;
import javax.swing.JFrame;
import gui.*;

/** The Zoom command.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 */
public class ZoomCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor method. */
    public ZoomCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** Execute method. */
    public void execute() {
        parent.getSectionView().getDrawingArea().setClickedCommand(new SetZoomCommand(parent));
        parent.getSectionView().getDrawingArea().setRightClickedCommand(new LastZoomCommand(parent));
        Image img = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/Zoom24.gif"));
        try{
            parent.getSectionView().getDrawingArea().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), new String()));
        }catch(IndexOutOfBoundsException obe){
            System.out.println(obe.toString());
        }
		parent.addOutputText("Specify first corner.");
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
