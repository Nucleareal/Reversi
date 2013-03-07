package Controler;

import javax.swing.ImageIcon;

import Model.Position;
import Model.Stone;

public class IconBridge extends Thread
{
	private IStoneReceptorView _view;
	private Position _pos;
	private ImageIcon _icon;
	private Stone _stone;

	public IconBridge(Position pos, ImageIcon icon, IStoneReceptorView view, Stone stone)
	{
		_view = view;
		_pos = pos;
		_icon = icon;
		_stone = stone;
	}

	public void run()
	{
		_view.setPositionToStone(_pos, _icon, _stone);
	}
}
