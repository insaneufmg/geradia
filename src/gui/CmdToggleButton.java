package gui;

import gui.command.*;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 */
public class CmdToggleButton extends JToggleButton implements CommandHolder {
	
    private Command command;
    
//*****************************************************************************
	
    /** Creates new CmdMenu */
    public CmdToggleButton() {
    }
	
//*****************************************************************************
	
    public CmdToggleButton(String name) {
        super(name);
    }
    
//*****************************************************************************
	
    public CmdToggleButton(String name, ImageIcon icon) {
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
