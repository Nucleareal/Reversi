package Model.Event.Reciver;

import Controler.Controler;
import Model.Event.Event;
import Model.Event.ReversiEndedEvent;

public class ReversiEndedReciver implements IEventReciver
{
	@Override
	public void recive(Event e)
	{
		if(e instanceof ReversiEndedEvent)
		{
			int[] counts = ((ReversiEndedEvent)e).getCounts();
			Controler.finishedGame(counts);
		}
	}
}
