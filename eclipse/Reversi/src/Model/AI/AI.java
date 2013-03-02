package Model.AI;

import Model.Position;
import Model.ReversiBoard;
import Model.Stone;

public interface AI
{
	public Position getNextPosition(ReversiBoard board);

	public Stone getTurn();

	public void setTurn(Stone stone);
}
