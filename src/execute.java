package src;

import java.util.ArrayList;

public class execute {
    public static Memory memory;
    public static int pc;
	public static int result;
	public static int clock;
	public static String ex;
	public void initialise(){
        result=0;
    }
    public static void execute( ArrayList<String> s){
		result=-1;
		int r1;
		int r2;
		int x;
		int imm;
		switch(s.get(0)){
			case "0000"://add
						r1=Integer.parseInt(s.get(1));
						r2=Integer.parseInt(s.get(2));
						x=r1+r2;
						
						result=x;
						break;
			case "0001"://sub
						r1=Integer.parseInt(s.get(1));
						r2=Integer.parseInt(s.get(2));
						x=r1-r2;
						
						result=x;
						break;
						
			case "0010": //muli
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1*imm;
						
						result=x;
						break;
						
			case "0011"://addi
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1+imm;
						
						result=x;
						break;		
			case "0100"://bne
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(3));
						if (r1!=imm){
							pc=pc+1+Integer.parseInt(s.get(3));
							result=1;
						}
						else{
							result =0;
						}
						break;
			case "0101"://andi
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1&imm;
						
						result=x;
						break;	
			case "0110"://ori
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1|imm;
						
						result=x;
						break;
			case "0111"://jump most probs faty
						String d= Integer.toBinaryString(pc);
						if(d.length()>=31)
							d=d.substring(28, 31);
						
						pc=(Integer.parseInt(d,2))|(Integer.parseInt(s.get(1)));
					
						result=0;
						break;
			case "1010"://LW
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1+imm;
						
						result=x;
						break;	
			case "1011"://SW
						r1=Integer.parseInt(s.get(1)); // rs 
						imm=Integer.parseInt(s.get(2));
						x=r1+imm;
						
						result=x;
						break;	
			case "1000"://SLL
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1<<imm;
						
						result=x;
						break;	
			case "1001"://SRL
						r1=Integer.parseInt(s.get(1));
						imm=Integer.parseInt(s.get(2));
						x=r1>>>imm;
						
						result=x;
						break;	
			
						
						
		 }
		 memory.ex=ex;
		
		 memory.clock=clock+2;
		
		 memory.memory(result,s);

	}
}
