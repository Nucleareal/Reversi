package Model.Event.Reciver;

import Controler.Controler;
import Model.Event.Event;

public class MovedNextTurnReciver implements IEventReciver
{
	@Override
	public void recive(Event e)
	{
		Controler.nextTurn();
	}
}