package Model.Event;

import java.util.LinkedList;

import Model.Event.Reciver.IEventReciver;

public class EventObserver
{
	private static LinkedList<IEventReciver> _listeners;
	static
	{
		_listeners = new LinkedList<>();
	}

	public static void register(IEventReciver listener)
	{
		_listeners.add(listener);
	}

	public static void remove(IEventReciver listener)
	{
		_listeners.remove(listener);
	}

	public static void observe(Event e)
	{
		for(IEventReciver recv : _listeners)
			recv.recive(e);
	}
}
