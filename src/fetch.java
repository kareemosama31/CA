package src;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.ElementScanner14;

public class fetch {
    public static String instruction;
    public static decode decode;
    public static String[] memory;
    public static int pc;
    public static int clock;
    public void setDecode(decode decode) {
        this.decode = decode;
    }
    public void initialise(){
        instruction=null;
    }
    public static void fetch() {
       
		instruction = memory[pc];
       
        
        if(instruction==null)
        return;
        System.out.println("no inputs -fetch-");
        System.out.println(instruction+" instruction -fetch-");
		if (pc < 1023) {
			pc++;
		} else {
			pc = 0;
		}
        decode.ex=instruction;
        System.out.println(clock+" -fetch-");
        decode.clock=++clock;
        
        
        decode.decode(instruction);
        
	}
    

}