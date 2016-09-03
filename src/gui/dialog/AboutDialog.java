package gui.dialog;

import gui.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

/**
 * @author Fonseca, Flavio; Silva, Filipe; Barros, Leandro; Rodrigues, Marcelo & Pitangueira, Roque
 * @since July 2004
 */
public class AboutDialog extends EnhancedDialog {
	
	private JButton close;
	
//*****************************************************************************
	
	/**Constructor*/
	public AboutDialog(Interface parent) {
		
		super(parent,"About INSANE",true);
		
		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(new EmptyBorder(12,12,12,12));
		setContentPane(content);
		
		content.add(BorderLayout.CENTER,new AboutPanel(parent));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.setBorder(new EmptyBorder(12,0,0,0));
		
		buttonPanel.add(Box.createGlue());
		close = new JButton("Close");
		close.addActionListener(new ActionHandler());
		getRootPane().setDefaultButton(close);
		buttonPanel.add(close);
		buttonPanel.add(Box.createGlue());
		content.add(BorderLayout.SOUTH,buttonPanel);
		
		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
		show();
	}
	
//*****************************************************************************
	
	/**ok() method*/
	public void ok() {
		dispose();
	}
	
//*****************************************************************************
	
	/**cancel() method*/
	public void cancel() {
		dispose();
	}
	
//*****************************************************************************
	
	//ActionHandler class
	class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			dispose();
		}
	}
	
//*****************************************************************************
	
	//AboutPanel class
	static class AboutPanel extends JComponent {
		Interface parent;
		ImageIcon image;
		Vector text;
		int scrollPosition;
		AnimationThread thread;
		int maxWidth;
		FontMetrics fm;
		
		public static int TOP = 100;
		public static int BOTTOM = 30;
		
	//*****************************************************************************
		
		AboutPanel(Interface parent) {
			this.parent = parent;
			setFont(UIManager.getFont("Label.font"));
			fm = getFontMetrics(getFont());
			
			setForeground(new Color(96,96,96));
			image = new ImageIcon(ClassLoader.getSystemResource("images/About.jpg"));
			
			setBorder(new MatteBorder(1,1,1,1,Color.gray));
			
			text = new Vector(50);
			
			String str = this.parent.getProperties().getAboutText();
			StringTokenizer st = new StringTokenizer(str,"||");
			while(st.hasMoreTokens()) {
				String line = st.nextToken();
				text.addElement(line);
				maxWidth = Math.max(maxWidth,
					fm.stringWidth(line) + 10);
			}
			
			scrollPosition = -270;
			
			thread = new AnimationThread();
		}
		
	//*****************************************************************************
		
		public void paintComponent(Graphics g) {
			//g.setColor(new Color(96,96,96));
			g.setColor(Color.BLACK);
			
			image.paintIcon(this,g,1,1);
			
			FontMetrics fm = g.getFontMetrics();
			
			String version = "INSANE " + parent.getProperties().getVersion();
			g.drawString(version,(getWidth() - fm.stringWidth(version)) / 2, getHeight() - 5);
			
			g = g.create((getWidth() - maxWidth) / 2,TOP,maxWidth, getHeight() - TOP - BOTTOM);
			
			int height = fm.getHeight();
			int firstLine = scrollPosition / height;
			
			int firstLineOffset = height - scrollPosition % height;
			int lines = (getHeight() - TOP - BOTTOM) / height;
			
			int y = firstLineOffset;
			
			for(int i = 0; i <= lines; i++) {
				if(i + firstLine >= 0 && i + firstLine < text.size()) {
					String line = (String)text.get(i + firstLine);
					g.drawString(line,(maxWidth - fm.stringWidth(line))/2,y);
				}
				y += fm.getHeight();
			}
		}
		
	//*****************************************************************************
		
		public Dimension getPreferredSize() {
			return new Dimension(1 + image.getIconWidth(),
				1 + image.getIconHeight());
		}
		
	//*****************************************************************************
		
		public void addNotify() {
			super.addNotify();
			thread.start();
		}
		
	//*****************************************************************************
		
		public void removeNotify() {
			super.removeNotify();
			thread.kill();
		}
		
	//*****************************************************************************
		
		class AnimationThread extends Thread {
			private boolean running = true;
			
		//*****************************************************************************
			
			AnimationThread() {
				super("About box animation thread");
				setPriority(Thread.MIN_PRIORITY);
			}
			
		//*****************************************************************************
			
			public void kill() {
				running = false;
			}
			
		//*****************************************************************************
			
			public void run() {
				FontMetrics fm = getFontMetrics(getFont());
				int max = (text.size() * fm.getHeight());
				
				while (running)	{
					scrollPosition += 2;
					
					if(scrollPosition > max)
						scrollPosition = -250;
					
					try	{
						Thread.sleep(100);
					}
					catch(Exception e){}
					
					repaint(getWidth() / 2 - maxWidth, TOP,maxWidth * 2, getHeight() - TOP - BOTTOM);
				}
			}
			
		//*****************************************************************************
		}
		
	//*****************************************************************************
	}
	
//*****************************************************************************
}
