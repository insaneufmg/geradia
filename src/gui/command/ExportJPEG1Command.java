package gui.command;

import java.io.*;
import java.awt.*;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import gui.*;
import model.Model;
import gui.ExtensionFileFilter;

/**
 * This class is an implementation of the interface Command.
 * its unabled to undo or redo.
 * its "execute" method encodes the current drawing image as
 * a JPEG image in a given file.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.command.Command
 */
public class ExportJPEG1Command implements Command {
    
    private Interface parent;
    private JFileChooser fileChooser = new JFileChooser();
    private ExtensionFileFilter filter = new ExtensionFileFilter(new String[]{"jpg", "jpeg"}, "JPEG Files");
	private boolean validFile;
    
//*****************************************************************************
	
    /**
     * Creates new ExportJPEG1Command
     * and sets the FileFilter of FileChooser.
     *
     * @param parent The JFrame parent.
     */
    public ExportJPEG1Command(JFrame parent) {
        this.parent = (Interface)parent;
        fileChooser.setFileFilter(filter);
    }
    
//*****************************************************************************
	
    /**
     * Calls the file chooser an encode the drawing as a JPEG image.
     *
     * @see gui.command.Command#execute()
     */
    public void execute() {
		
		validFile = false;
		while (!validFile) {
			
			fileChooser.setDialogTitle("Export JPEG");
			int retVal = fileChooser.showDialog(parent, "Export");
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
				if (!ext.equals("jpg") && !ext.equals("jpeg")){
					s = s + ".jpg";
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
        
		Iterator writers = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter)writers.next();
		
		try{
			ImageOutputStream ios = ImageIO.createImageOutputStream(fileChooser.getSelectedFile());
			writer.setOutput(ios);
			//getting and writing the buffered image in the output stream
			writer.write(parent.getSectionView().getDrawingArea().getImage());
			parent.addOutputText("File \"" + fileChooser.getSelectedFile().getPath() + "\" successfully exported.");
		} catch (IllegalArgumentException e) {
			System.out.println("One of the arguments to write image is null");
			e.printStackTrace();
		} catch (IOException ie) {
			System.out.println("Exception occured in I/O");
			ie.printStackTrace();
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
