package Controler;

import Model.ITimerReciver;
import Model.Position;
import Model.ReversiBoard;
import Model.AI.AI;
import Model.Util.HaltableThread;
import Model.Util.TimerThread;
import Other.IReversiInfo;

public class AIThinkThread extends HaltableThread implements IReversiInfo, ITimerReciver
{
	private ReversiBoard _board;
	private IStoneReceptorView _view;
	private AI _ai;

	public AIThinkThread(ReversiBoard board, IStoneReceptorView view, AI ai)
	{
		_board = board;
		_ai = ai;
		_view = view;
	}

	public void run()
	{
		TimerThread thread = new TimerThread(this, AI_THINK_MILLS);
		thread.start();

		Position pos = _ai.getNextPosition(_board);
		ModelToView.setClicked(pos, _view);

		Controler.onThinkStopped();
		try
		{
			thread.halt();
		}
		catch(Throwable e)
		{
		}
	}

	@Override
	public void onTimerStopped()
	{
		System.out.println("Think stopped.");
		_ai.enableStopThinking();
	}
}
