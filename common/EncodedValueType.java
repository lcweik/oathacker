package common;


public final class EncodedValueType {
    public static final int BYTE = 0x00;
    public static final int SHORT = 0x02;
    public static final int CHAR = 0x03;
    public static final int INT = 0x04;
    public static final int LONG = 0x06;
    public static final int FLOAT = 0x10;
    public static final int DOUBLE = 0x11;
    public static final int STRING = 0x17;
    public static final int TYPE = 0x18;
    public static final int FIELD = 0x19;
    public static final int METHOD = 0x1a;
    public static final int ENUM = 0x1b;
    public static final int ARRAY = 0x1c;
    public static final int ANNOTATION = 0x1d;
    public static final int NULL = 0x1e;
    public static final int BOOLEAN = 0x1f;

    private EncodedValueType() {}
    public static int getSizeFromEncodedHeader(int header) {
    	header = header & 0xff;
    	int type = header & 0x1f;
    	int arg = header >> 5;
    	switch (type) {
    	case BYTE:
    	case SHORT:
    	case CHAR:
    	case INT:
    	case LONG:
    	case FLOAT:
    	case DOUBLE:
    		return arg + 1;
    	case STRING:
    	case TYPE:
    	case FIELD:
    	case METHOD:
    	case ENUM:
    		return arg + 1;
    	case ARRAY:
    		return -1;
    	case ANNOTATION:
    		return -2;
    	case NULL:
    		return 0;
    	case BOOLEAN:
    		return 0;
    	default:
    			
    	}
    	return -3;
    }
    
    public static int getSizeFromOffset(byte[] byData, int offset) {
    	int curoff = offset;
    	int header = byData[curoff];
    	int size = getSizeFromEncodedHeader(header);
    	if (size >=0)
    		return size + 1;
    	int i = 0;
    	curoff ++;
    	int count = Uleb128.Uleb2long(byData, curoff);
    	curoff += Uleb128.UlebLength(byData, curoff);

    	if (size == -1) {
    		for (i=0; i<count; ++i) {
    			int tsize = getSizeFromOffset(byData, curoff);
    			curoff += tsize;
    		}
    	}
    	else if (size == -2) {
    		for (i=0; i<count; ++i) {
    			curoff += Uleb128.UlebLength(byData, curoff);
    			curoff += getSizeFromOffset(byData, curoff);
    		}
    	}
    	
    	return curoff - offset;
    }
}
