package ua.cw.project.model.color;

import java.awt.Color;

import ua.cw.project.model.interf.JColorInterface;

@SuppressWarnings("serial")
public class JColor extends Color implements JColorInterface
{
	public static final JColor WHITE = new JColor(-1);
	public static final JColor BLACK = new JColor(-16777216);
	
	
	private final int _hue;
	private final int _saturation;
	private final int _value;
//	private final int _lightness;
	private final int _cyan;
	private final int _magenta;
	private final int _yellow;
	private final int _black;
	
	
	public JColor(int argb)
	{
		super(argb);
		
		int red = getRed();
		int green = getGreen();
		int blue = getBlue();
		
		int[] hsv = RGBtoHSV(red, green, blue);
		int[] cmyk = RGBtoCMYK(red, green, blue);
		
		_hue = hsv[0];
		_saturation = hsv[1];
		_value = hsv[2];
		_cyan = cmyk[0];
		_magenta = cmyk[1];
		_yellow = cmyk[2];
		_black = cmyk[3];
	}
	
	private JColor(int red, int green, int blue)
	{
		super(red, green, blue);
		
		int[] hsv = RGBtoHSV(red, green, blue);
		int[] cmyk = RGBtoCMYK(red, green, blue);
		
		_hue = hsv[0];
		_saturation = hsv[1];
		_value = hsv[2];
		_cyan = cmyk[0];
		_magenta = cmyk[1];
		_yellow = cmyk[2];
		_black = cmyk[3];
	}
	
	private JColor(int red, int green, int blue, int alpha)
	{
		super(red, green, blue, alpha);
		
		int[] hsv = RGBtoHSV(red, green, blue);
		int[] cmyk = RGBtoCMYK(red, green, blue);
		
		_hue = hsv[0];
		_saturation = hsv[1];
		_value = hsv[2];
		_cyan = cmyk[0];
		_magenta = cmyk[1];
		_yellow = cmyk[2];
		_black = cmyk[3];
	}
	
	private JColor(int hue, int saturation, int value, float transparency)
	{
		// Если значение прозрачности меньше 100, необходимо в конструкторе суперкласса указать наличие альфа-канала
		super(JColor.HSVtoARGB(hue, saturation, value, transparency), transparency > 0);
		
		_hue = hue;
		_saturation = saturation;
		_value = value;
		
		int[] cmyk = RGBtoCMYK(getRed(), getGreen(), getBlue());
		_cyan = cmyk[0];
		_magenta = cmyk[1];
		_yellow = cmyk[2];
		_black = cmyk[3];
	}
	
	@Override
	public int getHue()
	{
		return _hue;
	}

	@Override
	public int getSaturation()
	{
		return _saturation;
	}

	@Override
	public int getValue()
	{
		return _value;
	}
	
	@Override
	public int getCyan()
	{
		return _cyan;
	}
	
	@Override
	public int getMagenta()
	{
		return _magenta;
	}
	
	@Override
	public int getYellow()
	{
		return _yellow;
	}
	
	@Override
	public int getBlack()
	{
		return _black;
	}
	
	@Override
	public int getTransparency()
	{
		return 100 - Math.round(getAlpha() / 2.55F);
	}
	
