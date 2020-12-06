/*
 * Christian Duerr
 * 756424
 * WKB4
 * 
 */

package de.hsesslingen.sa;

import javax.naming.OperationNotSupportedException;

public final class App {
	
	static String input;
	public static StringBuilder sb = new StringBuilder();
	
    private App() 
    {
    	
    }
    
    private static Character parseCharacter(String character) throws OperationNotSupportedException
    {
    	switch(character)
    	{
    	case "Mensch":
    		return new Human();
    	case "Orc":
    		return new Orc();
    	case "Oger":
    		return new Ogre();
    	case "Drache":
    		return new Dragon();
    	default:
    		throw new OperationNotSupportedException();
    	}
    }
    
    private static Character parseCondition (Character character, String condition) throws OperationNotSupportedException
    {
    	switch(condition)
    	{
    	case "vergifteter":
    		return new Poisened(character);
    	case "verwundeter":
    		return new Wounded(character);
    	case "hungriger":
    		return new Hungry(character);
    	case "müder":
    		return new Tired(character);
    	default:
    		throw new OperationNotSupportedException();
    	}
    }

    public static void main(String[] args) throws Exception 
    {
    	
    	Character character = parseCharacter(args[args.length - 1]);
    	
    	for(int i = args.length -2; i >= 0; i--)
    	{
    		character = parseCondition(character, args[i]);
    	}
    	    	
    	while(true)
    	{
    		sb = new StringBuilder();
    		input = Console.readLine();
    		
    		if(input.contains("angriff"))
    		{
    			character.attack();
    			Console.writeLine(sb.toString());
    		}
    		else if (input.contains("bewegen"))
    		{
    			String[] split = input.split(" ");
    			character.move();
    			Console.writeLine(sb.toString() + split[split.length - 1]);
    		}
    		else if(input.contains("tot"))
    		{
				System.exit(0);
			}
    	}
    }
         
}

