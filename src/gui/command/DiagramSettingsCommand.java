package gui.command;

import javax.swing.JFrame;
import gui.*;
import gui.dialog.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since November 2005
 */
public class DiagramSettingsCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new DiagramSettingsCommand */
    public DiagramSettingsCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
        DiagramSettingsDialog dlg = new DiagramSettingsDialog(parent, (DiagramViewState)parent.getDiagramView().getViewState());
        int retVal = dlg.showDialog();
    }
	
//*****************************************************************************
	
    public void undo() {
    }
    
//*****************************************************************************
	
    public void redo() {
    }
    
//*****************************************************************************
}
