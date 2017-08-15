package oat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import modify.killcode;

import search.find_oat_method;
import setting.option;
import common.*;
import dex.dex_classdef;
import dex.dex_header;
import dex.map_item;

public class oat_header {

	public oat_header() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] data, int offset) {
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset);
		magic = new byte[4];
		System.arraycopy(data, offset, magic, 0, 4);
		version = new byte[4];
		System.arraycopy(data, offset + 4, version, 0, 4);
		checksum = dt.DataInt(8);
		instruction_set = dt.DataInt(12);
		instruction_set_features = dt.DataInt(16);
		dex_file_count = dt.DataInt(20);
		executable_offset = dt.DataInt(24);
		data_buf_28byte = new byte[28];
		System.arraycopy(data, offset + 28, data_buf_28byte, 0, 28);
		image_patch_delta = dt.DataInt(56);
		oat_checksum = dt.DataInt(60);
		oat_data_begin = dt.DataInt(64);
		int oat45offset = 0;
		
		if (version[1] == '4')
			oat45offset = 12;
		key_value_store_size = dt.DataInt(68 + oat45offset);
		key_value_store = new byte[key_value_store_size];
		System.arraycopy(data, 72 + oat45offset, key_value_store, 0, key_value_store_size);
		
		oat_dex_files = new oat_dex_file[dex_file_count];
		dex_headers = new dex_header[dex_file_count];
		
		int dex_file_offset = offset + 72 + oat45offset + key_value_store_size;
		int i = 0;
		for (i=0; i<dex_file_count; ++i) {
			int method_count = 0;
			int compile_count = 0;
			oat_dex_files[i] = new oat_dex_file();
			oat_dex_files[i].attach(data, dex_file_offset);
			dex_headers[i] = new dex_header();
			int base_offset = offset + oat_dex_files[i].dex_file_offset;
			dex_headers[i].attach(data, offset + oat_dex_files[i].dex_file_offset);
			if (option.need_dump == 1) {
				System.out.println("dex" + i + ".dex=" + new String(oat_dex_files[i].dex_file_location_data));
				File fout = new File("dex" + i + ".dex");
				try {
					FileOutputStream fos = new FileOutputStream(fout);
					fos.write(data, offset + oat_dex_files[i].dex_file_offset, dex_headers[i].file_size);
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (option.need_redex == 1) {
				File fin = new File("dex" + i + ".dex");
				try {
					FileInputStream fis = new FileInputStream(fin);
					fis.read(data, offset + oat_dex_files[i].dex_file_offset, fis.available());
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if (option.killcode == 1) {
				String filename = new String(oat_dex_files[i].dex_file_location_data);
				if (i!=16)
					continue;
				if (inList(filename)) {
					System.out.println("skip dex file " + filename);
				}
				else {
					int item_count = dt.DataInt(base_offset - offset + dex_headers[i].map_off);
					int item = 0;
					for (item=0; item<item_count; ++item) {
						map_item mi = new map_item();
						mi.attach(data, base_offset + dex_headers[i].map_off + 4 + item * 12);
						if (mi.type == 0x2001) {
							killcode kc = new killcode();
							kc.attach(data, mi.offset + base_offset, mi.size);
						}
					}
				}
			}
			if (option.offset != 0 || option.verify != 0 || option.statistics != 0) {
				oat_dex_files[i].class_offs = new int[dex_headers[i].class_defs_size];
				oat_dex_files[i].oat_class_ = new oat_class[dex_headers[i].class_defs_size];
				dex_headers[i].dex_classdef_ = new dex_classdef[dex_headers[i].class_defs_size];
				int j = 0;
				for (j=0; j<dex_headers[i].class_defs_size; ++j) {
					if (version[1] == '7' || version[1] == '8') {
						int classes_offsets_ = dt.DataInt(dex_file_offset + 12
								+ oat_dex_files[i].dex_file_location_data.length - 4096);
						oat_dex_files[i].class_offs[j] = dt.DataInt(classes_offsets_ + 4 * j);
					} else {
						oat_dex_files[i].class_offs[j] = dt.DataInt(dex_file_offset + 12 + j * 4
								+ oat_dex_files[i].dex_file_location_data.length - 4096);
					}
					int class_def_off = dex_headers[i].class_defs_off + 32 * j;
					dex_classdef dcf = new dex_classdef();
					dcf.attach(data, class_def_off, offset + oat_dex_files[i].dex_file_offset);
					oat_class oc = new oat_class();
					int oc_offset = oat_dex_files[i].class_offs[j] + 4096;
					oc.attach(data, oc_offset, 
							dcf.class_data_.direct_methods_size + dcf.class_data_.virtual_methods_size);
					oat_dex_files[i].oat_class_[j] = oc;
					dex_headers[i].dex_classdef_[j] = dcf;
					if (oc.status != 8 && oc.status != 10 && option.verify != 0) {
						System.out.println("oat_dex_files happen " + i + " " + j + " " + oc.status);
					}
					if (option.statistics != 0) {
						method_count += dcf.class_data_.direct_methods_size + dcf.class_data_.virtual_methods_size;
						compile_count += oc.oat_method_off.length;
					}
				}
			}
			if (option.statistics != 0) {
				System.out.println("method info: " + method_count + " " + compile_count + " " + (method_count-compile_count));
			}
			if (version[1] == '7' || version[1] == '8') {
				dex_file_offset += oat_dex_files[i].size() + 8;
			} else {
				dex_file_offset += oat_dex_files[i].size() + dex_headers[i].class_defs_size * 4;
			}
		}

		for (i=0; i<dex_file_count && (option.offset != 0); ++i) {
			int j = 0;
			for (j=0; j<dex_headers[i].class_defs_size; ++j) {
				int k = 0;
				for (k=0; k<oat_dex_files[i].oat_class_[j].oat_method_off.length; ++k)
				if (oat_dex_files[i].oat_class_[j].oat_method_off[k] == option.search_result) {
					//System.out.println("option.search_result=" + option.search_result);
					find_oat_method.dump_result(dex_headers[i], j, k);
				}
				
			}
			
		}
	}
	
	public boolean inList(String filename) {
		int i = 0;
		if (!filename.contains("framework"))
			return true;
		for (i=0; i<option.white_list.size(); ++i) {
			if (filename.contains(option.white_list.get(i)))
				return true;
		}
		return false;
	}
	
	public oat_dex_file[] oat_dex_files;
	public dex_header[] dex_headers;
	
	
	
	public byte[] magic;
	public byte[] version;
	public int checksum;
	public int instruction_set;
	public int instruction_set_features;
	public int dex_file_count;
	public int executable_offset;
	public byte[] data_buf_28byte;
	public int image_patch_delta;
	public int oat_checksum;
	public int oat_data_begin;
	public int key_value_store_size;
	public byte[] key_value_store;
	
}
