package common;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class StringData {
	public StringData() {
		
	}
	
	public static String StringData2String(byte[] buf, int offset) {
		String result = "";
		int tsize = Uleb128.Uleb2long(buf, offset);
		offset += Uleb128.UlebLength(buf, offset);
		int size = 0;
		while (buf[offset+size++]!=0);
		--size;
		byte[] byStr = new byte[size];
		System.arraycopy(buf, offset, byStr, 0, size);
		try {
			result = new String(byStr, "UTF-8");
			/*if (result.length() != tsize) {
				result = null;
			}*/
			byte[] newby = result.getBytes("UTF-8");
			if (Arrays.equals(newby, byStr) == false) {
				result = null;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
