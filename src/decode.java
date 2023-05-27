package src;

import java.util.ArrayList;

public class decode {
    public static execute execute;
    public static int[] registerFile;
    private static ArrayList<String> result;
    public static int clock;
    public static String ex;
    public void initialise(){
        result=new ArrayList<String>();
    }
    public static void decode(String s){
        String opcode=s.substring(0, 4);
        String Rd=s.substring(4, 9);
        String Rs=s.substring(9, 14);
        String Rt=s.substring(14, 19);
        String Shamt=s.substring(19, 32); 
        String immediate=s.substring(14, 32);
        String address=s.substring(4, 32);
       result= new ArrayList<String>();
        result.add(opcode);
       int t,t1,t2;
        switch(opcode){
           case "0000":
           case "0001":
                       if(!Rs.equals("0zer0")){
                        t=Integer.parseInt(Rs,2);
                       
                       
                       
                           result.add(""+(registerFile[t-1]));
                       
                       }
                       else{
                           result.add("0");
                       }
                       if(!Rt.equals("0zer0")){
                        t1=Integer.parseInt(Rt,2);
                       
                           
                           result.add(""+(registerFile[t1-1]));
                       
                       }
                       else{
                           result.add("0");
                       }
                       if(!Rd.equals("0zer0")){
                        t2=Integer.parseInt(Rd,2);
                       result.add(""+t2);
                       }
                       else{
                           result.add("zero");
                       }
                       break;
           case "0010":
           case "0011":
           case "0101":
           case "0110":
           case "1010":
           case "1011":
                       if(!Rs.equals("0zer0")){
                        t=Integer.parseInt(Rs,2);
                       
                           result.add(""+(registerFile[t]));
                       
                   }
                   else{
                       result.add("0");
                   }
                       
                        t1=Integer.parseInt(immediate,2);
                       result.add(""+t1);
                       if(!Rd.equals("0zer0")){
                       t2=Integer.parseInt(Rd,2);
                       result.add(""+t2);	
                       }
                       else{
                           result.add("zero");
                       }
                       break;
                       
           case "0111":int j=Integer.parseInt(address,2);
                       result.add(""+j);
                       break;
           case "1000":
           case "1001":
                       if(!Rs.equals("0zer0")){
                       t=Integer.parseInt(Rs,2);
                       
                           result.add(""+(registerFile[t-1]));
                       
                   }
                   else{
                       result.add("0");
                   }
                        t1=Integer.parseInt(Shamt,2);
                           result.add(""+t1);

                           if(!Rd.equals("0zer0")){
                       t2=Integer.parseInt(Rd,2);
                       result.add(""+t2);	
                       }
                       else{
                           result.add("zero");
                       }
                       break;
           case "0100":
                   if(!Rs.equals("0zer0")){
                       t=Integer.parseInt(Rs,2);
                       
                           result.add(""+(registerFile[t-1]));
                       
                   }
                   else{
                       result.add("0");
                   }
                       int i1=Integer.parseInt(immediate,2);
                       result.add(""+i1);
                       if(!Rd.equals("0zer0")){
                           t2=Integer.parseInt(Rd,2);
                           result.add(""+t2);	
                           }
                           else{
                               result.add("zero");
                           }
                           break;
                       
        }
        execute.ex=ex;
        
        execute.clock=clock+2;
        
        execute.execute(result);
   }
}
