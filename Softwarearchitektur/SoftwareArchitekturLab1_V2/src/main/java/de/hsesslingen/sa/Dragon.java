package de.hsesslingen.sa;

public class Dragon implements Character
{

	@Override
	public void move()
	{
		App.sb.append("Schwing ");
	}

	@Override
	public void attack()
	{
		App.sb.append("Fauch!!");
	}
	
}
