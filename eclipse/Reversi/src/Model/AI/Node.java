package Model.AI;

import Model.Position;

public class Node
{
	private Position _pos;
	private int _value;
	private Node _parent;

	public Node(Position pos, int value, Node parent)
	{
		_pos = pos;
		_value = value;
		_parent = parent;
	}

	public int getValue()
	{
		return _value;
	}

	public Position getPosition()
	{
		return _pos;
	}

	public Node getParent()
	{
		return _parent;
	}

	public boolean isBiggerThan(Node node)
	{
		if(node == null)
		{
			return true;
		}
		return _value > node.getValue();
	}

	public String toString()
	{
		return "{"+_pos+" value:"+_value+"}";
	}

	public boolean isEBiggerThan(Node node)
	{
		return _value >= node.getValue();
	}

	public boolean isEqualTo(Node node)
	{
		if(node == null)
		{
			return false;
		}
		return _value == node.getValue();
	}
}
