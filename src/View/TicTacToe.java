package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class TicTacToe {
	
	List<String> actualGrid = new ArrayList<>();
	
	//element de l'interface graphique
	JFrame frame = new GameFrame();
	JPanel gamePanel = new JPanel();
	static JLabel label1 = new JLabel("Player 1 turn");
	List<JButton> buttons = new ArrayList<>();
	//________________
	
	public int turn = 0;
	static Boolean win;
    public int indChangement = 0;

	public TicTacToe() {
		win = false;
		//initialisation du grid
		for(int i=0;i<9;i++)
			actualGrid.add("");

		gamePanel.setPreferredSize(new Dimension(300, 300));
		gamePanel.setLayout(new GridLayout(3, 3));
		gamePanel.setBackground(new Color(0x123456));

		
		// the 9 buttons
		for (int i = 0; i < 9; i++) {
			buttons.add(new JButton());
		}
		for (int i = 0; i < 9; i++) {
			buttons.get(i).setOpaque(false);
			buttons.get(i).setBackground(new Color(0, 0, 0, 0));
			buttons.get(i).setBorder(BorderFactory.createEmptyBorder());
			buttons.get(i).setForeground(Color.MAGENTA);
			buttons.get(i).setFont(new Font("MV Boli", Font.PLAIN, 30));
		}

		for (int i = 0; i < 9; i++) {
			JButton b = buttons.get(i);
			buttons.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (b.getText() == "" && turn % 2 == 0) {
						b.setText("X");
						label1.setText("Player 2 Turn");
						turn++;
						
					}
					if (b.getText() == "" && turn % 2 == 1) {
						b.setText("O");
						label1.setText("Player 1 Turn");
						turn++;
						
					}
					updateGrid();
				}
			});
		}

		// Grid border color
		buttons.get(0).setBorder(new MatteBorder(0, 0, 2, 2, Color.WHITE));// top/left/buttom/right
		buttons.get(3).setBorder(new MatteBorder(0, 0, 2, 2, Color.WHITE));
		buttons.get(6).setBorder(new MatteBorder(0, 0, 0, 2, Color.WHITE));

		buttons.get(1).setBorder(new MatteBorder(0, 2, 2, 2, Color.WHITE));
		buttons.get(4).setBorder(new MatteBorder(0, 2, 2, 2, Color.WHITE));
		buttons.get(7).setBorder(new MatteBorder(0, 2, 0, 2, Color.WHITE));

		buttons.get(2).setBorder(new MatteBorder(0, 2, 2, 0, Color.WHITE));
		buttons.get(5).setBorder(new MatteBorder(0, 2, 2, 0, Color.WHITE));
		buttons.get(8).setBorder(new MatteBorder(0, 2, 0, 0, Color.WHITE));

		for (JButton button : buttons) {
			gamePanel.add(button);
		}

		// label
		
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("Arial", Font.BOLD, 16));
		label1.setForeground(Color.MAGENTA);

		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1;

		gbc.fill = GridBagConstraints.LAST_LINE_END;
		frame.add(label1, gbc);
		gbc.fill = GridBagConstraints.CENTER;
		gbc.weighty = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		frame.add(gamePanel, gbc);

		frame.pack();
		frame.setVisible(true);
	}
	
	//methode qui desactive les boutons qui ne sont pa utiliser pour eviter de les cliquer lorsque le jeu est fini
	static void desableButtons(List<JButton> buttons) {
		for(int i=0;i<9;i++) {
			if (buttons.get(i).getText()=="") {
				buttons.get(i).setEnabled(false);
			}
		}
	}

	public void updateGrid() {
		List<String> l = new ArrayList<>();
		for(int i =0;i<9;i++) {
			if (buttons.get(i).getText() != actualGrid.get(i)) indChangement = i;//element cliqué
			l.add(buttons.get(i).getText());
		}		
		actualGrid = l;
	}

	public void block() {
		for(int i =0;i<9;i++){
			if(buttons.get(i).getText()=="") buttons.get(i).setEnabled(false);
		}
	}

	public void permit() {
		for(int i =0;i<9;i++){
			if(buttons.get(i).getText()=="") buttons.get(i).setEnabled(true);
		}
	}

	public void setModifications(int ind) {
		//if empty then set a value
		if (buttons.get(ind).getText().equals("")) {
			buttons.get(ind).setEnabled(true);
			if (this.turn%2 == 0) 
				buttons.get(ind).setText("X");
			else
				buttons.get(ind).setText("O");
			if (turn % 2 == 0) {
				label1.setText("Player 2 Turn");			
			}
			if (turn % 2 == 1) {
				label1.setText("Player 1 Turn");
			}
			updateGrid();
			turn++;
		}
	}

	public void displayDraw() {
		label1.setText("DRAW");
		label1.setFont(new Font("Arial", Font.BOLD, 25));
		label1.setForeground(Color.WHITE);		
	}

	public void displayWin(int x, int y, int z) {
		buttons.get(x).setForeground(Color.GREEN);
		buttons.get(y).setForeground(Color.GREEN);
		buttons.get(z).setForeground(Color.GREEN);
		desableButtons(buttons);

		label1.setText("You Win");
		label1.setFont(new Font("Arial", Font.BOLD, 25));
		label1.setForeground(Color.GREEN);
		win = true;
		
	}

	public void displayLost(int x, int y, int z) {
		buttons.get(x).setForeground(Color.RED);
		buttons.get(y).setForeground(Color.RED);
		buttons.get(z).setForeground(Color.RED);
		desableButtons(buttons);
		//label
		label1.setText("You Lost");
		label1.setFont(new Font("Arial", Font.BOLD, 25));
		label1.setForeground(Color.RED);
		win = true;
	}

}