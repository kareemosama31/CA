package src;

import java.util.ArrayList;

public class writeBack {
    public static int[] registerFile;
    public static void writeBack(String result, ArrayList<String> s){
		if(result==null)
			return;
		else{
			if(!s.get(3).equals("0zer0"))
				registerFile[(Integer.parseInt(s.get(3)))]=Integer.parseInt(result);
		}
	}
}
