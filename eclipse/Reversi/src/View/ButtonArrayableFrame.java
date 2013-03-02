package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controler.IStoneReceptorView;
import Controler.ViewToModel;
import Model.Retentioner_Character;
import Model.Position;
import Model.Stone;
import Model.Character.State.CharacterState;
import Model.Listener.ButtonClickedListener;
import Model.Util.RedrawThread;
import Other.IReversiInfo;

public class ButtonArrayableFrame extends JFrame implements IReversiInfo, IStoneReceptorView
{
	private RedrawThread _thread;
	private PositionableButton[][] _buttons;
	private Container _root;
	private static final long serialVersionUID = 1L;
	private StateableLabel _slimg;
	private StateableLabel _sltxt;
	private StateableLabel _slnam;

	public ButtonArrayableFrame(Dimension dim)
	{
		this("", dim);
	}

	public ButtonArrayableFrame(String name, Dimension dim)
	{
		super(name);
		getContentPane().setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initialize(int xSize, int ySize)
	{
		_root = getContentPane();
		_root.removeAll();

		int height = getHeight();
		//int width = getWidth();

		Insets inset = new Insets(0, 0, 0, 0);
		int bySize = height / (ySize+1);
		int bxSize = bySize;//getWidth() / xSize;

		int pan_sx = 150;//(width-bxSize*8)/2;

		JPanel toRoot = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JPanel[] Sides = new JPanel[2];
		for(int i = 0; i < Sides.length; i++)
		{
			Sides[i] = new JPanel(new BorderLayout(0, 0));
			Sides[i].setMinimumSize(new Dimension(pan_sx, 0));
			Sides[i].setPreferredSize(new Dimension(150, 150));
			Sides[0].setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		}

		_buttons = new PositionableButton[xSize][ySize];
		JPanel board = new JPanel(new GridLayout(XSize, YSize, 0, 0));
		for(int i = 0; i < _buttons.length; i++)
		{
			for(int j = 0; j < _buttons[i].length; j++)
			{
				_buttons[i][j] = new PositionableButton();
				_buttons[i][j].addActionListener(new ButtonClickedListener());
				_buttons[i][j].setPosition(new Position(j, i));
				_buttons[i][j].setVisible(true);
				_buttons[i][j].setPreferredSize(new Dimension(bxSize, bySize));
				_buttons[i][j].setMargin(inset);
				board.add(_buttons[i][j]);
			}
		}

		if(_thread == null)
		{
			_thread = new RedrawThread(_buttons);
			_thread.start();
		}

		_slimg = new StateableLabel(StateableLabel.Type.Image);
			_slimg.setPreferredSize(new Dimension(100, 100));
			_slimg.setSize(new Dimension(100, 100));
			_slimg.setMaximumSize(new Dimension(100, 100));
			_slimg.setMinimumSize(new Dimension(100, 100));
		_slnam = new StateableLabel(StateableLabel.Type.Name);
		_sltxt = new StateableLabel();

		Sides[0].add(_slimg, BorderLayout.NORTH);
		Sides[0].add(_slnam, BorderLayout.CENTER);
		Sides[0].add(_sltxt, BorderLayout.SOUTH);

		toRoot.add(Sides[0], BorderLayout.WEST);
		toRoot.add(board   , BorderLayout.CENTER);
		toRoot.add(Sides[1], BorderLayout.EAST);

		_root.add(toRoot);
	}

	public void changeCharacter()
	{
		_slimg.setCharacter(Retentioner_Character.getCharacter());
		_slnam.setCharacter(Retentioner_Character.getCharacter());
		_sltxt.setCharacter(Retentioner_Character.getCharacter());
		_slimg.changeState(Retentioner_Character.getState());
		_slnam.changeState(Retentioner_Character.getState());
		_sltxt.changeState(Retentioner_Character.getState());
	}

	public void showInitStoneDialog(Stone type)
	{
		JDialog diag = new JDialog(this);

		JPanel pan0 = new JPanel(new FlowLayout());

		JLabel lab0 = new JLabel(TXT_STT_0);
		JLabel labi = new JLabel(type.getIcon());
		JLabel lab1 = new JLabel(TXT_STT_1);

		pan0.add(lab0); pan0.add(labi); pan0.add(lab1);

		JPanel pan = new JPanel(new BorderLayout());

		final JDialog dig = diag;
		JButton button = new JButton(GAME_START_BUTTON);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dig.dispose();
				//Controler.startGame();
			}
		});

		pan.add(pan0, BorderLayout.CENTER);
		pan.add(button, BorderLayout.SOUTH);

		diag.add(pan);

		diag.setModal(true);
		diag.setLocationRelativeTo(null);
		diag.setPreferredSize(new Dimension(300, 200));
		diag.setSize(new Dimension(300, 200));
		diag.setVisible(true);
	}

	@Override
	public void setPositionToStone(Position pos, ImageIcon icon, Stone stone)
	{
		_buttons[pos.getX()][pos.getY()].setIcon(icon);
	}

	@Override
	public void stoneChanged(Stone stone)
	{
		setTitle(Title+" - Turn:"+stone);
	}

	@Override
	public void finished(int[] stoneCounts)
	{
		setTitle(Title+" - "+getCountCout(stoneCounts));

		JDialog diag = new JDialog(this, GAMESET, true);
		diag.setSize(new Dimension(300, 200));

		final JDialog dia = diag;
		final JFrame frame = this;

		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(getCountCout(stoneCounts));
		JButton button = new JButton(VERIFY);
		JButton end = new JButton(END_BUTTON);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dia.dispose();
				ViewToModel.gameEnded();
			}
		});
		end.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dia.dispose();
				frame.dispose();
			}
		});
		JPanel buttons = new JPanel(new BorderLayout());

		buttons.add(button, BorderLayout.WEST);
		buttons.add(end, BorderLayout.EAST);

		panel.add(label, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.SOUTH);

		diag.getContentPane().add(panel);
		diag.setLocationRelativeTo(null);
		diag.setVisible(true);
	}

	private String getCountCout(int[] stoneCounts)
	{
		String str = "";
		Stone[] stones = Stone.getPlayableStones();
		for(int i = 0; i < stones.length; i++)
		{
			str += stones[i]+":"+stoneCounts[i]+" ";
		}
		return str;
	}

	@Override
	public void onCannotStonePlaced()
	{
		JDialog diag = new JDialog(this, CANNOT_PLACE, true);
		diag.setSize(new Dimension(300, 200));

		JPanel pan = new JPanel(new BorderLayout());
		JLabel lab = new JLabel(CANNOT_PLACE_MSG);
		lab.setSize(280, 190);
		pan.add(lab, BorderLayout.CENTER);
		JButton but = new JButton(VERIFY);
		pan.add(but, BorderLayout.SOUTH);

		final JDialog dia = diag;
		but.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dia.dispose();
			}
		});

		diag.getContentPane().add(pan);
		diag.setLocationRelativeTo(null);
		diag.setVisible(true);
	}

	@Override
	public void modelPlaceToPosition(Position pos)
	{
		if(pos == null)
		{
			return;
		}
		_buttons[pos.getY()][pos.getX()].doClick();
	}

	@Override
	public void dispose()
	{
		_thread.shutdown();
		super.dispose();
	}

	@Override
	public void onCharacterChanged()
	{
		CharacterState state = Retentioner_Character.getState();
		_slimg.changeState(state);
		_sltxt.changeState(state);
	}
}
