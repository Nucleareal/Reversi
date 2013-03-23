package Model.AI;

import java.util.LinkedList;
import java.util.Random;

import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Other.IReversiInfo;

//AI基底クラスです。AIを制作する際にはこれを継承してください
public abstract class AI_Base implements AI, IReversiInfo
{
	private Stone _turn;
	protected Random _rand = new Random();
	protected boolean _isStopThinking = false;
	protected Node __min = new Node(null, Integer.MIN_VALUE, null);
	protected Node __max = new Node(null, Integer.MAX_VALUE, null);
	protected Node __now = new Node(null,    0, null);

	public AI_Base(Stone turn)
	{
		_turn = turn;
	}

	public AI_Base()
	{
		this(Stone.None);
	}

	@Override
	public void setTurn(Stone stone)
	{
		_turn = stone;
	}

	@Override
	public Stone getTurn()
	{
		return _turn;
	}

	protected Position placeRandomly(ReversiBoard board, Stone turn)
	{
		LinkedList<Position> list = new LinkedList<>();
		for(int i = 0; i < XSize; i++)
		{
			for(int j = 0; j < YSize; j++)
			{
				Position pos = new Position(i, j);
				if(board.canPlaceOnPosition(pos))
				{
					list.offer(pos);
				}
			}
		}
		return (list.size() > 0 ? list.get(_rand.nextInt(list.size())) : null);
	}

	public final void enableStopThinking()
	{
		_isStopThinking = true;
	}

	public final void disableStopThinking()
	{
		_isStopThinking = false;
	}
}
