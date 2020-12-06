package senser;

import java.util.ArrayList;

public class AircraftSentenceFactory
{
	public ArrayList<AircraftSentence> fromAircraftJson(String jsonAircraftList)
	{	
		ArrayList<AircraftSentence> aircraftList = new ArrayList<AircraftSentence>();
		
		//aircraftList.add(new AircraftSentence("Dummy"));
		
		//TODO: Get distinct aircrafts from the jsonAircraftList string
		
		String splitJsonAircraftList[] = jsonAircraftList.split("(?!\\]),(?=\\[)");
		
		for(String ele : splitJsonAircraftList)
		{
			aircraftList.add(new AircraftSentence(ele));
		}

		return aircraftList;
	}
}
