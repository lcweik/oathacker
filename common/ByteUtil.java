package common;

import java.util.*;

public class ByteUtil {
	
	public static byte[] toArray(List<Byte> ltByte)
	{
		byte[] byRes = new byte[ltByte.size()];
		int i = 0;
		for (i=0; i<byRes.length; ++i)
		{
			byRes[i] = ltByte.get(i);
		}
		return byRes;
	}
	
	public static ArrayList<Byte> toList(byte[] byByte)
	{
		ArrayList<Byte> ltRes = new ArrayList<Byte>();
		int i = 0;
		for (i=0; i<byByte.length; ++i)
		{
			ltRes.add(byByte[i]);
		}
		return ltRes;
	}
	
	public static byte[] MergeArray(byte[] byByte1, byte[] byByte2)
	{
		byte[] byRes = new byte[byByte1.length + byByte2.length];
		System.arraycopy(byByte1, 0, byRes, 0, byByte1.length);
		System.arraycopy(byByte2, 0, byRes, byByte1.length, byByte2.length);
		return byRes;
	}
}
