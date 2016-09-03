package gui.command;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.PrintJob;
import java.awt.Graphics;
import gui.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class PrintCommand implements Command {
    
    private Interface parent;
	
//*****************************************************************************
	
    /** Creates new PrintCommand */
    public PrintCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
    public void execute() {
        PrintJob pj = parent.getToolkit().getPrintJob(parent, parent.getModelState().getName(), null);
        if (pj != null) { 
            Graphics g = pj.getGraphics();
            parent.getDiagramView().getDrawingArea().paint(g); 
            g.dispose(); 
            pj.end(); 
        }
    }
	
//*****************************************************************************
	
    public void undo() {
    }
    
//*****************************************************************************
	
    public void redo() {
    }
	
//*****************************************************************************
}
