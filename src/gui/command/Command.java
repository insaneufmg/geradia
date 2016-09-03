package gui.command;

/**
 *
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 */
public interface Command {
	
//*****************************************************************************
	
    public void execute();
    
//*****************************************************************************
	
    public void undo();
    
//*****************************************************************************
	
    public void redo();
    
//*****************************************************************************
}
