package draw;

import java.awt.Rectangle;
 
/**
 * Interface indicates that object can be selected with a mouse click.
 * To be moveable the object must implement the <code>Moveable</code>
 * interface.
 * 
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since April 2004
 */
public interface Selectable {
	
//*****************************************************************************
	
  /**
   * Sets the selected property.
   * 
   * @param sel true if selected, false if not.
   */
  public void setSelected(boolean sel);
  
//*****************************************************************************
  
  /**
   * Returns true if the object's selected property is set.
   * 
   * @return true if selected, false if not.
   */
  public boolean isSelected();
  
//*****************************************************************************
  
  /**
   * Gets the bounding rectangle in device
   * coordinates.
   *
   * @return bounding rectangle
   */
  public Rectangle getBounds();
  
//*****************************************************************************
  
  /**
   * Returns true if the current state is selectable.
   *
   * @return true if selectable
   */
  public boolean isSelectable();
  
//*****************************************************************************
  
  /**
   * Set the Selectable property.
   *
   * @param select if true object is selectable
   */
  public void setSelectable(boolean select);
  
//*****************************************************************************
}
