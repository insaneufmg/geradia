package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import gui.command.*;

/**
* This class is a implementation of the interface ActionListener.
* It is used to deal with events from Console's Prompt
* @author Fonseca,Flavio & Pitangueira, Roque
* @since January 2004
* @see java.awt.event.ActionListener
* @see gui.Console
*/
public class ConsoleHandler extends KeyAdapter implements ActionListener {
	
	private static int COMMAND_MODE = 1;
	private static int DATA_MODE = 2;
	
	private Interface parent;
	private String str;
	private Command cmd;
	private Command cmd2;
	private int counter;
	private int mode;
	
//*****************************************************************************
	
	/** Constructor Method.
	* @param parent The parent Interface.
	*/
	public ConsoleHandler(Interface parent) {
		this.parent = (Interface)parent;
		this.setToCommandMode();
	}
	
//*****************************************************************************
	
	/** Method which executes the desired action to each valid command.*/
	public void actionPerformed(ActionEvent evt) {
		JTextField source = (JTextField)evt.getSource();
		
		//Works only if prompt is not empty
		if (!evt.getActionCommand().equals("")) {
			//Writes command to output area and clears Prompt
			parent.getConsole().addOutputText("\"" + evt.getActionCommand() + "\"");
			parent.getConsole().addToHistory(evt.getActionCommand());
			counter = -1;
			
			source.setText("");
			
			//Converts the command to up case and stores it into str
			str = evt.getActionCommand().toUpperCase();
			
			if (str.equals("CLEAR")) {parent.getConsole().clear(); cmd = new DoesNothingCommand(parent);}
			else if (str.equals("REDRAW")) {cmd = new RedrawCommand(parent);}

			//Commands for StateButtons
			else if (str.equals("GRID")) {cmd = new GridCommand(parent);}
			else if (str.equals("SNAP")) {cmd = new SnapCommand(parent);}
			
			//Commands for File
			else if (str.equals("EXIT")) {cmd = new ExitCommand(parent);}
			else if (str.equals("NEW")) {cmd = new NewFileCommand(parent);}
			else if (str.equals("NEW")) {cmd = new NewFileCommand(parent);}
			else if (str.equals("OPEN")) {cmd = new OpenCommand(parent);}
			else if (str.equals("SAVE")) {cmd = new SaveFileCommand(parent);}
			else if (str.equals("SAVEAS") || str.equals("SAVE AS")) {cmd = new SaveFileAsCommand(parent);}
			else if (str.equals("PRINT")) {cmd = new PrintCommand(parent);}
			
			//Commands for View
			else if (str.equals("ZOOM")) {
				cmd = new ZoomCommand(parent);
				setKeybCommand(new ZoomKeybCommand(parent));
				setToDataMode();
				parent.addOutputText("Click first corner of window or");
				parent.addOutputText("All / Extents / In / Last / Out");
			}
			else if (str.equals("ZOOMWINDOW") || str.equals("ZOOM WINDOW")) {cmd = new ZoomCommand(parent);}
			else if (str.equals("ZOOMALL") || str.equals("ZOOM ALL")) {cmd = new ZoomAllCommand(parent);}
			else if (str.equals("ZOOMEXTENTS") || str.equals("ZOOM EXTENTS")) {cmd = new FitCommand(parent);}
			else if (str.equals("ZOOMIN") || str.equals("ZOOM IN")) {cmd = new ZoomInCommand(parent);}
			else if (str.equals("ZOOMOUT") || str.equals("ZOOM OUT")) {cmd = new ZoomOutCommand(parent);}
			else if (str.equals("ZOOMLAST") || str.equals("ZOOM LAST")) {cmd = new LastZoomCommand(parent);}
			
			//Commands for Edit
			else if (str.equals("UNDO")) {cmd = parent.getUndoCommand();}
			else if (str.equals("REDO")) {cmd = parent.getRedoCommand();}
			else if (str.equals("SELECTALL") || str.equals("SELECT ALL")) {cmd = new SelectAllCommand(parent);}
			else if (str.equals("DELETE") || str.equals("DEL")) {cmd = new DeleteCommand(parent);}
			
			//Commands for Help
			else if (str.equals("HELP")) {cmd = new HelpTopicsCommand(parent);}
			else if (str.equals("ABOUT")) {cmd = new AboutCommand(parent);}
			
			//Commands for Draw (works only if actualTreeNode == ModelState.GEOMETRY)
			}
			
			
			
			//Insert new commands over here
			
			//for COMMAND_MODE
			if (mode == COMMAND_MODE) {
				try { 
					cmd.execute();
					cmd = null;
				}catch (Exception e) {
					parent.getConsole().addOutputText("Invalid Command.");
				}
			}
			
			//for DATA_MODE
			else {
				try {
					cmd.execute();
					cmd = null;
				}catch (Exception e) {
					cmd2.execute();
				}
			}
		
	}
	
//*****************************************************************************
	
	/** Method to treat events from a key.
	*@param evt The key event to be treated;
	*/
	public void keyReleased(KeyEvent evt) {
		Console cons = parent.getConsole();
		if (evt.getKeyCode() == evt.VK_DOWN) {
			if (counter > 0) {counter--;}
			str = (String) cons.getHistory().get(counter);
			cons.setPromptText(str);
		} else if (evt.getKeyCode() == evt.VK_UP) {
			if (counter < cons.getHistory().size()-1) {counter++;}
			str = (String) cons.getHistory().get(counter);
			cons.setPromptText(str);
		} else if (evt.getKeyCode() == evt.VK_ESCAPE) {
			cmd = new RedrawCommand(parent);
			cmd.execute();
		}
	}
	
//*****************************************************************************
	
	/** Sets this ConsoleHandler to data mode.*/
	public void setToDataMode() {
		mode = DATA_MODE;
	}
	
//*****************************************************************************
	
	/** Sets this ConsoleHandler to command mode.*/
	public void setToCommandMode() {
		mode = COMMAND_MODE;
	}
	
//*****************************************************************************
	
	/** Sets the keyboard command (cmd2).
	* @param command The kayboard command to be set.
	*/
	public void setKeybCommand(Command command) {
		cmd2 = command;
	}
	
//*****************************************************************************
	
	/** Returns the keyboard command (cmd2).
	* @return The kayboard command (cmd2).
	*/
	public Command getKeybCommand() {
		return cmd2;
	}
	
//*****************************************************************************
}
