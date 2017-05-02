package surviveOrNot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Files {

	static Scanner readFile(String args0) {
		Scanner input = null;
		try {
			input = new Scanner(new File(args0));

		} catch (FileNotFoundException e) {
			System.out.println("Input file not found");

		}

		return input;

	}

	static FileWriter writeFile(String args1) {
		
		FileWriter output = null;
		try {
			output = new FileWriter(new File(args1));
		} catch (IOException e) {
			System.out.println("Output file can not be created, please check permissions");
		}

		return output;

	}
	
	
	static void closeOutputFile(FileWriter output)
	{
		try {
			output.close();
		} catch (IOException e) {
			System.out.println("Output file can not be closed");
		}
	}

}
