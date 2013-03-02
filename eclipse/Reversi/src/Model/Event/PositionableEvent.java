package Model.Event;

import Model.Position;

public class PositionableEvent extends Event
{
	private Position _pos;

	public PositionableEvent(Position pos)
	{
		this(pos, Type.Model);
	}

	public PositionableEvent(Position pos, Type type)
	{
		super(type);
		_pos = pos;
	}

	public Position getPosition()
	{
		return _pos;
	}

	@Override
	public String toString()
	{
		return _pos.toString();
	}
}
