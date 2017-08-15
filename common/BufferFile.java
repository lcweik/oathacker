package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferFile {
	public BufferFile() {
		
	}
	
	private File file;
	public long size;
	public byte[] buf;
	
	public BufferFile AttachFile(String filename) {
		file = new File(filename);
		size = file.length();
		buf = new byte[1024*1024*300];
		FileInputStream fs;
		try {
			fs = new FileInputStream(file);
			int really = fs.read(buf, 0, (int) size);
			fs.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
}
