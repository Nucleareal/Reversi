package Model.AI;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Other.IReversiInfo;

public class AI_Level_7 extends AI_Base implements IReversiInfo
{
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

	private static int DEFAULT_MAX_DEPTH = 4;

	public AI_Level_7(Stone stone)
	{
		super(stone);
	}

	public AI_Level_7()
	{
		super();
	}

	public Position getNextPosition(ReversiBoard board)
	{
		Position pos;
		Node node = getNextPosition(board, __now, __min, __max, DEFAULT_MAX_DEPTH, false);

		while(node.getParent() != null && node.getParent().getParent() != null)
			node = node.getParent();

		pos = node.getPosition();

		if(pos == null)
		{
			pos = placeRandomly(board, getTurn());
		}

		return pos;
	}

	public Node getNextPosition(ReversiBoard board, Node now, Node min, Node max, int depth, boolean isLastDepth)
	{
		if(_isStopThinking || depth < 0 || now.getValue() < LV7ScoreUnderLimit) return now;

		//System.out.println("D:"+depth);

		if(depth == 0)
		{
			if(isLastDepth)
			{
				return now;
			}
			else
			if(board.getTurnCount() > LastRead)
			{
				isLastDepth = true;
				depth = LastReadAmount;
			}
		}
		ReversiBoard board0;

		boolean isMyTurn = getTurn() == board.getTurn();

		List<Position> placeableNodes = board.getAllPlaceablePoints();
		if(placeableNodes.size() == 0 && board.getWinnedColor().equals(getTurn()))
		{
			return new Node(now.getPosition(), Integer.MAX_VALUE, now);
		}
		List<Node> nodes = new LinkedList<>();
		for(Position pos : placeableNodes)
			nodes.add(evaluation(board.clone(), pos, now.getValue(), now, isMyTurn, isLastDepth));
		Collections.sort(nodes, isMyTurn ? new NodeMyComparator() : new NodeOtComparator());

		for(Node node : nodes)
		{
			board0 = board.clone();
			board0.place(node.getPosition());
			if(isMyTurn)
			{
				min = max(min, getNextPosition(board0, node, min, max, depth-1, isLastDepth));
				//board.undo();
				if(min.isBiggerThan(max))
					return max;
			}
			else
			{
				max = min(max, getNextPosition(board0, node, min, max, depth-1, isLastDepth));
				//board.undo();
				if(min.isBiggerThan(max))
					return min;
			}
		}
		return isMyTurn ? min : max;
	}

	private Node evaluation(ReversiBoard board, Position pos, int nowValue, Node parent, boolean isMyTurn, boolean isLastDepth)
	{
		int posValue = _val_table[pos.getX()][pos.getY()];
		int[] results = board.placeAtPastAsReverse(pos); //board.undo();
		nowValue += results[2];
		nowValue -= (results[1] * results[1]); //opened * PlayerCan
		nowValue += isLastDepth ? posValue*posValue : posValue;
		//nowValue += isMyTurn ? 0 : (results[0] * results[0]);
		return new Node(pos, nowValue, parent);
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

	@Override
	public String toString()
	{
		return "AI Level7 © @Nucleareal";
	}
}
