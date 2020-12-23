package pobj.graphic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class TextArea extends JTextArea
{
	private String logFile = null;
	
	public TextArea(String logFile)
	{
		super();
		this.logFile = logFile;
		
		setEditable(false);
	}
	public void printLog()
	{
		if(logFile != null)
		{
			String file = "";
			String line = "";
			try (BufferedReader br = new BufferedReader(new FileReader(logFile)))
			{
				while ((line = br.readLine()) != null)
				{
					append(line);
					append("\n");
				}
				
				br.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			
		}
	}
		else
		{
			append("there is no log File");
		}
	}
}
