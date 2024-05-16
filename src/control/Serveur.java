package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Serveur {
	static boolean endGame = false;
	static int turn = 0;
	static String[] gameGrid = { "", "", "", "", "", "", "", "", "" };
	static int winner;
	static int[] gameEndedIndexes = new int[3];
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSoket = new ServerSocket(7050);
		while (true) {
			//IO
			ObjectInputStream inPlayer1 = null;
			ObjectOutputStream outPlayer1 = null;
			ObjectInputStream inPlayer2 = null;
			ObjectOutputStream outPlayer2 = null;

			Socket player1 = serverSoket.accept();
			//First Player
			try {
				outPlayer1 = new ObjectOutputStream(player1.getOutputStream());
				inPlayer1 = new ObjectInputStream(player1.getInputStream());
			}catch(IOException e){
				e.printStackTrace();
			}
			outPlayer1.writeInt(1);
			outPlayer1.flush();
			
			//Second Player
			Socket player2 = serverSoket.accept();

			try {
				outPlayer2 = new ObjectOutputStream(player2.getOutputStream());
				inPlayer2 = new ObjectInputStream(player2.getInputStream());	
			}catch(IOException e){
				e.printStackTrace();
			}
			
			outPlayer2.writeInt(2);
			outPlayer2.flush();

			int changement = 0;
			
			while (!endGame) {
				if (turn%2 == 0) {
					changement = inPlayer1.readInt();
					updateGame(changement);
					sendFormatedMessages(outPlayer1, outPlayer2, changement);
					
				} else {
					changement = inPlayer2.readInt();
					updateGame(changement);
					sendFormatedMessages(outPlayer1, outPlayer2, changement);
				}

			}//Game Ended here
			reinitializeGame();

			System.out.println("Game Ended !!");
		}
		
	}

	private static void reinitializeGame() {
		endGame = false;
		turn = 0;
		String[] t1 = { "", "", "", "", "", "", "", "", "" };
		gameGrid = t1;
		winner = 0;
		int[] t2 = new int[3];
		gameEndedIndexes = t2;
		
	}

	private static void updateGame(int indice) {
		if (turn % 2 == 0)
			gameGrid[indice] = "X";
		else
			gameGrid[indice] = "O";
		turn++;

		GameOverTest("X");
		GameOverTest("O");

	}

	
	public static void GameOverTest(String mark) {
		int num = 0;
		if (mark.equals("X"))
			num = 1;
		else
			num = 2;

		if (gameGrid[0].equals(mark) && gameGrid[1].equals(mark) && gameGrid[2].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=0;gameEndedIndexes[1]=1;gameEndedIndexes[2]=2;
		} else if (gameGrid[3].equals(mark) && gameGrid[4].equals(mark) && gameGrid[5].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=3;gameEndedIndexes[1]=4;gameEndedIndexes[2]=5;

		} else if (gameGrid[6].equals(mark) && gameGrid[7].equals(mark) && gameGrid[8].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=6;gameEndedIndexes[1]=7;gameEndedIndexes[2]=8;
		} else if (gameGrid[0].equals(mark) && gameGrid[3].equals(mark) && gameGrid[6].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=0;gameEndedIndexes[1]=3;gameEndedIndexes[2]=6;
		} else if (gameGrid[1].equals(mark) && gameGrid[4].equals(mark) && gameGrid[7].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=1;gameEndedIndexes[1]=4;gameEndedIndexes[2]=7;
		} else if (gameGrid[2].equals(mark) && gameGrid[5].equals(mark) && gameGrid[8].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=2;gameEndedIndexes[1]=5;gameEndedIndexes[2]=8;
		} else if (gameGrid[0].equals(mark) && gameGrid[4].equals(mark) && gameGrid[8].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=0;gameEndedIndexes[1]=4;gameEndedIndexes[2]=8;
		} else if (gameGrid[2].equals(mark) && gameGrid[4].equals(mark) && gameGrid[6].equals(mark)) {
			endGame = true;
			winner = num;
			gameEndedIndexes[0]=2;gameEndedIndexes[1]=4;gameEndedIndexes[2]=6;
		}
	}
	
	public static void sendFormatedMessages(ObjectOutputStream outPlayer1, ObjectOutputStream outPlayer2, int changement) throws IOException {
		String msg  = String.valueOf(changement) + "/" + "notyet";
		String winMsg = String.valueOf(changement) + "/" + "WIN"+ "/"+ gameEndedIndexes[0]+ "/"+ gameEndedIndexes[1]+ "/"+ gameEndedIndexes[2];
		String lostMsg = String.valueOf(changement) + "/" + "LOST"+ "/"+ gameEndedIndexes[0]+ "/"+ gameEndedIndexes[1]+ "/"+ gameEndedIndexes[2]; 
		String drawMsg = String.valueOf(changement) + "/" + "DRAW"; 

		
		boolean test = true;
		for(int i =0; i<9; i++) {
			if(gameGrid[i].equals("")) test = false;
		}
		//draw : grid full and no one wins
		if (winner == 0 && test) {
			outPlayer1.writeUTF(drawMsg);
			outPlayer1.flush();
			outPlayer2.writeUTF(drawMsg);
			outPlayer2.flush();
		}
		//still playing  only one player who recive the modification of the other one
		else if (winner == 0 && !test) {
			//modification ici 
			if(turn%2 == 1) {
				outPlayer2.writeUTF(msg);		
				outPlayer2.flush();
			}else {
				outPlayer1.writeUTF(msg);		
				outPlayer1.flush();
			}
			//================
		//player 1 wins
		} else if (winner == 1) {
			outPlayer1.writeUTF(winMsg);
			outPlayer1.flush();
			outPlayer2.writeUTF(lostMsg);
			outPlayer2.flush();
		//player 2 wins
		} else if (winner == 2) { 
			outPlayer1.writeUTF(lostMsg);
			outPlayer1.flush();
			outPlayer2.writeUTF(winMsg);
			outPlayer2.flush();
		}

	}

}
