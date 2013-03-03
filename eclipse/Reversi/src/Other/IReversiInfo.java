package Other;


public interface IReversiInfo
{
	String Version = "1.0.0";
	String Title = "NukReversi ver "+Version;
	String GAMESET = "Game Set!";
	String CANNOT_PLACE = "そこは置けないです(´・ω・｀)";
	String CANNOT_PLACE_MSG = "置けません(´・ω・｀)";
	String VERIFY = "わかったv('ω'v)";
	String END_BUTTON = "もうオセロやめる(´；ω；｀)";
	String GAME_START_BUTTON = "おっけv('ω'v三v'ω')v";
	String TXT_STT_0 = "貴方は";
	String TXT_STT_1 = "です";
	int WinX = 640;
	int WinY = 480;
	int SplashSX = 300;
	int SplashSY = 200;
	int XSize = 8;
	int YSize = 8;
	int WAIT_MILLS = 1000/60;
	int LastRead = 48;
	int LastReadAmount = 16;
	int MiddleDepth = 36;
	int LV3ScoreUnderLimit = 0;
	int LV4ScoreUnderLimit = -10000;
	int LV7ScoreUnderLimit = 0;
}
