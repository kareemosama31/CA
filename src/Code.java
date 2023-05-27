package src;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
	public static ArrayList<String> inst= new ArrayList<String>() ;
		
	
	

	public static void init() throws IOException {
		file = new File("testFile.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String temp = br.readLine();
		while (temp != null) {
			
			Inst++;
			String instruction = assemblytoBinary(temp);
			
			inst.add(instruction);
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
		writeBack.inst=inst;
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
				
			}
			instruction = ("0" + Integer.toBinaryString(opcode)+ (newImm));
			
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
	
	public static void run(ArrayList<String> instructions) {
		fetch.initialise();
		decode.initialise();
		execute.initialise();
		Memory.initialise();
		
		clockCal();
		for(int i=1;i<=clock;i++){
			if(i%2!=0){
				fetch.clock=i;
				fetch.fetch();
				
			}
			if (i== clock){
				System.out.println("Full memory:");
				for (int j=0;j<memory.length;j++){
					System.out.println(memory[j]);
				}
				
			}
		}
	}
	

	public static void writeBack(String result, ArrayList<String> s){
		if(result==null)
			return;
		else{
			if(!s.get(3).equals("0zer0"))
				registerFile[(Integer.parseInt(s.get(3)))]=Integer.parseInt(result);
		}
	}
	

	public static void main(String[] args) throws IOException {
		init();
		run(inst);
		
		
		
	}

}
