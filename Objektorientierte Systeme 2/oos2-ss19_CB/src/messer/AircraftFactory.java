package messer;

import java.security.acl.Group;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.VariableElement;

import org.json.JSONObject;

import kotlin.text.Regex;
import senser.AircraftSentence;
import senser.Senser;

public class AircraftFactory
{
	
	boolean _isLab3 = true;
	DateFormat format = new SimpleDateFormat("s");

	public BasicAircraft fromAircraftSentence(AircraftSentence sentence)
	{

		String icao = null;
		String operator = null;
		Date posTime = null;
		double longitude = 0;
		double latitude = 0;
		double speed = 0;
		double trak = 0;
		
		//String inputDummy = "[\"3c6dc8\", \"EWG87U  \", \"Germany\", 1560500800, 1560500800, 9.6884,   48.8508,3558.54, false, 197.81, 50.8, 13.98, null, 3695.7, \"7716\", false, 0]";
		// ["3c6dc8", "EWG87U ", "Germany", 1560500800, 1560500800, 9.6884, 48.8508,
		// icao        operator 			posTime 			    longitude latitude
		// 3558.54, false, 197.81, 50.8, 13.98, null, 3695.7, "7716", false, 0]
		// 				   speed   trak

		// TODO: Extract attributes from sentence using regex only (no String methods)
		
		if(_isLab3)
		{
		
		String[] sentenceValues = sentence.toString().split("[,\\[]");
		
		JSONObject json = new JSONObject();
		
		json.put("icao", sentenceValues[1]);
		json.put("operator", sentenceValues[2]);
		json.put("posTime", sentenceValues[4]);
		json.put("longitude", sentenceValues[6]);
		json.put("latitude", sentenceValues[7]);
		json.put("speed", sentenceValues[10]);
		json.put("trak", sentenceValues[11]);
		
		icao =  json.getString("icao");
		operator = json.getString("operator");
		try
		{
			posTime = format.parse(json.getString("posTime"));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		longitude = json.getDouble("longitude");
		latitude = json.getDouble("latitude");
		speed = json.getDouble("speed");
		trak = json.getDouble("trak");
	
		
		}
		else
		{
			Pattern pattern = Pattern.compile("([a-z0-9]{6})" + "[ ,\"]*" +
											  "([A-Z0-9 ]{8})" + "[A-Za-z,\" ]*" +
											  "(\\d{10})" + "[ ,\"]*" + "[0-9]*" + "[ ,\"]*" +
											  "(\\d*.\\d*)" + "[ ,\"]*" +
											  "(\\d*.\\d*)" + "[ ,\"]*" + "[0-9 .\"]*" + "[ ,\"]*" + "[a-z, \"]*" + "[ ,\"]*" +
											  "(\\d*.\\d*)" + "[ ,\"]*" +
											  "(\\d*.\\d*)");								
			Matcher matcher = pattern.matcher(sentence.getAircraft());

			if(matcher.find())
			{
				icao = matcher.group(1);
				operator = matcher.group(2);
				try
				{
					posTime = format.parse(matcher.group(3));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				longitude = Double.parseDouble(matcher.group(4));
				latitude = Double.parseDouble(matcher.group(5));
				speed = Double.parseDouble(matcher.group(6));
				trak = Double.parseDouble(matcher.group(7));
				
			}
			
		}

		BasicAircraft msg = new BasicAircraft(icao, operator, posTime, new Coordinate(latitude, longitude), speed,
						trak);

		return msg;
	}
}
