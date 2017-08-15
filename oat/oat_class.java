package oat;

import setting.option;
import common.DataTransfer;

public class oat_class {

	public oat_class() {
		// TODO Auto-generated constructor stub
	}
	
	public int attach(byte[] data, int offset, int med_count) {
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset);
		status = dt.DataShort(0);
		type = dt.DataShort(2);
		int size = 4;
		if (type == 1) {
			bitsize = dt.DataInt(4);
			size += 4;
			arr = new byte[bitsize];
			System.arraycopy(data, offset + size, arr, 0, bitsize);
			size += bitsize;
			
			med_count = compute_count(arr);
		} else if (type == 2) {
			med_count = 0;
		}
		oat_method_off = new int[med_count];
		int i = 0;
		for (i=0; i<med_count; ++i) {
			oat_method_off[i] = dt.DataInt(size + i * 4);
			if (option.search_result <= oat_method_off[i] && oat_method_off[i] <= option.offset) {
				//System.out.println("" + option.search_result + " " + oat_method_off[i]);
				option.search_result = oat_method_off[i];
			}
		}
		size += med_count * 4;
		return size;
	}
	
	int compute_byte_count(byte arr) {
		int i = 0;
		int count = 0;
		int bit = 1;
		for (i=0; i<8; ++i, bit = (bit << 1)) {
			if ((arr & bit) != 0)
				++count;
		}
		return count;
	}
	
	int compute_count(byte[] arr) {
		int i = 0;
		int count = 0;
		for (i=0; i<arr.length; ++i) {
			count += compute_byte_count(arr[i]);
		}
		return count;
	}

	public int status;
	public int type;
	public int bitsize;
	public byte[] arr;
	
	public int []oat_method_off;

}
