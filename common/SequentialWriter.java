package common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class SequentialWriter {

	FileOutputStream m_SaveFile = null;


	public SequentialWriter AttachFile(String strResFileName)
	{
		try {
			m_SaveFile = new FileOutputStream(strResFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void Close()
	{
		try {
			m_SaveFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void WriteInt(int num) throws IOException
	{
		byte[] byInt = new byte[4];
		DataTransfer dt = new DataTransfer(byInt);
		dt.WriteInt(0, num);
		m_SaveFile.write(byInt);
		
	}
	
	public void WriteBlock(List<Byte> byData) throws IOException
	{
		int i = 0;
		for (i=0; i<byData.size(); ++i)
			m_SaveFile.write(byData.get(i));
	}
	
	public void WriteBlock(byte[] byData) throws IOException
	{
		m_SaveFile.write(byData);
	}
}
