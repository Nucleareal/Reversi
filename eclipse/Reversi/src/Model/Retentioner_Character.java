package Model;

import Controler.Controler;
import Model.Character.ICharacter;
import Model.Character.State.CharacterState;

public class Retentioner_Character
{
	private static volatile ICharacter _chr;
	private static volatile CharacterState _state;

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
}
