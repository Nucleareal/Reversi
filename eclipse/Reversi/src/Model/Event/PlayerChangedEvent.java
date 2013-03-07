package Model.Event;

import Model.Stone;

public class PlayerChangedEvent extends Event
{
	private Stone _changed;

	public PlayerChangedEvent(Stone changed)
	{
		_changed = changed;
	}

	public Stone getStone()
	{
		return _changed;
	}
}
