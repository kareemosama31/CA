package src;

import java.util.ArrayList;

public class Memory {
    public static writeBack writeBack;
    public static String[] memory;
    public static int[] registerFile;
    public static void memory(int result,ArrayList<String> s){
		String res=null;
        int r2;
        int x;
        int imm;
		System.out.println(s.get(0));
        switch(s.get(0)){
            case "1010"://LW
                        
                        res=memory[result];
						System.out.println(res+"zzzzz");
                        break;
            case "1011"://SW
						if(!s.get(3).equals("0zer0")){
                        int r=registerFile[Integer.parseInt(s.get(3))];
						memory[result]=r+"";
						}
						
						
                        break;
            default:
			       writeBack.writeBack(result+"",s);



         }
         writeBack.writeBack(res,s);
    }
}
