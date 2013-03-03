package Controler;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundLoader
{
	private SoundLoader()
	{
	}
	private static final SoundLoader _ins = new SoundLoader();
	public static SoundLoader get()
	{
		return _ins;
	}

	public Clip load(String path)
	{
		ClassLoader loader = getClass().getClassLoader();
		URL resUrl = loader.getResource(path);
		Clip clip = null;
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(resUrl);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch(Exception e)
		{
		}
		return clip;
	}
}
