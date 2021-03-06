package Controler;

import javax.swing.ImageIcon;

import Model.Position;
import Model.Stone;
import Other.IReversiInfo;

public class ModelToView implements IReversiInfo
{
	public static void setInfoToButtons(Position pos, IStoneReceptorModel model, IStoneReceptorView view)
	{
		Stone stone = model.getStoneInPosition(pos.getX(), pos.getY());
		ImageIcon icon = stone.getIcon();
		IconBridge bridge = new IconBridge(pos, icon, view, stone);
		bridge.start();
	}

	public static void setInfoToTitle(Stone stone, IStoneReceptorView view)
	{
		view.stoneChanged(stone);
	}

	public static void setEndToTitle(IStoneReceptorView view, int[] stoneCounts)
	{
		view.finished(stoneCounts);
	}

	public static void onCannotStonePlacing(IStoneReceptorView view)
	{
		view.onCannotStonePlaced();
	}

	public static void setClicked(Position pos, IStoneReceptorView view)
	{
		view.modelPlaceToPosition(pos);
	}

	public static void changedCharacter(IStoneReceptorView view)
	{
		view.onCharacterChanged();
	}

	public static void showStoneDialog(IStoneReceptorView view, Stone stone)
	{
		view.showInitStoneDialog(stone);
	}
}
