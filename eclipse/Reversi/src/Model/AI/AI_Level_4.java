package Model.AI;

import java.util.PriorityQueue;

import Controler.Controler;
import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Other.IReversiInfo;

public class AI_Level_4 extends AI_Base implements IReversiInfo, AI
{
	public AI_Level_4(Stone turn)
	{
		super(turn);
	}

	public AI_Level_4()
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

	private static int DEFAULT_MAX_DEPTH = 1;
	private static PriorityQueue<Node> _Mqueue = new PriorityQueue<>(1, new NodeMyComparator());
	private static PriorityQueue<Node> _Oqueue = new PriorityQueue<>(1, new NodeOtComparator());

	public Position getNextPosition(ReversiBoard board)
	{
		Controler.AILockEnable();
		Node min = new Node(null, -999, null);
		Node max = new Node(null, +999, null);
		Node now = new Node(null,    0, null);
		Position pos;
		Node node = getNextPosition(board, DEFAULT_MAX_DEPTH, false, min, max, now, getTurn());
		while(node.getParent() != null && node.getParent().getParent() != null)
			node = node.getParent();

		pos = node.getPosition();

		if(pos == null)
		{
			pos = placeRandomly(board, getTurn());
		}

		try
		{
			Thread.sleep(1000+(_rand.nextInt(1001) - 500));
		}
		catch(Exception e)
		{
		}

		Controler.AILockDisable();

		return pos;
	}

	public Node getNextPosition(ReversiBoard board, int depth, boolean isLastReading, Node min, Node max, Node now, Stone turn)
	{
		ReversiBoard board0 = null;

		if(depth == 0 || board.getPlaceablePlayer() == 0 || now == null)
			return now;

		boolean isMyTurn = board.getTurn() == turn;
		int nextDepth = depth-1;
		if(!isLastReading && board.getTurnCount() > LastRead)
		{
			nextDepth = LastReadAmount;
			isLastReading = true;
		}

		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
			{
				Position pos = new Position(i, j);
				board0 = board.clone();
				if(board0.canPlaceOnPosition(pos))
				{
					int res0 = (depth == DEFAULT_MAX_DEPTH ? 0 : now.getValue());
					int[] res1s = board0.placeAtPastAsReverse(pos);
					int res1 = res1s[0] * res1s[1];
					res1 *= res1 * res1;
					res0 += - res1 + (_val_table[i][j] * 8);
					now = new Node(pos, res0, now);
					if(isMyTurn)
					{
						_Mqueue.offer(now);
					}
					else
					{
						_Oqueue.offer(now);
					}
				}
			}
		if(_Mqueue.isEmpty() && _Oqueue.isEmpty())
			return now;
		while(	( isMyTurn && !_Mqueue.isEmpty()) ||
				(!isMyTurn && !_Oqueue.isEmpty()) )
		{
			Node node = board.getTurn() == turn ? _Mqueue.poll() : _Oqueue.poll() ;

			if(isMyTurn)
			{
				min = max(min, getNextPosition(board0, nextDepth, isLastReading, min, max, node, turn));
				if(min.isBiggerThan(max))
				{
					return max;
				}
			}
			else
			{
				max = min(max, getNextPosition(board0, nextDepth, isLastReading, min, max, node, turn));
				if(min.isBiggerThan(max))
				{
					return min;
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
		if(b == null)
		{
			return a;
		}
		if(b.isBiggerThan(a))
		{
			return a;
		}
		return b;
	}

	private Node max(Node a, Node b)
	{
		if(b == null)
		{
			return a;
		}
		if(a.isBiggerThan(b))
		{
			return a;
		}
		return b;
	}
}