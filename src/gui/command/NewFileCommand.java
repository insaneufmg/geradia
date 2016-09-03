package gui.command;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Toolkit;

import model.Model;
import gui.*;

/**
 * This class is an implementation of Command interface.
 * Its "execute" method simple creates a new instance of model
 * closing the old one (if that is one) and calling the appropriate settings
 * methods of parent (a JFrame).
 
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.command.Command
 */
public class NewFileCommand implements Command {
    
    private Interface parent;
    
//*****************************************************************************
	
    /**
     * Constructor method.
     * @param   parent The JFrame parent.
     */
    public NewFileCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
        try {
            if (parent.closeModel()) {
                parent.setModel(new Model());
				parent.nothingToUndo();
                parent.nothingToRedo();
				parent.getSectionView().getDrawingArea().setZoom(parent.getSectionView().getViewState().getLimits());
				((SectionViewState)parent.getSectionView().getViewState()).showDiscretized = false;
				((DiagramView)parent.getDiagramView()).setInitialState();
				parent.setInterface();
				parent.getConsole().clear();
				parent.getConsole().requestFocus();
				parent.getConsoleHandler().setToCommandMode();
				parent.getConsoleHandler().setKeybCommand(null);
				parent.setTitle("Geradia " + parent.getProperties().getVersion() + " - " + parent.getModelState().getName());
				parent.getSplitPane().setDividerLocation(parent.getWidth()/2 - parent.getSplitPane().getDividerSize());
            }
        }catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            System.out.print(e.toString());
            JOptionPane.showMessageDialog(parent, "Couldn't create new model!", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
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
