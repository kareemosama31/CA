package src;

import java.util.ArrayList;

public class Memory {
    public static writeBack writeBack;
    public static String[] memory;
    public static int[] registerFile;
    public static String res;
    public static int clock;
    public static String ex;
    public void initialise(){
        res=null;
    }
    public static void memory(int result,ArrayList<String> s){
        if(clock%2!=0){
            clock++;
        }
		String res=null;
        int r2;
        int x;
        int imm;
		
        switch(s.get(0)){
            case "1010"://LW
                        
                        res=memory[1024+result];
						
                        break;
            case "1011"://SW
						if(!s.get(3).equals("0zer0")){
                        int r=registerFile[Integer.parseInt(s.get(3))];
						memory[1024+result]=r+"";
                        res=null;
						}
						
						
                        break;
           



         }
         writeBack.ex=ex;
         
         writeBack.clock=++clock;
         
         writeBack.writeBack(res,s);
        
        
    }
}
