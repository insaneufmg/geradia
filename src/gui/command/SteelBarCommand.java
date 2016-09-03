package gui.command;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gui.*;

/** Command which prepares the Interface to receive data to add steel bars.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since October 2005
 */
public class SteelBarCommand implements Command {
	
    private Interface parent;
	
//*****************************************************************************
	
    /** Constructor method. */
    public SteelBarCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
	
//*****************************************************************************
	
	/** The execute method.<br>
	* Prepares the Interface to receive data to add points.
	*/
    public void execute() {
		double dia = -1;
		boolean string = false;
		while (dia <= 0 || string) {
			string = false;
			String dia2 = JOptionPane.showInputDialog("Enter the diameter of the steel bar (mm):");
			try {
				dia = Double.parseDouble(dia2);
			} catch (Exception e) {
				string = true;
			}
			if (dia == -1) {
				break;
			}
		}
		if (dia != -1) {
			AddSteelBarMouseCommand cmd = new AddSteelBarMouseCommand(parent);
			cmd.setDiameter(dia);
			AddSteelBarKeybCommand cmd2 = new AddSteelBarKeybCommand(parent);
			cmd2.setDiameter(dia);
			parent.getSectionView().getDrawingArea().setClickedCommand(cmd);
			parent.getSectionView().getDrawingArea().setRightClickedCommand(new SelectCommand(parent));
			parent.getSectionView().getDrawingArea().setCursor();
			parent.getSectionView().getDrawingArea().setShape(null);
			parent.getSectionView().getDrawingArea().setMovedCommand(new DoesNothingCommand(parent));
			parent.getConsoleHandler().setToDataMode();
			parent.getConsoleHandler().setKeybCommand(cmd2);
			parent.addOutputText("Specify a point.");
		}
    }
	
//*****************************************************************************
	
	/** Impossible to undo - not implemented. */
    public void undo() {
    }
	
//*****************************************************************************
	
	/** Impossible to redo - not implemented. */
    public void redo() {
    }
    
//*****************************************************************************
}
