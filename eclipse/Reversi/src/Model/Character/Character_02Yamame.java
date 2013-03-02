package Model.Character;

import javax.swing.ImageIcon;

import Model.Retentioner_Image;
import Model.AI.*;
import Model.Character.State.CharacterState;

public class Character_02Yamame extends CharacterBase implements ICharacter
{
	@Override
	public String getName()
	{
		return "黒谷ヤマメ";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return Retentioner_Image.CharaYamame[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai0a;
	}

	@Override
	public String getWord(CharacterState state)
	{
		return _words[state.ordinal()][nextInt(_words[state.ordinal()].length)];
	}

	private static String[][] _words = new String[][]
	{
		{"<HTML>へー、リバーシかぁ。<BR>私が勝ったら食っていいか？"},
		{"Normal."},
		{"Think."},
		{"Probably Win."},
		{"Probably Lose."},
		{"Win."},
		{"Lose."},
	};
}
