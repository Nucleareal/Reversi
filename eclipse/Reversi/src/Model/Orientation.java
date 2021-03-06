package Model;

public enum Orientation
{
	XNegYNeu(-1, +0), //X- Y0
	XNegYNeg(-1, -1), //X- Y-
	XNeuYNeg(+0, -1), //X0 Y-
	XPosYNeg(+1, -1), //X+ Y-
	XPosYNeu(+1, +0), //X+ Y0
	XPosYPos(+1, +1), //X+ Y+
	XNeuYPos(+0, +1), //X0 Y+
	XNegYPos(-1, +1), //X- Y+
	Undef   (+0, +0); //X0 Y0

	private static Orientation[] _instance = new Orientation[]
	{ XNegYNeu, XNegYNeg, XNeuYNeg, XPosYNeg, XPosYNeu, XPosYPos, XNeuYPos, XNegYPos, Undef };
	private int _vx;
	private int _vy;

	private Orientation(int vx, int vy)
	{
		_vx = vx;
		_vy = vy;
	}

	public int getVectorX()
	{
		return _vx;
	}

	public int getVectorY()
	{
		return _vy;
	}

	public Orientation reverse()
	{
		switch(this)
		{
			case XNegYNeu: return XPosYNeu;
			case XNegYNeg: return XPosYPos;
			case XNeuYNeg: return XNeuYPos;
			case XPosYNeg: return XNegYPos;
			case XPosYNeu: return XNegYNeu;
			case XPosYPos: return XNegYNeg;
			case XNeuYPos: return XNeuYNeg;
			case XNegYPos: return XPosYNeg;
			default: return Undef;
		}
	}

	public static int getMaxNextPositions()
	{
		return _instance.length-1;
	}

	public static Orientation indexOf(int index)
	{
		return _instance[index];
	}
}
