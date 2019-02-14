package ua.cw.project.formatters;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;

@SuppressWarnings("serial")
public class HexFormatter extends DefaultFormatter
{
	/** The mask for this MaskFormatter **/
	private String _mask = "#HHHHHH";
	
	/** A String used for easy access to valid HEX characters **/
	private static String _hexString = "0123456789ABCDEFabcdef";
	
	public HexFormatter()
	{
		setAllowsInvalid(false);
		setCommitsOnValidEdit(true);
	}
	
	public void setAlphaEnabled(boolean isAlphaEnabled)
	{
		if (isAlphaEnabled)
			_mask = "#HHHHHHHH";
		else
			_mask = "#HHHHHH";
	}
	
	public boolean isAlphaEnabled()
	{
		return _mask.length() > 7;
	}
	
	/**
	 * Installs this MaskFormatter on the JFormattedTextField.
	 * Invokes valueToString to convert the current value from the 
	 * JFormattedTextField to a String, then installs the Actions from
	 * getActions, the DocumentFilter from getDocumentFilter, and the 
	 * NavigationFilter from getNavigationFilter.
	 * 
	 * If valueToString throws a ParseException, this method sets the text
	 * to an empty String and marks the JFormattedTextField as invalid.
	 */
	public void install(JFormattedTextField ftf)
	{
		super.install(ftf);
		if (ftf != null)
		{
			try
			{
				valueToString(ftf.getValue());
			}
			catch (ParseException pe)
			{
				// Set the text to an empty String and mark the JFormattedTextField
				// as invalid.
				ftf.setText("");
				setEditValid(false);
			}
		}
	}
	
	@Override
	public Object stringToValue(String value) throws ParseException
	{
		return super.stringToValue(convertStringToValue(value));
	}
	
	private String convertStringToValue(String value) throws ParseException
	{
		int length = _mask.length();
		
		if (value.length() > _mask.length())
			throw new ParseException ("Invalid string length", value.length());
		
		StringBuffer result = new StringBuffer();
		char valueChar, maskChar;
		
		for (int i = 0, j = 0; j < length; j++)
		{
			if (i < value.length())
			{
				valueChar = value.charAt(i);
				maskChar = _mask.charAt(j);
				
				if (maskChar == '#')
				{
					if (valueChar != maskChar)
						throw new ParseException ("Invalid character: "+ valueChar, i);
					
					result.append(maskChar);
					
					i++;
				}
				else
				{
					if (_hexString.indexOf(valueChar) == -1)
						throw new ParseException("Hexadecimal character expected", i);
					
					result.append(valueChar);
					
					i++;
				}
			}
		}
		
		return result.toString();
	}
	
	@Override
	public String valueToString(Object value) throws ParseException
	{
		String string = value != null ? value.toString() : "";
		
		return convertValueToString(string);
	}
	
	private String convertValueToString(String value) throws ParseException
	{
		int length = _mask.length();
		
		if (value.length() > _mask.length())
			throw new ParseException ("Invalid string length", value.length());
		
		StringBuffer result = new StringBuffer();
		char valueChar, maskChar;
		
		for (int i = 0, j = 0; j < length; j++)
		{
			if (i < value.length())
			{
				valueChar = value.charAt(i);
				maskChar = _mask.charAt(j);
				
				if (maskChar == '#')
				{
					if (valueChar != maskChar)
						throw new ParseException ("Invalid character: "+ valueChar, i);
					
					result.append(maskChar);
					
					i++;
				}
				else
				{
					if (_hexString.indexOf(valueChar) == -1)
						throw new ParseException("Hexadecimal character expected", i);
					
					result.append(valueChar);
						
					i++;
				}
			}
		}
		
		return result.toString();
	}
}