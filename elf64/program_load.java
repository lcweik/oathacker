package elf64;

public class program_load extends program_data {

	public program_load() {
		// TODO Auto-generated constructor stub
	}

	@Override
	void attach(byte[] data, program header) {
		// TODO Auto-generated method stub
		super.attach(data, header);
		
		buf = new byte[(int) header.p_filesz];
		System.arraycopy(data, (int)header.p_offset, buf, 0, (int)header.p_filesz);
	}
	
	public byte[] buf;

}
