package de.hsesslingen.sa;

public class Human implements Character
{

	@Override
	public void move()
	{
		App.sb.append("Gehe nach ");
	}

	@Override
	public void attack()
	{
		App.sb.append("Angriff!!");
	}
	
}
