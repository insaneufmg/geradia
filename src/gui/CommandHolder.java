package gui;

import gui.command.*;

/**
 *
 * @author Fonseca,Flavio & Pitangueira, Roque
 * @since January 2004
 */
public interface CommandHolder {
	
//*****************************************************************************
	
    public void setCommand(Command cmd);
    
//*****************************************************************************
	
    public Command getCommand();
    
//*****************************************************************************
}
