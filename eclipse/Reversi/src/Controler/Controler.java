package Controler;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controler.Reciver.NextTurnReciver;
import Model.ImageControler;
import Model.Position;
import Model.ReversiBoard;
import Model.Stone;
import Model.AI.AI;
import Model.Character.CharacterList;
import Model.Character.ICharacter;
import Model.Character.State.CharacterState;
import Model.Event.EventObserver;
import Model.Event.Reciver.ButtonClickReciver;
import Model.Event.Reciver.MovedNextTurnReciver;
import Model.Event.Reciver.PlayerChangeReciver;
import Model.Event.Reciver.ReversiEndedReciver;
import Model.Util.CountThread;
import Model.Util.TimerThread;
import Other.IReversiInfo;
import View.ButtonArrayableFrame;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class Controler implements IReversiInfo
{
	private static ButtonArrayableFrame _window;
	private static ReversiBoard _board;
	//private static AI _ai;
	//private static AI _aj;
	//private static int _AIEnabled = 1;
	//private static int _AJEnabled = 1;
	//private static boolean _isAIMode = true;
	private static volatile int _Pcount;
	private static boolean _isEnableGame = false;
	private static int _Ccounter;
	private static volatile boolean _isAIControling;

	public static void launch()
	{
		JFrame splash = new JFrame();
		splash.setUndecorated(true);
		Dimension dim = new Dimension(SplashSX, SplashSY);
		splash.setPreferredSize(dim);
		splash.setSize(dim);

		JLabel label = new JLabel(ImageControler.Splash);
		splash.add(label);

		splash.setVisible(true);
		splash.setLocationRelativeTo(null);

		//ボタンリスナ
		EventObserver.register(new ButtonClickReciver());
		EventObserver.register(new PlayerChangeReciver());
		EventObserver.register(new ReversiEndedReciver());
		EventObserver.register(new MovedNextTurnReciver());

		//_ai = new AI_Level_0A(Stone.Black);
		//_aj = new AI_Level_0(Stone.White);
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

	private static void nextCharacter()
	{
		ICharacter chr = CharacterList.get(_Ccounter++);

		_isEnableGame = false;
		_isAIControling = false;

		if(chr != null)
		{
			CharacterRetentioner.setCharacter(chr, CharacterState.Meet);

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
		catch (UnsupportedLookAndFeelException e)
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
		ModelToView.setEndToTitle(_window, stoneCounts);
		Stone stone = _board.getWinnedColor();
		System.out.println("Win:"+stone+" Ene:"+CharacterRetentioner.getStone());
		if(!stone.equals(CharacterRetentioner.getStone()))
		{
			CharacterRetentioner.changeState(CharacterState.Lose);
			System.out.println("Win");
		}
		else
		{
			CharacterRetentioner.changeState(CharacterState.Win);
			System.out.println("Lose");
			_Ccounter--;
		}
		nextCharacter();
	}

	public static void cannotSetedStone()
	{
		_board.printBoard();
		ModelToView.onCannotStonePlacing(_window);
	}

	public static void nextTurn()
	{
		if(_isEnableGame && !_isAIControling)
		{
			{
				_isAIControling = true;
				Stone stone = _board.getTurn();
				Stone aiturn = CharacterRetentioner.getStone();
				if(stone.equals(aiturn))
				{
					_Pcount = 0;
					_thread = new TimerThread(new NextTurnReciver(), WAIT_MILLS);
					_thread.start();
				}
				else
				{
					_thread = null;
					_isAIControling = false;
				}
			}
		}
	}

	private static Thread _thread;

	public static void AILockEnable()
	{
		CharacterRetentioner.changeState(CharacterState.Think);
		_board.AILockEnable();
	}

	public static void AILockDisable()
	{
		CharacterRetentioner.changeState(CharacterState.Meet);
		_board.AILockDisable();
	}

	public static synchronized void onTimerStopped()
	{
		if(_isEnableGame)
		{
			_Pcount = 0;
			if(_thread != null)
			{
				_thread = null;
			}
			AI ai = CharacterRetentioner.getCharacter().getAI();
			Stone aiturn = CharacterRetentioner.getStone();
			if(_board.getTurn() == aiturn)
			{
				Position pos = ai.getNextPosition(_board);
				ModelToView.setClicked(pos, _window);
			}
			_isAIControling = false;

			nextTurn();
		}
		else
		{
			startGame();
		}
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
		CharacterRetentioner.setStone(stone.next());

		ModelToView.showStoneDialog(_window, stone);
		_board.initialize();
		_board.startGame();
	}
}