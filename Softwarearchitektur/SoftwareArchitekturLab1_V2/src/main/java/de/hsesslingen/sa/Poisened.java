package de.hsesslingen.sa;

public class Poisened extends Decorator
{

	public Poisened(Character character)
	{
		super(character);
	}

	@Override
	public void attack()
	{
		App.sb.append("Buah.. ");
		character.attack();
	}

	@Override
	public void move()
	{
		App.sb.append("Ugh.. ");
		character.move();
	}
	
}
