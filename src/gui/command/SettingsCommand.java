package gui.command;

import javax.swing.JFrame;
import gui.*;
import gui.dialog.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since 	October 2005
 */
public class SettingsCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new SettingsCommand */
    public SettingsCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
        SettingsDialog dlg = new SettingsDialog(parent, parent.getModel().getState());
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
