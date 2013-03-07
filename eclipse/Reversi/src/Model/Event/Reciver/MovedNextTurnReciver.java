package Model.Event.Reciver;

import Controler.Controler;
import Model.Event.Event;
import Model.Event.MoveToNextTurnEvent;

public class MovedNextTurnReciver implements IEventReciver
{
	@Override
	public void recive(Event e)
	{
		if(e instanceof MoveToNextTurnEvent)
		{
			Controler.nextTurn();
		}
	}
}
