package Model.Character;

import javax.swing.ImageIcon;

import Model.ImageControler;
import Model.AI.*;
import Model.Character.State.CharacterState;

public class Character_04Yuugi extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "星熊勇儀";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return ImageControler.CharaYuugi[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai2;
	}

	@Override
	public String getWord(CharacterState state)
	{
		return _words[state.ordinal()];
	}

	private static String[] _words = new String[]
	{
		"Meet.",
		"Think.",
		"Probably Win.",
		"Probably Lose.",
		"Win.",
		"Lose.",
	};
}
