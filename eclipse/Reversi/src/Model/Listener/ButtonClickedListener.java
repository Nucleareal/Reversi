package Model.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controler.Controler;
import Model.Event.Event;
import Model.Event.EventObserver;
import Model.Event.PositionableEvent;
import Model.Event.Type;
import View.PositionableButton;

public class ButtonClickedListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		PositionableButton button = (PositionableButton)ae.getSource();
		Event event;
		event = new PositionableEvent(button.getPosition(), Type.View);
		EventObserver.observe(event);

		Controler.onPlacedStone();
	}
}
