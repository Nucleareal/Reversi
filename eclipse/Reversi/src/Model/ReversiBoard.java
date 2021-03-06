package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import Controler.IStoneReceptorModel;
import Model.Event.EventObserver;
import Model.Event.PlayerChangedEvent;
import Model.Event.ReversiEndedEvent;
import Model.Util.ObjectPair;
import Other.IReversiInfo;

public class ReversiBoard implements IReversiInfo, IStoneReceptorModel, Cloneable
{
	private Stone[][] _board;
	private int _tcount;
	private Stone _turn;
	private static List<ObjectPair<Position, Stone>> _initStones;
	private static Position _min;
	private static Position _max;
	private boolean _isAILocking = false;
	private LinkedList<ObjectPair<Position, Queue<ObjectPair<Position, Stone>>>> _undoList;
	private Queue<ObjectPair<Position, Stone>> _queueForUndoList;
	private Random _rand;
	static
	{
		_initStones = new LinkedList<>();
		_initStones.add(new ObjectPair<Position, Stone>(new Position(3, 3), Stone.Black));
		_initStones.add(new ObjectPair<Position, Stone>(new Position(3, 4), Stone.White));
		_initStones.add(new ObjectPair<Position, Stone>(new Position(4, 3), Stone.White));
		_initStones.add(new ObjectPair<Position, Stone>(new Position(4, 4), Stone.Black));

		//_initStones.add(new ObjectPair<Position, Stone>(new Position(5, 5), Stone.Black));
		//_initStones.add(new ObjectPair<Position, Stone>(new Position(3, 5), Stone.White));
		//_initStones.add(new ObjectPair<Position, Stone>(new Position(5, 4), Stone.White));
		//_initStones.add(new ObjectPair<Position, Stone>(new Position(4, 5), Stone.Satori));
		//_initStones.add(new ObjectPair<Position, Stone>(new Position(5, 3), Stone.Satori));
		//_initStones.add(new ObjectPair<Position, Stone>(new Position(3, 4), Stone.Satori));
	}

	public ReversiBoard()
	{
		_board = new Stone[XSize][YSize];
		_min = new Position(0, 0);
		_max = new Position(XSize-1, YSize-1);
		_undoList = new LinkedList<>();
		_queueForUndoList = new LinkedList<>();
		_rand = new Random();
	}

	public void initialize()
	{
		_turn = Stone.White;
		_tcount = 0;
		_undoList.clear();
		_queueForUndoList.clear();

		for(int i = 0; i < XSize; i++)
		{
			for(int j = 0; j < YSize; j++)
			{
				_board[i][j] = Stone.None;
			}
		}
		for(ObjectPair<Position, Stone> obj : _initStones)
		{
			Position pos = obj.getO1();
			_board[pos.getX()][pos.getY()] = obj.getO2();
		}
	}

	public void startGame()
	{
		nextPlayer();
	}

	@Override
	public Stone getStoneInPosition(int i, int j)
	{
		return _board[i][j];
	}

	@Override
	public boolean canPlaceOnPosition(Position pos)
	{
		boolean result = true;
		result &= availableStone(pos);
		result &= canReverseEach(pos);
		return result;
	}

	private boolean availableStone(Position pos)
	{
		Stone stone = _board[pos.getY()][pos.getX()];
		switch(stone)
		{
		case Black: return false;
		case White: return false;
		case BlackAble: return _turn == Stone.Black;
		case WhiteAble: return _turn == Stone.White;
		default: return true;
		}
	}

	private boolean canReverseEach(Position pos)
	{
		List<Position> nexts = getNextPoints(pos);
		boolean isReverseable = false;
		for(Position pos0 : nexts)
		{
			isReverseable |= canReverse(pos0);
		}
		return isReverseable;
	}

	private boolean canReverse(Position pos)
	{
		boolean isReverseable = false;
		Position pos0 = pos.clone();
		while(pos0.isInRange(_min, _max))
		{
			Stone stone = getStoneInPosition(pos0);
			if(stone.equals(_turn))
			{
				break;
			}
			if(stone.equals(Stone.None) || stone.toAble().equals(Stone.None))
			{
				isReverseable = false;
				break;
			}
			isReverseable = true;
			pos0 = pos0.next();
		}
		isReverseable &= pos0.isInRange(_min, _max);
		return isReverseable;
	}

