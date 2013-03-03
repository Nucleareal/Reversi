package Model.Character;

import javax.swing.ImageIcon;

import Model.Retentioner_Image;
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
		return Retentioner_Image.CharaParsee[state.ordinal()];
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
		{"<HTML>セリフ書くの飽きましたので<BR>次の更新でどうにか<BR>古明地さとりまで書きます"},
		{"Normal."},
		{"Think."},
		{"Probably Win."},
		{"Probably Lose."},
		{"Win."},
		{"Lose."},
	};
}
