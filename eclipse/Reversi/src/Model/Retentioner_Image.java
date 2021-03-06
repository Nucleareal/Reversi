package Model;

import javax.swing.ImageIcon;

import Controler.ImageLoader;
import Model.Character.State.CharacterState;

public class Retentioner_Image
{
	public static ImageIcon[] CharaKisume;
	public static ImageIcon[] CharaYamame;
	public static ImageIcon[] CharaParsee;
	public static ImageIcon[] CharaYuugi;
	public static ImageIcon[] CharaSatori_Closed;
	public static ImageIcon[] CharaOrin;
	public static ImageIcon[] CharaUtsuho;
	public static ImageIcon[] CharaKoishi;
	public static ImageIcon[] CharaSatori_Opened;

	public static ImageIcon StoneBlack;
	public static ImageIcon StoneWhite;
	//public static ImageIcon StoneSatori;
	public static ImageIcon StoneBlackAble;
	public static ImageIcon StoneWhiteAble;
	public static ImageIcon StoneDefault;

	public static ImageIcon Splash;

	private static int size = CharacterState.size();

	static
	{
		Splash = ImageLoader.get().load("img/Splash.png");
		StoneDefault = ImageLoader.get().load("img/Default.png");
		StoneBlack = ImageLoader.get().load("img/Black.png");
		StoneWhite = ImageLoader.get().load("img/White.png");
		//StoneSatori = new ImageIcon("./img/Satori.png");
		StoneBlackAble = ImageLoader.get().load("img/BlackAble.png");
		StoneWhiteAble = ImageLoader.get().load("img/WhiteAble.png");

		charaLoad(1, CharaKisume=new ImageIcon[size]);
		charaLoad(2, CharaYamame=new ImageIcon[size]);
		charaLoad(3, CharaParsee=new ImageIcon[size]);
		charaLoad(4, CharaYuugi=new ImageIcon[size]);
		charaLoad(5, CharaSatori_Closed=new ImageIcon[size]);
		charaLoad(6, CharaOrin=new ImageIcon[size]);
		charaLoad(7, CharaKoishi=new ImageIcon[size]);
		charaLoad(8, CharaUtsuho=new ImageIcon[size]);
		charaLoad(9, CharaSatori_Opened=new ImageIcon[size]);
	}

	private static void charaLoad(int number, ImageIcon[] imageIcons)
	{
		for(int i = 0; i < size; i++)
		{
			String s = String.format("img/chr/%02d/"+CharacterState.indexOf(i)+".png", number);
			imageIcons[i] = ImageLoader.get().load(s);
			//System.out.println("Loaded: "+s);
		}
	}

	public static void Load()
	{
	}
}
