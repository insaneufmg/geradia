package gui;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import gui.command.Command;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since	January 2004
 */
public class DrawingArea extends PrintableGridCanvas {
    
    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	protected IView view;
    private Command mouseMovedCmd;
    private Command mouseDraggedCmd;
    private Command mouseClickedCmd;
    private Command rigthMouseClickedCmd;
    private Command mouseEnteredCmd;
    private Command mouseExitedCmd;
    private Command mousePressedCmd;
    private Command mouseReleasedCmd;
    private boolean repainting = false;
    private Shape shp;
	
    /**
     * The constructor method
     */
    public DrawingArea(IView v) {
        view = v;
    }
    
	
    public void setRepainting(boolean on) {
        repainting = on;
    }
    
	
    public boolean isRepainting() {
        return repainting;
    }
    
	
    public void setShape(Shape s) {
        shp = s;
    }
    
	
	public Shape getShape() {
        return shp;
    }
	

    public void setMovedCommand(Command cmd) {
        mouseMovedCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
    
	
    public void setDraggedCommand(Command cmd) {
        mouseDraggedCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }   

    public void setClickedCommand(Command cmd) {
        mouseClickedCmd = cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
	
    
	public Command getClickedCommand() {
		return mouseClickedCmd;
	};
		
    public void setRightClickedCommand(Command cmd) {
        rigthMouseClickedCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
    	
    public void setEnteredCommand(Command cmd) {
        mouseEnteredCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
    	
    public void setExitedCommand(Command cmd) {
        mouseExitedCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
    	
    public void setPressedCommand(Command cmd) {
        mousePressedCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
    	
    public void setReleasedCommand(Command cmd) {
        mouseReleasedCmd =cmd;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
    	
    public void removeAllCommands() {
        mouseMovedCmd = null;
        mouseDraggedCmd = null;
        mouseClickedCmd = null;
        rigthMouseClickedCmd = null;
        mouseEnteredCmd = null;
        mouseExitedCmd = null;
        mousePressedCmd = null;
        mouseReleasedCmd = null;
        setRepainting(false);
        setShape(null);
        this.repaint();
    }
	
    public void setCursor(){
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }
	
	public void recompose() {
		try {
			view.getController().compose();
		} catch (NullPointerException e) {
			//System.out.println("Null Controller");
			//There is always null controller before setToGeometry() in program initialization
		}
		this.repaint();
	}
		
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
		
		if (shp != null) {
       		g2.setStroke( new BasicStroke( 0.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND ) );
       		g2.draw(shp);
		}
		
        view.getController().draw(g2);
        
    }
	    
    /**
     * Gets the mouse coords, transform and sets the message field
     * on Interface (The parent) status bar according to that.
     *
     * @param e The mouse event.
     */
    public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		if (isRepainting()) {
			repaint();
        }
        
        if (mouseMovedCmd != null) {
            mouseMovedCmd.execute();
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(((ViewState) this.view.getViewState()).getPrecision());
		nf.setMaximumFractionDigits(((ViewState) this.view.getViewState()).getPrecision());
		
        view.setCoord("" + nf.format(getWorldPoint().getX()),
			"" + nf.format(getWorldPoint().getY()));
    }
    	
    /**
     * A method necessary from implementation.
     * It must be overwrited by subclasses.
     *
     * @param e The mouse event.
     */
    public void mouseDragged(MouseEvent e) {
        if (mouseDraggedCmd != null) {
            mouseDraggedCmd.execute();
        }
    }
    
	
    /**
     * A method necessary from implementation.
     * It must be overwrited by subclasses.
     *
     * @param e The mouse event.
     */
    public void mouseClicked(MouseEvent e) {
        if((e.getModifiers() & Event.META_MASK) !=0){
            if (rigthMouseClickedCmd != null) {
                rigthMouseClickedCmd.execute();
            }
        }else {
            if (mouseClickedCmd != null) {
                mouseClickedCmd.execute();
            }
        }
    }
    
	
    /**
     * A method necessary from implementation.
     * It must be overwrited by subclasses.
     *
     * @param e The mouse event.
     */
    public void mouseEntered(MouseEvent e) {
        if (mouseEnteredCmd != null) {
            mouseEnteredCmd.execute();
        }
    }
    
	
    /**
     * A method necessary from implementation.
     * It must be overwrited by subclasses.
     *
     * @param e The mouse event.
     */
    public void mouseExited(MouseEvent e) {
        if (mouseExitedCmd != null) {
            mouseExitedCmd.execute();
        }
    }
    
	
    /**
     * A method necessary from implementation.
     * It must be overwrited by subclasses.
     *
     * @param e The mouse event.
     */
    public void mousePressed(MouseEvent e) {
        if (mousePressedCmd != null) {
            mousePressedCmd.execute();
        }
    }
    
	
    /**
     * A method necessary from implementation.
     * It must be overwrited by subclasses.
     *
     * @param e The mouse event.
     */
    public void mouseReleased(MouseEvent e) {
        if (mouseReleasedCmd != null) {
            mouseReleasedCmd.execute();
        }
    }
    
}//End of the Class
