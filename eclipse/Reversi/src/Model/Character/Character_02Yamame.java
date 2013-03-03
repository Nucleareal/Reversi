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
		{"<HTML>へー、リバーシかぁ。<BR>私が勝ったら食っていいか？", "<HTML>たまには人間と遊ぶのも<BR>悪くないかもね。", "<HTML>リバーシ？弾幕が打てないのか。<BR>いいよ。",
			"<HTML>お前さん<BR>釣瓶落としを倒してるのか。<BR>仕方ねぇ、私の出番か"},
		{"<HTML>こういう頭使うゲームは<BR>苦手でね…", "おっと、そう来るか", "何処に置けばいいのかねぇ", "<HTML>お前さん<BR>釣瓶落としを倒してるのか。",
			"あそこに置けば…ふむ……", "<HTML>今日の天気はっと…<BR>地下だからわからないか。", "<HTML>ここに置いたら…<BR>こうなって……", "うーん……",
			"どうするかねぇ", "えーっと……"},
		{"うーん……", "おっと、そう来るか", "何処に置けばいいのかねぇ"},
		{"ふんふふーん", "お、これは…", "<HTML>お前さんの野望を<BR>阻止する時が来たようだね！"},
		{"このままじゃ負ける……", "うーっ……", "ん～～…"},
		{"私の勝ちだねぇ、再戦するかい？", "～♪", "～♪♪"},
		{"<HTML>ぐぅ……<BR>…鬼には気をつけるんだよ", "<HTML>負けかぁ…<BR>通っていいよ。", "お前さん強いねぇ……"},
	};
}
