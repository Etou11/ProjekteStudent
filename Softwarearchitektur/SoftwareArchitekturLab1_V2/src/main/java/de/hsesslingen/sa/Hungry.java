package de.hsesslingen.sa;

public class Hungry extends Decorator
{

	public Hungry(Character character)
	{
		super(character);
	}

	@Override
	public void attack()
	{
		App.sb.append("Grumml.. ");
		character.attack();
	}

	@Override
	public void move()
	{
		App.sb.append("Grrmbl.. ");
		character.move();
	}
}
