package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SequentialReader {
	FileInputStream m_ReadFile = null;
	
	public SequentialReader()
	{

	}
	
	
	
	public SequentialReader AttachFile(String filename)
	{
		try {
			m_ReadFile = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return this;
	}

	
	
	public void Close()
	{
		try {
			m_ReadFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int ReadInt() throws IOException
	{
		int res = 0;
		byte[] byInt = new byte[4];
		res = m_ReadFile.read(byInt);
		if (4 != res)
		{
			throw(new IOException()	{
				@Override
				public String toString() {
					return "EndOfFile";
				}				
			});
		}
		DataTransfer dt = new DataTransfer(byInt);
		return dt.DataInt(0);
	}
	
	
	public void ReadBlock(byte[] byData) throws IOException
	{
		m_ReadFile.read(byData);
	}
	
}
