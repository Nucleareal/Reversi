package Model.Character;

import javax.swing.ImageIcon;

import Model.AI.AI;
import Model.Character.State.CharacterState;

public interface ICharacter
{
	public String getName();

	public ImageIcon getImage(CharacterState state);

	public AI getAI();

	public String getWord(CharacterState state);
}
