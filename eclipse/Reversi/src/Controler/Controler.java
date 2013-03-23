package Controler;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import Controler.Reciver.NextTurnReciver;
import Model.Position;
import Model.Retentioner_Character;
import Model.Retentioner_Image;
import Model.ReversiBoard;
import Model.Sound;
import Model.Stone;
import Model.AI.*;
import Model.Character.CharacterList;
import Model.Character.ICharacter;
import Model.Character.State.CharacterState;
import Model.Event.EventObserver;
import Model.Event.Reciver.ButtonClickReciver;
import Model.Event.Reciver.MovedNextTurnReciver;
import Model.Event.Reciver.PlayerChangeReciver;
import Model.Event.Reciver.ReversiEndedReciver;
import Model.Util.HaltableThread;
import Model.Util.TimerThread;
import Other.IReversiInfo;
import View.ButtonArrayableFrame;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class Controler implements IReversiInfo
{
	private static ButtonArrayableFrame _window;
	private static ReversiBoard _board;
	private static volatile int _Pcount;
	private static boolean _isEnableGame = false;
	private static int _Ccounter;
	private static HaltableThread _thread;
	private static volatile boolean _isAIControling;

	private static boolean _isAIVSMode = false;
	private static AI _ai1;
	private static AI _ai2;

	public static void launch()
	{
		JFrame splash = new JFrame();
		splash.setUndecorated(true);
		Dimension dim = new Dimension(SplashSX, SplashSY);
		splash.setPreferredSize(dim);
		splash.setSize(dim);
		JLabel label = new JLabel(Retentioner_Image.Splash);
		splash.add(label);
		splash.setLocationRelativeTo(null);
		splash.setVisible(true);


		//ここでAIを指定します
		_ai1 = new AI_Level_8();
		_ai2 = new AI_Level_7();
		_isAIVSMode = true; //AI同士の対戦モード

		Retentioner_Image.Load();
		Sound.Load();

		//ボタンリスナ
		EventObserver.register(new ButtonClickReciver());
		EventObserver.register(new PlayerChangeReciver());
		EventObserver.register(new ReversiEndedReciver());
		EventObserver.register(new MovedNextTurnReciver());

		_Pcount = 0;
		_Ccounter = 0;

		lookAndFeelChange();

		_window = new ButtonArrayableFrame(Title, new Dimension(WinX, WinY));
		_window.pack();
		_window.initialize(XSize, YSize);
		_window.setLocationRelativeTo(null);
		_window.setVisible(true);

		splash.dispose();

		_board = new ReversiBoard();

		initialize();
	}

	private static void initialize()
	{
		nextCharacter();

		setBoardToViewAll();
	}

	public static void nextCharacter()
	{
		ICharacter chr = CharacterList.get(_Ccounter);
		_Ccounter += _isAIVSMode ? 0 : 1;

		_isEnableGame = false;
		_isAIControling = false;

		if(chr != null)
		{
			Retentioner_Character.setCharacter(chr, CharacterState.Meet);

			_window.changeCharacter();

			_board.initialize();

			TimerThread thread = new TimerThread(new NextTurnReciver(), 1500);
			thread.start();
		}
	}

	public static void setBoardToViewAll()
	{
		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
				ModelToView.setInfoToButtons(new Position(i, j), _board, _window);
	}

	private static void lookAndFeelChange()
	{
		try
		{
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void setBoardToView(Position pos)
	{
		ModelToView.setInfoToButtons(pos, _board, _window);
	}

	public static void setClickToModel(Position position)
	{
		if(_isEnableGame)
		{
			ViewToModel.onClicked(position, _board);
		}
	}

	public static void playerChanged(Stone stone)
	{
		ModelToView.setInfoToTitle(stone, _window);
	}

	public static void finishedGame(int[] stoneCounts)
	{
		if(_thread != null)
		{
			try
			{
				_thread.halt();
			}
			catch(Throwable e)
			{
			}
			_thread = null;
		}
		ModelToView.setEndToTitle(_window, stoneCounts);
		Stone stone = _board.getWinnedColor();
		Stone ai_turn = Retentioner_Character.getCharacter().getAI().getTurn();
		if(!stone.equals(ai_turn))
		{
			Retentioner_Character.changeState(CharacterState.Lose);
		}
		else
		{
			Retentioner_Character.changeState(CharacterState.Win);
			_Ccounter--;
		}
		_isEnableGame = false;
	}

	public static void cannotSetedStone()
	{
		ModelToView.onCannotStonePlacing(_window);
	}

	public static void nextTurn()
	{
		if(_isEnableGame && !_isAIControling)
		{
			{
				_isAIControling = true;
				Stone stone = _board.getTurn();
				Stone aiturn = Retentioner_Character.getCharacter().getAI().getTurn();
				if(stone.equals(aiturn) || _isAIVSMode)
				{
					_Pcount = 0;
					_thread = new TimerThread(new NextTurnReciver(), WAIT_MILLS);
					_thread.start();
				}
				else
				{
					if(_thread != null)
					{
						try
						{
							_thread.halt();
						}
						catch(Throwable e)
						{
						}
						_thread = null;
					}
					_isAIControling = false;
				}
			}
		}
	}

	public static void AILockEnable()
	{
		if(_isEnableGame)
		{
			Retentioner_Character.changeState(CharacterState.Think);
			_board.AILockEnable();
		}
	}

	public static void AILockDisable()
	{
		if(_isEnableGame)
		{
			CharacterState state = CharacterState.Normal;

			int turn = _board.getTurnCount();
			if(turn > MiddleDepth)
			{
				double rate = _board.getStoneRate(Retentioner_Character.getCharacter().getAI().getTurn());
				if(rate >= 2D)
				{
					state = CharacterState.Probably_Win;
				}
				else
				if(rate <= 0.5D)
				{
					state = CharacterState.Probably_Lose;
				}
			}
			Retentioner_Character.changeState(state);
			_board.AILockDisable();
		}
	}

	public static synchronized void onTimerStopped()
	{
		if(_isEnableGame)
		{
			if(_isAIControling)
			{
				_Pcount = 0;
				if(_thread != null && _thread instanceof AIThinkThread)
				{
					try
					{
						_thread.halt();
					}
					catch(Throwable e)
					{
					}
					_thread = null;
				}
				AI ai = Retentioner_Character.getCharacter().getAI();
				Stone aiturn = ai.getTurn();
				if(!_isAIVSMode && _board.getTurn() == aiturn)
				{
					ai.disableStopThinking();

					AIThinkThread thread = new AIThinkThread(_board, _window, ai);
					thread.start();
				}
				else
				if(_isAIVSMode)
				{
					AI ai0 = _ai1;
					if(_ai2.getTurn() == _board.getTurn())
					{
						ai0 = _ai2;
					}
					System.out.println("Thinking : "+ai0);

					ai0.disableStopThinking();
					AIThinkThread thread = new AIThinkThread(_board, _window, ai0);
					thread.start();
				}
			}
		}
		else
		{
			startGame();
		}
	}

	public static void onThinkStopped()
	{
		_isAIControling = false;
		nextTurn();
	}

	public static void addPainter()
	{
		_Pcount++;
	}

	public static int getPaintCount()
	{
		return _Pcount;
	}

	public static void reset()
	{
		_board.initialize();
	}

	public static void changedCharacter()
	{
		ModelToView.changedCharacter(_window);
	}

	public static void startGame()
	{
		_isEnableGame = true;

		Stone stone = _board.randomStone();
		Retentioner_Character.getCharacter().getAI().setTurn(stone.next());

		_ai1.setTurn(stone);
		_ai2.setTurn(stone.next());
		System.out.println("AI1 Turn is "+stone);

		ModelToView.showStoneDialog(_window, stone);
		_board.initialize();
		_board.startGame();

		nextTurn();
	}

	public static void onPlacedStone()
	{
		Sound.Place.play();
	}
}
