package Model.Character;

import javax.swing.ImageIcon;

import Model.ImageControler;
import Model.AI.AI;
import Model.AI.AI_Level_0;
import Model.Character.State.CharacterState;

public class Character_00Null extends CharacterBase
{
	@Override
	public String getName()
	{
		return "AI";
	}

	@Override
	public ImageIcon getImage(CharacterState state)
	{
		return ImageControler.CharaNull[state.ordinal()];
	}

	@Override
	public AI getAI()
	{
		return new AI_Level_0();
	}

	@Override
	public String getWord(CharacterState state)
	{
		return "";
	}

}
