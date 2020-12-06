/*
 * Christian Duerr
 * 756424
 * WKB4
 * 
 */

package de.hsesslingen.sa;
import java.util.ArrayList;
import java.util.List;

public final class App {
	
	static String input;
	
    private App() 
    {
    	
    }

    public static void main(String[] args) throws Exception 
    {
    	
    	
    	List<Dekorierer> decoList = new ArrayList<Dekorierer>();
    	for(int i = 0; i < args.length - 1; i++)
    	{
    		decoList.add(new Dekorierer(args[i]));
    	}
    	
    	
    	Player player = new Player(args);
    	
    	//Dekorierer dekorierer = new Dekorierer(args)
    	
    	
    	
    	while(true)
    	{
    	
    		
    		/*
    		input = Console.readLine();
    		
    		if(input.contains("angriff"))
    		{
    			player.attack();
    		}
    		else if (input.contains("bewegen"))
    		{
    			player.move(input);
    		}
    		else if(input.contains("tot"))
    		{
				System.exit(0);
			}
			*/
    	}
    }
         
}

