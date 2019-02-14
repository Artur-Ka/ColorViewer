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
public class CMYKPanel extends JPanel implements ColorModel
{
	public static final int SLIDER_WIDTH = 130;
	public static final int SLIDER_HEIGHT = 20;
	public static final int SPINNER_WIDTH = 40;
	public static final int SPINNER_HEIGHT = 20;
	
	
	private final JLabel _labelC = new JLabel("C:");
	private final JSlider _sliderC = new JSlider(0, 100, 0);
	private final JSpinner _spinnerC = new JSpinner();
	private final JLabel _labelM = new JLabel("M:");
	private final JSlider _sliderM = new JSlider(0, 100, 0);
	private final JSpinner _spinnerM = new JSpinner();
	private final JLabel _labelY = new JLabel("Y:");
	private final JSlider _sliderY = new JSlider(0, 100, 0);
	private final JSpinner _spinnerY = new JSpinner();
	private final JLabel _labelBlack = new JLabel("Black:");
	private final JSlider _sliderBlack = new JSlider(0, 100, 0);
	private final JSpinner _spinnerBlack = new JSpinner();
	
	private ColorViewer _mainFrame;
	private JColor _selectedColor;
	
	
	public CMYKPanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		_selectedColor = _mainFrame.getSelectedColor();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Dimension sliderDimension = new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT);
		Dimension spinnerDimension = new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT);
		
		// Панель C
		//============================================================================
		JPanel panelC = new JPanel();
		
		_labelC.setToolTipText("Циан");
		
		_sliderC.setValue(_selectedColor.getCyan());
		_sliderC.setPreferredSize(sliderDimension);
		_sliderC.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderC.getValue() != _selectedColor.getHue())
				{
					_spinnerC.setValue(_sliderC.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerC.setPreferredSize(spinnerDimension);
		_spinnerC.setModel(new SpinnerNumberModel(_selectedColor.getCyan(), 0, 100, 1));
		DefaultEditor editorC = (DefaultEditor) _spinnerC.getEditor();
		DefaultFormatter formatterC = (DefaultFormatter) editorC.getTextField().getFormatter();
		Document documentC = editorC.getTextField().getDocument();
		formatterC.setCommitsOnValidEdit(true);
		documentC.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerC.getValue() != _selectedColor.getCyan())
					_sliderC.setValue((int) _spinnerC.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelC.add(_labelC);
		panelC.add(_sliderC);
		panelC.add(_spinnerC);
		//============================================================================
		
		// Панель M
		//============================================================================
		JPanel panelM = new JPanel();
		
		_labelM.setToolTipText("Магента");
		
		_sliderM.setValue(_selectedColor.getMagenta());
		_sliderM.setPreferredSize(sliderDimension);
		_sliderM.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderM.getValue() != _selectedColor.getMagenta())
				{
					_spinnerM.setValue(_sliderM.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerM.setPreferredSize(spinnerDimension);
		_spinnerM.setModel(new SpinnerNumberModel(_selectedColor.getMagenta(), 0, 100, 1));
		DefaultEditor editorM = (DefaultEditor) _spinnerM.getEditor();
		DefaultFormatter formatterM = (DefaultFormatter) editorM.getTextField().getFormatter();
		Document documentM = editorM.getTextField().getDocument();
		formatterM.setCommitsOnValidEdit(true);
		documentM.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerM.getValue() != _selectedColor.getMagenta())
					_sliderM.setValue((int) _spinnerM.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelM.add(_labelM);
		panelM.add(_sliderM);
		panelM.add(_spinnerM);
		//============================================================================
		
		// Панель Y
		//============================================================================
		JPanel panelY = new JPanel();
		
		_labelY.setToolTipText("Жёлтый");
		
		_sliderY.setValue(_selectedColor.getYellow());
		_sliderY.setPreferredSize(sliderDimension);
		_sliderY.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderY.getValue() != _selectedColor.getYellow())
				{
					_spinnerY.setValue(_sliderY.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerY.setPreferredSize(spinnerDimension);
		_spinnerY.setModel(new SpinnerNumberModel(_selectedColor.getYellow(), 0, 100, 1));
		DefaultEditor editorY = (DefaultEditor) _spinnerY.getEditor();
		DefaultFormatter formatterY = (DefaultFormatter) editorY.getTextField().getFormatter();
		Document documentY = editorY.getTextField().getDocument();
		formatterY.setCommitsOnValidEdit(true);
		documentY.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerY.getValue() != _selectedColor.getYellow())
					_sliderY.setValue((int) _spinnerY.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelY.add(_labelY);
		panelY.add(_sliderY);
		panelY.add(_spinnerY);
		//============================================================================
		
		// Панель Black
		//============================================================================
		JPanel panelBlack = new JPanel();
		
		_labelBlack.setToolTipText("Чёрный");
		
		_sliderBlack.setValue(_selectedColor.getBlack());
		_sliderBlack.setPreferredSize(sliderDimension);
		_sliderBlack.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				if (_sliderBlack.getValue() != _selectedColor.getBlack())
				{
					_spinnerBlack.setValue(_sliderBlack.getValue());
					updateSelectedColor();
				}
			}
		});
		
		_spinnerBlack.setPreferredSize(spinnerDimension);
		_spinnerBlack.setModel(new SpinnerNumberModel(_selectedColor.getBlack(), 0, 100, 1));
		DefaultEditor editorBlack = (DefaultEditor) _spinnerBlack.getEditor();
		DefaultFormatter formatterBlack = (DefaultFormatter) editorBlack.getTextField().getFormatter();
		Document documentBlack = editorBlack.getTextField().getDocument();
		formatterBlack.setCommitsOnValidEdit(true);
		documentBlack.addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				if ((int)_spinnerBlack.getValue() != _selectedColor.getBlack())
					_sliderBlack.setValue((int) _spinnerBlack.getValue());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		panelBlack.add(_labelBlack);
		panelBlack.add(_sliderBlack);
		panelBlack.add(_spinnerBlack);
		//============================================================================
		
		add(panelC);
		add(panelM);
		add(panelY);
		add(panelBlack);
	}
	
	private void updateSelectedColor()
	{
		_selectedColor = JColor.getCMYKColor(_sliderC.getValue(), _sliderM.getValue(), _sliderY.getValue(), _sliderBlack.getValue());
		
		if (!_selectedColor.equals(_mainFrame.getSelectedColor()))
			_mainFrame.setSelectedColor(0, _selectedColor);
	}
	
	@Override
	public synchronized void updateModel(JColor color)
	{
		if (!_selectedColor.equals(color))
		{
			_selectedColor = color;
			
			_sliderC.setValue(_selectedColor.getCyan());
			_spinnerC.setValue(_selectedColor.getCyan());
			_sliderM.setValue(_selectedColor.getMagenta());
			_spinnerM.setValue(_selectedColor.getMagenta());
			_sliderY.setValue(_selectedColor.getYellow());
			_spinnerY.setValue(_selectedColor.getYellow());
			_sliderBlack.setValue(_selectedColor.getBlack());
			_spinnerBlack.setValue(_selectedColor.getBlack());
		}
	}
}
