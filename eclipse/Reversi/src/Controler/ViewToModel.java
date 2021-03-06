package Controler;

import Model.Position;
import Model.Event.Event;
import Model.Event.EventObserver;
import Model.Event.MoveToNextTurnEvent;

public class ViewToModel
{
	public static void onClicked(Position pos, IStoneReceptorModel model)
	{
		//System.out.println("Try Place On "+pos);
		if(model.canPlaceOnPosition(pos))
		{
			model.place(pos);
			model.reverse(pos);
			model.nextPlayer();
			Controler.setBoardToViewAll();

			Event event = new MoveToNextTurnEvent();
			EventObserver.observe(event);
		}
		else
		{
			//System.out.println("Cannot Place On "+pos);
			Controler.cannotSetedStone();
		}
	}

	public static void LockVictory(IStoneReceptorModel model)
	{
		model.AILockEnable();
	}

	public static void gameEnded()
	{
		Controler.nextCharacter();
		Controler.setBoardToViewAll();
	}
}
