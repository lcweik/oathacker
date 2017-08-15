package elf64;

public class memmap {

	public memmap(long foff, long vaddr, long fsize, long vsize) {
		super();
		this.foff = foff;
		this.vaddr = vaddr;
		this.fsize = fsize;
		this.vsize = vsize;
	}

	public long offset2addr(long off) {
		if (off >= this.foff && off < this.foff + this.fsize) {
			return off - this.foff + this.vaddr;
		}
		else {
			return -1;
		}
	}
	
	public long addr2offset(long addr) {
		if (addr >= this.vaddr && addr < this.vaddr + this.vsize) {
			return addr - this.vaddr + this.foff;
		}
		else {
			return -1;
		}
	}

	public long foff;
	public long vaddr;
	public long fsize;
	public long vsize;

}
