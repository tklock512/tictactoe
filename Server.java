import java.lang.*;
import java.io.*;
import java.util.Scanner;

public class Server
{
    public static void main(String[] args)
    {
	try
	    {
		Process AI = Runtime.getRuntime().exec("java AIClient");
		InputStream inStream = AI.getInputStream();
		OutputStream outStream = AI.getOutputStream();
		Scanner fromAI = new Scanner(inStream);
		PrintStream toAI = new PrintStream(outStream);
		String nextMove;

		String[] moveHistory = new String[9];
		int numMoves = 0;
		
		boolean isX; //boolean set true when user is X, false when user is O

		boolean winState = false;//boolean set to true if the game is in a win state for X or O, or the board is filled

		boolean validMove = false; //boolean if the user inputted move is valid
		
		Scanner userInput = new Scanner(System.in);

		String command = userInput.next();

		String boardState = "----------";
		
		if(command.equals("X"))
		    {
			isX = true;
			boardState = "X---------";

			while(!validMove)
			    {
				System.out.println(boardState);
				command = userInput.next();
				if(command.equals("quit"))
				    validMove = true;
				if(command.matches("^[123];[123]$"))
				    validMove = true;
				if(command.matches("skip"))
				    validMove = false;
				if(!validMove)
				    System.out.println("Invalid");
			    }
			validMove = false;
			boardState = moveTranslator(command, boardState, isX);
			moveHistory[numMoves] = command;
			numMoves++;
			//no need to check for win state since this is the first letter
		    }
	       	else
		    {
			isX = false;
			boardState = "O---------";
		    }

		while(!command.equals("quit") && !winState)
		    {
			
			toAI.println(boardState);
			toAI.flush();

			nextMove = fromAI.next();

			boardState = moveTranslator(nextMove, boardState, !isX); //negate isX since AI has opposite state than user
			moveHistory[numMoves] = nextMove;
			numMoves++;
			
			winState = checkWinState(boardState, numMoves);
			if(winState)
			    break;

			while(!validMove) //checks for regex, user input needs to match correct syntax
			    {
				System.out.println(boardState);
				command = userInput.next();
				if(command.equals("quit")) //user is quitting
				    {
					validMove = true;
				    }
				if(command.matches("^[123];[123]$")) //user is making a move
				    {
					validMove = true;
					for(int i = 0; i < numMoves; i++)
					    {
						if(moveHistory[i].equals(command))
						    validMove = false;
					    }
				    }
				if(command.matches("skip")) //user cannot skip until game is in winState. Not valid
				   {
				       validMove = false;
				   }
				if(!validMove) //entered invalid move
			           {
				       System.out.println("Invalid");
				   }
			    }
			validMove = false;
		       	boardState = moveTranslator(command, boardState, isX);
	       		moveHistory[numMoves] = command;
       			numMoves++;
			
			winState = checkWinState(boardState, numMoves);
		       
		    }
		//user has sent quit command, telling AI to quit
		if(winState)
		    {
			while(!command.equals("quit"))
			    {
				System.out.println("End");
				System.out.println(boardState);

				command = userInput.next();
				if(!command.equals("quit") && !command.equals("skip"))
				    System.out.println("Invalid");
			    }
		    }
		
		toAI.println("quit");
		toAI.flush();
		AI.waitFor();

	    }
	catch(IOException ex)
	    {
		System.out.println("Unable to run AI");
	    }
	catch(InterruptedException ex)
	    {
		System.out.println("Unexpected Termination for server");
	    }

    }

    private static String moveTranslator(String nextMove, String boardState, boolean isX)
    {
	int i;
	char XO;

	if(isX)
	    XO = 'X';
	else
	    XO = 'O';

	switch(nextMove)
	    {
	     case "1;1":
	        i = 1;
	        break;
	     case "1;2":
		i = 2;
		break;
	     case "1;3":
		i = 3;
		break;
	     case "2;1":
		i = 4;
		break;
	     case "2;2":
		i = 5;
		break;
	     case "2;3":
	        i = 6;
		break;
	    case "3;1":
		i = 7;
		break;
	    case "3;2":
		i = 8;
		break;
	    case "3;3":
		i = 9;
		break;
	    default:
		i = 10;
		break;
	    }
	
	char [] boardArray = boardState.toCharArray(); //convert string to char array
	if(i < 10)
	    {
		boardArray[i] = XO; //replace char at move location to X or O
	    }
	return String.valueOf(boardArray); //return changed boardState string
    }

    private static boolean checkWinState(String board, int numMoves)
    {
	if(numMoves >= 9)
	    {
		return true;
	    }
	for(int i = 0; i < 9; i++)
	    {
		String line = "";

		switch(i)
		    {
		    case 0:
			line = String.valueOf(board.charAt(1)) + String.valueOf(board.charAt(2)) + String.valueOf(board.charAt(3));
			break;
		    case 1:
			line = String.valueOf(board.charAt(4)) + String.valueOf(board.charAt(5)) + String.valueOf(board.charAt(6));
			break;
		    case 2:
			line = String.valueOf(board.charAt(7)) + String.valueOf(board.charAt(8)) + String.valueOf(board.charAt(9));
			break;
		    case 3:
			line = String.valueOf(board.charAt(1)) + String.valueOf(board.charAt(4)) + String.valueOf(board.charAt(7));
			break;
		    case 4:
			line = String.valueOf(board.charAt(2)) + String.valueOf(board.charAt(5)) + String.valueOf(board.charAt(8));
			break;
		    case 5:
			line = String.valueOf(board.charAt(3)) + String.valueOf(board.charAt(6)) + String.valueOf(board.charAt(9));
			break;
		    case 6:
			line = String.valueOf(board.charAt(1)) + String.valueOf(board.charAt(5)) + String.valueOf(board.charAt(9));
			break;
		    case 7:
			line = String.valueOf(board.charAt(3)) + String.valueOf(board.charAt(5)) + String.valueOf(board.charAt(7));
			break;
		    }
		if(line.equals("XXX") || line.equals("OOO"))
		    {
			return true;
		    }
	    }
	return false;
    }
}
