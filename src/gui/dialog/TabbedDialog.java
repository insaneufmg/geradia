package gui.dialog;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * An abstract class extending JComponent representing a dialog on screen.
 * This class provides a tabbed pane for the dialog.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @see gui.Interface
 * @since January 2004
 */
public abstract class TabbedDialog extends JComponent implements ActionListener {
	
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
	
    private int command;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JDialog dlg;
    private JPanel confirmationPane = new JPanel();
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");
	
//*****************************************************************************
	
    public int showDialog(JFrame parent, String title) {
		dlg = new JDialog(parent, title, true);
        dlg.setSize(new Dimension(300, 200));
        dlg.setLocationRelativeTo(parent);
        dlg.setResizable(false);
        dlg.getContentPane().setLayout(new BorderLayout());
        dlg.getContentPane().add(tabbedPane, BorderLayout.CENTER);
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
	
    abstract void approvedPerform();
	
//*****************************************************************************
	
    protected void addTab(String title, Component component) {
        tabbedPane.addTab(title, component);
    }
    
//*****************************************************************************
	
    protected void cancel() {
        command = CANCEL_OPTION;
    }
    
//*****************************************************************************
	
	public void setButtonsEnabled(boolean val) {
		ok.setEnabled(val);
		cancel.setEnabled(val);
	}
	
//*****************************************************************************
}