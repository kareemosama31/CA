package src;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.SwingPropertyChangeSupport;

public class writeBack {
	public static int[] registerFile;
	public static ArrayList<String> inst;
	public static int clock;
	public static String ex;

	public static void writeBack(String result, ArrayList<String> s) {
		if (clock == 0)
			return;
		if (!pipelineIsEmpty() && clock % 2 != 0) {

			if (result == null) {

				inst.remove(ex);
				++clock;
				return;
			} else {
				if (!s.get(3).equals("0zer0")) {
					System.out.println("R" + (Integer.parseInt(s.get(3)) - 1) + " changed from "
							+ registerFile[(Integer.parseInt(s.get(3)))] + " to " + result);
					registerFile[(Integer.parseInt(s.get(3)))] = Integer.parseInt(result);
				}
			}
			inst.remove(ex);

			++clock;
		}

	}

	public static boolean pipelineIsEmpty() {
		if (inst.isEmpty())
			return true;
		return false;
	}

}
