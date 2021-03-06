package Model.Character;

import javax.swing.ImageIcon;

import Model.Retentioner_Image;
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
		return Retentioner_Image.CharaKisume[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return _ai0;
	}

	public String getWord(CharacterState state)
	{
		return _words[state.ordinal()][nextInt(_words[state.ordinal()].length)];
	}

	private static String[][] _words = new String[][]
	{
		{"………よろしく", "…よろしく……", "リバーシかぁ……"},
		{"…………", "………", "あの………", "<HTML>……久しぶり、<BR>人間が地底へ来たの", "なんで地下へ……",
			"…人間が地下へ……", "……妖怪に食われるよ", "……戻るなら今だよ", "……", "……悪いことは言わないから…",
			"うーん……", "えっと……", "どうぞ……", "…どうぞ……"},
		{"…うーん……", "………", "…………", "えっと……", "…それなら……"},
		{"…………"},
		{"あー………", "うー………", "…負けた……", "<HTML>……せいぜい<BR>食われないようにね…"},
		{"……あ、勝った", "ありがとうございました……"},
		{"…………", "ありがとうございました……"},
	};
}
