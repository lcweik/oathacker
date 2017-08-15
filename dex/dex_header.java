package dex;

import common.DataTransfer;
import common.Uleb128;

public class dex_header {

	public dex_header() {
		// TODO Auto-generated constructor stub
	}
	
	public int base_offset = 0;
	DataTransfer dt;
	
	public void attach(byte[] data, int offset) {
		dt = new DataTransfer(data);
		dt.SetBase(offset);
		base_offset = offset;
		magic = new byte[4];
		System.arraycopy(data, offset, magic, 0, 4);
		version = new byte[4];
		System.arraycopy(data, offset + 4, version, 0, 4);
		checksum = dt.DataInt(8);
		signature = new byte[20];
		System.arraycopy(data, offset + 12, signature, 0, 20);
		file_size = dt.DataInt(32);
		header_size = dt.DataInt(36);
		endian_tag = dt.DataInt(40);
		link_size = dt.DataInt(44);
		link_off = dt.DataInt(48);
		map_off = dt.DataInt(52);
		string_ids_size = dt.DataInt(56);
		string_ids_off = dt.DataInt(60);
		type_ids_size = dt.DataInt(64);
		type_ids_off = dt.DataInt(68);
		proto_ids_size = dt.DataInt(72);
		proto_ids_off = dt.DataInt(76);
		field_ids_size = dt.DataInt(80);
		field_ids_off = dt.DataInt(84);
		method_ids_size = dt.DataInt(88);
		method_ids_off = dt.DataInt(92);
		class_defs_size = dt.DataInt(96);
		class_defs_off = dt.DataInt(100);
		data_size = dt.DataInt(104);
		data_off = dt.DataInt(108);
	}
	
	public String getString(int index) {
		int string_off = dt.DataInt(string_ids_off + index * 4);
		int string_length = Uleb128.Uleb2long(dt.m_byData, base_offset + string_off);
		string_off += Uleb128.UlebLength(dt.m_byData, base_offset + string_off);
		byte[] str_utf8 = new byte[string_length];
		System.arraycopy(dt.m_byData, base_offset + string_off, str_utf8, 0, string_length);
		String result = new String(str_utf8);
		return result;
	}
	
	public String getType(int index) {
		int type_string = dt.DataInt(this.type_ids_off + index * 4);
		String result = getString(type_string);
		if (result.startsWith("L") && result.endsWith(";")) {
			result = result.substring(1, result.length()-1);
			result = result.replaceAll("/", ".");
		} else if (result.equals("Z")) {
			result = "Boolean";
		} else if (result.equals("V")) {
			result = "Void";
		} else if (result.equals("I")) {
			result = "int";
		} else if (result.equals("J")) {
			result = "long";
		} else if (result.equals("B")) {
			result = "byte";
		} else if (result.equals("C")) {
			result = "char";
		} else if (result.equals("D")) {
			result = "double";
		} else if (result.equals("F")) {
			result = "float";
		}
		return result;
	}
	
	public String getClass(int index) {
		int type = dex_classdef_[index].class_idx;
		String result = getType(type);
		return result;
	}
	
	public String getProt(int index) {
		return "";
	}
	
	public String getMethd(int index) {
		int prot_ids = dt.DataShort(this.method_ids_off + index * 8 + 2);
		int med_string = dt.DataInt(this.method_ids_off + index * 8 + 4);
		int prot_array = dt.DataInt(this.proto_ids_off + prot_ids * 12 + 8);
		String param = "";
		if (prot_array != 0) {
			int prot_size = dt.DataInt(prot_array);
			int i = 0;
			for (i=0; i<prot_size; ++i) {
				int param_item = dt.DataShort(prot_array + 4 + 2 * i);
				param += getType(param_item);
				if (i != prot_size-1) 
					param += ",";
			}
		}
		String result = getString(med_string) + "(" + param +")";
		return result;
	}
	

	public byte[] magic;
	public byte[] version;
	public int checksum;
	public byte[] signature;
	public int file_size;
	public int header_size;
	public int endian_tag;
	public int link_size;
	public int link_off;
	public int map_off;
	public int string_ids_size;
	public int string_ids_off;
	public int type_ids_size;
	public int type_ids_off;
	public int proto_ids_size;
	public int proto_ids_off;
	public int field_ids_size;
	public int field_ids_off;
	public int method_ids_size;
	public int method_ids_off;
	public int class_defs_size;
	public int class_defs_off;
	public int data_size;
	public int data_off;
	
	public dex_classdef []dex_classdef_;
}
