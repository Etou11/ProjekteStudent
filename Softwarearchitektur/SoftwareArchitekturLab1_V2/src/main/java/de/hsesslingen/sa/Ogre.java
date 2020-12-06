package de.hsesslingen.sa;

public class Ogre implements Character
{

	@Override
	public void move()
	{
		App.sb.append("Grunz ");
	}

	@Override
	public void attack()
	{
			App.sb.append("Waah!!");
	}
	
}
