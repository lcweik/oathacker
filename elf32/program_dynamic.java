package elf32;

import common.DataTransfer;

public class program_dynamic extends program_data {

	public program_dynamic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	void attach(byte[] data, program header) {
		// TODO Auto-generated method stub
		super.attach(data, header);
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(header.p_offset);
		int dynamic_size = header.p_filesz / 8;
		int i = 0;
		for (i=0; i<dynamic_size; ++i) {
			int type = dt.DataInt(i*8);
			int value = dt.DataInt(i*8 + 4);
			if (type == dt_hash) {
				hash = value;
			} else if (type == dt_strtab) {
				strtab = value;
			} else if (type == dt_symtab) {
				symtab = value;
			} else if (type == dt_strsz) {
				strsz = value;
			} else if (type == dt_syment) {
				syment = value;
			} else if (type == dt_soname) {
				soname = value;
			}
		}
		
		strtab_buf = new byte[strsz];
		System.arraycopy(data, elf_info.addr2offset(strtab), strtab_buf, 0, strsz);
		int symtaboff = elf_info.addr2offset(symtab);
		symtabs = new sym[4];
		
		for (i=0; i<4; ++i) {
			symtabs[i] = new sym();
			symtabs[i].attach(data, symtaboff + i * 16);
		}
		
		
	}

	static int dt_hash = 4;
	static int dt_strtab = 5;
	static int dt_symtab = 6;
	static int dt_strsz = 10;
	static int dt_syment = 11;
	static int dt_soname = 14;
	
	public class sym {
		public void attach(byte[] data, int offset) {
			DataTransfer dt = new DataTransfer(data);
			dt.SetBase(offset);
			st_name = dt.DataInt(0);
			st_value = dt.DataInt(4);
			st_size = dt.DataInt(8);
			st_info = dt.DataByte(12);
			st_other = dt.DataByte(13);
			st_shndx = dt.DataShort(14);
		}
		
		public int st_name;
		public int st_value;
		public int st_size;
		public byte st_info;
		public byte st_other;
		public short st_shndx;
	}
	
	
	public byte[] strtab_buf;
	public sym[] symtabs;
	
	public int hash;
	public int strtab;
	public int symtab;
	public int strsz;
	public int syment;
	public int soname;
	
}
