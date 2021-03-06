package View;

import java.awt.Graphics;

import javax.swing.JButton;

import Controler.Controler;
import Model.Position;

public class PositionableButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private Position _pos;

	public PositionableButton setPosition(Position pos)
	{
		_pos = pos;
		return this;
	}

	public Position getPosition()
	{
		return _pos;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Controler.addPainter();
	}

	public void doClick()
	{
		super.doClick();
	}
}
