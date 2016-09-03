package gui.command;

import java.io.*;
import java.awt.*;
import java.awt.print.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
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
 * a postscript in a given file.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since February 2004
 * @see gui.command.Command
 */
public class ExportPS2Command implements Command {
    
    private Interface parent;
    private JFileChooser fileChooser = new JFileChooser();
    private ExtensionFileFilter filter = new ExtensionFileFilter("ps", "PostScript Files");
    private boolean validFile;
	
//*****************************************************************************
	
    /**
     * Creates new ExportPSCommand
     * and sets the FileFilter do FileChooser.
     *
     * @param parent The JFrame parent.
     */
    public ExportPS2Command(JFrame parent) {
        this.parent = (Interface)parent;
        fileChooser.setFileFilter(filter);
    }
    
//*****************************************************************************
	
    /**
     * Calls the file chooser an encode the drawing as an image JPEG.
     *
     * @see gui.command.Command#execute()
     */
    public void execute() {
        
		validFile = false;
		while (!validFile) {
			
			fileChooser.setDialogTitle("Export PostScript");
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
				if (!ext.equals("ps")){
					s = s + ".ps";
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
		
		/* Use the pre-defined flavor for a Printable from an InputStream */
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		
		/* Specify the type of the output stream */
		String psMimeType = DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType();
		
		/* Locate factory which can export a GIF image stream as Postscript */
		StreamPrintServiceFactory[] factories =
		StreamPrintServiceFactory.lookupStreamPrintServiceFactories(
		flavor, psMimeType);
		if (factories.length == 0) {
			System.err.println("No suitable factories");
			return;
		}
		
		try {
			/* Create a file for the exported postscript */
			FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile().getPath());
			
			/* Create a Stream printer for Postscript */
			StreamPrintService sps = factories[0].getPrintService(fos);
			
			/* Create and call a Print Job */
			DocPrintJob pj = sps.createPrintJob();
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			aset.add(OrientationRequested.LANDSCAPE);
			
			Doc doc = new SimpleDoc(parent.getDiagramView().getDrawingArea(), flavor, null);
			
			pj.print(doc, aset);
			fos.close();
			parent.addOutputText("File \"" + fileChooser.getSelectedFile().getPath() + "\" successfully exported.");
			
		} catch (PrintException pe) {
			System.err.println(pe);
		} catch (IOException ie) {
			System.err.println(ie);
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
