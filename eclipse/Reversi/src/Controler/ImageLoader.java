package Controler;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImageLoader
{
	private ImageLoader()
	{
	}
	private static final ImageLoader _ins = new ImageLoader();
	public static ImageLoader get()
	{
		return _ins;
	}

	public ImageIcon load(String path)
	{
		ClassLoader loader = getClass().getClassLoader();
		URL resUrl = loader.getResource(path);
		ImageIcon image = new ImageIcon(resUrl);
		return image;
	}
}
