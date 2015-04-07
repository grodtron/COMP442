package comp442.test.semantic.symbols;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import comp442.semantic.SymbolTable;
import comp442.semantic.action.SemanticContext;
import comp442.semantic.symboltable.entries.ClassEntry;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.ParameterEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.ClassType;
import comp442.semantic.symboltable.entries.types.PrimitiveType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.syntactical.parser.Parser;
import comp442.utils.Permutations;
import comp442.utils.StringUtils;

@RunWith(Parameterized.class)
public class ParameterTest {

	private static enum Location {
		member_function,
		free_function
	}
	
	@Parameters(name="{0}")
	public static Collection<Object[]> data(){
		
		List<Object[]> values = new ArrayList<Object[]>();
		
		// name, code, expected, search-path
		for(SymbolTableEntryType type : new SymbolTableEntryType[]{
				new PrimitiveType("int"), new PrimitiveType("float"),
				new ClassType(new ClassEntry("TestClass", null))
		}){
			for(Integer nParams : new Integer[]{1,2,3}){
				for(List<SymbolTableEntryType> types : Permutations.permutations(new SymbolTableEntryType[]{
						new PrimitiveType("int"), new PrimitiveType("float"),
						new ClassType(new ClassEntry("TestClass", null))
				}, nParams)){
					for(List<Integer> dimensions : Permutations.permutations(new Integer[]{0, 1,2}, nParams)){
						for(Location location : Location.values()){
							
							FunctionEntry expectedEntry = new FunctionEntry("test", type, new SymbolTable(null));

							List<String> params = new ArrayList<String>();
							List<SymbolTableEntry> expectedEntries   = new ArrayList<SymbolTableEntry>();
							
							for(int i = 0; i < nParams; ++i){
								String param = types.get(i) + " p" + i;
								List<Integer> expectedDimension = new ArrayList<Integer>();
								for(int j = 0; j < dimensions.get(i); ++j){
									param += "[" + j + "]";
									expectedDimension.add(j);
								}
								params.add(param);
								SymbolTableEntryType t;
								if(expectedDimension.size() == 0){
									t = types.get(i);
								}else{
									t = new ArrayType(types.get(i), expectedDimension);
								}
								ParameterEntry p = new ParameterEntry("p"+i, t);
								expectedEntries.add(p);
								expectedEntry.addParameter(p);
							}

														
							String paramString = StringUtils.join(params, ", ");
							
							String code 
									= "class TestClass{"
											+ type + " test("
												+ (location == Location.member_function ? paramString : "")
											+ "){"
											+ "};"
									+ "};"
									+ "program {"
									+ "};"
									+ type + " test("
										+ (location == Location.free_function ? paramString : "")
									+ "){"
									+ "};";
							
														
							String[] searchPath;
							switch(location){
							case member_function:
								searchPath = new String[]{"TestClass", "test"};
								break;
							case free_function:
								searchPath = new String[]{"test"};
								break;
							default:
								searchPath = new String[]{"default"};
								break;
							
							}
							
							values.add(new Object[]{location + " " + expectedEntry.toString(), code, expectedEntry, expectedEntries, searchPath});
						}
					}
				}
			}
		}
		return values;
	}
	
	String code;
	SymbolTableEntry expected;
	List<SymbolTableEntry> expectedEntries;
	String searchPath[];
	public ParameterTest(String name, String code, FunctionEntry expected, List<SymbolTableEntry> expectedEntries, String[] searchPath) {
		this.code       = code;
		this.expected   = expected;
		this.expectedEntries = expectedEntries;
		this.searchPath = searchPath;
	}
	
	@org.junit.Test
	public void test() throws Exception {
		Parser p = new Parser(new ByteArrayInputStream(code.getBytes()));
		
		p.parse();

		
		SymbolTable scope = SemanticContext.getCurrentScope();
		
		SymbolTableEntry entry = null;
		for(String name : searchPath){
			entry = scope.find(name);
			scope = entry.getScope();
		}
		
		assertEquals(expected, entry);
		for(SymbolTableEntry e : expectedEntries){
			SymbolTableEntry param = scope.find(e.getName());
			assertEquals(e, param);
		}
	}
}
