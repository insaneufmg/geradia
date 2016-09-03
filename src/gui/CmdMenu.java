package gui;

import gui.command.*;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

/**
 *
 * @author Fonseca,Flavio & Pitangueira, Roque
 */
public class CmdMenu extends JMenuItem implements CommandHolder {
	
    private Command command;
    
//*****************************************************************************
	
    /** Creates new CmdMenu */
    public CmdMenu() {
    }
	
//*****************************************************************************
	
    public CmdMenu(String name) {
        super(name);
    }
    
//*****************************************************************************
	
    public CmdMenu(String name, ImageIcon icon) {
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
