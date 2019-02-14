package ua.cw.project.model;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.Document;

import ua.cw.project.ColorViewer;
import ua.cw.project.model.color.JColor;
import ua.cw.project.model.interf.ColorModel;

@SuppressWarnings("serial")
public class RGBPanel extends JPanel implements ColorModel
{
	public static final int SLIDER_WIDTH = 130;
	public static final int SLIDER_HEIGHT = 20;
	public static final int SPINNER_WIDTH = 40;
	public static final int SPINNER_HEIGHT = 20;
	
	
	private final JLabel _labelR = new JLabel("R:");
	private final JSlider _sliderR = new JSlider(0, 255, 0);
	private final JSpinner _spinnerR = new JSpinner();
	private final JLabel _labelG = new JLabel("G:");
	private final JSlider _sliderG = new JSlider(0, 255, 0);
	private final JSpinner _spinnerG = new JSpinner();
	private final JLabel _labelB = new JLabel("B:");
	private final JSlider _sliderB = new JSlider(0, 255, 0);
	private final JSpinner _spinnerB = new JSpinner();
	private final JLabel _labelAlpha = new JLabel("Альфа:");
	private final JSlider _sliderAlpha = new JSlider(0, 255, 0);
	private final JSpinner _spinnerAlpha = new JSpinner();
	
	private ColorViewer _mainFrame;
	private JColor _selectedColor;
	
	
	public RGBPanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		_selectedColor = _mainFrame.getSelectedColor();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Dimension sliderDimension = new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT);
		Dimension spinnerDimension = new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT);
		
		// Панель R
		//============================================================================
		JPanel panelR = new JPanel();
		
		_labelR.setToolTipText("Красный");
		
		_sliderR.setValue(_selectedColor.getRed());
		_sliderR.setPreferredSize(sliderDimension);
		_sliderR.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderR.getValue() != _selectedColor.getRed())
				{
					_spinnerR.setValue(_sliderR.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerR.setPreferredSize(spinnerDimension);
		_spinnerR.setModel(new SpinnerNumberModel(_selectedColor.getRed(), 0, 255, 1));
		DefaultEditor editorR = (DefaultEditor) _spinnerR.getEditor();
		DefaultFormatter formatterR = (DefaultFormatter) editorR.getTextField().getFormatter();
		Document documentR = editorR.getTextField().getDocument();
		formatterR.setCommitsOnValidEdit(true);
		documentR.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerR.getValue() != _selectedColor.getRed())
					_sliderR.setValue((int) _spinnerR.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelR.add(_labelR);
		panelR.add(_sliderR);
		panelR.add(_spinnerR);
		//============================================================================
		
		// Панель G
		//============================================================================
		JPanel panelG = new JPanel();
		
		_labelG.setToolTipText("Зелёный");
		
		_sliderG.setValue(_selectedColor.getGreen());
		_sliderG.setPreferredSize(sliderDimension);
		_sliderG.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderG.getValue() != _selectedColor.getGreen())
				{
					_spinnerG.setValue(_sliderG.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerG.setPreferredSize(spinnerDimension);
		_spinnerG.setModel(new SpinnerNumberModel(_selectedColor.getGreen(), 0, 255, 1));
		DefaultEditor editorG = (DefaultEditor) _spinnerG.getEditor();
		DefaultFormatter formatterG = (DefaultFormatter) editorG.getTextField().getFormatter();
		Document documentG = editorG.getTextField().getDocument();
		formatterG.setCommitsOnValidEdit(true);
		documentG.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerG.getValue() != _selectedColor.getGreen())
					_sliderG.setValue((int) _spinnerG.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelG.add(_labelG);
		panelG.add(_sliderG);
		panelG.add(_spinnerG);
		//============================================================================
		
		// Панель B
		//============================================================================
		JPanel panelB = new JPanel();
		
		_labelB.setToolTipText("Синий");
		
		_sliderB.setValue(_selectedColor.getBlue());
		_sliderB.setPreferredSize(sliderDimension);
		_sliderB.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderB.getValue() != _selectedColor.getBlue())
				{
					_spinnerB.setValue(_sliderB.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerB.setPreferredSize(spinnerDimension);
		_spinnerB.setModel(new SpinnerNumberModel(_selectedColor.getBlue(), 0, 255, 1));
		DefaultEditor editorB = (DefaultEditor) _spinnerB.getEditor();
		DefaultFormatter formatterB = (DefaultFormatter) editorB.getTextField().getFormatter();
		Document documentB = editorB.getTextField().getDocument();
		formatterB.setCommitsOnValidEdit(true);
		documentB.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerB.getValue() != _selectedColor.getBlue())
					_sliderB.setValue((int) _spinnerB.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelB.add(_labelB);
		panelB.add(_sliderB);
		panelB.add(_spinnerB);
		//============================================================================
		
		// Панель Alpha
		//============================================================================
		JPanel panelAlpha = new JPanel();
		
		_labelAlpha.setToolTipText("Альфа-канал");
		
		_sliderAlpha.setValue(_selectedColor.getAlpha());
		_sliderAlpha.setPreferredSize(sliderDimension);
		_sliderAlpha.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderAlpha.getValue() != _selectedColor.getAlpha())
				{
					_spinnerAlpha.setValue(_sliderAlpha.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerAlpha.setPreferredSize(spinnerDimension);
		_spinnerAlpha.setModel(new SpinnerNumberModel(_selectedColor.getAlpha(), 0, 255, 1));
		DefaultEditor editorAlpha = (DefaultEditor) _spinnerAlpha.getEditor();
		DefaultFormatter formatterAlpha = (DefaultFormatter) editorAlpha.getTextField().getFormatter();
		Document documentAlpha = editorAlpha.getTextField().getDocument();
		formatterAlpha.setCommitsOnValidEdit(true);
		documentAlpha.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerAlpha.getValue() != _selectedColor.getAlpha())
					_sliderAlpha.setValue((int) _spinnerAlpha.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelAlpha.add(_labelAlpha);
		panelAlpha.add(_sliderAlpha);
		panelAlpha.add(_spinnerAlpha);
		//============================================================================
		
		add(panelR);
		add(panelG);
		add(panelB);
		add(panelAlpha);
	}
	
	private void updateSelectedColor()
	{
		_selectedColor = JColor.getRGBColor(_sliderR.getValue(), _sliderG.getValue(), _sliderB.getValue(), _sliderAlpha.getValue());
		
		if (!_selectedColor.equals(_mainFrame.getSelectedColor()))
			_mainFrame.setSelectedColor(ColorViewer.RGB_PANEL, _selectedColor);
	}
	
	@Override
	public synchronized void updateModel(JColor color)
	{
		_selectedColor = color;
		
		_sliderR.setValue(_selectedColor.getRed());
		_spinnerR.setValue(_selectedColor.getRed());
		_sliderG.setValue(_selectedColor.getGreen());
		_spinnerG.setValue(_selectedColor.getGreen());
		_sliderB.setValue(_selectedColor.getBlue());
		_spinnerB.setValue(_selectedColor.getBlue());
		_sliderAlpha.setValue(_selectedColor.getAlpha());
		_spinnerAlpha.setValue(_selectedColor.getAlpha());
	}
}
