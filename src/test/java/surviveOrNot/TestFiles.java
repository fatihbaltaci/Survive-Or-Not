package surviveOrNot;

import java.io.FileWriter;
import java.util.Scanner;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestFiles {
	
	@Test
	public void testReadFile()
	{
		String args0 = "";
		Scanner input = Files.readFile(args0);
		
		assertEquals("", null, input);
	}
	
	@Test
	public void testWriteFile()
	{
		String args1 = "";
		FileWriter output = Files.writeFile(args1);
		
		assertEquals("", null, output);
	}
	
	

}
