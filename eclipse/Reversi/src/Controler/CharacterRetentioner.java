package Controler;

import Model.Stone;
import Model.Character.ICharacter;
import Model.Character.State.CharacterState;

public class CharacterRetentioner
{
	private static volatile ICharacter _chr;
	private static volatile CharacterState _state;
	private static volatile Stone _stone;

	public static void setCharacter(ICharacter chara, CharacterState state)
	{
		_chr = chara;
		_state = state;
		Controler.changedCharacter();
	}

	public static void changeState(CharacterState state)
	{
		_state = state;
		Controler.changedCharacter();
	}

	public static ICharacter getCharacter()
	{
		return _chr;
	}

	public static CharacterState getState()
	{
		return _state;
	}

	public static void setStone(Stone stone)
	{
		_stone = stone;
	}

	public static Stone getStone()
	{
		return _stone;
	}
}
