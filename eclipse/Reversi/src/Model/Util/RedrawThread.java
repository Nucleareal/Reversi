package Model.Util;

import java.awt.Component;

import Other.IReversiInfo;

public class RedrawThread extends Thread implements IReversiInfo
{
	private int _cur;
	private Component[][] _components;

	public RedrawThread(Component[][] components)
	{
		_components = components;
	}

	public void run()
	{
		_cur = 0;

		while(true)
		{
			try
			{
				Thread.sleep(WAIT_MILLS);
			}
			catch (InterruptedException e)
			{
			}
			_components[_cur%XSize][_cur/XSize].repaint();
			_cur = (_cur + 1) % (XSize*YSize);
		}
	}

	public void shutdown()
	{
		yield();
	}

	public void setComponents(Component[][] components)
	{
		_components = components;
	}
}
