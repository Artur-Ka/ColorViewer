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
public class HSVPanel extends JPanel implements ColorModel
{
	public static final int SLIDER_WIDTH = 130;
	public static final int SLIDER_HEIGHT = 20;
	public static final int SPINNER_WIDTH = 40;
	public static final int SPINNER_HEIGHT = 20;
	
	
	private final JLabel _labelH = new JLabel("H:");
	private final JSlider _sliderH = new JSlider(0, 360, 0);
	private final JSpinner _spinnerH = new JSpinner();
	private final JLabel _labelS = new JLabel("S:");
	private final JSlider _sliderS = new JSlider(0, 100, 0);
	private final JSpinner _spinnerS = new JSpinner();
	private final JLabel _labelV = new JLabel("V:");
	private final JSlider _sliderV = new JSlider(0, 100, 0);
	private final JSpinner _spinnerV = new JSpinner();
	private final JLabel _labelTransp = new JLabel("Прозр:");
	private final JSlider _sliderTransp = new JSlider(0, 100, 0);
	private final JSpinner _spinnerTransp = new JSpinner();
	
	private ColorViewer _mainFrame;
	private JColor _selectedColor;
	
	
	public HSVPanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		_selectedColor = _mainFrame.getSelectedColor();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Dimension sliderDimension = new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT);
		Dimension spinnerDimension = new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT);
		
		// Панель H
		//============================================================================
		JPanel panelH = new JPanel();
		
		_labelH.setToolTipText("Тон");
		
		_sliderH.setValue(_selectedColor.getHue());
		_sliderH.setPreferredSize(sliderDimension);
		_sliderH.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderH.getValue() != _selectedColor.getHue())
				{
					_spinnerH.setValue(_sliderH.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerH.setPreferredSize(spinnerDimension);
		_spinnerH.setModel(new SpinnerNumberModel(_selectedColor.getHue(), 0, 360, 1));
		DefaultEditor editorH = (DefaultEditor) _spinnerH.getEditor();
		DefaultFormatter formatterH = (DefaultFormatter) editorH.getTextField().getFormatter();
		Document documentH = editorH.getTextField().getDocument();
		formatterH.setCommitsOnValidEdit(true);
		documentH.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerH.getValue() != _selectedColor.getHue())
					_sliderH.setValue((int) _spinnerH.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelH.add(_labelH);
		panelH.add(_sliderH);
		panelH.add(_spinnerH);
		//============================================================================
		
		// Панель S
		//============================================================================
		JPanel panelS = new JPanel();
		
		_labelS.setToolTipText("Насыщенность");
		
		_sliderS.setValue(_selectedColor.getSaturation());
		_sliderS.setPreferredSize(sliderDimension);
		_sliderS.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderS.getValue() != _selectedColor.getSaturation())
				{
					_spinnerS.setValue(_sliderS.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerS.setPreferredSize(spinnerDimension);
		_spinnerS.setModel(new SpinnerNumberModel(_selectedColor.getSaturation(), 0, 100, 1));
		DefaultEditor editorS = (DefaultEditor) _spinnerS.getEditor();
		DefaultFormatter formatterS = (DefaultFormatter) editorS.getTextField().getFormatter();
		Document documentS = editorS.getTextField().getDocument();
		formatterS.setCommitsOnValidEdit(true);
		documentS.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerS.getValue() != _selectedColor.getSaturation())
					_sliderS.setValue((int) _spinnerS.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelS.add(_labelS);
		panelS.add(_sliderS);
		panelS.add(_spinnerS);
		//============================================================================
		
		// Панель V
		//============================================================================
		JPanel panelV = new JPanel();
		
		_labelV.setToolTipText("Значение");
		
		_sliderV.setValue(_selectedColor.getValue());
		_sliderV.setPreferredSize(sliderDimension);
		_sliderV.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderV.getValue() != _selectedColor.getValue())
				{
					_spinnerV.setValue(_sliderV.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerV.setPreferredSize(spinnerDimension);
		_spinnerV.setModel(new SpinnerNumberModel(_selectedColor.getValue(), 0, 100, 1));
		DefaultEditor editorV = (DefaultEditor) _spinnerV.getEditor();
		DefaultFormatter formatterV = (DefaultFormatter) editorV.getTextField().getFormatter();
		Document documentV = editorV.getTextField().getDocument();
		formatterV.setCommitsOnValidEdit(true);
		documentV.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerV.getValue() != _selectedColor.getValue())
					_sliderV.setValue((int) _spinnerV.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelV.add(_labelV);
		panelV.add(_sliderV);
		panelV.add(_spinnerV);
		//============================================================================
		
		// Панель Transparency
		//============================================================================
		JPanel panelTransp = new JPanel();
		
		_labelTransp.setToolTipText("Прозрачность");
		
		_sliderTransp.setValue(_selectedColor.getTransparency());
		_sliderTransp.setPreferredSize(sliderDimension);
		_sliderTransp.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderTransp.getValue() != _selectedColor.getTransparency())
				{
					_spinnerTransp.setValue(_sliderTransp.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerTransp.setPreferredSize(spinnerDimension);
		_spinnerTransp.setModel(new SpinnerNumberModel(_selectedColor.getTransparency(), 0, 100, 1));
		DefaultEditor editorTransp = (DefaultEditor) _spinnerTransp.getEditor();
		DefaultFormatter formatterTransp = (DefaultFormatter) editorTransp.getTextField().getFormatter();
		Document documentTransp = editorTransp.getTextField().getDocument();
		formatterTransp.setCommitsOnValidEdit(true);
		documentTransp.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerTransp.getValue() != _selectedColor.getTransparency())
					_sliderTransp.setValue((int) _spinnerTransp.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelTransp.add(_labelTransp);
		panelTransp.add(_sliderTransp);
		panelTransp.add(_spinnerTransp);
		//============================================================================
		
		add(panelH);
		add(panelS);
		add(panelV);
		add(panelTransp);
	}
	
	private void updateSelectedColor()
	{
		_selectedColor = JColor.getHSVColor(_sliderH.getValue(), _sliderS.getValue(), _sliderV.getValue(), _sliderTransp.getValue());
		
		if (!_selectedColor.equals(_mainFrame.getSelectedColor()))
			_mainFrame.setSelectedColor(ColorViewer.HSV_PANEL, _selectedColor);
	}
	
	@Override
	public synchronized void updateModel(JColor color)
	{
		_selectedColor = color;
		
		_sliderH.setValue(_selectedColor.getHue());
		_spinnerH.setValue(_selectedColor.getHue());
		_sliderS.setValue(_selectedColor.getSaturation());
		_spinnerS.setValue(_selectedColor.getSaturation());
		_sliderV.setValue(_selectedColor.getValue());
		_spinnerV.setValue(_selectedColor.getValue());
		_sliderTransp.setValue(_selectedColor.getTransparency());
		_spinnerTransp.setValue(_selectedColor.getTransparency());
	}
}