	private Stone getStoneInPosition(Position pos)
	{
		return getStoneInPosition(pos.getY(), pos.getX());
	}

	@Override
	public int nextPlayer()
	{
		int result = 0;
		_tcount++;

		Stone buf = _turn;
		boolean isContinue = false;
		do
		{
			_turn = _turn.next();
			if((result = getPlaceablePlayer()) != 0 || _isAILocking)
			{
				isContinue = true;
				break;
			}
		}
		while(!buf.equals(_turn));

		if(!_isAILocking)
		{
			PlayerChangedEvent event = new PlayerChangedEvent(_turn);
			EventObserver.observe(event);
			if(!isContinue)
			{
				ReversiEndedEvent event0 = new ReversiEndedEvent(getCounts());
				EventObserver.observe(event0);
			}
		}
		return result;
	}

	private int[] getCounts()
	{
		int[] result = new int[2];
		for(int i = 0; i < XSize; i++)
		{
			for(int j = 0; j < YSize; j++)
			{
				if(_board[i][j].equals(Stone.Black))
				{
					result[0]++;
				}
				else
				if(_board[i][j].equals(Stone.White))
				{
					result[1]++;
				}
			}
		}
		return result;
	}

	public int getPlaceablePlayer()
	{
		int isPlaceableCount = 0;
		for(int i = 0; i < XSize; i++)
		{
			for(int j = 0; j < YSize; j++)
			{
				Position pos = new Position(i, j);
				if(getStoneInPosition(pos).toAble().equals(Stone.None))
				{
					place(pos, Stone.None);
				}
				if(canPlaceOnPosition(pos))
				{
					isPlaceableCount++;
					place(pos, _turn.toAble());
				}
			}
		}
		return isPlaceableCount;
	}

	@Override
	public int[] reverse(Position pos)
	{
		int result[] = new int[]{1, 0};
		List<Position> nexts = getNextPoints(pos);
		for(Position pos0 : nexts)
		{
			if(canReverse(pos0))
			{
				int[] res = reverse_do(pos0);
				result[0] += res[0];
				result[1] += res[1];
			}
		}
		Position pos0 = _queueForUndoList.poll().getO1();
		LinkedList<ObjectPair<Position, Stone>> list = new LinkedList<>();
		while(!_queueForUndoList.isEmpty())
		{
			ObjectPair<Position, Stone> pair = _queueForUndoList.poll();
			list.offer(pair);
		}
		_undoList.add(new ObjectPair<Position, Queue<ObjectPair<Position,Stone>>>(pos0, list));
		return result;
	}

	// return value
	// [0]: count
	// [1]: opened
	private int[] reverse_do(Position pos)
	{
		int[] result = new int[2];
		Position pos0 = pos.clone();
		while(pos0.isInRange(_min, _max) && !getStoneInPosition(pos0).equals(_turn))
		{
			place(pos0, true); result[0]++;
			result[1] += getOpened(pos);
			pos0 = pos0.next();
		}
		return result;
	}

	private int getOpened(Position pos)
	{
		int result = 0;
		List<Position> list = getNextPoints(pos);
		for(Position pos0 : list)
		{
			Stone stone = getStoneInPosition(pos0);
			if(stone.toAble() == Stone.None)
			{
				result++;
			}
		}
		return result;
	}

	@Deprecated
	public void undo()
	{
		if(_undoList.isEmpty())
			return;
		_turn = _turn.prev();
		_tcount--;
		ObjectPair<Position, Queue<ObjectPair<Position,Stone>>> pair = _undoList.removeLast();
		place(pair.getO1(), Stone.None);
		Queue<ObjectPair<Position, Stone>> list = pair.getO2();
		while(!list.isEmpty())
		{
			ObjectPair<Position, Stone> pair0 = list.poll();
			place(pair0.getO1(), pair0.getO2());
		}
	}

	public void place(Position pos, Stone stone, boolean isUndoring)
	{
		if(isUndoring)
		{
			_queueForUndoList.offer(new ObjectPair<Position, Stone>(pos, _board[pos.getY()][pos.getX()]));
		}
		_board[pos.getY()][pos.getX()] = stone;
	}

	public void place(Position pos, boolean isUndoring)
	{
		place(pos, _turn, isUndoring);
	}

	@Override
	public void place(Position pos, Stone stone)
	{
		place(pos, stone, false);
	}

