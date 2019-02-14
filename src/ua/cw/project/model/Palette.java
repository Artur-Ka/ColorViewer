package ua.cw.project.model;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ua.cw.project.ColorViewer;

@SuppressWarnings("serial")
public class Palette extends JFrame
{
	private JColorChooser _colorChooser;
	
	public Palette(ColorViewer mainFrame)
	{
		_colorChooser = new JColorChooser();
		
		add(new JColorChooser());
		pack();
		setVisible(true);
		
		_colorChooser.getSelectionModel().addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
//				mainFrame.setSelectedColor(_colorChooser.getColor());
				mainFrame.setVisible(false);
			}
		});
	}
	
	public Color getColor()
	{
		return _colorChooser.getColor();
	}
}
