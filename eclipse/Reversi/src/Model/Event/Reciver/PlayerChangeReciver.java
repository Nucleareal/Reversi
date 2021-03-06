package Model.Event.Reciver;

import Controler.Controler;
import Model.Stone;
import Model.Event.Event;
import Model.Event.PlayerChangedEvent;
import Other.IReversiInfo;

public class PlayerChangeReciver implements IEventReciver, IReversiInfo
{
	@Override
	public void recive(Event event)
	{
		if(event instanceof PlayerChangedEvent)
		{
			Stone stone = ((PlayerChangedEvent)event).getStone();
			Controler.playerChanged(stone);
		}
	}
}
