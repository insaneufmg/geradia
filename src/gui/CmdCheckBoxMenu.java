package gui;

import gui.command.*;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ImageIcon;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public class CmdCheckBoxMenu extends JCheckBoxMenuItem implements CommandHolder {
	
    private Command command;
    
//*****************************************************************************
	
    /** Creates new CmdCheckBoxMenu */
    public CmdCheckBoxMenu() {
    }
	
//*****************************************************************************
	
    public CmdCheckBoxMenu(String name) {
        super(name);
    }
    
//*****************************************************************************
	
    public CmdCheckBoxMenu(String name, ImageIcon icon) {
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
