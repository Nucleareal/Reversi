package Model.Event;

public class Event
{
	private Type _type;

	public Event()
	{
		this(Type.Model);
	}

	public Event(Type type)
	{
		_type = type;
	}

	public Type getType()
	{
		return _type;
	}
}
