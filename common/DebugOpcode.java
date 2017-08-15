package common;



public class DebugOpcode {
	
	public int opcode_size = 0;
	public int[] opcode_param = new int[10];
	public int opcode_param_num = 0;
	public int opcode = -1;
	
	public DebugOpcode() {
		
	}
	
	public int SetOpcode(byte[] byData, int offset) {
		DataTransfer dt = new DataTransfer(byData);
		dt.SetBase(offset);
		opcode = dt.DataByte(0);
		opcode_size = 1;
		
		int ps = 0;
		
		switch (opcode) {
        	case DebugItemType.END_SEQUENCE: {
        		break;
        	}
        	case DebugItemType.ADVANCE_PC: {
        		opcode_param_num = 1;
        		
        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        		break;
        	}
        	case DebugItemType.ADVANCE_LINE: {
        		opcode_param_num = 1;
        		
        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        		break;
        	}
        	case DebugItemType.START_LOCAL: {
        		opcode_param_num = 3;

        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;

        		opcode_param[1] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;

        		opcode_param[2] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        		break;
        	}
        	case DebugItemType.START_LOCAL_EXTENDED: {
        		opcode_param_num = 4;
        		
        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;

        		opcode_param[1] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;

        		opcode_param[2] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;

        		opcode_param[3] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        		
        		break;
        	}
        	case DebugItemType.END_LOCAL: {
        		opcode_param_num = 1;
        		
        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        	
        		break;
        	}
        	case DebugItemType.RESTART_LOCAL: {
        		opcode_param_num = 1;
        		
        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        	
        		break;
        	}
        	case DebugItemType.PROLOGUE_END: {
        		break;
        	}
        	case DebugItemType.EPILOGUE_BEGIN: {
        		break;
        	}
        	case DebugItemType.SET_SOURCE_FILE: {
        		opcode_param_num = 1;
        		
        		opcode_param[0] = Uleb128.Uleb2long(byData, offset);
        		ps = (int) Uleb128.UlebLength(byData, offset);
        		offset += ps;
        		opcode_size += ps;
        	
        		break;
        	}
        	default: {
        		break;
        	}
		}
		return opcode_size;
	}
}
