package Controler.Reciver;

import Controler.Controler;
import Model.ITimerReciver;

public class NextTurnReciver implements ITimerReciver
{
	@Override
	public void onTimerStopped()
	{
		Controler.onTimerStopped();
	}
}
