package Model.Character.State;

public enum CharacterState
{
	Meet,
	Normal,
	Think,
	Probably_Win,
	Probably_Lose,
	Win,
	Lose;

	private static CharacterState[] _ins = new CharacterState[]
	{
		Meet, Normal, Think, Probably_Win, Probably_Lose, Win, Lose,
	};

	public static CharacterState indexOf(int i)
	{
		return _ins[i];
	}

	public static int size()
	{
		return _ins.length;
	}
}
