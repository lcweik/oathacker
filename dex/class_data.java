package dex;

import common.DataTransfer;
import common.Uleb128;

public class class_data {

	public class_data() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] data, int offset) {
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset);
		
		static_fields_size = Uleb128.Uleb2long(data, offset);
		offset += Uleb128.UlebLength(data, offset);
		instance_fields_size = Uleb128.Uleb2long(data, offset);
		offset += Uleb128.UlebLength(data, offset);
		direct_methods_size = Uleb128.Uleb2long(data, offset);
		offset += Uleb128.UlebLength(data, offset);
		virtual_methods_size = Uleb128.Uleb2long(data, offset);
		offset += Uleb128.UlebLength(data, offset);
		
		// skip field
		int i = 0;
		for (i=0; i<static_fields_size + instance_fields_size; ++i) {
			offset += Uleb128.UlebLength(data, offset);
			offset += Uleb128.UlebLength(data, offset);
		}
		dex_methods_ = new dex_method[direct_methods_size + virtual_methods_size];
		int lastmid = 0;
		for (i=0; i<direct_methods_size + virtual_methods_size; ++i) {
			if (i == direct_methods_size)
				lastmid = 0;
			dex_methods_[i] = new dex_method();
			int middiff = Uleb128.Uleb2long(data, offset);
			lastmid += middiff;
			dex_methods_[i].mid = lastmid;
			offset += Uleb128.UlebLength(data, offset);
			int af = Uleb128.Uleb2long(data, offset);
			dex_methods_[i].af = af;
			offset += Uleb128.UlebLength(data, offset);
			int off = Uleb128.Uleb2long(data, offset);
			dex_methods_[i].off = off;
			offset += Uleb128.UlebLength(data, offset);
		}
	}
	
	public int static_fields_size;
	public int instance_fields_size;
	public int direct_methods_size;
	public int virtual_methods_size;
	
	public dex_method[] dex_methods_;
}
