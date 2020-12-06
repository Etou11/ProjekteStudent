package de.hsesslingen.sa;

public class Wounded extends Decorator
{

	public Wounded(Character character)
	{
		super(character);
	}

	@Override
	public void attack()
	{
		App.sb.append("Autsch! ");
		character.attack();
	}

	@Override
	public void move()
	{
		App.sb.append("Aua.. ");
		character.move();
	}
	
}
