package ua.cw.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ua.cw.project.holders.MouseHolder;
import ua.cw.project.model.CodePanel;
import ua.cw.project.model.ControlPanel;
import ua.cw.project.model.HSVPanel;
import ua.cw.project.model.PalettePanel;
import ua.cw.project.model.RGBPanel;
import ua.cw.project.model.ResultColorPanel;
import ua.cw.project.model.color.JColor;
import ua.cw.project.model.interf.ColorModel;
import ua.cw.project.util.DataLoader;

/**
 * @author Hei
 * @date 19.10.2016
 * @version 2.02.07
 */
@SuppressWarnings("serial")
public class ColorViewer extends JFrame
{
	public static final boolean DEBUG = false;
	
	public static final int WIDTH = 460;
	public static final int HEIGHT = 360;
	
	public static final int RGB_PANEL = 1;
	public static final int HSV_PANEL = 2;
	public static final int PALETTE_PANEL = 3;
	
	private JColor _selectedColor = JColor.WHITE;
	
	private final JMenuBar _menuBar;
	private final AboutFrame _aboutFrame;
	private final PalettePanel _palettePanel;
	private final ControlPanel _controlPanel;
	private final CodePanel _codePanel;
	private final ResultColorPanel _resultColorPanel;
	private final Map<Integer, ColorModel> _colorModels = new HashMap<>();
	
	private boolean _isTextEnabled = false;
	
	private ColorViewer()
	{
		super("ColorViewer ver. 2.02.07");
		
		// Загрузка
		//============================================================================
		MouseHolder.getInstance().load(this); // загрузка глобального обработчика мыши
		//============================================================================
		
		// Настройка главного окна
		//============================================================================
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(WIDTH, HEIGHT);
		setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		if (DataLoader.ICON_IMAGE != null)
			setIconImage(DataLoader.ICON_IMAGE.getImage());
		//============================================================================
		
		_menuBar = new JMenuBar();
		_aboutFrame = new AboutFrame();
		_palettePanel = new PalettePanel(this);
		_controlPanel = new ControlPanel(this);
		_codePanel = new CodePanel(this);
		_resultColorPanel = new ResultColorPanel(this);
		
		_colorModels.put(RGB_PANEL, new RGBPanel(this));
		_colorModels.put(HSV_PANEL, new HSVPanel(this));
//		_colorModels.add(new HSLPanel(this));
//		_colorModels.put("CMYK", new CMYKPanel(this));
		
		// Меню настроек
		//============================================================================
		JCheckBoxMenuItem onTop = new JCheckBoxMenuItem("Поверх окон");
		onTop.setMaximumSize(new Dimension(100, 60));
		onTop.setSelected(true);
		onTop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setAlwaysOnTop(onTop.isSelected());
			}
		});
		
		JMenu helpMenu = new JMenu("Справка");
		JMenuItem about = new JMenuItem("О программе");
		about.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Point buttonLoc = new Point();
				// Узнать расположение кнопки относительно экрана (не компонента)
				SwingUtilities.convertPointFromScreen(buttonLoc, helpMenu);
				int x = Math.abs(buttonLoc.x);
				int y = Math.abs(buttonLoc.y) + helpMenu.getHeight();
				
				_aboutFrame.setAlwaysOnTop(isAlwaysOnTop());
				_aboutFrame.showWindow(x, y);
			}
		});
		if (DataLoader.INFO_IMAGE != null)
			about.setIcon(DataLoader.INFO_IMAGE);
		
		helpMenu.add(about);
		
		_menuBar.add(onTop);
		_menuBar.add(helpMenu);
		//============================================================================
		
		// Панель палитры, кнопока и названия цвета
		//============================================================================
		JPanel palettePanel = new JPanel();
		palettePanel.setLayout(new BoxLayout(palettePanel, BoxLayout.Y_AXIS));
		
		palettePanel.add(_palettePanel);
		palettePanel.add(_controlPanel);
		
		//============================================================================
		
		// Параметры цвета
		//============================================================================
		JPanel colorParamPanel = new JPanel();
		colorParamPanel.setLayout(new BoxLayout(colorParamPanel, BoxLayout.Y_AXIS));
		//============================================================================
		
		// Цветовые модели
		//=======================================================
		JTabbedPane colorModelPane = new JTabbedPane();
		
		colorModelPane.add((RGBPanel) _colorModels.get(RGB_PANEL), "RGB");
		colorModelPane.add((HSVPanel) _colorModels.get(HSV_PANEL), "HSV");
//		colorModelPane.add(null, "HSL");
//		colorModelPane.add((CMYKPanel) _colorModels.get("CMYK"), "CMYK");
//		colorModelPane.add(null, "XYZ");
//		colorModelPane.add(null, "Lab");
		//=======================================================
		
		colorParamPanel.add(colorModelPane);
		colorParamPanel.add(_codePanel);
		//============================================================================
		
		// Добавление компонентов в окно
		//============================================================================
		add(_menuBar, BorderLayout.NORTH);
		add(palettePanel, BorderLayout.WEST);
		add(colorParamPanel, BorderLayout.EAST);
		add(_resultColorPanel, BorderLayout.SOUTH);
	}
	
	public JColor getSelectedColor()
	{
		return _selectedColor;
	}
	
	public boolean isTextEnabled()
	{
		return _isTextEnabled;
	}
	
	public void setTextEnabled(boolean isTextEnabled)
	{
		_isTextEnabled = isTextEnabled;
		
		if (_isTextEnabled)
			setSelectedColor(0, _resultColorPanel.getTextColor());
		else
			setSelectedColor(0, _resultColorPanel.getBackColor());
	}
	
	/**
	 * Обновление всех панелей, кроме той, которая вызывает метод.<BR><BR>
	 * @param panelId
	 * @param color
	 */
	public synchronized void setSelectedColor(int panelId, JColor color)
	{
		_selectedColor = color;
		
		if (panelId != PALETTE_PANEL)
			_palettePanel.updatePanel();
		
		_codePanel.updatePanel();
		_controlPanel.updatePanel();
		_resultColorPanel.updatePanel();
		
		for (int i = 1; i <= _colorModels.size(); i++)
		{
			if (i != panelId)
				_colorModels.get(i).updateModel(_selectedColor);
		}
		
		if (DEBUG)
			System.out.println("RGB:["+color.getRed()+", "+color.getGreen()+ ","+color.getBlue()+"]; "
					+ "HSV:["+color.getHue()+", "+color.getSaturation()+", "+color.getValue()+"]; "
					+ "CMYK:["+color.getCyan()+", "+color.getMagenta()+", "+color.getYellow()+", "+color.getBlack()+"]; "
					+ "Position:["+_palettePanel.getCarretX()+", "+_palettePanel.getCarretY()+"];");
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
//		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				}
				catch (Throwable thrown)
				{
					thrown.printStackTrace();
				}
				
				new ColorViewer().setVisible(true);
			}
		});
	}
}