package Model;

import javax.sound.sampled.Clip;

import Controler.SoundLoader;

public enum Sound
{
	Place("snd/Place.wav");


	public static enum Volume
	{ Mute, Low, Middle, High; }

	private Clip _clip;
	public static Volume volume = Volume.Low;

	private Sound(String url)
	{
		_clip = SoundLoader.get().load(url);
	}

	public void play()
	{
		if (volume != Volume.Mute)
		{
			if (_clip.isRunning())
				_clip.stop();
			_clip.setFramePosition(0);
			_clip.start();
		}
	}

	public static void Load()
	{
	}
}
