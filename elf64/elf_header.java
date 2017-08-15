package elf64;
import common.*;

public class elf_header {

	public elf_header() {
		// TODO Auto-generated constructor stub
	}

	public void attach(byte[] data) {
		e_ident = new byte[16];
		System.arraycopy(data, 0, e_ident, 0, 16);
		DataTransfer dt = new DataTransfer(data);
		e_type = dt.DataInt(16);
		e_machine = dt.DataInt(18);
		e_version = dt.DataInt(20);
		e_entry = dt.DataQuad(24);
		e_phoff = dt.DataQuad(32);
		e_shoff = dt.DataQuad(40);
		e_flags = dt.DataInt(48);
		e_ehsize = dt.DataShort(52);
		e_phentsize = dt.DataShort(54);
		e_phnum = dt.DataShort(56);
		e_shentsize = dt.DataShort(58);
		e_shnum = dt.DataShort(60);
		e_shstrndx = dt.DataShort(62);
		
		elf_info.cur_elf = this;
		
		programs = new program[e_phnum];
		int i = 0;
		int offset = (int)e_phoff;
		
		for (i=0; i<e_phnum; ++i) {
			programs[i] = new program();
			programs[i].attach(data, offset);
			offset += e_phentsize;
		}
		
		sections = new section[e_shnum];
		offset = (int)e_shoff;
		for (i=0; i<e_shnum; ++i) {
			sections[i] = new section();
			sections[i].attach(data, offset);
			offset += e_shentsize;
		}
	}
	
	public program[] programs;
	public section[] sections;
	
	public byte[] e_ident;
	public int e_type;
	public int e_machine;
	public int e_version;
	public long e_entry;
	public long e_phoff;
	public long e_shoff;
	public int e_flags;
	public short e_ehsize;
	public short e_phentsize;
	public short e_phnum;
	public short e_shentsize;
	public short e_shnum;
	public short e_shstrndx;

}
