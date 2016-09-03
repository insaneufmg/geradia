package gui.command;

import javax.swing.JFrame;
import gui.*;
import gui.dialog.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since November 2005
 */
public class SectionSettingsCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new SectionSettingsCommand */
    public SectionSettingsCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
        SectionSettingsDialog dlg = new SectionSettingsDialog(parent, (SectionViewState)parent.getSectionView().getViewState());
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
