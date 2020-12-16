import java.lang.*;
import java.io.*;
import java.util.Scanner;

public class userClient
{
    public static void main(String[] args)
    {
	
	try
	    {
      		Process server = Runtime.getRuntime().exec("java Server");
		InputStream inStream = server.getInputStream();
		OutputStream outStream = server.getOutputStream();
		Scanner fromServer = new Scanner(inStream);
		PrintStream toServer = new PrintStream(outStream);
		
		Scanner userScan = new Scanner(System.in);
		String input;
		String boardState = "";

		String letter;
		char[] spaces = new char[10];

		for(int i = 0; i < 10; i ++)
		    {
			spaces[i] = ' '; //fills spaces with... spaces, for formatting
		    }

		
		System.out.println("What do you want to play? (X/O)");
		input = userScan.next();

		
		toServer.println(input);
		toServer.flush();
		boardState = fromServer.next();
		
		while(!input.equals("quit"))
		    {
			char[] board = boardState.toCharArray();

			letter = String.valueOf(board[0]);
			for(int i = 1; i < 10; i++)
			    {
				if(Character.compare(board[i], '-') != 0)
				    spaces[i] = board[i];
			    }
			System.out.println(spaces[1] + ":" + spaces[2] + ":" + spaces[3]);
			System.out.println("-----");
			System.out.println(spaces[4] + ":" + spaces[5] + ":" + spaces[6]);
			System.out.println("-----");
			System.out.println(spaces[7] + ":" + spaces[8] + ":" + spaces[9]);
			System.out.println();

			
			System.out.print("Enter your move (you are " + letter + "):  ");
			input = userScan.next();
			System.out.println();

			toServer.println(input);
			toServer.flush();
			if(input.equals("quit"))
			   break;
			boardState = fromServer.next();

			if(boardState.equals("Invalid"))
			    {
				System.out.println("Error: Invalid move. Please enter different move.");
				boardState = fromServer.next();
			    }
			if(boardState.equals("End"))
			    {
				System.out.println("The game has finished");
				boardState = fromServer.next();
			    }
		    }
		
		
	    }
	catch(IOException ex)
	    {
		System.out.println("Unable to run Server process");
	    }
	/*catch(InterruptedException ex)
	    {
		System.out.println("Unexpected Termination");
		}*/
    }

}
