package Model.AI;

import java.util.Comparator;

public class NodeOtComparator implements Comparator<Node>
{
	@Override
	public int compare(Node o1, Node o2)
	{
		if(o1 == null && o2 == null)
		{
			return 0;
		}
		if(o2 == null)
		{
			return 1;
		}
		if(o1 == null)
		{
			return -1;
		}
		if(o2.isBiggerThan(o1))
		{
			return 1;
		}
		if(o2.isEqualTo(o1))
		{
			return 0;
		}
		return -1;
	}
}
