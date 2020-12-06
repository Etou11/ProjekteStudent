package acamo;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import messer.*;

//TODO: create hash map and complete all operations
public class ActiveAircrafts implements Observer
{
	
	String icao;
	BasicAircraft ac;
	private HashMap<String, BasicAircraft> activeAircrafts = new HashMap<String, BasicAircraft>();    	// store the basic aircraft and use its Icao as key 
																										// replace K and V with the correct class names


	public ActiveAircrafts () 
	{

	}

	public synchronized void store(String icao, BasicAircraft ac) 
	{
		activeAircrafts.put(icao, ac);
	}

	public synchronized void clear() 
	{
		activeAircrafts.clear();
	}
	

	public synchronized BasicAircraft retrieve(String icao) 
	{
		return activeAircrafts.get(icao);
	}

	public synchronized ArrayList<BasicAircraft> values () 
	{
		ArrayList<BasicAircraft> basicAircraftList = new ArrayList<BasicAircraft>();
		
		for(String ele : activeAircrafts.keySet())
		{
			basicAircraftList.add(activeAircrafts.get(ele));
		}
		
		return basicAircraftList;
	}

	public String toString () 
	{
		return activeAircrafts.toString();
	}

	@Override
	// TODO: store arg in hashmap using the method above
	public void update(Observable o, Object arg) 
	{
		store(((BasicAircraft)arg).getIcao(), (BasicAircraft)arg);
	}
}