package Model.Character;

import javax.swing.ImageIcon;

import Model.Retentioner_Image;
import Model.AI.*;
import Model.Character.State.CharacterState;

public class Character_07Koishi extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "古明地こいし";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return Retentioner_Image.CharaKoishi[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai5;
	}

	@Override
	public String getWord(CharacterState state)
	{
		return _words[state.ordinal()][nextInt(_words[state.ordinal()].length)];
	}

	private static String[][] _words = new String[][]
	{
		{"Meet."},
		{"Normal."},
		{"Think."},
		{"Probably Win."},
		{"Probably Lose."},
		{"Win."},
		{"Lose."},
	};
}
