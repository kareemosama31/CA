import java.io.*;
import java.util.ArrayList;

public class Code {
	public static int[] registerFile = new int[31];
	public static String[] memory = new String[2048];//0-1023 instructions 1024-2047 data
	public static int pc = 0;
	public final static int zero = 0;
	public static File file;
	public static int startI=0;
	public static int startD=1024;


	public static void init() throws IOException {
		file = new File("assemblyInstructions.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String temp = br.readLine();
		while (temp != null) {

			String instruction = assemblytoBinary(temp);
			memory[startI]=instruction;
			startI++;
			temp = br.readLine();

		}
		br.close();
		for(int i=0;i<registerFile.length;i++){
			registerFile[i]=0;
		}
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

	public static String fetch() {
		String instruction = memory[pc];
		if (pc < 1023) {
			pc++;
		} else {
			pc = 0;
		}
		return instruction;
	}
	public static ArrayList<String> decode(String s){
		 String opcode=s.substring(0, 4);
		 String Rd=s.substring(4, 9);
		 String Rs=s.substring(9, 14);
		 String Rt=s.substring(14, 19);
		 String Shamt=s.substring(19, 32); 
		 String immediate=s.substring(14, 32);
		 String address=s.substring(4, 32);
		 ArrayList<String> result=new ArrayList<String>();
		 result.add(opcode);

		 switch(opcode){
			case "0000":
			case "0001":int t2=Integer.parseInt(Rd,2);
						result.add(""+t2);
						int t=Integer.parseInt(Rs,2);
						if (t==0){
							result.add("0");
						}
						else{
							result.add(""+(registerFile[t-1]));
						}
						int t1=Integer.parseInt(Rt,2);
						if (t1==0){
							result.add("0");
						}
						else{
							result.add(""+(registerFile[t1-1]));
						}
						
						break;
			case "0010":
			case "0011":
			case "0100":
			case "0101":
			case "0110":
			case "1010":
			case "1011":int i2=Integer.parseInt(Rd,2);
						result.add(""+i2);
						int i=Integer.parseInt(Rs,2);
						if (i==0){
							result.add("0");
						}
						else{
							result.add(""+(registerFile[i-1]));
						}
						int i1=Integer.parseInt(immediate,2);
							result.add(""+i1);
						break;
			case "0111":int j=Integer.parseInt(address,2);
						result.add(""+j);
						break;
			case "1000":
			case "1001":int s2=Integer.parseInt(Rd,2);
						result.add(""+s2);
						int s1=Integer.parseInt(Rs,2);
						if (s1==0){
							result.add("0");
						}
						else{
							result.add(""+(registerFile[s1-1]));
						}
						int s3=Integer.parseInt(Shamt,2);
							result.add(""+s3);
							break;
						
						
		 }

		return result;
	}

	public static void main(String[] args) {
		int i=222;
		String s=assemblytoBinary("SRL R1 R2 9222229");
		ArrayList<String> y=decode(s);
		System.out.println(y);

		
	}

}
