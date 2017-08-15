package oat;

import common.DataTransfer;

public class oat_dex_file {

	public oat_dex_file() {
		// TODO Auto-generated constructor stub
	}
	
	void attach(byte[] data, int offset) {
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset);
		dex_file_location_size = dt.DataInt(0);
		dex_file_location_data = new byte[dex_file_location_size];
		System.arraycopy(data, offset + 4, dex_file_location_data, 0, dex_file_location_size);
		dex_file_checksum = dt.DataInt(dex_file_location_size + 4);
		dex_file_offset = dt.DataInt(dex_file_location_size + 8);
	}
	
	int size() {
		return dex_file_location_size + 12;
	}

	public int dex_file_location_size;
	public byte[] dex_file_location_data;
	public int dex_file_checksum;
	public int dex_file_offset;
	public int[] class_offs;
	public oat_class[] oat_class_;

}
