package Model.AI;

import java.util.PriorityQueue;

import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Other.IReversiInfo;

public class AI_Level_3 extends AI_Base implements IReversiInfo, AI
{
	public AI_Level_3(Stone turn)
	{
		super(turn);
	}

	public AI_Level_3()
	{
		super();
	}

	private static int MAX_DEPTH = 4;

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

		if(depth == 0 || board.getPlaceablePlayer() == 0 || now.getValue() < LV3ScoreUnderLimit)
			return now;

		boolean isMyTurn = board.getTurn() == turn;

		PriorityQueue<Node> Mqueue = new PriorityQueue<>(1, new NodeMyComparator());
		PriorityQueue<Node> Oqueue = new PriorityQueue<>(1, new NodeOtComparator());

		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
			{
				Position pos = new Position(i, j);
				board0 = board.clone();
				if(board0.canPlaceOnPosition(pos))
				{
					int res0 = now.getValue();
					int[] res1s = board0.placeAtPastAsReverse(pos);
					int res1 = res1s[0] * res1s[2];
					res1 *= res1;
					res0 += res1;
					now = new Node(pos, res0, now);
					if(isMyTurn)
					{
						Mqueue.offer(now);
					}
					else
					{
						Oqueue.offer(now);
					}
				}
			}
		while(	( isMyTurn && !Mqueue.isEmpty()) ||
				(!isMyTurn && !Oqueue.isEmpty()) )
		{
			Node node = isMyTurn ? Mqueue.poll() : Oqueue.poll() ;

			if(isMyTurn)
			{
				min = max(min, getNextPosition(board0, depth-1, min, max, node, turn));
				if(min.isBiggerThan(max))
				{
					return max;
				}
			}
			else
			{
				max = min(max, getNextPosition(board0, depth-1, min, max, node, turn));
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
}
