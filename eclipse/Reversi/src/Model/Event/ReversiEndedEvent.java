package Model.Event;

public class ReversiEndedEvent extends Event
{
	private int[] _counts;

	public ReversiEndedEvent(int[] counts)
	{
		_counts = counts;
	}

	public int[] getCounts()
	{
		return _counts;
	}
}
