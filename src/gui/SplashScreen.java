package gui;

import java.awt.*;

/** Class representing the SplashScreen of the application.
* @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
* @since January 2004
*/
public class SplashScreen extends Window {
	
	//Message to be show with the image
	private final String message = "Loading Geradia";
	
	//Image to be show in the SplashScreen
	private final String imgName = "images/Insane.jpg";
	Image splashImage;
	
	public Toolkit toolkit;
	private static SplashScreen splash;
	private static final int PROGRESS_HEIGHT = 20;
	private int progress;
	private Image offscreenImg;
	private Graphics offscreenGfx;
	private FontMetrics fm;
	
	//Singleton to guaratee that there will be only one instace in memory
	static {
		splash = new SplashScreen();
	}
	
//*****************************************************************************
	
	private SplashScreen()
	{
		super(new Frame());
		setVisible(false);
		
		splashImage = null;
		toolkit = Toolkit.getDefaultToolkit();
	}
	
//*****************************************************************************
	
	public static SplashScreen getInstance()
	{
		return splash;
	}
	
//*****************************************************************************
	
	/**
	 * Method initSplash.
	 */
	private void initSplash()
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		setBackground(Color.white);
		
		Font font = new Font("Dialog",Font.PLAIN,10);
		setFont(font);
		fm = getFontMetrics(font);
		
		//Loads image into memory
		MediaTracker media = new MediaTracker(this);
		splashImage = toolkit.getImage(ClassLoader.getSystemResource("images/Insane.jpg"));
		
		if (splashImage != null) {
			media.addImage(splashImage, 0);
			
			try {
				media.waitForID(0);
			}
			catch (InterruptedException ie) {}
		}
		
		//Sets SplashScreen's size and position
		setSize(splashImage.getWidth(this), splashImage.getHeight(this) + PROGRESS_HEIGHT);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getSize();
		
		if (size.width > screenSize.width)
			size.width = screenSize.width;
		
		if (size.height > screenSize.height)
			size.height = screenSize.height;
		
		setLocation((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2);
		setVisible(true);
	}
	
//*****************************************************************************
	
	public void openSplash()
	{
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		initSplash();
	}
	
//*****************************************************************************
	
	public void finish()
	{
		setVisible(false);
		dispose();
	}
	
//*****************************************************************************
	
	public synchronized void advance() {
		progress++;
		repaint();
		
		// wait for it to be painted to ensure progress is updated continuously
		try	{
			wait();
		}
		catch(InterruptedException ie) {}
	}
		
//*****************************************************************************
		
	public void update(Graphics g) {
		paint(g);
	}
	
//*****************************************************************************
	
	public synchronized void paint(Graphics g) {
		Dimension size = getSize();
		
		if(offscreenImg == null) {
			offscreenImg = createImage(size.width,size.height);
			offscreenGfx = offscreenImg.getGraphics();
			offscreenGfx.setFont(getFont());
		}
		
		offscreenGfx.setColor(Color.gray);
		offscreenGfx.drawRect(0,0,size.width - 1,size.height - 1);
		
		offscreenGfx.drawImage(splashImage,1,1,this);
		
		// Obs: This should not be hardcoded
		offscreenGfx.setColor(new Color(168,173,189));
		offscreenGfx.fillRect(1,splashImage.getHeight(this) + 1, ((this.getWidth() - 2) * progress) / 3,PROGRESS_HEIGHT);
		
		offscreenGfx.setColor(Color.BLACK);
		
		offscreenGfx.drawString(message, (getWidth() - fm.stringWidth(message)) / 2, splashImage.getHeight(this)+PROGRESS_HEIGHT+1 - fm.getDescent() - 5);
		
		g.drawImage(offscreenImg,0,0,this);
		
		notify();
		
	}
	
//*****************************************************************************
}
