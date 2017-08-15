package common;

public class Uleb128 {
	public Uleb128() {
		
	}
	
	public static int Uleb2long(byte[] buf, int offset) {
		int result = 0;
		int tmp = 0;
		int i = 0;
		boolean leave = false;
		for (i=0; leave == false; ++i) {
			tmp = 0;
			tmp = tmp | buf[offset + i];
			if (tmp >= 0)
				leave = true;
			else
				tmp = tmp + 128;
			tmp = (tmp << (7 * i));
			result = tmp | result;
		}
		
		return result;
	}
	
	public static long UlebLength(byte[] buf, int offset) {
		int i=0;
		for (i=0; buf[offset + i] <0&& i< 100 ; ++i);
		return i+1;
	}
	
	public static byte[] Long2uleb(long num) {
		byte[] byuleb = new byte[20];
		int i = 0;
		do
		{
			long tmp = num % 128;
			num = (num >> 7);
			byuleb[i] = (byte)tmp;
			if (num != 0)
				byuleb[i] = (byte) (byuleb[i] | 128);
			++i;
		}while(num != 0);
		byte[] result = new byte[i];
		System.arraycopy(byuleb, 0, result, 0, i);
		return result;
	}
}