	@Override
	public void place(Position pos)
	{
		place(pos, _turn);
	}

	private List<Position> getNextPoints(Position pos)
	{
		List<Position> nexts = new ArrayList<>(); nexts.clear();
		for(int i = 0; i < Orientation.getMaxNextPositions(); i++)
		{
			Position pos0 = pos.transOrientation(Orientation.indexOf(i)).next();
			if(pos0.isInRange(_min, _max))
			{
				nexts.add(pos0);
			}
		}
		return nexts;
	}

	@Override
	public ReversiBoard clone()
	{
		ReversiBoard board = new ReversiBoard();
		board.setBoard(_board);
		board.setTurn(_turn);
		board.AILockEnable();
		board.setTurnCount(_tcount);
		return board;
	}

	private void setTurnCount(int tcount)
	{
		_tcount = tcount;
	}

	@Override
	public void AILockEnable()
	{
		_isAILocking = true;
	}

	@Override
	public void AILockDisable()
	{
		_isAILocking = false;
	}

	private void setTurn(Stone turn)
	{
		_turn = turn;
	}

	private void setBoard(Stone[][] board)
	{
		_board = new Stone[XSize][YSize];
		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
				_board[i][j] = board[i][j];
	}

	public Stone getTurn()
	{
		return _turn;
	}

	public int placeAtPast(Position pos)
	{
		int result;
		place(pos);
		result = reverse(pos)[0];
		nextPlayer();
		return result;
	}

	// [0]: reversed
	// [1]: opened
	// [2]: placeCounts
	public int[] placeAtPastAsReverse(Position pos)
	{
		place(pos, true);
		int[] res = reverse(pos);
		int result = nextPlayer();
		return new int[]{res[0], res[1], result};
	}

	public void printBoard()
	{
		for(int i = 0; i < XSize; i++)
		{
			for(int j = 0; j < YSize; j++)
			{
				System.out.println(_board[i][j].toAlink());
			}
			System.out.println();
		}
	}

	/** @param pos 場所
	 * @param myStone 石
	 * @return その場所が属する辺が自分の石しかないかどうか
	 */
	public boolean isMyPlace(Position pos, Stone myStone)
	{
		//辺ではない場合
		if(pos.isInRange(new Position(1, 1), new Position(XSize-2, YSize-2)))
		{
			return false;
		}
		boolean result = false;
		if(pos.getX() % (XSize-1) == 0 && pos.getY() % (YSize-1) == 0)
		{
			//隅
			result = true;
		}
		else
		{
			result = true;
			//始点座標を求める
			int SX = pos.getX();
			int SY = pos.getY();
			Orientation orient = Orientation.Undef;
			if(pos.getY() % (YSize - 1) == 0)
				{ SX = 1; orient = Orientation.XPosYNeu; }
			if(pos.getX() % (XSize - 1) == 0)
				{ SY = 1; orient = Orientation.XNeuYPos; }
			Position pos0 = new Position(SX, SY, orient);
			//実際に石を調べていく
			do
			{
				if(myStone != _board[pos0.getX()][pos0.getY()])
					result = false;
				pos0 = pos0.next();
			}
			while(pos0.next().isInRange(_min, _max));
		}
		return result;
	}

	public int getTurnCount()
	{
		return _tcount;
	}

	public List<Position> getAllPlaceablePoints()
	{
		List<Position> result = new LinkedList<>();
		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
			{
				Position pos = new Position(i, j);
				if(canPlaceOnPosition(pos))
					result.add(pos);
			}
		return result;
	}

	public boolean isVerifiedCorner(Position pos)
	{
		int x = (pos.getX() / 4) * (XSize - 1);
		int y = (pos.getY() / 4) * (YSize - 1);
		return _board[x][y].toAble() != Stone.None;
	}

	public Stone randomStone()
	{
		return _rand.nextInt(2) == 0 ? Stone.Black : Stone.White;
	}

	public Stone getWinnedColor()
	{
		int[] counts = getCounts();
		return counts[0] > counts[1] ? Stone.Black : Stone.White;
	}

	public double getStoneRate(Stone turn)
	{
		int[] counts = getCounts();
		for(int i = 0; i < counts.length; i++)
			if(counts[i] == 0)
				counts[i] = 1;
		return turn == Stone.Black ? ((double)counts[0])/counts[1] : ((double)counts[1])/counts[0];
	}
}
