package messer;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

import senser.AircraftSentence;

public class Messer extends Observable implements Observer, Runnable
{
	private static boolean lab2 = false;
	private LinkedBlockingQueue<AircraftSentence> queue;
	
	public Messer()
	{
		this.queue = new LinkedBlockingQueue<AircraftSentence>();
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		queue.add((AircraftSentence) arg1);			
	}

	public void run()
	{ 
		//TODO: Create factory and display object 
		
		AircraftDisplay display = new AircraftDisplay();
		AircraftFactory factory = new AircraftFactory();

		while(true)
		{
			AircraftSentence sentence = null;
			try {
				sentence = queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BasicAircraft basicAircraft = factory.fromAircraftSentence(sentence);

			// Display the message in Lab 2; disable for other labs
			if (lab2) display.display(basicAircraft);

			// Notify all observers
			if (basicAircraft.getIcao() != null)
			{
				setChanged();
				notifyObservers(basicAircraft);
			}
		}
	}
}
