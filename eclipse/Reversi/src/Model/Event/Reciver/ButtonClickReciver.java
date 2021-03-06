package Model.Event.Reciver;

import Controler.Controler;
import Model.Event.Event;
import Model.Event.PositionableEvent;
import Model.Event.Type;

public class ButtonClickReciver implements IEventReciver
{
	@Override
	public void recive(Event event)
	{
		if(event.getType().equals(Type.View) && event instanceof PositionableEvent)
		{
			PositionableEvent posEvent = (PositionableEvent)event;
			Controler.setClickToModel(posEvent.getPosition());
		}
	}
}
