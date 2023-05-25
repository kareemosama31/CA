package src;
import java.io.*;
import java.util.ArrayList;

import javax.lang.model.util.ElementScanner14;

public class Code {
	public static int[] registerFile = new int[31];
	public static String[] memory = new String[2048];//0-1023 instructions 1024-2047 data
	public static int pc = 0; //pc register
	public final static int zero = 0; //zero reg
	public static File file;
	public static int startI=0;
	public static int startD=1024;
	static  int Inst=0;
	static  int clock=0;
	public static fetch fetch;
    public static decode decode;
    public static execute execute;
    public static Memory Memory;
    public static writeBack writeBack;

	

	public static void init() throws IOException {
		file = new File("assemblyInstructions.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String temp = br.readLine();
		while (temp != null) {
			Inst++;
			String instruction = assemblytoBinary(temp);
			memory[startI]=instruction;
			startI++;
			temp = br.readLine();

		}
		br.close();
		for(int i=0;i<registerFile.length;i++){
			registerFile[i]=0;
		}
		fetch= new fetch();
		decode=new decode();
		execute=new execute();
		Memory=new Memory();
		writeBack=new writeBack();
		fetch.pc=pc;
		fetch.memory=memory;
		decode.registerFile=registerFile;
		execute.pc=pc;
		Memory.memory=memory;
		Memory.registerFile=registerFile;
		writeBack.registerFile=registerFile;
		fetch.decode=decode;
		decode.execute=execute;
		execute.memory=Memory;
		Memory.writeBack=writeBack;
	}
	public static void clockCal(){
		clock=7+((Inst-1)*2);
	}


	public static String assemblytoBinary(String line) {

		String[] splitted = line.split(" ");
		int opcode;
		String reg1;
		int imm;
		String reg2;
		String reg3;
		int shamt;
		int address;
		String type;
		String instruction="";
		String newImm="";
		switch (splitted[0]) {// r--> opcode r1 r2 r3 shamt
		// i--> opcode r1 r2 imm
		// j --> opcode address;
		case "ADD":
			opcode = 0;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			reg3 = getRegNumber(splitted[3]);
			type ="RR";
			instruction = ("000" + Integer.toBinaryString(opcode))+ (reg1) + (reg2) + (reg3)+"0000000000000";
			break;
		case "SUB":
			opcode = 1;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			reg3 = getRegNumber(splitted[3]);
			type= "RR";
			instruction = ("000" + Integer.toBinaryString(opcode)) + (reg1) + (reg2) + (reg3)+"0000000000000";
			break;
		case "MULI":
			opcode = 2;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			type= "I";
			instruction = ("00" + Integer.toBinaryString(opcode)) + (reg1) + (reg2) + newImm;
			break;
		case "ADDI":
			opcode = 3;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			type= "I";
			instruction = ("00" + Integer.toBinaryString(opcode)) + (reg1)
					+ (reg2) + (newImm);
			break;
		case "BNE":
			opcode = 4;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			instruction = ("0" + Integer.toBinaryString(opcode)) + (reg1)
					+ (reg2) +(newImm);
			break;
		case "ANDI":
			opcode = 5;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			type= "I";
			instruction = ("0" + Integer.toBinaryString(opcode)+(reg1)+(reg2)+(newImm));
			break;
		case "ORI":
			opcode = 6;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			instruction = ("0" + Integer.toBinaryString(opcode)+(reg1)+(reg2)+(newImm));
			break;
		case "J":
			opcode = 7;
			address = Integer.parseInt(splitted[1]);
			type= "J";
			if(address>268435455){
				newImm=Integer.toBinaryString(address).substring(0,28);
			}
			else{
				int a=28-Integer.toBinaryString(address).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(address);
				System.out.println(newImm.length());
			}
			instruction = ("0" + Integer.toBinaryString(opcode)+ (newImm));//32?
			System.out.println(instruction+"habiba");
			break;
		case "SLL":
			opcode = 8;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			shamt = Integer.parseInt(splitted[3]);
			type= "RS";
			if(shamt>8191){
				newImm=Integer.toBinaryString(shamt).substring(0,13);
			}
			else{
				int a=13-Integer.toBinaryString(shamt).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(shamt);
				System.out.println(newImm.length());
			}
			instruction = ( Integer.toBinaryString(opcode)+(reg1)+(reg2)+"00000"+newImm);
			break;
		case "SRL":
			opcode = 9;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			shamt = Integer.parseInt(splitted[3]);
			type= "RS";
			if(shamt>8191){
				newImm=Integer.toBinaryString(shamt).substring(0,13);
			}
			else{
				int a=13-Integer.toBinaryString(shamt).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(shamt);
				System.out.println(newImm.length());
			}
			instruction = ( Integer.toBinaryString(opcode)+(reg1)+(reg2)+"00000"+newImm);
			break;
		case "LW":
			opcode = 10;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			instruction = ( Integer.toBinaryString(opcode)+(reg1)+(reg2)+(newImm));

			break;

		case "SW":
			opcode = 11;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			if(imm>262143){
				newImm=Integer.toBinaryString(imm).substring(0,18);
			}
			else{
				int a=18-Integer.toBinaryString(imm).length();
				for(int i=0;i<a;i++){
					newImm+="0";
				}
				newImm+=Integer.toBinaryString(imm);
				System.out.println(newImm.length());
			}
			instruction = (Integer.toBinaryString(opcode)+(reg1)+(reg2)+(newImm));
			break;
		}
		
		return instruction;

	}

	private static String getRegNumber(String string) {
		String register;
		switch (string) {
		case "R0":
			register = "0zer0";
			break;
		case "R1":
			register = "00000";
			break;
		case "R2":
			register = "00001";
			break;
		case "R3":
			register = "00010";
			break;
		case "R4":
			register = "00011";
			break;
		case "R5":
			register = "00100";
			break;
		case "R6":
			register = "00101";
			break;
		case "R7":
			register = "00110";
			break;
		case "R8":
			register = "00111";
			break;
		case "R9":
			register = "01000";
			break;
		case "R10":
			register = "01001";
			break;
		case "R11":
			register = "01010";
			break;
		case "R12":
			register = "01011";
			break;
		case "R13":
			register = "01100";
			break;
		case "R14":
			register = "01101";
			break;
		case "R15":
			register = "01110";
			break;
		case "R16":
			register = "01111";
			break;
		case "R17":
			register = "10000";
			break;
		case "R18":
			register = "10001";
			break;
		case "R19":
			register = "10010";
			break;
		case "R20":
			register = "10011";
			break;
		case "R21":
			register = "10100";
			break;
		case "R22":
			register = "10101";
			break;
		case "R23":
			register = "10110";
			break;
		case "R24":
			register = "10111";
			break;
		case "R25":
			register = "11000";
			break;
		case "R26":
			register = "11001";
			break;
		case "R27":
			register = "11010";
			break;
		case "R28":
			register = "11011";
			break;
		case "R29":
			register = "11100";
			break;
		case "R30":
			register = "11101";
			break;
		case "R31":
			register = "11110";
			break;
		case "R32":
			register = "11111";
			break;
		default:
			register = "-1111";
		}
		return register;
	}
	

	// public static String fetch() {
	// 	String instruction = memory[pc];
	// 	if (pc < 1023) {
	// 		pc++;
	// 	} else {
	// 		pc = 0;
	// 	}
	// 	return instruction;
	// }
	// public static ArrayList<String> decode(String s){
	// 	 String opcode=s.substring(0, 4);
	// 	 String Rd=s.substring(4, 9);
	// 	 String Rs=s.substring(9, 14);
	// 	 String Rt=s.substring(14, 19);
	// 	 String Shamt=s.substring(19, 32); 
	// 	 String immediate=s.substring(14, 32);
	// 	 String address=s.substring(4, 32);
	// 	 ArrayList<String> result=new ArrayList<String>();
	// 	 result.add(opcode);
	// 	int t,t1,t2;
	// 	 switch(opcode){
	// 		case "0000":
	// 		case "0001":
	// 					if(!Rs.equals("0zer0")){
	// 					 t=Integer.parseInt(Rs,2);
						
						
						
	// 						result.add(""+(registerFile[t-1]));
						
	// 					}
	// 					else{
	// 						result.add("0");
	// 					}
	// 					if(!Rt.equals("0zer0")){
	// 					 t1=Integer.parseInt(Rt,2);
						
							
	// 						result.add(""+(registerFile[t1-1]));
						
	// 					}
	// 					else{
	// 						result.add("0");
	// 					}
	// 					if(!Rd.equals("0zer0")){
	// 					 t2=Integer.parseInt(Rd,2);
	// 					result.add(""+t2);
	// 					}
	// 					else{
	// 						result.add("zero");
	// 					}
	// 					break;
	// 		case "0010":
	// 		case "0011":
	// 		case "0101":
	// 		case "0110":
	// 		case "1010":
	// 		case "1011":
	// 					if(!Rs.equals("0zer0")){
	// 					 t=Integer.parseInt(Rs,2);
						
	// 						result.add(""+(registerFile[t]));
						
	// 				}
	// 				else{
	// 					result.add("0");
	// 				}
						
	// 					 t1=Integer.parseInt(immediate,2);
	// 					result.add(""+t1);
	// 					if(!Rd.equals("0zer0")){
	// 				    t2=Integer.parseInt(Rd,2);
	// 					result.add(""+t2);	
	// 					}
	// 					else{
	// 						result.add("zero");
	// 					}
	// 					break;
						
	// 		case "0111":int j=Integer.parseInt(address,2);
	// 					result.add(""+j);
	// 					break;
	// 		case "1000":
	// 		case "1001":
	// 					if(!Rs.equals("0zer0")){
	// 					t=Integer.parseInt(Rs,2);
						
	// 						result.add(""+(registerFile[t-1]));
						
	// 				}
	// 				else{
	// 					result.add("0");
	// 				}
	// 					 t1=Integer.parseInt(Shamt,2);
	// 						result.add(""+t1);

	// 						if(!Rd.equals("0zer0")){
	// 				    t2=Integer.parseInt(Rd,2);
	// 					result.add(""+t2);	
	// 					}
	// 					else{
	// 						result.add("zero");
	// 					}
	// 					break;
	// 		case "0100":
	// 				if(!Rs.equals("0zer0")){
	// 					t=Integer.parseInt(Rs,2);
						
	// 						result.add(""+(registerFile[t-1]));
						
	// 				}
	// 				else{
	// 					result.add("0");
	// 				}
	// 					int i1=Integer.parseInt(immediate,2);
	// 					result.add(""+i1);
	// 					if(!Rd.equals("0zer0")){
	// 						t2=Integer.parseInt(Rd,2);
	// 						result.add(""+t2);	
	// 						}
	// 						else{
	// 							result.add("zero");
	// 						}
	// 						break;
						
	// 	 }

	// 	return result;
	// }
	// public static int execute( ArrayList<String> s){
	// 	int result=-1;
	// 	int r1;
	// 	int r2;
	// 	int x;
	// 	int imm;
	// 	switch(s.get(0)){
	// 		case "0000"://add
	// 					r1=Integer.parseInt(s.get(1));
	// 					r2=Integer.parseInt(s.get(2));
	// 					x=r1+r2;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;
	// 		case "0001"://sub
	// 					r1=Integer.parseInt(s.get(1));
	// 					r2=Integer.parseInt(s.get(2));
	// 					x=r1-r2;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;
						
	// 		case "0010": //muli
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1*imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;
						
	// 		case "0011"://addi
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1+imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;		
	// 		case "0100"://bne
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(3));
	// 					if (r1!=imm){
	// 						pc=pc+1+Integer.parseInt(s.get(3));
	// 						result=1;
	// 					}
	// 					else{
	// 						result =0;
	// 					}
	// 					break;
	// 		case "0101"://andi
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1&imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;	
	// 		case "0110"://ori
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1|imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;
	// 		case "0111"://jump most probs faty
	// 					String d= Integer.toBinaryString(pc);
	// 					if(d.length()>=31)
	// 						d=d.substring(28, 31);
	// 					System.out.println("pc"+pc);
	// 					pc=(Integer.parseInt(d,2))|(Integer.parseInt(s.get(1)));
	// 					System.out.println(pc+"pc");
	// 					result=0;
	// 					break;
	// 		case "1010"://LW
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1+imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;	
	// 		case "1011"://SW
	// 					r1=Integer.parseInt(s.get(1)); // rs 
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1+imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;	
	// 		case "1000"://SLL
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1<<imm;
	// 					System.out.println(x+s.get(3)+"pewwwwww");
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;	
	// 		case "1001"://SRL
	// 					r1=Integer.parseInt(s.get(1));
	// 					imm=Integer.parseInt(s.get(2));
	// 					x=r1>>>imm;
	// 					//registerFile[Integer.parseInt(s.get(3))]=x;
	// 					result=x;
	// 					break;	
			
						
						
