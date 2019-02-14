package ua.cw.project.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ua.cw.project.ColorViewer;
import ua.cw.project.model.color.JColor;

@SuppressWarnings("serial")
public class PalettePanel extends JPanel
{
	public static final int PANEL_WIDTH = 200;
	public static final int PANEL_HEIGHT = 200;
	public static final int PALETTE_WIDTH = 175;
	public static final int PALETTE_HEIGHT = 175;
	public static final int SCROLL_WIDTH = 16;
	public static final int SCROLL_HEIGHT = 175;
	public static final int CARRET_WIDTH = 18;
	public static final int CARRET_HEIGHT = 18;
	
	
	private final Palette _palette = new Palette();
	private final PaletteScroll _scroll = new PaletteScroll();
	
	private ColorViewer _mainFrame;
	private JColor _selectedColor;
	private int _paletteCarretX;
	private int _paletteCarretY;
	private int _scrollCarretY;
	
	public PalettePanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		
		_selectedColor = _mainFrame.getSelectedColor();
		
		_paletteCarretX = getPaletteX(_selectedColor.getSaturation());
		_paletteCarretY = getPaletteY(_selectedColor.getValue());
		_scrollCarretY = getScrollY(_selectedColor.getHue());
		
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new FlowLayout(FlowLayout.CENTER, 2, 0));
		
		add(_palette);
		add(_scroll);
	}
	
	public int getCarretX()
	{
		return _paletteCarretX;
	}
	
	public int getCarretY()
	{
		return _paletteCarretY;
	}
	
	/**
	 * Преобразование значения Hue [0, 360] в значение скроллера палитры [0, SCROLL_HEIGHT].<BR><BR>
	 */
	private static int getScrollY(int hue)
	{
		return Math.round(((SCROLL_HEIGHT - 1F) - (hue / (360F / (SCROLL_HEIGHT - 1F)))));
	}
	
	/**
	 * Преобразование значения Saturation [0, 100] в значение оси X палитры [0, PALETTE_WIDTH].<BR><BR>
	 */
	private static int getPaletteX(int saturation)
	{
		return Math.round(saturation * (PALETTE_WIDTH - 1F) / 100F);
	}
	
	/**
	 * Преобразование значения Value [0, 100] в значение оси Y палитры [0, PALETTE_HEIGHT].<BR><BR>
	 */
	private static int getPaletteY(int value)
	{
		return Math.round((PALETTE_HEIGHT - 1F) - (value * (PALETTE_HEIGHT - 1F) / 100F));
	}
	
	/**
	 * Преобразование значения скроллера палитры [0, SCROLL_HEIGHT] в значение Hue [0, 360].<BR><BR>
	 */
	private static int getHue(int lineY)
	{
		return (int) (((SCROLL_HEIGHT - 1F) - lineY) * 360F / (SCROLL_HEIGHT - 1F));
	}
	
	/**
	 * Преобразование значения оси X палитры [0, PALETTE_WIDTH] в значение Saturation [0, 100].<BR><BR>
	 */
	private static int getSaturation(int pixelX, int currentSaturation)
	{
		float newSaturation = pixelX / (PALETTE_WIDTH - 1F) * 100F;
		
		// Одному цвету соответствует несколько пикселей на каретке, т. к. ее ширина больше диапазона значений Saturation
		// Надо это учитывать, иначе каретка будет двигаться когда не нужно.
		return (int) (Math.abs(newSaturation - currentSaturation) > (PALETTE_WIDTH - 1F) / 100F ? newSaturation : currentSaturation);
	}
	
	/**
	 * Преобразование значения оси Y палитры [0, PALETTE_HEIGHT] в значение Value [0, 100].<BR><BR>
	 */
	private static int getValue(int pixelY, int currentValue)
	{
		float newValue = ((PALETTE_HEIGHT - 1F) - pixelY) / (PALETTE_HEIGHT - 1F) * 100F;
		
		// Одному цвету соответствует несколько пикселей на каретке, т. к. ее высота больше диапазона значений Value
		// Надо это учитывать, иначе каретка будет двигаться когда не нужно. Так же по оси Y значения инвертированы.
		return (int) (Math.abs(newValue - currentValue) > (PALETTE_HEIGHT - 1F) / 100F ? newValue : currentValue);
	}
	
	private void updateSelectedColor()
	{
		_selectedColor = JColor.getHSVColor(getHue(_scrollCarretY), getSaturation(_paletteCarretX, _selectedColor.getSaturation()), getValue(_paletteCarretY, _selectedColor.getValue()));
			
		if (!_selectedColor.equals(_mainFrame.getSelectedColor()))
			_mainFrame.setSelectedColor(ColorViewer.PALETTE_PANEL, _selectedColor);
	}
	
	public synchronized void updatePanel()
	{
		_selectedColor = _mainFrame.getSelectedColor();
		
		_paletteCarretX = getPaletteX(_selectedColor.getSaturation());
		_paletteCarretY = getPaletteY(_selectedColor.getValue());
		_scrollCarretY = getScrollY(_selectedColor.getHue());
		
		_palette.repaint();
		_scroll.repaint();
	}
	
	// Палитра
	private class Palette extends JPanel
	{
		private Palette()
		{
			setPreferredSize(new Dimension(PALETTE_WIDTH, PALETTE_HEIGHT));
			
			addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(MouseEvent arg0){}
				@Override
				public void mouseEntered(MouseEvent arg0){}
				@Override
				public void mouseExited(MouseEvent arg0){}
				@Override
				public void mouseReleased(MouseEvent arg0){}
				
				@Override
				public void mousePressed(MouseEvent arg0)
				{
					if (arg0.getButton() != MouseEvent.BUTTON1)
						return;
					
					// Не перемещаем каретку по клитку на саму каретку
					if (Math.abs(arg0.getX() - _paletteCarretX) < CARRET_WIDTH / 2 && Math.abs(arg0.getY() - _paletteCarretY) < CARRET_HEIGHT / 2)
						return;
					
					_paletteCarretX = arg0.getX();
					_paletteCarretY = arg0.getY();
					
					// Обновление цвета при изменении положения каретки
					repaint();
					updateSelectedColor();
				}
			});
			addMouseMotionListener(new MouseMotionListener()
			{
				@Override
				public void mouseMoved(MouseEvent arg0){}
				
				@Override
				public void mouseDragged(MouseEvent arg0)
				{
					if (arg0.getModifiers() != InputEvent.BUTTON1_MASK)
						return;
					
					// Не выводим каретку за пределы палитры
					if (arg0.getX() < 0)
						_paletteCarretX = 0;
					else if (arg0.getX() >= getWidth())
						_paletteCarretX = getWidth() - 1;
					else
						_paletteCarretX = arg0.getX();
					
					if (arg0.getY() < 0)
						_paletteCarretY = 0;
					else if (arg0.getY() >= getHeight())
						_paletteCarretY = getHeight() - 1;
					else
						_paletteCarretY = arg0.getY();
					
					// Обновление цвета при изменении положения каретки
					repaint();
					updateSelectedColor();
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			// Заливаем палитру
			for (int pixelY = 0; pixelY < getHeight(); pixelY++)
			{
				for (int pixelX = 0; pixelX < getWidth(); pixelX++)
				{
					g.setColor(JColor.getHSVColor(getHue(_scrollCarretY), getSaturation(pixelX, 0), getValue(pixelY, 0)));
					g.fillRect(pixelX, pixelY, 1, 1);
				}
			}
		}
		
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			
			// Инвертируем цвет каретки относительно выбранного цвета
			g.setColor(new Color(255 - _selectedColor.getRed(), 255 - _selectedColor.getGreen(), 255 - _selectedColor.getBlue()));
			
			// Рисуем каретку на палитре
			g.drawLine(_paletteCarretX - CARRET_WIDTH / 2, _paletteCarretY, _paletteCarretX + CARRET_WIDTH / 2, _paletteCarretY);
			g.drawLine(_paletteCarretX, _paletteCarretY - CARRET_HEIGHT / 2, _paletteCarretX, _paletteCarretY + CARRET_HEIGHT / 2);
		}
	}
	
	// Скроллер палитры
	private class PaletteScroll extends JPanel
	{
		private PaletteScroll()
		{
			setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
			
			addMouseListener(new MouseListener()
			{
				@Override
				public void mouseClicked(MouseEvent arg0){}
				@Override
				public void mouseEntered(MouseEvent arg0){}
				@Override
				public void mouseExited(MouseEvent arg0){}
				@Override
				public void mouseReleased(MouseEvent arg0){}
				
				@Override
				public void mousePressed(MouseEvent arg0)
				{
					if (arg0.getButton() != MouseEvent.BUTTON1)
						return;
					
					_scrollCarretY = arg0.getY();
					
					// Обновление цвета при изменении положения каретки
					repaint();
					_palette.repaint();
					updateSelectedColor();
				}
			});
			addMouseMotionListener(new MouseMotionListener()
			{
				@Override
				public void mouseMoved(MouseEvent arg0){}
				
				@Override
				public void mouseDragged(MouseEvent arg0)
				{
					if (arg0.getModifiers() != InputEvent.BUTTON1_MASK)
						return;
					
					// Не выводим каретку за пределы скроллера
					if (arg0.getY() < 0)
						_scrollCarretY = 0;
					else if (arg0.getY() >= getHeight())
						_scrollCarretY = getHeight() - 1;
					else
						_scrollCarretY = arg0.getY();
					
					// Обновление цвета при изменении положения каретки
					repaint();
					_palette.repaint();
					updateSelectedColor();
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			for (int lineY = 0; lineY < getHeight(); lineY++)
			{
				// Заливка скроллера палитры
				g.setColor(JColor.getHSVColor(getHue(lineY), 100, 100));
				g.fillRect(0, lineY, getWidth(), 1);
			}
		}
		
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			
			// Рисуем каретку на скроллере
			g.setColor(Color.WHITE);
			g.drawLine(0, _scrollCarretY, getWidth(), _scrollCarretY);
		}
	}
}