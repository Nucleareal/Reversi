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
		default: return None;
		}
	}

	public Stone prev()
	{
		switch(this)
		{
		case Black: return White;
		case White: return Black;
		default: return None;
		}
	}

	public Stone toAble()
	{
		switch(this)
		{
		case Black: return BlackAble;
		case White: return WhiteAble;
		default: return None;
		}
	}

	public int toArrayInIndex()
	{
		switch(this)
		{
		case Black: return 0;
		case White: return 1;
		default: return -1;
		}
	}

	public ImageIcon getIcon()
	{
		switch(this)
		{
		case Black: return Retentioner_Image.StoneBlack;
		case White: return Retentioner_Image.StoneWhite;
		case BlackAble: return Retentioner_Image.StoneBlackAble;
		case WhiteAble: return Retentioner_Image.StoneWhiteAble;
		default: return Retentioner_Image.StoneDefault;
		}
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
		default: return "-";
		}
	}
}
