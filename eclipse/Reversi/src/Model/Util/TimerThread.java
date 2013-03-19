package Model.Util;

import Model.ITimerReciver;

public class TimerThread extends HaltableThread
{
	private long _mills;
	private ITimerReciver _reciver;

	public TimerThread(ITimerReciver reciver, long mills)
	{
		_reciver = reciver;
		_mills = mills;
	}

	public void run()
	{
		try
		{
			Thread.sleep(_mills);
		}
		catch (InterruptedException e)
		{
		}
		_reciver.onTimerStopped();
	}
}
