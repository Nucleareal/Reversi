package Model.Character;

import javax.swing.ImageIcon;

import Model.ImageControler;
import Model.AI.*;
import Model.Character.State.CharacterState;

public class Character_08Okuu extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "霊烏路空";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return ImageControler.CharaUtsuho[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai6;
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