package ua.cw.project.formatters;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;

@SuppressWarnings("serial")
public class DecFormatter extends DefaultFormatter
{
	private int _length = 8;
	
	
	public DecFormatter()
	{
		setAllowsInvalid(false);
		setCommitsOnValidEdit(true);
	}
	
	public void setAlphaEnabled(boolean isAlphaEnabled)
	{
		if (isAlphaEnabled)
			_length = 11;
		else
			_length = 8;
	}
	
	public boolean isAlphaEnabled()
	{
		return _length > 8;
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
		if (value.length() > _length)
			throw new ParseException ("Invalid string length", value.length());
		
		StringBuffer result = new StringBuffer();
		char valueChar;
		
		for (int i = 0, j = 0; j < _length; j++)
		{
			if (i < value.length())
			{
				valueChar = value.charAt(i);
				
				if (!(isAlphaEnabled() || Character.isDigit(valueChar)))
					throw new ParseException("Number expected: " + valueChar, i);
				
				if (!Character.isDigit(valueChar) && valueChar != '-')
					throw new ParseException("Number expected: " + valueChar, i);
				
				result.append(valueChar);
					
				i++;
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
		if (value.length() > _length)
			throw new ParseException ("Invalid string length", value.length());
		
		StringBuffer result = new StringBuffer();
		char valueChar;
		
		for (int i = 0, j = 0; j < _length; j++)
		{
			if (i < value.length())
			{
				valueChar = value.charAt(i);
				
				if (!(isAlphaEnabled() || Character.isDigit(valueChar)))
					throw new ParseException("Number expected: " + valueChar, i);
				
				if (!Character.isDigit(valueChar) && valueChar != '-')
					throw new ParseException("Number expected: " + valueChar, i);
				
				result.append(valueChar);
					
				i++;
			}
		}
		
		return result.toString();
	}
}