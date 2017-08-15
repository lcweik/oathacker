package elf32;

import common.DataTransfer;

public class program {

	public program() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] data, int offset) {
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset);
		p_type = dt.DataInt(0);
		p_offset = dt.DataInt(4);
		p_vaddr = dt.DataInt(8);
		p_paddr = dt.DataInt(12);
		p_filesz = dt.DataInt(16);
		p_memsz = dt.DataInt(20);
		p_flags = dt.DataInt(24);
		p_align = dt.DataInt(28);
		
		mem = new memmap(p_offset, p_vaddr, p_filesz, p_memsz);
		
		if (p_type == 1) {
			this.data = new program_load();
			this.data.attach(data, this);
		} else if (p_type == 2) {
			this.data = new program_dynamic();
			this.data.attach(data, this);
		}
			
	}
	public program_data data = null; 
	public memmap mem = null;
	
	
	public int p_type;
	public int p_offset;
	public int p_vaddr;
	public int p_paddr;
	public int p_filesz;
	public int p_memsz;
	public int p_flags;
	public int p_align;

}
