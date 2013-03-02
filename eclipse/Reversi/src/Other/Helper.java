package Other;

public class Helper
{
	public static int max(int ... array)
	{
		int result = array[0];
		for(int i : array)
		{
			if(i < result)
			{
				result = i;
			}
		}
		return result;
	}
}
