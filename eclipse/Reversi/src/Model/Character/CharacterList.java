package Model.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterList
{
	private static List<ICharacter> _list;
	static
	{
		_list = new ArrayList<>();
		//_list.add(new Character_01Kisume());
		//_list.add(new Character_02Yamame());
		//_list.add(new Character_03Parsee());
		//_list.add(new Character_04Yuugi());
		_list.add(new Character_05Satori());
		_list.add(new Character_06Orin());
		_list.add(new Character_07Koishi());
		_list.add(new Character_08Okuu());
		_list.add(new Character_09Satori());
	}

	public static ICharacter get(int i)
	{
		if(i >= _list.size())
		{
			return null;
		}
		return _list.get(i);
	}
}
