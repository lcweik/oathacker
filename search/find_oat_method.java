package search;

import dex.*;

public class find_oat_method {

	public find_oat_method() {
		// TODO Auto-generated constructor stub
	}
	
	public static int offset = 0;

	public static int is_search_method() {
		return 0;
	}
	
	public static void dump_result(dex_header dex_, int class_, int method_) {
		String clz = dex_.getClass(class_);
		int medindex = dex_.dex_classdef_[class_].class_data_.dex_methods_[method_].mid;
		String med = dex_.getMethd(medindex);
		System.out.println(clz + "." + med);
		//dex_.dex_classdef_[i].class_data_;
	}

}
