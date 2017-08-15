package launcher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import setting.option;
import common.*;

public class main {

	public main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferFile bf = new BufferFile();
		String cmd = args[0];
		if (cmd.equals("dump")) {
			option.need_dump = 1;
		} else if (cmd.equals("redex")) {
			option.need_redex = 1;
			option.need_save = 1;
		} else if (cmd.equals("killcode")) {
			option.killcode = 1;
			option.need_save = 1;
		} else if (cmd.equals("offset")) {
			option.offset = Integer.valueOf(args[2], 16);
		} else if (cmd.equals("verify")) {
			option.verify = 1;
		} else if (cmd.equals("statistics")) {
			option.statistics = 1;
		}
		if (args.length == 4) {
			try {
				FileReader fr=new FileReader(args[3]);
				BufferedReader br=new BufferedReader(fr);
				String line;
				while ((line=br.readLine())!=null) {
					option.white_list.add(line);
				}
		        br.close();
		        fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bf.AttachFile(args[1]);
		DataTransfer dt = new DataTransfer(bf.buf);
		if (bf.buf[4] == 0x01) {
			elf32.elf_header eh = new elf32.elf_header();
			eh.attach(bf.buf);
			oat.oat_header oh = new oat.oat_header();
			oh.attach(bf.buf, (int) elf32.elf_info.oatdata_offset());
		}
		else if (bf.buf[4] == 0x02){
			elf64.elf_header eh = new elf64.elf_header();
			eh.attach(bf.buf);
			oat.oat_header oh = new oat.oat_header();
			oh.attach(bf.buf, (int) elf64.elf_info.oatdata_offset());
		}
		if (option.need_save == 1) {
			try {
				File saveFile = new File(args[2]);
				FileOutputStream saveStream = new FileOutputStream(saveFile);
				saveStream.write(bf.buf, 0, (int) bf.size);
				saveStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
