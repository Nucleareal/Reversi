package Model.AI;

import java.util.Comparator;

public class NodeMyComparator implements Comparator<Node>
{
	@Override
	public int compare(Node o1, Node o2)
	{
		if(o1.isBiggerThan(o2))
		{
			return 1;
		}
		if(o1.isEqualTo(o2))
		{
			return 0;
		}
		return -1;
	}
}
