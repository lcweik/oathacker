package elf32;

public class elf_info {

	public elf_info() {
		// TODO Auto-generated constructor stub
	}

	public static int addr2offset(int addr) {
		int i = 0;
		for (i=0; i<cur_elf.e_phnum; ++i) {
			int ret = cur_elf.programs[i].mem.addr2offset(addr);
			if (ret == -1) {
				continue;
			}
			else {
				return ret;
			}
		}
		return -1;
	}
	
	public static int offset2addr(int offset) {
		int i = 0;
		for (i=0; i<cur_elf.e_phnum; ++i) {
			int ret = cur_elf.programs[i].mem.offset2addr(offset);
			if (ret == -1) {
				continue;
			}
			else {
				return ret;
			}
		}
		return -1;
	}
	
	public static program dynamic_progarm() {
		int i = 0;
		for (i=0; i<cur_elf.e_phnum; ++i) {
			if (cur_elf.programs[i].p_type == 2) {
				return cur_elf.programs[i];
			}
		}
		return null;
	}
	
	public static int oatdata_offset() {
		int addr = ((program_dynamic)(dynamic_progarm().data)).symtabs[1].st_value;
		return addr2offset(addr);
	}
	
	public static int oatexec_offset() {
		int addr = ((program_dynamic)(dynamic_progarm().data)).symtabs[2].st_value;
		return addr2offset(addr);
	}
	
	public static int oatlastword_offset() {
		int addr = ((program_dynamic)(dynamic_progarm().data)).symtabs[3].st_value;
		return addr2offset(addr);
	}
	
	public static elf32.elf_header cur_elf;

}
