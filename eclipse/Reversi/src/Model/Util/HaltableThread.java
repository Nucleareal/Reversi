package Model.Util;

public class HaltableThread extends Thread
{
	protected boolean _halt;

	public void halt()
	{
		_halt = true;
		interrupt();
	}
}
