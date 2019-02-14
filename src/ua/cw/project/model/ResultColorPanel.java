package ua.cw.project.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import ua.cw.project.ColorViewer;
import ua.cw.project.model.color.JColor;

@SuppressWarnings("serial")
public class ResultColorPanel extends JPanel
{
	public static final int HEIGHT = 50;
	public static final int FONT_SIZE = 40;
	
	
	private final TextColorPane _colorPane = new TextColorPane();
	
	private final ColorViewer _mainFrame;
	
	private JColor _backColor;
	private JColor _textColor;
	
	public ResultColorPanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		_backColor = _mainFrame.getSelectedColor();
		_textColor = JColor.BLACK;
		
		setPreferredSize(new Dimension(_mainFrame.getWidth(), HEIGHT));
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		_colorPane.setEditable(false);
		_colorPane.setFont(new Font("Dialog", 0, FONT_SIZE));
		_colorPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		_colorPane.setPreferredSize(new Dimension(_mainFrame.getWidth(), HEIGHT));
		_colorPane.setBackground(_mainFrame.getSelectedColor());
		
		add(_colorPane, BorderLayout.NORTH);
	}
	
	public JColor getBackColor()
	{
		return _backColor;
	}
	
	public JColor getTextColor()
	{
		return _textColor;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Делаем фоновую бело-серую рубашку
		g.setColor(Color.LIGHT_GRAY);
		
		int tmp = 0;
		for (int i = 0; i < getHeight(); i += 10)
		{
			// На парной строке получаем 0, на непарной - 10
			tmp = (i / 10 % 2) * 10;
			
			for (int j = tmp; j < getWidth(); j += 20)
			{
				g.fillRect(j, i, 10, 10);
			}
		}
	}
	
	public void updatePanel()
	{
		if (_mainFrame.isTextEnabled())
		{
			_colorPane.setOpaque(true);
			_colorPane.setEditable(true);
			
			JColor color = _mainFrame.getSelectedColor();
			
			_textColor = color;
			_colorPane.setForeground(color);
		}
		else
		{
			// Включать прозрачность только при отсутствии текста
			if (_colorPane.getText().length() == 0)
			{
				_colorPane.setOpaque(false);
				_colorPane.setEditable(false);
			}
			
			JColor color = _mainFrame.getSelectedColor();
			
			_backColor = color;
			_colorPane.setBackground(color);
		}
	}
	
	private class TextColorPane extends JTextPane
	{
		private TextColorPane()
		{
			setOpaque(false);
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			// Включать прозрачность только если выключен режим ввода текста и текста нету
			if (!_mainFrame.isTextEnabled() && getText().length() == 0)
			{
				Graphics2D g2d = (Graphics2D) g.create();
				
				g2d.setColor(getBackground());
				g2d.fillRect(0, 0, getWidth(), getHeight());
				
				g2d.dispose();
			}
		}
	}
}
