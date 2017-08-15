package dex;

import common.DataTransfer;

public class dex_classdef {

	public dex_classdef() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte []data, int offset, int dex_offset) {
		this.dex_offset = dex_offset;
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset + dex_offset);
		class_idx = dt.DataInt(0);
		access_flags = dt.DataInt(4);
		superclass_idx = dt.DataInt(8);
		interfaces_off = dt.DataInt(12);
		source_file_idx = dt.DataInt(16);
		annotations_off = dt.DataInt(20);
		class_data_off = dt.DataInt(24);
		static_values_off = dt.DataInt(28);
		if (class_data_off != 0)
			class_data_.attach(data, class_data_off + dex_offset);
	}
	
	public int dex_offset;
	
	public int class_idx;
	public int access_flags;
	public int superclass_idx;
	public int interfaces_off;
	public int source_file_idx;
	public int annotations_off;
	public int class_data_off;
	public int static_values_off;
	
	public class_data class_data_ = new class_data();
}
