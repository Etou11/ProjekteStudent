package senser;

public class AircraftSentence
{
	String aircraft;
	
	public AircraftSentence(String aircraft)
	{
		this.aircraft = aircraft;
	}
	
	public void setAircraft(String aircraft)
	{
		this.aircraft = aircraft;
	}
	
	public String getAircraft()
	{
		return aircraft;
	}
	
	@Override
	public String toString()
	{
		return "Aircraft: " + this.aircraft;
	}
	

	//TODO: Create constructor

	//TODO: Create relevant getter methods
	
	//TODO: Overwrite toString() method to print our relevant fields
}
