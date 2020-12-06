package de.hsesslingen.sa;

public class Tired extends Decorator
{

	public Tired(Character character)
	{
		super(character);
	}

	@Override
	public void attack()
	{
		App.sb.append("Zzz... ");
		character.attack();
	}

	@Override
	public void move()
	{
		App.sb.append("Gähn.. ");
		character.move();
	}
}
