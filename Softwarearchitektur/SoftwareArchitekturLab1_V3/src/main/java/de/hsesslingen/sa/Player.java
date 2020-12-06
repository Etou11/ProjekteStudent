package de.hsesslingen.sa;

import java.util.Arrays;
import java.util.List;

public class Player implements ConditionInterface
{
	String race;
	String [] conditions;
	
	
	public Player(String[] args)
	{
		this.race = args[args.length - 1];
		this.conditions = Arrays.copyOf(args, args.length - 1);
	}
	
	public String movementNoise()
	{
		switch (this.race)
		{
		case "Mensch":
			return "Gehe nach ";
		case "Orc":
			return "Drod ";
		case "Oger":
			return "Grunz ";
		case "Drache":
			return "Schwing ";
		default:
			return null;
		}
	}

	public String attackNoise()
	{
		switch (this.race)
		{
		case "Mensch":
			return "Angriff!!";
		case "Orc":
			return "Adog!!";
		case "Oger":
			return "Waah!!";
		case "Drache":
			return "Fauch!!";
		default:
			return null;
		}
	}	
}
