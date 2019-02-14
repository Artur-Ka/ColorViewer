package ua.cw.project.holders;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import ua.cw.project.ColorViewer;
import ua.cw.project.model.color.JColor;

public class MouseHolder
{
	private ColorViewer _mainFrame;
	
	public static final MouseHolder getInstance()
	{
		return SingletonHolder._instance;
	}
	
	public void load(ColorViewer mainFrame)
	{
		_mainFrame = mainFrame;
		GlobalScreen.addNativeMouseListener(new GlobalMouseListener());
	}
	
	public void startMouseListening()
	{
		if (GlobalScreen.isNativeHookRegistered())
			return;
		
		try
		{
			GlobalScreen.registerNativeHook();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void stopMouseListening()
	{
		if (!GlobalScreen.isNativeHookRegistered())
			return;
		
		try
		{
			GlobalScreen.unregisterNativeHook();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private class GlobalMouseListener implements NativeMouseListener
	{
		@Override
		public void nativeMouseClicked(NativeMouseEvent arg0)
		{
			if (arg0.getButton() == NativeMouseEvent.BUTTON1)
			{
				Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				try
				{
					Robot robot = new Robot();
					Color color = robot.getPixelColor((int) mousePoint.getX(), (int) mousePoint.getY());
					
					_mainFrame.setSelectedColor(0, new JColor(color.getRGB()));
				}
				catch (AWTException e)
				{
					e.printStackTrace();
				}
			}
			
			stopMouseListening();
		}

		@Override
		public void nativeMousePressed(NativeMouseEvent arg0)
		{
		}

		@Override
		public void nativeMouseReleased(NativeMouseEvent arg0)
		{
		}
	}
	
	private static class SingletonHolder
	{
		private static final MouseHolder _instance = new MouseHolder();
	}
}
