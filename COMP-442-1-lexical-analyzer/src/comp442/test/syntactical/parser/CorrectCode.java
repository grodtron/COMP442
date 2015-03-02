package comp442.test.syntactical.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import comp442.lexical.Scanner;
import comp442.syntactical.parser.Parser;

@RunWith(Parameterized.class)
public class CorrectCode {

	@Parameters(name="{0}")
	public static Collection<Object[]> data(){
		List<Object[]> result = new ArrayList<Object[]>();
		
		String dirName = "samples/correct-code";
		
		File dir = new File(dirName);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				result.add(new Object[]{ child });
			}
		} else {
		    throw new ExceptionInInitializerError("directory " + dirName + " does not exist or is not a directory");
		}
		
		return result;
	}
	
	private final Parser p;
	
	public CorrectCode(File f) throws FileNotFoundException{
		FileInputStream input;
		input = new FileInputStream(f);
		
		p = new Parser(new Scanner(input));
	}
	
	@Test
	public void test_parsesCorrectly() {
		p.parse();
		assertEquals(0, p.getNErrors());
	}

}
