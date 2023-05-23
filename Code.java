import java.io.*;

public class Code {
	public static int[] registerFile = new int[31];
	public static int[] memory = new int[2048];
	public static int pc = 0;
	public final static int zero = 0;
	public static File file;

	public static void init() throws IOException {
		file = new File("assemblyInstructions.txt");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String temp = br.readLine();
		while (temp != null) {

			String instruction = assemblytoBinary(temp);
			temp = br.readLine();

		}
		br.close();
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
		String instruction;
		switch (splitted[0]) {// r--> opcode r1 r2 r3 shamt
		// i--> opcode r1 r2 imm
		// j --> opcode address;
		case "ADD":
			opcode = 0;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			reg3 = getRegNumber(splitted[3]);
			type ="RR";
			break;
		case "SUB":
			opcode = 1;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			reg3 = getRegNumber(splitted[3]);
			type= "RR";
			///instruction = ("000" + Integer.toBinaryString(opcode)) + (reg1) + (reg2) + (reg3);
			break;
		case "MULI":
			opcode = 2;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			//instruction = ("00" + Integer.toBinaryString(opcode)) + (reg1) + (reg2) + (Integer.toBinaryString(imm).substring(16));
			break;
		case "ADDI":
			opcode = 3;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
//			instruction = ("00" + Integer.toBinaryString(opcode)) + Integer.toBinaryString(reg1)
//					+ Integer.toBinaryString(reg2) + Integer.toBinaryString(imm);
			break;
		case "BNE":
			opcode = 4;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			//instruction = ("0" + Integer.toBinaryString(opcode)) + Integer.toBinaryString(reg1)
					//+ Integer.toBinaryString(reg2) + Integer.toBinaryString(reg3);
			break;
		case "ANDI":
			opcode = 5;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			//instruction = ("0" + Integer.toBinaryString(opcode));
			break;
		case "ORI":
			opcode = 6;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			//instruction = ("0" + Integer.toBinaryString(opcode));
			break;
		case "J":
			opcode = 7;
			address = Integer.parseInt(splitted[1]);
			type= "J";
			//instruction = ("0" + Integer.toBinaryString(opcode));
			break;
		case "SLL":
			opcode = 8;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			shamt = Integer.parseInt(splitted[3]);
			type= "RS";
			//instruction = (Integer.toBinaryString(opcode));
			break;
		case "SRL":
			opcode = 9;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			shamt = Integer.parseInt(splitted[3]);
			type= "RS";
			//instruction = (Integer.toBinaryString(opcode));
			break;
		case "LW":
			opcode = 10;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			//instruction = (Integer.toBinaryString(opcode));

			break;

		case "SW":
			opcode = 11;
			reg1 = getRegNumber(splitted[1]);
			reg2 = getRegNumber(splitted[2]);
			imm = Integer.parseInt(splitted[3]);
			type= "I";
			//instruction = (Integer.toBinaryString(opcode));
			break;
		}
		
		return "";

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

	public static int fetch() {
		int instruction = memory[pc];
		if (pc < 1023) {
			pc++;
		} else {
			pc = 0;
		}
		return instruction;
	}

	public static void main(String[] args) {
		int i=2;
		System.out.println((Integer.toBinaryString(2)));
	}

}
