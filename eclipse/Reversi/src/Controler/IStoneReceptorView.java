package Controler;

import javax.swing.ImageIcon;

import Model.Position;
import Model.Stone;

public interface IStoneReceptorView
{
	public void stoneChanged(Stone stone);

	public void finished(int[] stoneCounts);

	public void onCannotStonePlaced();

	public void modelPlaceToPosition(Position pos);

	public void setPositionToStone(Position pos, ImageIcon icon, Stone stone);

	public void onCharacterChanged();

	public void showInitStoneDialog(Stone stone);
}
