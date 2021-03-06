package Model.AI;

import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Other.IReversiInfo;

public class AI_Level_0 extends AI_Base implements IReversiInfo, AI
{
	private static int MAX_DEPTH = 5;

	public AI_Level_0(Stone stone)
	{
		super(stone);
	}

	public AI_Level_0()
	{
		super();
	}

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
						if(pos.getX() % (XSize-1) == 0 && pos.getY() % (YSize-1) == 0)
							res1 += 8;
						else
						if(pos.getX() % (XSize-1) == 0 || pos.getY() % (YSize-1) == 0)
							res1 += 4;
						if((pos.getX()-1) % (XSize-3) == 0 && (pos.getY()-1) % (YSize-3) == 0)
							res1 -= 8;
						if((pos.getX()-1) % (XSize-3) == 0 && pos.getY() % (YSize-1) == 0)
							res1 -= 6;
						if(pos.getX() % (XSize-1) == 0 && (pos.getY()-1) % (YSize-3) == 0)
							res1 -= 6;
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
		return "AI Level0 © @Nucleareal";
	}
}
