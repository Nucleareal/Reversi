package Model.AI;

import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Other.IReversiInfo;

public class AI_Level_2 extends AI_Base implements IReversiInfo, AI
{
	public AI_Level_2(Stone turn)
	{
		super(turn);
	}

	public AI_Level_2()
	{
		super();
	}

	private static int _val_table[][] =
	{
		{120, -20,  20,   5,   5,  20, -20, 120},
		{-20, -40,  -5,  -5,  -5,  -5, -40, -20},
		{ 20,  -5,  15,   3,   3,  15,  -5,  20},
		{  5,  -5,   3,   3,   3,   3,  -5,   5},
		{  5,  -5,   3,   3,   3,   3,  -5,   5},
		{ 20,  -5,  15,   3,   3,  15,  -5,  20},
		{-20, -40,  -5,  -5,  -5,  -5, -40, -20},
		{120, -20,  20,   5,   5,  20, -20, 120},
	};

	private static int MAX_DEPTH = 3;

	public Position getNextPosition(ReversiBoard board)
	{
		Position pos;
		Node node = getNextPosition(board, MAX_DEPTH, __min, __max, __now, getTurn());
		while(node.getParent() != null && node.getParent().getParent() != null)
			node = node.getParent();

		pos = node.getPosition();

		if(pos == null)
		{
			pos = placeRandomly(board, getTurn());
		}
		return pos;
	}

	public Node getNextPosition(ReversiBoard board, int depth, Node min, Node max, Node now, Stone turn)
	{
		ReversiBoard board0 = null;

		if(depth == 0 || board.getPlaceablePlayer() == 0) return now;

		boolean isMyTurn = board.getTurn() == turn;

		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
			{
				Position pos = new Position(i, j);
				board0 = board.clone();
				if(board0.canPlaceOnPosition(pos))
				{
					int res0 = (depth == MAX_DEPTH ? 0 : now.getValue());
					int res1 = board0.placeAtPast(pos);
					if(depth == MAX_DEPTH)
					{
						res1 = _val_table[pos.getX()][pos.getY()];
					}
					if(isMyTurn)
					{
						res0 += res1;
						now = new Node(pos, res0, now);
						min = max(min, getNextPosition(board0, depth-1, min, max, now, turn));
						if(min.isBiggerThan(max))
						{
							return max;
						}
					}
					else
					{
						res0 += res1;
						now = new Node(pos, res0, now);
						max = min(max, getNextPosition(board0, depth-1, min, max, now, turn));
						if(min.isBiggerThan(max))
						{
							return min;
						}
					}
				}
			}
		if(isMyTurn)
		{
			return min;
		}
		else
		{
			return max;
		}
	}

	private Node min(Node a, Node b)
	{
		if(b.isBiggerThan(a))
		{
			return a;
		}
		return b;
	}

	private Node max(Node a, Node b)
	{
		if(a.isBiggerThan(b))
		{
			return a;
		}
		return b;
	}

	@Override
	public String toString()
	{
		return "AI Level2 © @Nucleareal";
	}
}
