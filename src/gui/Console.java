package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.*;
import gui.command.*;
import java.util.*;

public class Console extends JPanel {
	
	private Interface parent;
	private JLabel label;
	private JTextArea output;
	private JTextField prompt;
	private Command command;
	private ArrayList history;
	private Point2D lastPoint;
	
//*****************************************************************************
	
	public Console(Interface par) {
		
		super(new BorderLayout(5, 5));
		parent = par;
		
		history = new ArrayList(20);
		lastPoint = new Point2D.Double(0,0);
		
		label = new JLabel("Prompt: ");
		this.add(BorderLayout.WEST, label);
		
		prompt = new JTextField();
		this.add(BorderLayout.CENTER, prompt);
		
		output = new JTextArea(2, 120);
		output.setLineWrap(true);
		output.setEditable(false);
		JScrollPane scroller = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(BorderLayout.NORTH, scroller);
		
		this.setBorder(BorderFactory.createEmptyBorder(2,3,1,3));
	}
	
//*****************************************************************************
		
	public void setOutputText(String str) {
		output.setText(str);
		output.setCaretPosition(output.getText().length());
	};
	
//*****************************************************************************
	
	public void addOutputText(String str) {
		output.append("\n");
		output.append(str);
		output.setCaretPosition(output.getText().length());
	};
	
//*****************************************************************************
	
	public void clearPrompt() {
		prompt.setText("");
	}
	
//*****************************************************************************
	
	public void setPromptText(String str) {
		prompt.setText(str);
	}
	
//*****************************************************************************
	
	public void clear() {
		output.setText("Universidade Federal de Minas Gerais");
		output.append("\nEscola de Engenharia");
		output.setCaretPosition(output.getText().length());
		history.clear();
	}
	
//*****************************************************************************
	
	public void addListener(ConsoleHandler ch) {
		prompt.addActionListener(ch);
		prompt.addKeyListener(ch);
	}
	
//*****************************************************************************
	
	public void requestFocus() {
		prompt.requestFocus();
	}
	
//*****************************************************************************
	
	public void addToHistory(String str) {
		if (history.size() == 0) {history.add(0, str);}
		else {
			String str2 = (String) history.get(0);
			if (!str.equals(str2)) {history.add(0, str);}
			}
	}
	
//*****************************************************************************
	
	public ArrayList getHistory() {
		return history;
	}
	
//*****************************************************************************
	
	public String getLastCommand() {
		String str = (String) this.getHistory().get(0);
		return str;
	}
	
//*****************************************************************************
	
	public Point2D getLastPoint() {
		return lastPoint;
	}
	
//*****************************************************************************
	
	public void setLastPoint(Point2D p) {
		lastPoint = p;
	}
	
//*****************************************************************************
}
