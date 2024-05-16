package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	ImageIcon logo;
	
	public GameFrame() {
		
		this.setTitle("X/O GAME");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500,500));
		this.setLayout(new BorderLayout());
		//this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		
		logo = new ImageIcon("src//icons//logo.png");
		this.setIconImage(logo.getImage());
		this.getContentPane().setBackground(new Color(0x123456));
		
	}
}