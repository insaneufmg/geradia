package gui.command;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import gui.*;

/**
 * This class is a implementation of the interface Command.
 * its unabled to undo or redo.
 * its "execute" method verify the existence of a model, and
 * if that is one, it calls a file chooser to set the models name and
 * the save routine is called.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since Febraury 2004
 * @see gui.command.Command
 */
public class SaveFileAsCommand implements Command {
    
    private Interface parent;
	private JFileChooser fileChooser = new JFileChooser();
    private ExtensionFileFilter filter = new ExtensionFileFilter("isn", "INSANE Files");
	private boolean validFile;
    
//*****************************************************************************
	
    /**
     * Creates new SaveAsFileCommand
     * and sets the FileFilter to FileChooser.
     *
     * @param parent The JFrame parent.
     */
    public SaveFileAsCommand(JFrame parent) {
        this.parent = (Interface)parent;
        fileChooser.setFileFilter(filter);
    }
    
//*****************************************************************************
	
    /**
     * Saves the current model accordding to ModelState name.
     * Prompts a dialog to choose another model name.
     *
     * @see gui.command.Command#execute()
     */
    public void execute() {
        if (parent.getModel() != null) {
			validFile = false;
			while (!validFile) {
				fileChooser.setCurrentDirectory(new File(parent.getModelState().getPath()));
				fileChooser.setDialogTitle("Save as");
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
						
						if (parent.getModelState().getPath().equals(fileChooser.getSelectedFile().getPath())) {
							validFile = true;
						}
						else {
							JOptionPane op = new JOptionPane();
							int value = 10;
							value = op.showConfirmDialog(parent, 
							"File \"" + fileChooser.getSelectedFile().getPath() + "\" exists.\nDo you want to replace it?",
							"Warning", op.YES_NO_OPTION, op.WARNING_MESSAGE);
							if (value == op.YES_OPTION){
								validFile = true;
							}
						}
					}
					else {
						validFile = true;
					}
				}
				else return;
			}
			
			if (parent.getModelState().getName().equals("untitled.isn"))
				parent.getModelState().setName(fileChooser.getSelectedFile().getName());
            save();
        }
        parent.repaint();
    }
    
//*****************************************************************************
	
    /**
     * Writes model on disc using a FileOutputStream and
     * an ObjectOutputStream.
     */
    private void save() {
        try{
            FileOutputStream serialStream = new FileOutputStream(fileChooser.getSelectedFile());
            ObjectOutputStream stream = new ObjectOutputStream(serialStream);
            stream.writeObject(parent.getModel());
            serialStream.flush();
			parent.addOutputText("File \"" + fileChooser.getSelectedFile().getPath() + "\" successfully saved.");
        }catch (Exception exc){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(parent, "Couldn't save file!", "ERROR Message", JOptionPane.ERROR_MESSAGE);
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
