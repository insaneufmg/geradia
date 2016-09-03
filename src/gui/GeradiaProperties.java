package gui;

/**
 * A class containing the properties of INSANE
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since August 2004
 */
public class GeradiaProperties {
	
	private String aboutText;
	private String version;
	private String date;
	
//*****************************************************************************
	
	/** Constructor */
	GeradiaProperties() {
		date = "November 2005";
		version = "1.0";
		
		//"||" is the line separator which may be used by a StringTokenizer object
		aboutText = "Geradia is brought to you by || ||";
		aboutText += "Universidade Federal de Minas Gerais" + "||";
		aboutText += "Escola de Engenharia" + "||";
		aboutText += "Departamento de Engenharia de Estruturas" + "||";
		aboutText += " || || ||";
		aboutText += "The Geradia team || ||";
		aboutText += "Filipe de Paula Braga Silva" + "||";
		aboutText += "Flavio Torres da Fonseca" + "||";
		aboutText += "Leandro Augusto Campelo de Barros" + "||";
		aboutText += "Marcelo Paixão Pinto Rodrigues" + "||";
		aboutText += "Roque Luiz Pitangueira" + "||";
		aboutText += " || || ||";
		aboutText += "For comments, feedback and bug reports," + "||";
		aboutText += "please send a mail to" + "|| ||";
		aboutText += "insane@dees.ufmg.br" + "||";
		aboutText += " || || ||";
		aboutText += "We'd also like to thank the users for their" + "||";
		aboutText += "help in testing and improving this program." + "||";
		aboutText += " || || ||";
		aboutText += "Belo Horizonte, Minas Gerais, Brazil" + "||";
		aboutText += this.getDate() + "||";
	}
	
//*****************************************************************************
	
	/** Returns the about text.
	* @return The aboutText.
	*/
	public String getAboutText() {
		return aboutText;
	}
	
//*****************************************************************************
	
	/** Sets the about text.
	* @param a The desired aboutText
	*/
	public void setAboutText(String a) {
		aboutText = a;
	}
	
//*****************************************************************************
	
	/** Returns the version of INSANE.
	* @return The version of INSANE.
	*/
	public String getVersion(){
		return version;
	}
	
//*****************************************************************************
	
	/** Returns the version of INSANE.
	* @param a The desired version.
	*/
	public void setVersion(String a){
		version = a;
	}
	
//*****************************************************************************
	
	/** Returns the date of INSANE.
	* @return The date of INSANE.
	*/
	public String getDate(){
		return date;
	}
	
//*****************************************************************************
	
	/** Returns the date of INSANE.
	* @param a The desired date.
	*/
	public void setDate(String a){
		date = a;
	}
	
//*****************************************************************************
}
