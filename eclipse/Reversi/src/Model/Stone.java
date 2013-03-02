package Model;

import javax.swing.ImageIcon;

public enum Stone
{
	None,
	Black,
	White,
	BlackAble,
	WhiteAble,
	/*Satori*/;

	private static Stone[] _plays = new Stone[]
	{ Black, White, };

	public Stone next()
	{
		switch(this)
		{
		case Black: return White;
		case White: return Black;
		}
		return None;
	}

	public Stone prev()
	{
		switch(this)
		{
		case Black: return White;
		case White: return Black;
		}
		return None;
	}

	public Stone toAble()
	{
		switch(this)
		{
		case Black: return BlackAble;
		case White: return WhiteAble;
		}
		return None;
	}

	public int toArrayInIndex()
	{
		switch(this)
		{
		case Black: return 0;
		case White: return 1;
		}
		return -1;
	}

	public ImageIcon getIcon()
	{
		switch(this)
		{
		case Black: return ImageControler.StoneBlack;
		case White: return ImageControler.StoneWhite;
		case BlackAble: return ImageControler.StoneBlackAble;
		case WhiteAble: return ImageControler.StoneWhiteAble;
		}
		return ImageControler.StoneDefault;
	}

	public static Stone[] getPlayableStones()
	{
		return _plays;
	}

	public String toAlink()
	{
		switch(this)
		{
		case Black: return "B";
		case BlackAble: return "E";
		case White: return "W";
		case WhiteAble: return "N";
		}
		return "-";
	}
}
