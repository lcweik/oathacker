package elf32;

public class memmap {

	public memmap(int foff, int vaddr, int fsize, int vsize) {
		super();
		this.foff = foff;
		this.vaddr = vaddr;
		this.fsize = fsize;
		this.vsize = vsize;
	}

	public int offset2addr(int off) {
		if (off >= this.foff && off < this.foff + this.fsize) {
			return off - this.foff + this.vaddr;
		}
		else {
			return -1;
		}
	}
	
	public int addr2offset(int addr) {
		if (addr >= this.vaddr && addr < this.vaddr + this.vsize) {
			return addr - this.vaddr + this.foff;
		}
		else {
			return -1;
		}
	}

	public int foff;
	public int vaddr;
	public int fsize;
	public int vsize;

}
