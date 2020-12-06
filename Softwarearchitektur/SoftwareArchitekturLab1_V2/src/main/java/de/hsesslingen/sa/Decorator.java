package de.hsesslingen.sa;

public abstract class Decorator implements Character
{
	
	public Character character;

	public Decorator(Character character)
	{
		this.character = character;
	}

}
