package Model.Util;

public class ObjectPair<T1, T2>
{
	private T1 _o1;
	private T2 _o2;

	public ObjectPair(T1 o1, T2 o2)
	{
		_o1 = o1;
		_o2 = o2;
	}

	public T1 getO1()
	{
		return _o1;
	}

	public T2 getO2()
	{
		return _o2;
	}
}
