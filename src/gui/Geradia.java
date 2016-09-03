package gui;

import javax.swing.UIManager;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.plaf.FontUIResource;

/**
 * This is the main class of Geradia.
 * Sets Look and Feel, validates and centers interface.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @version 1.0
 * @since November 2003
 */
public class Geradia {
    
	static SplashScreen splash;
	
//*****************************************************************************
	
    /**
     * Constructor method. <br>
     * Validates and centers interface
     */
    Geradia() {
		
        Interface frame = new Interface();
        frame.validate();
		
		//Centers the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
      	frame.show();
		
		frame.getSplitPane().setDividerLocation(frame.getWidth()/2 - frame.getSplitPane().getDividerSize());
	}
    
//*****************************************************************************
	
    /**
     * Sets Look And Feel
     * Starts Aplication
     */
    public static void main(String[] args ) {
		try {
			splash = SplashScreen.getInstance();
			splash.openSplash();
			
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            Font fontCurrent = UIManager.getFont("Button.font");
            String name = fontCurrent.getName();
            
			Geradia.advanceSplashProgress();
			
            //Creates similar font with the specified style and size
            FontUIResource fontResourceNew  = new FontUIResource(name, Font.PLAIN, 12);
            
            //Sets all items fonts
            UIManager.put("Button.font", fontResourceNew);
            UIManager.put("CheckBox.font", fontResourceNew);
            UIManager.put("CheckBoxMenuItem.font", fontResourceNew);
            UIManager.put("ColorChooser.font", fontResourceNew);
            UIManager.put("ComboBox.font", fontResourceNew);
            UIManager.put("DesktopIcon.font", fontResourceNew);
            UIManager.put("EditorPane.font", fontResourceNew);
            UIManager.put("InternalFrame.font", fontResourceNew);
            UIManager.put("Label.font", fontResourceNew);
            UIManager.put("List.font", fontResourceNew);
            UIManager.put("MenuBar.font", fontResourceNew);
            UIManager.put("Menu.font", fontResourceNew);
            UIManager.put("MenuItem.font", fontResourceNew);
            UIManager.put("OptionPane.font", fontResourceNew);
            UIManager.put("PasswordField.font", fontResourceNew);
            UIManager.put("PopupMenu.font", fontResourceNew);
            UIManager.put("ProgressBar.font", fontResourceNew);
            UIManager.put("RadioButton.font", fontResourceNew);
            UIManager.put("RadioButtonMenuItem.font", fontResourceNew);
            UIManager.put("ScrollPane.font", fontResourceNew);
            UIManager.put("TabbedPane.font", fontResourceNew);
            UIManager.put("TableHeader.font", fontResourceNew);
            UIManager.put("TextArea.font", fontResourceNew);
            UIManager.put("TextField.font", fontResourceNew);
            UIManager.put("TextPane.font", fontResourceNew);
            UIManager.put("TitledBorder.font", fontResourceNew);
            UIManager.put("ToggleButton.font", fontResourceNew);
            UIManager.put("ToolBar.font", fontResourceNew);
            UIManager.put("ToolTip.font", fontResourceNew);
            UIManager.put("Tree.font", fontResourceNew);
            UIManager.put("Viewport.font", fontResourceNew);
        }
        catch (Exception e){}
        new Geradia();
    }
	
//*****************************************************************************
	
	public static void advanceSplashProgress() {
		if(splash != null)
			splash.advance();
	}
	
//*****************************************************************************
}
