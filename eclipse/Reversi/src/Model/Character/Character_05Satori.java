package Model.Character;

import javax.swing.ImageIcon;

import Model.Retentioner_Image;
import Model.AI.*;
import Model.Character.State.CharacterState;

public class Character_05Satori extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "古明地さとり";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return Retentioner_Image.CharaSatori_Closed[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai3;
	}

	@Override
	public String getWord(CharacterState state)
	{
		return _words[state.ordinal()][nextInt(_words[state.ordinal()].length)];
	}

	private static String[][] _words = new String[][]
	{
		{"…………"},
		{"<HTML>何しにきたの？自殺？", "<HTML>お燐なら灼熱地獄跡に<BR>居るわ。"},
		{"Think."},
		{"Probably Win."},
		{"Probably Lose."},
		{"Win."},
		{"Lose."},
	};
}
