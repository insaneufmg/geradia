package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.net.URL;

/**
 * An Image component providing a method subscription.
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since January 2004
 */
public class ImageDA extends JComponent {
	
    /** The image to show */
    Image image;
	
//*****************************************************************************
	
    /**The constructor method getting URL*/
    public ImageDA(URL url) {
        image = Toolkit.getDefaultToolkit().getImage(url);
    }
	
//*****************************************************************************
	
    /** The constructor method getting a string*/
    public ImageDA(String s) {
        image = Toolkit.getDefaultToolkit().getImage(s);
    }
	
//*****************************************************************************
	
    /** Draws the image
     * @param     g  the graphic element.
     */
    public void paintComponent(Graphics g) {
        Rectangle me = new Rectangle();
        me = this.getBounds();
        Rectangle img = new Rectangle();
        img.width = image.getWidth(this);
        img.height = image.getHeight(this);
        g.setColor(Color.white);
        g.fillRect(me.x - 4, me.y - 4, me.width + 8, me.height + 8);
        g.drawImage(image, me.width/2 - img.width/2, me.height/2 - img.height/2, this);
    }
	
//*****************************************************************************

}