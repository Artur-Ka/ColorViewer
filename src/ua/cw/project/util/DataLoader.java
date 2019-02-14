package ua.cw.project.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;

public class DataLoader
{
	private static final String KEY = "nA3kL7";
	
	public static ImageIcon ICON_IMAGE;
	public static ImageIcon INFO_IMAGE;
	public static ImageIcon HELP_IMAGE;
	public static ImageIcon RESET_IMAGE;
	public static ImageIcon PIPET_IMAGE;
	public static ImageIcon PALETTE_IMAGE;
	
	static
	{
		try
		{
			ICON_IMAGE = loadDecodeImage(DataLoader.class, "/img/icon.img");
			INFO_IMAGE = loadDecodeImage(DataLoader.class, "/img/info.img");
			HELP_IMAGE = loadDecodeImage(DataLoader.class, "/img/help.img");
			RESET_IMAGE = loadDecodeImage(DataLoader.class, "/img/reset.img");
			PIPET_IMAGE = loadDecodeImage(DataLoader.class, "/img/pipet.img");
			PALETTE_IMAGE = loadDecodeImage(DataLoader.class, "/img/palette.img");
			
			ColorNames.loadColors();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static final ImageIcon loadDecodeImage(Class<?> object, String path)
	{
		try
		{
			// Достаем файл из jar`а
			final DataInputStream inputStream = new DataInputStream(object.getResourceAsStream(path));
			byte[] imageInByte = new byte[(int) inputStream.available()];
	        
	        // Записываем байты из файла в массив
	        inputStream.readFully(imageInByte);
	        inputStream.close();
	        
	        return new ImageIcon(decode(imageInByte));
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	private static final byte[] encode(byte[] object)
	{
		byte[] key = KEY.getBytes();
		
		byte[] result = new byte[object.length];
		
		for (int i = 0; i < object.length; i++)
		{
			result[i] = (byte) (object[i] ^ key[i % key.length]);
		}
		
		return result;
	}

	private static final byte[] decode(byte[] secret)
	{
		byte[] key = KEY.getBytes();
		
		byte[] result = new byte[secret.length];
		
		for (int i = 0; i < secret.length; i++)
		{
			result[i] = (byte) (secret[i] ^ key[i % key.length]);
		}
		
		return result;
	}
	
	/**
	 * Кодирование иконок для чтения программы.<BR>
	 */
	public static void main(String[] args)
	{
		File dec = new File("D:/Work/Eclipse workspace/Color Viewer/res/img1/reset.png");
		File enc = new File("D:/Work/Eclipse workspace/Color Viewer/res/img/reset.img");
		
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		
		try
		{
			if (!enc.exists())
				enc.createNewFile();
			
			inputStream = new FileInputStream(dec);
			outputStream = new FileOutputStream(enc);
			
			byte[] origByte = new byte[(int) dec.length()];
			inputStream.read(origByte);
			
			// Записываем закодированный массив бай в новый файл
			outputStream.write(encode(origByte));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}