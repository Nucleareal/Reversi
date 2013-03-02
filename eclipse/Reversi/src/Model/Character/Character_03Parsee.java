package Model.Character;

import javax.swing.ImageIcon;

import Model.ImageControler;
import Model.AI.*;
import Model.Character.State.CharacterState;

public class Character_03Parsee extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "水橋パルスィ";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return ImageControler.CharaParsee[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai1;
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
