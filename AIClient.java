import java.lang.*;
import java.util.Scanner;

public class AIClient
{
    public static void main(String[] args)
    {
	String command; //string for server command
	Scanner input = new Scanner(System.in); //scanner for server
	char[] board = new char[10]; //character array for board state/player
	boolean sentCommand = false; //boolean turned on if AI has sent a command this turn already. Changed to false at end of turn
	String nextMove; //string containing the next move sent to the server
	
	command = input.next();

	while(!command.equals("quit"))
	    {
		//server checks if command is acceptable. Therefore, AI can assume string is of correct grammar and state
		for(int i = 0; i < 10; i++)
		    {
			if((command.charAt(i) == '-') && !sentCommand) //blank space is found and move has not been made
			    {
				switch(i) //this switch could probably just be solved using mathematical expressions, i.e space 2 is (1-1)*3 + 2 and space 8 is (3-1)*3 + 2
				    {
				    case 1: nextMove = "1;1";
				        break;
				    case 2: nextMove = "1;2";
					break; 
				    case 3: nextMove = "1;3";
					break;
				    case 4: nextMove = "2;1";
					break;
				    case 5: nextMove = "2;2";
					break;
				    case 6: nextMove = "2;3";
					break;
				    case 7: nextMove = "3;1";
					break;
				    case 8: nextMove = "3;2";
					break;
				    case 9: nextMove = "3;3";
					break;
				    default: nextMove = "Broken board state";
					break;
				    }
				System.out.println(nextMove); //send AI move to server
				sentCommand = true;
			    } 
		    } //end for loop
		sentCommand = false; //no longer checking board state, we can send commands again for next run
		command = input.next(); //wait for next server command
		
	    } //end while loop

    }





}
