package View;

import javax.swing.JLabel;

import Model.Character.ICharacter;
import Model.Character.State.CharacterState;

public class StateableLabel extends JLabel
{
	private static final long serialVersionUID = 1L;
	private ICharacter _chara;
	private CharacterState _state;
	private Type _type;

	public StateableLabel()
	{
		this(Type.String);
	}

	public StateableLabel(Type type)
	{
		super();
		_type = type;
	}

	/** Use setCharacter(ICharacter chara) */
	@Deprecated
	public StateableLabel(ICharacter chara)
	{
		_chara = chara;
		_state = CharacterState.Meet;
	}

	public void setCharacter(ICharacter chara)
	{
		_chara = chara;
	}

	public void changeState(CharacterState state)
	{
		if(_chara != null)
		{
			switch(_type)
			{
			case Image: setIcon(_chara.getImage(state)); break;
			case String: setText(_chara.getWord(state)); break;
			case Name: setText(_chara.getName()); break;
			}
		}
	}

	public void refresh()
	{
		changeState(_state);
	}

	public enum Type
	{
		String,
		Image,
		Name;
	}
}
