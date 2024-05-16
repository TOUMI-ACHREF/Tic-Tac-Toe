package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import View.TicTacToe;

public class Client {
	
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		int playerNumber = 0;
		boolean endGame = false;
		
		Socket socket = new Socket("localhost",7050);

		ObjectInputStream reader = null;
		ObjectOutputStream writer = null;
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
		}catch (IOException e) {
			e.printStackTrace();
		}
		//recuperation du numero du client
        playerNumber = reader.readInt();
        
        System.out.println(playerNumber);
        
        TicTacToe game = new TicTacToe();
        
        //game beggin
        while(!endGame) {
        	//player 1
        	if(game.turn%2 == 0 && playerNumber == 1) {
        		game.permit();
        		while(game.turn%2 == 0 && playerNumber == 1) Thread.sleep(500);
        		writer.writeInt(game.indChangement);writer.flush();
        	}
        	else if(game.turn%2 == 1 && playerNumber == 1) {
        		game.block();
        		String changement = reader.readUTF();
        		endGame = traiteMessage(game, changement);
        	}
        	//_________
        	
        	//Player 2
        	if(game.turn%2 == 1 && playerNumber == 2) {
        		game.permit();
        		while(game.turn%2 == 1 && playerNumber == 2) Thread.sleep(500);
        		writer.writeInt(game.indChangement);writer.flush();
        	}
        	else if(game.turn%2 == 0 && playerNumber == 2) {
        		game.block();
        		String changement = reader.readUTF();
        		endGame = traiteMessage(game, changement);
        	}
        	//_________
        	
        	
        }//game ended here
        socket.close();
	}

	private static boolean traiteMessage(TicTacToe game, String changement) {
		String[] message = changement.split("/");
		
		if (message[1].equals("WIN")) {
			int x = Integer.parseInt(message[2]);
			int y = Integer.parseInt(message[3]);
			int z = Integer.parseInt(message[4]);
			game.displayWin(x,y,z);
			return true;
		}else if(message[1].equals("LOST")) {
			int x = Integer.parseInt(message[2]);
			int y = Integer.parseInt(message[3]);
			int z = Integer.parseInt(message[4]);
			game.setModifications(Integer.parseInt(message[0]));
			game.displayLost(x,y,z);
			return true;
		}else if (message[1].equals("DRAW")) {
			game.setModifications(Integer.parseInt(message[0]));
			game.displayDraw();
			return true;
		}else if(message[1].equals("notyet")){
			game.setModifications(Integer.parseInt(message[0]));
			return false;
		}
		else {
			//unrechable code in the algorithm
			return false;
		}
		
	}
	
}
