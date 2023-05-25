package src;
import java.io.*;
import java.util.ArrayList;

import javax.lang.model.util.ElementScanner14;

public class fetch {
    public static decode decode;
    public static String[] memory;
    public static int pc;
    public void setDecode(decode decode) {
        this.decode = decode;
    }
    public static void fetch() {
		String instruction = memory[pc];
		if (pc < 1023) {
			pc++;
		} else {
			pc = 0;
		}
        decode.decode(instruction);
	}
    

}