	/**
	 * Допиленный метод из java.awt.Color.<BR><BR>
	 */
	private static final int[] RGBtoHSV(int r, int g, int b)
	{
		float hue, saturation, value;
		int[] hsv = new int[3];
        
		int cmax = (r > g) ? r : g;
		if (b > cmax)
			cmax = b;
		
		int cmin = (r < g) ? r : g;
		if (b < cmin)
			cmin = b;
		
		value = ((float) cmax) / 255.0f;
		
		if (cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		else
			saturation = 0;
		
		if (saturation == 0)
			hue = 0;
		else
		{
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if (r == cmax)
				hue = bluec - greenc;
			else if (g == cmax)
				hue = 2.0f + redc - bluec;
			else
				hue = 4.0f + greenc - redc;
			hue = hue / 6.0f;
			if (hue < 0)
				hue = hue + 1.0f;
		}
        
		hsv[0] = Math.round(hue * 360.0f);
        hsv[1] = Math.round(saturation * 100.0f);
        hsv[2] = Math.round(value * 100.0f);
        
        return hsv;
	}
	
	private static final int[] RGBtoHSL(int r, int g, int b)
	{
		float tr = r / 255.0f;
		float tg = g / 255.0f;
		float tb = b / 255.0f;
		
		int cmax = (r > g) ? r : g;
		if (b > cmax)
			cmax = b;
		
		int cmin = (r < g) ? r : g;
		if (b < cmin)
			cmin = b;
		
		float h, s, l = (cmax + cmin) / 2.0f;
		
		if(cmax == cmin)
			h = s = 0; // achromatic
		else
		{
			float d = cmax - cmin;
			s = l > 0.5f ? d / (2.0f - cmax - cmin) : d / (cmax + cmin);
			
//			switch(cmax)
//			{
//				case r:
//					h = (g - b) / d + (g < b ? 6 : 0);
//					break;
//				case g:
//					h = (b - r) / d + 2;
//					break;
//				case b:
//					h = (r - g) / d + 4;
//					break;
//			}
			
//			h /= 6;
	    }

//	    return [h, s, l];
		
		return null;
	}
	
	private static final int[] RGBtoCMYK(int r, int g, int b)
	{
		float tr = r / 255.0f;
		float tg = g / 255.0f;
		float tb = b / 255.0f;
		
		float cmax = (tr > tg) ? tr : tg;
		if (tb > cmax)
			cmax = tb;
		
		float black = 1f - cmax;
		
		int[] cmyk = new int[4];
		
		cmyk[3] = Math.round(black * 100.0f); // Black
		cmyk[0] = Math.round((1f - tr - black) / (1f - black) * 100.0f); // Cyan
		cmyk[1] = Math.round((1f - tg - black) / (1f - black) * 100.0f); // Magenta
		cmyk[2] = Math.round((1f - tb - black) / (1f - black) * 100.0f); // Yellow
		
		return cmyk;
	}
	
	/**
	 * Допиленный метод из java.awt.Color.<BR>
	 * Теперь учитывает прозрачность.<BR><BR>
	 */
	private static final int HSVtoARGB(float hue, float saturation, float value, float transparency)
	{
		hue /= 360.0f;
		saturation /=  100.0f;
		value /=  100.0f;
		int r = 0, g = 0, b = 0, a = Math.round((100 - transparency) * 2.55f);
		
		if (saturation == 0)
			r = g = b = (int) (value * 255.0f + 0.5f);
		else
		{
			float h = (hue - (float)Math.floor(hue)) * 6.0f;
			float f = h - (float)Math.floor(h);
			float p = value * (1.0f - saturation);
			float q = value * (1.0f - saturation * f);
			float t = value * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h)
			{
				case 0:
					r = (int) (value * 255.0f + 0.5f);
					g = (int) (t * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
					break;
				case 1:
					r = (int) (q * 255.0f + 0.5f);
					g = (int) (value * 255.0f + 0.5f);
					b = (int) (p * 255.0f + 0.5f);
					break;
				case 2:
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (value * 255.0f + 0.5f);
					b = (int) (t * 255.0f + 0.5f);
					break;
				case 3:
					r = (int) (p * 255.0f + 0.5f);
					g = (int) (q * 255.0f + 0.5f);
					b = (int) (value * 255.0f + 0.5f);
					break;
				case 4:
					r = (int) (t * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (value * 255.0f + 0.5f);
					break;
				case 5:
					r = (int) (value * 255.0f + 0.5f);
					g = (int) (p * 255.0f + 0.5f);
					b = (int) (q * 255.0f + 0.5f);
					break;
			}
		}
		
		return (a << 24) | (r << 16) | (g << 8) | (b << 0);
	}
	
	private static final int CMYKtoARGB(int cyan, int magenta, int yellow, int black, int transparency)
	{
		int r = Math.round((100.0f - cyan) * (100.0f - black) * 0.0255f);
		int g = Math.round((100.0f - magenta) * (100.0f - black) * 0.0255f);
		int b = Math.round((100.0f - yellow) * (100.0f - black) * 0.0255f);
		int a = Math.round((100 - transparency) * 2.55f);
		
		return (a << 24) | (r << 16) | (g << 8) | (b << 0);
	}
	
	public static final JColor getRGBColor(int red, int green, int blue)
	{
		return new JColor(red, green, blue);
	}
	
	public static final JColor getRGBColor(int red, int green, int blue, int alpha)
	{
		return new JColor(red, green, blue, alpha);
	}
	
	public static final JColor getHSVColor(int hue, int saturation, int value)
	{
		return new JColor(hue, saturation, value, 0f);
	}
	
	public static final JColor getHSVColor(int hue, int saturation, int value, int transparency)
	{
		return new JColor(hue, saturation, value, (float) transparency);
	}
	
	public static final JColor getHSLColor(int hue, int saturation, int lightness)
	{
		return new JColor(hue, saturation, lightness, (float) 0f);
	}
	
	public static final JColor getHSLColor(int hue, int saturation, int lightness, int transparency)
	{
		return new JColor(hue, saturation, lightness, (float) transparency);
	}
	
	public static final JColor getCMYKColor(int cyan, int magenta, int yellow, int black)
	{
		return new JColor(CMYKtoARGB(cyan, magenta, yellow, black, 0));
	}
	
	public static final JColor getCMYKColor(int cyan, int magenta, int yellow, int black, int transparency)
	{
		return new JColor(CMYKtoARGB(cyan, magenta, yellow, black, transparency));
	}
	
	public static final JColor getLabColor(int cyan, int magenta, int yellow, int black)
	{
		return null;
	}
	
	public static final JColor getLabColor(int cyan, int magenta, int yellow, int black, int transparency)
	{
		return null;
	}
}