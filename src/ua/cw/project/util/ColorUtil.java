package ua.cw.project.util;

public class ColorUtil
{
	/**
	 * Преобразует числовой код ARGB в строку.<BR>
	 * Учитывает альфа-канал.<BR>
	 */
	public static String ARGBtoARGB(int argb)
	{
		return String.valueOf(argb);
	}
	
	/**
	 * Преобразует числовой код ARGB в строку.<BR>
	 * Не учитывает альфа-канал.<BR>
	 */
	public static String ARGBtoRGB(int argb)
	{
		return String.valueOf(argb & 0xFFFFFF);
	}
	
	/**
	 * Преобразует числовой код ARGB в строку с HEX-кодом.<BR>
	 * Не учитывает альфа-канал.<BR>
	 */
	public static String ARGBtoHex(int argb)
	{
		return "#" + String.format("%6s%n", Integer.toHexString(argb & 0xFFFFFF).toUpperCase()).replaceAll(" ", "0").trim();
	}
	
	/**
	 * Преобразует числовой код ARGB в строку с HEX-кодом.<BR>
	 * Учитывает альфа-канал.<BR>
	 */
	public static String ARGBtoHexWithAlpha(int argb)
	{
		return "#" + String.format("%8s%n", Integer.toHexString(argb).toUpperCase()).replaceAll(" ", "0").trim();
	}
	
	/**
	 * Преобразует числовой код ARGB в строку с биранрым кодом.<BR>
	 * Не учитывает альфа-канал.<BR>
	 */
	public static String ARGBtoBin(int argb)
	{
		return Integer.toBinaryString(argb & 0xFFFFFF);
	}
	
	/**
	 * Преобразует числовой код ARGB в строку с биранрым кодом.<BR>
	 * Учитывает альфа-канал.<BR>
	 */
	public static String ARGBtoBinWithAlpha(int argb)
	{
		return Integer.toBinaryString(argb);
	}
	
	/**
	 * Возвращает имя цвета.<BR>
	 * <font color="RED">ВАЖНО:</font> В качестве параметра передается RGB, а не ARGB!<BR>
	 */
	public static String getColorName(int rgb)
	{
		return ColorNames.COLOR_NAMES.get(rgb & 0xFFFFFF);
	}
}
