package gui;

import gui.command.*;
import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 *
 * @author Fonseca, Flavio Pitangueira, Roque
 * @since January 2004
 */
public class CmdButton extends JButton implements CommandHolder {
	
    private Command command;
    
//*****************************************************************************
	
    /** Creates new CmdMenu */
    public CmdButton() {
    }
	
//*****************************************************************************
	
    public CmdButton(String name) {
        super(name);
    }
	
//*****************************************************************************
    
    public CmdButton(ImageIcon icon) {
        super(icon);
    }
	
//*****************************************************************************
	
    public CmdButton(String name, ImageIcon icon) {
        super(name, icon);
    }
    
//*****************************************************************************
	
    public void setCommand(Command cmd) {
        command = cmd;
    }
    
//*****************************************************************************
	
    public Command getCommand() {
        return command;
    }
    
//*****************************************************************************
}
