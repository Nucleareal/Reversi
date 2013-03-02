package Model.Character;

import Model.AI.AI;
import Model.AI.AI_Level_0;
import Model.AI.AI_Level_0A;
import Model.AI.AI_Level_1;
import Model.AI.AI_Level_2;
import Model.AI.AI_Level_3;
import Model.AI.AI_Level_4;
import Model.AI.AI_Level_5;
import Model.AI.AI_Level_6;
import Model.AI.AI_Level_7;

public abstract class CharacterBase implements ICharacter
{
	protected static AI _ai0 = new AI_Level_0();
	protected static AI _ai0a = new AI_Level_0A();
	protected static AI _ai1 = new AI_Level_1();
	protected static AI _ai2 = new AI_Level_2();
	protected static AI _ai3 = new AI_Level_3();
	protected static AI _ai4 = new AI_Level_4();
	protected static AI _ai5 = new AI_Level_5();
	protected static AI _ai6 = new AI_Level_6();
	protected static AI _ai7 = new AI_Level_7();
}
