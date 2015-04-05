package comp442.test.syntactical.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
				if(child.getPath().endsWith(".code")){
					result.add(new Object[]{ child });
				}
			}
		} else {
		    throw new ExceptionInInitializerError("directory " + dirName + " does not exist or is not a directory");
		}
		
		return result;
	}
	
	private final Parser p;
	
	public CorrectCode(File f) throws FileNotFoundException{
		p = new Parser(f);
	}
	
	@Test
	public void test_parsesCorrectly() {
		try{
		p.parse();
		}catch(Throwable e){
			System.out.println(e);
		}
		assertEquals(0, p.getNErrors());
	}

}
