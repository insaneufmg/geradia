package gui.command;

import javax.swing.JFrame;
import java.awt.geom.GeneralPath;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import gui.*;
import gui.dialog.*;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since 	October 2005
 */
public class VerifyCommand implements Command {
	
    private Interface parent;
    
//*****************************************************************************
	
    /** Creates new VerifyCommand */
    public VerifyCommand(JFrame parent) {
        this.parent = (Interface)parent;
    }
    
//*****************************************************************************
	
    public void execute() {
        VerifyDialog dlg = new VerifyDialog(parent, parent.getModel().getState());
        int retVal = dlg.showDialog();
		
		if (retVal == dlg.OK_OPTION) {
			double mx = parent.getModel().getState().mx;
			double my = parent.getModel().getState().my;
			
			GeneralPath dia = new GeneralPath();
			ListIterator dpl = parent.getModel().getDiscreteModel().getDiagramPointsList().listIterator();
			while (dpl.hasNext()) {
				IPoint p = (IPoint)dpl.next();
				if (dpl.previousIndex() == 0) {
					dia.moveTo((float)p.getX()/1000, (float)p.getY()/1000);
				} else if (!dpl.hasNext()) {
					dia.lineTo((float)p.getX()/1000, (float)p.getY()/1000);
					dia.closePath();
				} else {
					dia.lineTo((float)p.getX()/1000, (float)p.getY()/1000);
				}
			}
			
			this.parent.getDiagramView().getController().compose();
			this.parent.getDiagramView().getDrawingArea().repaint();
			
			boolean isOk = dia.contains(mx, my);
			if (isOk) {
				String mes = "The pair Mx=" + Double.toString(mx) + " kN and My=" + Double.toString(my) + " kN is inside the diagram.";
				mes = mes + "\nThe specified section is ok for this load.";
				JOptionPane.showMessageDialog(parent, mes, "Verify Result", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String mes = "The pair Mx=" + Double.toString(mx) + " kN and My=" + Double.toString(my) + " kN is NOT inside the diagram.";
				mes = mes + "\nThe specified section is NOT ok for this load.";
				JOptionPane.showMessageDialog(parent, mes, "Verify Result", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
    }
	
//*****************************************************************************
	
    public void undo() {
    }
    
//*****************************************************************************
	
    public void redo() {
    }
    
//*****************************************************************************
}
