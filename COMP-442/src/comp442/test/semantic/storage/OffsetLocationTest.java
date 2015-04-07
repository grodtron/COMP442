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

import comp442.semantic.SymbolTable;
import comp442.semantic.action.SemanticContext;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.syntactical.parser.Parser;

@RunWith(Parameterized.class)
public class OffsetLocationTest {

	@Parameters(name="{0}")
	public static Collection<Object[]> data(){
		
		List<Object[]> tests = new ArrayList<Object[]>();
		
		tests.add(new Object[]{
				"int test",
				
				"  class Z{"
				+ "  int x[4];"
				+ "};"
				+ "program {"
				+ "  int x;"
				+ "  float y;"
				+ "  Z z;"
				+ "  int q;"	
				+ "};",
				
				new Object[][]{
						new Object[]{
								new String[]{"program", "x"},
								0
						},
						new Object[]{
								new String[]{"program", "y"},
								4	
						},
						new Object[]{
								new String[]{"program", "z"},
								8
						},
						new Object[]{
								new String[]{"program", "q"},
								8 + (4*4)
						},
				}
			});
		
		return tests;
	}
	
	private Parser p;
	
	private Object[][] vals;
	
	public OffsetLocationTest(String name, String program, Object[][] vals) {
		this.p = new Parser(new ByteArrayInputStream(program.getBytes()));
		this.vals = vals;
	}
	
	@Test
	public void test(){
		p.parse();
		
		for(Object[] v : vals){
			SymbolTable scope = SemanticContext.getGlobalScope();
			SymbolTableEntry entry = null;
			for(String name : (String[])v[0]){
				entry = scope.find(name);
				scope = entry.getScope();
			}
			
			assertEquals(entry.getName(), (int)v[1], entry.getOffset());
			
		}		
	}
}
