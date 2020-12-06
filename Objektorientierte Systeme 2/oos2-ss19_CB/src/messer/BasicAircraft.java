package messer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class BasicAircraft {
	private String icao;
	private String operator;
	private Date posTime;
	private Coordinate coordinate;
	private Double speed;
	private Double trak;

	
	//TODO: Create constructor
	
	public BasicAircraft(String icao, String operator, Date posTime, Coordinate coordinate, Double speed, Double trak)
	{
		this.icao = icao;
		this.operator = operator;
		this.posTime = posTime;
		this.coordinate = coordinate;
		this.speed = speed;
		this.trak = trak;
	}

	//TODO: Create relevant getter methods
	
	public void setIcao(String icao)
	{
		this.icao = icao;
	}
	
	public void setOperator(String operator)
	{
		this.operator = operator;
	}
	
	public void setPosTime(Date posTime)
	{
		this.posTime = posTime;
	}
	
	public void setCoordinate(Coordinate coordinate)
	{
		this.coordinate = coordinate;
	}
	
	public void setSpeed(Double speed)
	{
		this.speed = speed;
	}
	
	public void setTrak(Double trak)
	{
		this.trak = trak;
	}
	
	
	public String getIcao()
	{
		return this.icao;
	}
	
	public String getOperator()
	{
		return this.operator;
	}
	
	public Date getPosTime()
	{
		return this.posTime;
	}
	
	public Coordinate getCoordinate()
	{
		return this.coordinate;
	}
	
	public Double getSpeed()
	{
		return this.speed;
	}
	
	public Double getTrak()
	{
		return this.trak;
	}
	
	

	//TODO: Lab 4-6 return attribute names and values for table
	public static ArrayList<String> getAttributesNames()
	{
		ArrayList<String> attributes = new ArrayList<String>();
		Field[] fields = BasicAircraft.class.getDeclaredFields();
		
		for(Field ele : fields)
		{
			attributes.add(ele.getName());
		}
		
		return attributes;
	}

	public static ArrayList<Object> getAttributesValues(BasicAircraft ac)
	{
		ArrayList<Object> attributes = new ArrayList<Object>();
		ArrayList<String> attributeNames= BasicAircraft.getAttributesNames();
		
		for(String ele : attributeNames)
		{
			try
			{
				Field field = ac.getClass().getDeclaredField(ele);
				attributes.add(field.get(ac));
			}
			catch (Exception e)
			{
				System.out.println("Error in getAttributesValues e: " + e);
			}
			
		}

		return attributes;
	}
	
	//TODO: Overwrite toString() method to print fields
	@Override
	public String toString()
	{
		return "icao: " + this.icao + " operator: " + this.operator + " posTime: " + this.posTime + " coordinate: " + this.coordinate + " speed: " + this.speed + " trak: " + this.trak;
	}

}
