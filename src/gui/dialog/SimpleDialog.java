package gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gui.*;

/**
 * An abstract class extending JDialog representing a dialog on screen.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.Interface
 * @since April 2004
 */
public abstract class SimpleDialog extends JComponent implements ActionListener {
	
	public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
	
	private int command;
	private JPanel mainPane = new JPanel();
	private JDialog dlg;
	private JPanel confirmationPane = new JPanel();
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");
	
//*****************************************************************************
	
	/** Shows this dialog.
	* @param parent This dialog's parent
	* @param title The title of this dialog
	*/
	public int showDialog(JFrame parent, String title) {
		dlg = new JDialog(parent, title, true);
		
		dlg.setSize(new Dimension(300, 200));
		
		dlg.setLocationRelativeTo(parent);
		
		dlg.setResizable(false);
        dlg.getContentPane().setLayout(new BorderLayout());
        dlg.getContentPane().add(mainPane, BorderLayout.CENTER);
        dlg.getContentPane().add(confirmationPane, BorderLayout.SOUTH);
        dlg.getRootPane().setDefaultButton(ok);
		
        //Sets up confirmation pane
        confirmationPane.add(ok);
        ok.addActionListener(this);
		
        cancel.setMnemonic(KeyEvent.VK_C);
        confirmationPane.add(cancel);
        cancel.addActionListener(this);
        
		dlg.pack();
        dlg.setVisible(true);
        return command;
    }
	
//*****************************************************************************
	
	/** Implements method actionPerformed(ActionEvent e) of ActionListener class.
	* @see ActionListener
	*/
	public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        if (btn.getActionCommand() == "OK"){
            command = OK_OPTION;
            approvedPerform();
        }else{
            command = CANCEL_OPTION;
        }
        dlg.setVisible(false);
    }
	
//*****************************************************************************
	
	/** Does something when OK button is pressed. */
    public abstract void approvedPerform();
	
//*****************************************************************************
	
	public void setPane(JPanel pane) {
		mainPane = pane;
	}
	
//*****************************************************************************
	
	public void setButtonsEnabled(boolean val) {
		ok.setEnabled(val);
		cancel.setEnabled(val);
	}
	
//*****************************************************************************
}
