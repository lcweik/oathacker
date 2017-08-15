package elf64;

import common.DataTransfer;

public class program {

	public program() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] data, int offset) {
		DataTransfer dt = new DataTransfer(data);
		dt.SetBase(offset);
		p_type = dt.DataInt(0);
		p_flags = dt.DataInt(4);
		p_offset = dt.DataQuad(8);
		p_vaddr = dt.DataQuad(16);
		p_paddr = dt.DataQuad(24);
		p_filesz = dt.DataQuad(32);
		p_memsz = dt.DataQuad(40);
		p_align = dt.DataQuad(48);
		
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
	public int p_flags;
	public long p_offset;
	public long p_vaddr;
	public long p_paddr;
	public long p_filesz;
	public long p_memsz;
	public long p_align;

}
