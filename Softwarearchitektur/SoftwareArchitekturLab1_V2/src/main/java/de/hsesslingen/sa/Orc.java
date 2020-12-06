package de.hsesslingen.sa;

public class Orc implements Character
{

	@Override
	public void move()
	{
		App.sb.append("Drod ");
	}

	@Override
	public void attack()
	{
		App.sb.append("Adog!!");
	}
	
}
