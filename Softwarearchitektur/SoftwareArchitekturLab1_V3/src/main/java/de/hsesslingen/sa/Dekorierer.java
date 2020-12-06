package de.hsesslingen.sa;

import java.util.List;

public class Dekorierer implements ConditionInterface
{
	Player player;
	List<ConditionInterface> args;

	public Dekorierer(Player player, List<ConditionInterface> args)
	{
		this.player = player;
		this.args = args;
	}

	@Override
	public String attackNoise()
	{	
		
		if(args.size() <= 0)
		{
			return null;
		}
		
		if(args[0] == "müder")
		
		
		switch (this.args[0].)
		{
		case "müder":
			return "Zzz...";
		case "hungriger":
			return "Grumml..";
		case "verwundeter":
			return "Autsch!";
		case "vergifteter":
			return "Buah..";
		default:
			return null;
		}
	}

	@Override
	public String movementNoise()
	{
		switch (this.args.movementNoise())
		{
		case "müder":
			return "Gähn..";
		case "hungriger":
			return "Grrmbl..";
		case "verwundeter":
			return "Aua..";
		case "vergifteter":
			return "Ugh..";
		default:
			return null;
		}
	}
	
	/*
	public void attack()
	{
		
		StringBuilder sb = new StringBuilder();
		
		for(Dekorierer ele : decoList)
		{
			sb.append(ele.attackNoise() + " ");
		}
		sb.append(this.attackNoise());
		Console.writeLine(sb.toString());
	}
	
	public void move(String input)
	{
		StringBuilder sb = new StringBuilder();
		String[] split = input.split(" ");
		
		for(Dekorierer ele : decoList)
		{
			sb.append(ele.movementNoise() + " ");
		}
		sb.append(this.movementNoise());
		
		sb.append(split[split.length - 1]);
		Console.writeLine(sb.toString());
	}
	*/
}
