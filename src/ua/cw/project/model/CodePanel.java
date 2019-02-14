package ua.cw.project.model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ua.cw.project.ColorViewer;
import ua.cw.project.formatters.DecFormatter;
import ua.cw.project.formatters.HexFormatter;
import ua.cw.project.model.color.JColor;
import ua.cw.project.util.ColorUtil;

@SuppressWarnings("serial")
public class CodePanel extends JPanel
{
	public static final int FIELD_WIDTH = 80;
	public static final int FIELD_HEIGHT = 20;
	public static final int BIN_FONT_SIZE = 9;
	
	
	private final JCheckBox _alphaCheck = new JCheckBox("Учитывать альфа-канал");
	private final JLabel _labelDec = new JLabel("Dec:");
	private final JFormattedTextField _fieldDec = new JFormattedTextField(new DecFormatter());
	private final JLabel _labelHex = new JLabel("Hex:");
	private final JFormattedTextField _fieldHex = new JFormattedTextField(new HexFormatter());
	private final JLabel _labelBin = new JLabel("Bin:");
	private final JTextField _fieldBin = new JTextField();
	
	private ColorViewer _mainFrame;
	
	public CodePanel(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Dimension fieldDimension = new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
		Dimension fieldBinDimension = new Dimension(170, 18);
		
		// Флажок включения альфа-канала
		//=======================================================
		JPanel alphaCheckPanel = new JPanel();
		
		_alphaCheck.setToolTipText("Альфа-канал в кодах цвета.");
		_alphaCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (_alphaCheck.isSelected())
				{
					((DecFormatter) _fieldDec.getFormatter()).setAlphaEnabled(true);
					((HexFormatter) _fieldHex.getFormatter()).setAlphaEnabled(true);
				}
				else
				{
					((DecFormatter) _fieldDec.getFormatter()).setAlphaEnabled(false);
					((HexFormatter) _fieldHex.getFormatter()).setAlphaEnabled(false);
				}
				
				updatePanel();
			}
		});
		
		alphaCheckPanel.add(_alphaCheck);
		//=======================================================
		
		// Панель с Dec и Hex полями
		//=======================================================
		JPanel topPanel = new JPanel();
		
		_fieldDec.setText(ColorUtil.ARGBtoRGB(_mainFrame.getSelectedColor().getRGB()));
		_fieldDec.setPreferredSize(fieldDimension);
		_fieldDec.setBorder(BorderFactory.createEtchedBorder());
		_fieldDec.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		_fieldHex.setText(ColorUtil.ARGBtoHex(_mainFrame.getSelectedColor().getRGB()));
		_fieldHex.setPreferredSize(fieldDimension);
		_fieldHex.setBorder(BorderFactory.createEtchedBorder());
		_fieldHex.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void changedUpdate(DocumentEvent arg0)
			{
			}

			@Override
			public void insertUpdate(DocumentEvent arg0)
			{
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0)
			{
			}
		});
		
		topPanel.add(_labelDec);
		topPanel.add(_fieldDec);
		topPanel.add(_labelHex);
		topPanel.add(_fieldHex);
		//=======================================================
		
		// Панель с Bin полем
		//=======================================================
		JPanel lowPanel = new JPanel();
		
		_fieldBin.setText(ColorUtil.ARGBtoBin(_mainFrame.getSelectedColor().getRGB()));
		_fieldBin.setPreferredSize(fieldBinDimension);
		_fieldBin.setBorder(BorderFactory.createEtchedBorder());
		_fieldBin.setFont(new Font("Dialog", 0, BIN_FONT_SIZE));
		_fieldBin.setEditable(false);
		
		
		lowPanel.add(_labelBin);
		lowPanel.add(_fieldBin);
		
		add(alphaCheckPanel);
		add(topPanel);
		add(lowPanel);
		//=======================================================
	}
	
	private void updateSelectedColor(JColor color)
	{
		if (!_mainFrame.getSelectedColor().equals(color))
			_mainFrame.setSelectedColor(0, color);
	}
	
	public void updatePanel()
	{
		int argb = _mainFrame.getSelectedColor().getRGB();
		
		if (_alphaCheck.isSelected())
		{
			_fieldDec.setText(ColorUtil.ARGBtoARGB(argb));
			_fieldHex.setText(ColorUtil.ARGBtoHexWithAlpha(argb));
			_fieldBin.setText(ColorUtil.ARGBtoBinWithAlpha(argb));
		}
		else
		{
			_fieldDec.setText(ColorUtil.ARGBtoRGB(argb));
			_fieldHex.setText(ColorUtil.ARGBtoHex(argb));
			_fieldBin.setText(ColorUtil.ARGBtoBin(argb));
		}
	}
}
