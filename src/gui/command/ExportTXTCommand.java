package gui.command;

import java.io.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.ListIterator;
import java.text.*;
import gui.*;
import model.*;
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
public class ExportTXTCommand implements Command {
    
    private Interface parent;
    private JFileChooser fileChooser = new JFileChooser();
    private ExtensionFileFilter filter = new ExtensionFileFilter(new String[]{"txt"}, "TXT Files");
	private boolean validFile;
    
//*****************************************************************************
	
    /**
     * Creates new ExportTXTCommand
     * and sets the FileFilter of FileChooser.
     *
     * @param parent The JFrame parent.
     */
    public ExportTXTCommand(JFrame parent) {
        this.parent = (Interface)parent;
        fileChooser.setFileFilter(filter);
    }
    
//*****************************************************************************
	
    /**
     * Calls the file chooser an encode the result as a text.
     *
     * @see gui.command.Command#execute()
     */
    public void execute() {
		
		validFile = false;
		while (!validFile) {
			
			fileChooser.setDialogTitle("Export TXT");
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
				if (!ext.equals("txt")){
					s = s + ".txt";
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
        
		//Creating formator
		DecimalFormat fmt = new DecimalFormat();
		DecimalFormatSymbols fsym = new DecimalFormatSymbols();
		fsym.setDecimalSeparator('.');
		fmt.setDecimalFormatSymbols(fsym);
		fmt.applyPattern("####0.00");
		
		try{
 			
 			FileWriter fw = new FileWriter(fileChooser.getSelectedFile().getPath());
 			BufferedWriter bw = new BufferedWriter(fw);
 			PrintWriter pw = new PrintWriter(bw);
 			
			ModelState state = parent.getModel().getState();
			
			String title = "GERADIA - Biaxial Bending Diagram Generator";
 			pw.write(title);
 			
			pw.write("\r\n");
			pw.write("\r\n");
			
			String str;
			
			str = "-------------------------------------------------------------------";
			pw.write(str);
			
			pw.write("\r\n");
			pw.write("\r\n");
			
			str = "Nd0 = " + state.nd0 + " kN";
			pw.write(str);
			pw.write("\r\n");
			
			str = "Fck = " + state.fck + " MPa";
			pw.write(str);
			pw.write("\r\n");
			
			str = "Fyk = " + state.fyk + " MPa";
			pw.write(str);
			pw.write("\r\n");
			
			str = "Steel Elasticity = " + state.steelE + " MPa";
			pw.write(str);
			pw.write("\r\n");
			
			str = "GamaC = " + state.gamaC;
			pw.write(str);
			pw.write("\r\n");
			
			str = "GamaS = " + state.gamaS;
			pw.write(str);
			pw.write("\r\n");
			
			str = "Discretization Precision = " + state.discPrec + " %";
			pw.write(str);
			pw.write("\r\n");
			
			str = "Delta Theta = " + state.deltaTheta + " Degrees";
			pw.write(str);
			pw.write("\r\n");
			
			str = "Result Precision = " + state.resultPrec + " %";
			pw.write(str);
			pw.write("\r\n");
			
			str = "Maximum Number of Iterations = " + state.maxIteration;
			pw.write(str);
			pw.write("\r\n");
			pw.write("\r\n");
			
			str = "-------------------------------------------------------------------";
			pw.write(str);
			
			pw.write("\r\n");
			pw.write("\r\n");
			
			str = "Theta\tMx [KN.m]\tMy [KN.m]";
			pw.write(str);
			
			pw.write("\r\n");
			pw.write("\r\n");
			
			str = "-------------------------------------------------------------------";
			pw.write(str);
 			pw.flush();
			
			pw.write("\r\n");
			pw.write("\r\n");
			
			double theta = 0;
			ListIterator dpl = parent.getModel().getDiscreteModel().getDiagramPointsList().listIterator();
			while (dpl.hasNext()) {
				IPoint p = (IPoint)dpl.next();
				str = theta + "\t" + fmt.format(p.getX()/1000) + "\t" + fmt.format(p.getY()/1000) + "\t";
				pw.write(str);
				pw.write("\r\n");
				theta = theta + state.deltaTheta;
			}
			
 			pw.close();
 			bw.close();
 			fw.close();
 			
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
