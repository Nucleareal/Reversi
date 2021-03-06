package Model;

public class Position implements Cloneable
{
	private int _x;
	private int _y;
	private Orientation _orient;

	//方向が与えられない座標はニュートラルポジションとして登録
	public Position(int x, int y)
	{
		this(x, y, Orientation.Undef);
	}

	public Position(int x, int y, Orientation orient)
	{
		_x = x;
		_y = y;
		_orient = orient;
	}

	public Position next()
	{
		Position pos = new Position(_x+_orient.getVectorX(), _y+_orient.getVectorY(), _orient);
		if(equals(pos)) return null;
		return pos;
	}

	public Position reverse()
	{
		_orient = _orient.reverse();
		return this;
	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

	public Orientation getOrientation()
	{
		return _orient;
	}

	public boolean equals(Position p)
	{
		return _x == p.getX() && _y == p.getY() && p.getOrientation().equals(_orient);
	}

	@Override
	public String toString()
	{
		return "("+_x+", "+_y+") o:"+(_orient != null ? _orient : Orientation.Undef);
	}

	public boolean isInRange(Position min, Position max)
	{
		return (min.getX() <= _x && min.getY() <= _y) && (_x <= max.getX() && _y <= max.getY());
	}

	public Position transOrientation(Orientation orientation)
	{
		return new Position(_x, _y, orientation);
	}

	public Position clone()
	{
		return new Position(_x, _y, _orient);
	}

	public Position XYReverse()
	{
		return new Position(_y, _x, _orient);
	}
}
