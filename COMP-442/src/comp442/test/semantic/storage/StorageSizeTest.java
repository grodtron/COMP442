package comp442.test.semantic.storage;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.syntactical.parser.Parser;

@RunWith(Parameterized.class)
public class StorageSizeTest {

	@Parameters(name="{0}")
	public static Collection<Object[]> data(){
		
		List<Object[]> tests = new ArrayList<Object[]>();
		
		tests.add(new Object[]{
				"int test",
				
				"program {"
				+ "int x;"
				+ "};",
				
				new String[]{"program", "x"},
				
				4
			});

		tests.add(new Object[]{
				"class test",
				
				"class TestClass {"
				+ "int x;"
				+ "float y;"
				+ "};"
				+ "program {"
				+ "};",
				
				new String[]{"TestClass"},
				
				8
			});

		tests.add(new Object[]{
				"class test",
				
				"class TestClass {"
				+ "int x[4];"
				+ "};"
				+ "program {"
				+ "};",
				
				new String[]{"TestClass"},
				
				4*4
			});

		tests.add(new Object[]{
				"array test",
				
				"program {"
				+ "int x[3][2][1];"
				+ "};",
				
				new String[]{"program", "x"},
				
				4*3*2*1
			});
		
		tests.add(new Object[]{
				"func test with params",
				
				"program {"
				+ "};"
				+ "int foo(int x, float arr[3][2][1]){"
				+ "};",
				
				new String[]{"foo"},
				
				8 + 8/* +8 for return addrs */
			});

		tests.add(new Object[]{
				"func test with vars",
				
				"program {"
				+ "};"
				+ "int foo(){"
				+ "int x; float arr[3][2][1];"
				+ "};",
				
				new String[]{"foo"},
				
				4 + (4*3*2*1) + 8/* plus 8 for return addrs */
			});
		
		return tests;
	}
	
	private Parser p;
	
	private String [] path;
	private int expectedSize;
	
	public StorageSizeTest(String name, String program, String[] path, Integer expected) {
		this.p = new Parser(new ByteArrayInputStream(program.getBytes()));
		this.path = path;
		this.expectedSize = expected;
	}
	
	@Test
	public void test(){
		p.parse();
		
		SymbolTable scope = SymbolContext.getCurrentScope();
		
		SymbolTableEntry entry = null;
		for(String name : path){
			entry = scope.find(name);
			scope = entry.getScope();
		}
		
		assertEquals(expectedSize, entry.getSize());
	}

}