	// 	 }
	// 	 return result;

	// }
	// public static String Memory(int result,ArrayList<String> s){
	// 	String res=null;
    //     int r2;
    //     int x;
    //     int imm;
	// 	System.out.println(s.get(0));
    //     switch(s.get(0)){
    //         case "1010"://LW
                        
    //                     res=memory[result];
	// 					System.out.println(res+"zzzzz");
    //                     break;
    //         case "1011"://SW
	// 					if(!s.get(3).equals("0zer0")){
    //                     int r=registerFile[Integer.parseInt(s.get(3))];
	// 					memory[result]=r+"";
	// 					}
						
						
    //                     break;
    //         default:
	// 		       return result+"";



    //      }
    //      return res;
    // }

	public static void writeBack(String result, ArrayList<String> s){
		if(result==null)
			return;
		else{
			if(!s.get(3).equals("0zer0"))
				registerFile[(Integer.parseInt(s.get(3)))]=Integer.parseInt(result);
		}
	}
	

	public static void main(String[] args) {
		int i=222;
		memory[7]="5";
		registerFile[1]=8;
		registerFile[0]=7000;
		String s=assemblytoBinary("ADDI R2 R1 2000");
		//String a=assemblytoBinary("SLL R2 R1 2");
		ArrayList<String> y=decode(s);
		// ArrayList<String> x=decode(a);
		int result=execute(y);
		// int result2=execute(x);
		String x=Memory(result, y);
		writeBack(x, y);
		for(int j=0;j<registerFile.length;j++){
			System.out.println(registerFile[j]);
		}

		
	}

}
