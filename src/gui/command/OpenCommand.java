package gui.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import gui.*;
import model.Model;

/**
 * This class is an implementation of the interface Command.
 * It's unabled to undo or redo.
 * Its "execute" opens a stored model calling
 * a file chooser to get the file.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since Febraury 2004
 * @see gui.command.Command
 */
public class OpenCommand implements Command {
    
    private Interface parent;
    private JFileChooser fileChooser = new JFileChooser();
    private ExtensionFileFilter filter = new ExtensionFileFilter("isn", "INSANE Files");
    private boolean validFile;
	
//*****************************************************************************
	
    /**
     * Creates new OpenCommand
     * and sets the FileFilter do FileChooser.
     *
     * @param parent The JFrame parent.
     */
    public OpenCommand(JFrame parent) {
        this.parent = (Interface)parent;
        fileChooser.setFileFilter(filter);
    }
    
//*****************************************************************************
	
    /**
     * Calls the file chooser and opens a stored model.
     *
     * @see gui.command.Command#execute()
     */
    public void execute() {
        if (parent.closeModel()) {
			validFile = false;
			while (!validFile) {
				int retVal = fileChooser.showOpenDialog(parent);
				if (retVal == fileChooser.APPROVE_OPTION) {
					//Checks if the file exists and is readable
					File file = fileChooser.getSelectedFile();
					if (file.exists() && file.isFile() && file.canRead()){
						validFile = true;
					}
					else {
						JOptionPane.showMessageDialog(parent, "File \"" + fileChooser.getSelectedFile().getPath() + "\" doesn't exist.", "Error Message", JOptionPane.ERROR_MESSAGE);
					}
				}
				else return;
			}
			
			try{
				ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
				parent.setModel((Model)stream.readObject());
				parent.getModelState().setPath(fileChooser.getSelectedFile().getPath());
				parent.getModelState().setName(fileChooser.getSelectedFile().getName());
				parent.setInterface();
			}catch (Exception exc) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(parent, "Couldn't open file!", "Error Message", JOptionPane.ERROR_MESSAGE);
			}
			parent.nothingToUndo();
			parent.nothingToRedo();
			parent.getConsole().clear();
			parent.getConsole().requestFocus();
			parent.getConsoleHandler().setToCommandMode();
			parent.getConsoleHandler().setKeybCommand(null);
			parent.setTitle("Geradia " + parent.getProperties().getVersion() + " - " + parent.getModelState().getName());
			parent.getSplitPane().setDividerLocation(parent.getWidth()/2 - parent.getSplitPane().getDividerSize());
			
			if (parent.getModel().getGModel().getBoundary() != null)
				parent.boundaryClosed();
			if (parent.getModel().getDiscreteModel().getDiagramPointsList().size() != 0)
				parent.problemSolved();
			
			parent.getSectionView().getController().compose();
			parent.getSectionView().getDrawingArea().repaint();
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
