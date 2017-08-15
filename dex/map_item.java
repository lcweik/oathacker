package dex;

import common.DataTransfer;

public class map_item {

	public map_item() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] buf, int offset) {
		DataTransfer dt = new DataTransfer(buf);
		dt.SetBase(offset);
		type = dt.DataInt(0);
		size = dt.DataInt(4);
		this.offset = dt.DataInt(8);
	}
	
	public int type;
	public int size;
	public int offset;
}
