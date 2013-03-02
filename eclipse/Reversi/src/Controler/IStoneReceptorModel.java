package Controler;

import Model.Position;
import Model.Stone;

public interface IStoneReceptorModel
{
	public Stone getStoneInPosition(int i, int j);

	public boolean canPlaceOnPosition(Position pos);

	public int[] reverse(Position pos);

	public int nextPlayer();

	public void place(Position pos, Stone stone);

	public void place(Position pos);

	public void AILockEnable();

	public void AILockDisable();
}
