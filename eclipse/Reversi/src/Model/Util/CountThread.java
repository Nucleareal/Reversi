package Model.Util;

import Controler.Controler;
import Model.ITimerReciver;
import Other.IReversiInfo;

public class CountThread extends Thread implements IReversiInfo
{
	private ITimerReciver _reciver;

	public CountThread(ITimerReciver reciver)
	{
		_reciver = reciver;
	}

	@Override
	public void run()
	{
		while(Controler.getPaintCount() < XSize*YSize)
		{
			try
			{
				Thread.sleep(10);
			}
			catch(Exception e)
			{
			}
		}
		_reciver.onTimerStopped();
		yield();
	}
}
