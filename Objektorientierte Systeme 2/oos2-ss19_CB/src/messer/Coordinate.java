package messer;

public class Coordinate {
	private double latitude;
	private double longitude;

	
	//TODO: Constructor, Getter/Setter and toString()
	
	public Coordinate (double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	
	public double getLatitude()
	{
		return this.latitude;
	}
	
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	@Override
	public String toString()
	{
		return this.longitude + " " + this.latitude;
	}
}