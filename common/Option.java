package common;

import java.util.HashSet;

public class Option {
	public static int fakedebuginfo = 0;
	public static int showinfo = 0;
	
	public static int scandex = 0;
	public static int injectlib = 0;
	public static int check = 0;
	public static int resizeheader = 0;
	public static int headerappend = 0;
	
	public static int replacestring = 0;
	public static String replacenamesrc = "";
	public static String replacenametar = "";
	
	public static int hackmethod = 0;
	public static String hackmethodname = "";
	public static int AgainstDex2jar = 0;
	public static String AgainstDex2jarMethodName = "";
	public static int AgainstDex2jarMethodId1 = -1;
	public static int AgainstDex2jarMethodId2 = -1;
	
	public static int injectlibs = 0;
	public static HashSet<String> injectliblist = new HashSet<String>();
	
	public static int getqqemethod = 0;
	
	
	public static int tmpcount = 0;
	
	
	public static int tongfudun = 0;
	
	public static int last_code_offset = -1;
}
