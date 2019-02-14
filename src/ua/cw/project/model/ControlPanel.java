package ua.cw.project.model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ua.cw.project.ColorViewer;
import ua.cw.project.holders.MouseHolder;
import ua.cw.project.model.color.JColor;
import ua.cw.project.util.ColorUtil;
import ua.cw.project.util.DataLoader;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel
{
	public static final int FIELD_WIDTH = 160;
	public static final int FIELD_HEIGHT = 20;
	public static final int BUTTON_WIDTH = 30;
	public static final int BUTTON_HEIGHT = 30;
	public static final int RADIO_WIDTH = 60;
	public static final int RADIO_HEIGHT = 15;
	public static final int NAME_MAX_SIZE = 30;
	
	public static final boolean AUTOSCROLL_NAME = false;
	
	
	private final JButton _pipetButton = new JButton();
	private final JButton _paletteButton = new JButton();
	private final JButton _resetButton = new JButton();
	private final JRadioButton _backButton = new JRadioButton("Фон");
	private final JRadioButton _textButton = new JRadioButton("Текст");
	private final JTextField _nameField = new JTextField();
	private AutoscrollName _autoscrollThread;
	
	private final ColorViewer _mainFrame;
	
	public ControlPanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		
		if (AUTOSCROLL_NAME)
			_autoscrollThread = new AutoscrollName();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEtchedBorder());
		updatePanel();
		
		_nameField.setEditable(false);
		_nameField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
		_nameField.setBorder(BorderFactory.createEtchedBorder());
		
		// Панель кнопок
		//============================================================================
		JPanel buttonPanel = new JPanel();
		Dimension buttonDimension = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		_pipetButton.setPreferredSize(buttonDimension);
		if (DataLoader.PIPET_IMAGE != null)
			_pipetButton.setIcon(DataLoader.PIPET_IMAGE);
		_pipetButton.setToolTipText("<html><body><center>Пипетка<br>Берёт образец цвета из указанной точки на экране.<br>(отмена - правая кнопка мыши)</center></body></html>");
		_pipetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MouseHolder.getInstance().startMouseListening();
			}
		});
		
		_paletteButton.setPreferredSize(buttonDimension);
		if (DataLoader.PIPET_IMAGE != null)
			_paletteButton.setIcon(DataLoader.PALETTE_IMAGE);
		_paletteButton.setToolTipText("<html><body><center>Палитра<br>Более детальные настройки цвета.</center></body></html>");
		_paletteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new Palette(_mainFrame);
			}
		});
		
		_resetButton.setPreferredSize(buttonDimension);
		if (DataLoader.RESET_IMAGE != null)
			_resetButton.setIcon(DataLoader.RESET_IMAGE);
		_resetButton.setToolTipText("<html><body><center>Сброс<br>Устанавливает все параметры цвета по-умолчанию.</center></body></html>");
		_resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (_textButton.isSelected())
				{
					if (!_mainFrame.getSelectedColor().equals(JColor.BLACK))
						_mainFrame.setSelectedColor(0, JColor.BLACK);
				}
				else
				{
					if (!_mainFrame.getSelectedColor().equals(JColor.WHITE))
						_mainFrame.setSelectedColor(0, JColor.WHITE);
				}
			}
		});
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		Dimension radioDimension = new Dimension(RADIO_WIDTH, RADIO_HEIGHT);
		
		_backButton.setPreferredSize(radioDimension);
		_backButton.setSelected(true);
		_backButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				mainFrame.setTextEnabled(false);
			}
		});
		
		_textButton.setPreferredSize(radioDimension);
		_textButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				mainFrame.setTextEnabled(true);
			}
		});
		
		buttonGroup.add(_backButton);
		buttonGroup.add(_textButton);
		
		radioPanel.add(_backButton);
		radioPanel.add(_textButton);
		
		buttonPanel.add(_pipetButton);
		buttonPanel.add(_paletteButton);
		buttonPanel.add(_resetButton);
		buttonPanel.add(radioPanel);
		//=======================================================
		
		add(buttonPanel);
		add(_nameField);
	}
	
	
	/**
	 * Unused только для того, чтобы IDE не показывала варнинг...<BR>
	 */
	@SuppressWarnings("unused")
	public synchronized void updatePanel()
	{
		String colorName = ColorUtil.getColorName(_mainFrame.getSelectedColor().getRGB());
		
		if (colorName != null)
		{
			//TODO: Работает только если включена автопрокрутка названия цвета.
			if (AUTOSCROLL_NAME && colorName.length() > NAME_MAX_SIZE)
				_autoscrollThread.setColorName(colorName);
			else
			{
				if (AUTOSCROLL_NAME)
					_autoscrollThread.setColorName(null);
				
				if (colorName.length() > NAME_MAX_SIZE)
					_nameField.setText(colorName.substring(0, NAME_MAX_SIZE - 2) + "...");
				else
					_nameField.setText(colorName);
			}
			
			_nameField.setToolTipText(colorName);
		}
		else
		{
			if (AUTOSCROLL_NAME)
				_autoscrollThread.setColorName(null);
			
			_nameField.setText("");
			_nameField.setToolTipText("Неизвестный оттенок");
		}
	}
	
	// Поток прокрутки названия цвета
	private class AutoscrollName extends Thread
	{
		private String _colorName;
		
		private AutoscrollName()
		{
			setDaemon(true);
			start();
		}
		
		private void setColorName(String colorName)
		{
			_colorName = colorName;
		}
		
		@Override
		public void run()
		{
			while (!isInterrupted())
			{
				try
				{
					if (_colorName != null)
					{
						if (_colorName.length() > NAME_MAX_SIZE)
						{
							int variantsCount = _colorName.length() - (NAME_MAX_SIZE - 1);
							for (int i = 0; i < variantsCount; i++)
							{
								_nameField.setText(_colorName.substring(i, NAME_MAX_SIZE + i));
									
								Thread.sleep(1000L);
							}
						}
						
						Thread.sleep(2000L);
					}
				}
				catch (Exception e)
				{
				}
			}
		}
	}
}
