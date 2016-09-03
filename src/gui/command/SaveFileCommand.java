package gui.command;

import java.io.*;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import gui.*;

/**
 * This class is an implementation of the interface Command.
 * its unabled to undo or redo.
 * its "execute" method verify the existence of a model, and
 * if that is one, the save routine is called.
 * If necessary it calls a file chooser to set the models name.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 * @see gui.command.Command
 */
public class SaveFileCommand implements Command {
    
    private Interface parent;
    private JFileChooser fileChooser = new JFileChooser();
    private ExtensionFileFilter filter = new ExtensionFileFilter("isn", "INSANE Files");
	private boolean validFile;
	
//*****************************************************************************
	
    /**
     * Creates new SaveFileCommand
     * and sets the FileFilter do FileChooser.
     *
     * @param parent The JFrame parent.
     */
    public SaveFileCommand(JFrame parent) {
        this.parent = (Interface)parent;
        fileChooser.setFileFilter(filter);
    }
	
//*****************************************************************************
	
    /**
     * Saves the current model accordding to ModelState name.
     * If ModelState has its initial name it prompts a dialog to
     * choose another name.
     *
     * @see gui.command.Command#execute()
     */ 
    public void execute() {
        if (parent.getModel() != null) {
			
			if (parent.getModelState().getName().equals("untitled.isn")){
				
				validFile = false;
				while (!validFile) {
					int retVal = fileChooser.showSaveDialog(parent);
					if (retVal == fileChooser.APPROVE_OPTION) {
						
						//Getting the file name
						String s = fileChooser.getSelectedFile().getName();
						
						//Getting the file extension
						String ext = new String();
						int i = s.lastIndexOf('.');
						if (i > 0 &&  i < s.length() - 1) {
							ext = s.substring(i+1).toLowerCase();
						}
						
						//Comparing the extension
						if (!ext.equals("isn")) {
							s = s + ".isn";
							File file = fileChooser.getSelectedFile();
							
							//Changing the file in case of no good extensions
							fileChooser.setSelectedFile(new File(file.getParent() + file.separator + s));
						}
						
						//Checks if the file exists
						if (fileChooser.getSelectedFile().exists()){
							JOptionPane op = new JOptionPane();
							int value = 10;
							value = op.showConfirmDialog(parent,
							"File \"" + fileChooser.getSelectedFile().getPath() + "\" exists.\nDo you want to replace it?",
							"Warning", op.YES_NO_OPTION, op.WARNING_MESSAGE);
							if (value == op.YES_OPTION){
								validFile = true;
							}
						}
						else {
							validFile = true;
						}
					}
					else return;
				}
				
				parent.getModelState().setName(fileChooser.getSelectedFile().getName());
				parent.getModelState().setPath(fileChooser.getSelectedFile().getPath());
				save();
				
			}else {
				
				fileChooser.setSelectedFile(new File(parent.getModelState().getPath()));
				save();
				
			}
			
			parent.repaint();
		}
	}
    
//*****************************************************************************
	
    /**
     * Writes model on disc using a FileOutputStream and 
     * an ObjectOutputStream.
     */
    private void save() {
        try{
			parent.getSectionView().getViewState().setCurrentZoom(parent.getSectionView().getDrawingArea().getCurrentZoom());
            FileOutputStream serialStream = new FileOutputStream(fileChooser.getSelectedFile());
            ObjectOutputStream stream = new ObjectOutputStream(serialStream);
            stream.writeObject(parent.getModel());
            serialStream.flush();
			parent.setTitle("INSANE " + parent.getProperties().getVersion() + " - " + parent.getModelState().getName());
			parent.addOutputText("File \"" + fileChooser.getSelectedFile().getPath() + "\" successfully saved.");
		}catch (Exception exc){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(parent, "Couldn't save file!", "Error Message", JOptionPane.ERROR_MESSAGE);
			parent.addOutputText("" + exc.getClass());
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
