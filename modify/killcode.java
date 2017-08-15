package modify;

import java.util.Arrays;

import common.DataTransfer;
import common.Uleb128;

import dex.dex_header;

public class killcode {

	public killcode() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] buf, dex_header dex) {
		
	}
	
	public static int fixoffset(int offset) {
		if (offset % 4  == 0) {
			return offset;
		}
		offset += (4 - offset % 4);
		return offset;
	}
	
	static int acount = 0;
	
	public void attach(byte[] buf, int offset, int len) {
		int i = 0;
		int init_offset = offset;
		DataTransfer dt = new DataTransfer(buf);
		for (i=0; i<len; ++i) {
			
			dt.SetBase(offset);
			//System.out.println(" " + i + "=" + offset);
			int in_count = dt.DataShort(2);
			int out_count = dt.DataShort(4);
			int try_count = dt.DataShort(6);
			int insns_count = dt.DataInt(12);
			offset += 16;
			//if (in_count==0 && out_count==0 &&try_count == 0 && insns_count>300 ) {
			if (hasFill(buf, offset, insns_count * 2)/*||i>=10061*/) {
			//if (i >= 10000) {
				System.out.println("i=" + i +" :" + offset + " " + insns_count);
				//Arrays.fill(buf, offset, offset + insns_count * 2, (byte)0);
			}
			else {
				Arrays.fill(buf, offset, offset + insns_count * 2, (byte)0);
			}
			acount ++;
			offset += insns_count * 2;
			offset = fixoffset(offset);
			if (try_count != 0) {
				offset += try_count * 8;
				int catch_count = Uleb128.Uleb2long(buf, offset);
				offset += Uleb128.UlebLength(buf, offset);
				int j = 0;
				for (j=0; j<catch_count; ++j) {
					int has_catch_all = 0;
					int handler_count = Uleb128.Uleb2long(buf, offset);
					if (handler_count >= 64) {
						handler_count = 128 - handler_count;
						has_catch_all = 1;
					}
					if (handler_count == 0) {
						has_catch_all = 1;
					}
					offset += Uleb128.UlebLength(buf, offset);
					int k = 0;
					for (k=0; k<handler_count; ++k) {
						offset += Uleb128.UlebLength(buf, offset);
						offset += Uleb128.UlebLength(buf, offset);
					}
					if (has_catch_all == 1) {
						offset += Uleb128.UlebLength(buf, offset);
					}
				}
				offset = fixoffset(offset);
			}
		}
	}

	private boolean hasFill(byte[] buf, int offset, int len) {
		// TODO Auto-generated method stub
		int i = 0; 
		DataTransfer dt = new DataTransfer(buf);
		dt.SetBase(offset);
		for (i=0; i<len; i += 2) {
			if (buf[offset+i] == 0x26) {
				int data_offset = dt.DataShort(i+2) * 2;
				if (data_offset + i <= len && data_offset > 1) {
					if (dt.DataShort(i + data_offset) == 0x300)
						return true;
				}
			}
		}
		return false;
	}

}
