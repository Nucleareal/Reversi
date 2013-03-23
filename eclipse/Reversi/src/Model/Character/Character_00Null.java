package Model.Character;

import javax.swing.ImageIcon;

import Model.AI.AI;
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
		return null;
	}

	@Override
	public AI getAI()
	{
		return null;
	}

	@Override
	public String getWord(CharacterState state)
	{
		return null;
	}

}
