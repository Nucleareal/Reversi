package Model.Character;

import javax.swing.ImageIcon;

import Model.ImageControler;
import Model.AI.AI;
import Model.Character.State.CharacterState;

public class Character_01Kisume extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "キスメ";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return ImageControler.CharaKisume[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai0;
	}

	public String getWord(CharacterState state)
	{
		return _words[state.ordinal()];
	}

	private static String[] _words = new String[]
	{
		"………よろしく……",
		"…うーん……",
		"Probably Win.",
		"Probably Lose.",
		"……あ、勝った",
		"…………",
	};
}
